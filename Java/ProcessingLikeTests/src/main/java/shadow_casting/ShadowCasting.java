package shadow_casting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShadowCasting {

  private List<Segment> segments;

  public ShadowCasting(List<Segment> segments) {
    this.segments = new ArrayList<>(segments);
  }

  public void setSegments(List<Segment> segments) {
    this.segments = new ArrayList<>(segments);
  }

  public List<Point> getVertices(Point source) {
    Set<Point> uniquePoints = new HashSet<>();
    List<Float> uniqueAngles = new ArrayList<>();

    // Get all unique points
    for (Segment seg : segments) {
      uniquePoints.add(seg.a);
      uniquePoints.add(seg.b);
    }

    // Get all unique angles
    for (Point uniquePoint : uniquePoints) {
      float angle = (float) Math.atan2(uniquePoint.y - source.y, uniquePoint.x - source.x);
      float offset = 0.0001f;
      uniqueAngles.add(angle - offset);
      uniqueAngles.add(angle);
      uniqueAngles.add(angle + offset);
    }

    // RAYS IN ALL DIRECTIONS
    List<Intersection> intersects = new ArrayList<>();

    for (Float angle : uniqueAngles) {
      // Calculate dx & dy from angle
      float dx = (float) Math.cos(angle);
      float dy = (float) Math.sin(angle);

      // Ray from center of screen to mouse
      Segment ray = new Segment(source.x, source.y, source.x + dx, source.y + dy);

      // Find CLOSEST intersection
      Intersection closestIntersect = null;
      for (Segment seg : segments) {
        Intersection intersect = getIntersection(ray, seg);
        if (intersect == null)
          continue;
        if (closestIntersect == null || intersect.param < closestIntersect.param) {
          closestIntersect = intersect;
        }
      }

      // Intersect angle
      if (closestIntersect == null)
        continue;
      closestIntersect.angle = angle;

      // Add to list of intersects
      intersects.add(closestIntersect);
    }

    // Sort intersects by angle
    intersects.sort((a, b) -> a.angle == b.angle ? 0 : a.angle < b.angle ? -1 : 1);

    return intersects.stream()
      .map(i -> new Point(i.x, i.y))
      .collect(Collectors.toList());
  }

  // Find intersection of RAY & SEGMENT
  private Intersection getIntersection(Segment ray, Segment segment) {

    // RAY in parametric: Point + Delta*T1
    float r_px = ray.a.x;
    float r_py = ray.a.y;
    float r_dx = ray.b.x - ray.a.x;
    float r_dy = ray.b.y - ray.a.y;

    // SEGMENT in parametric: Point + Delta*T2
    float s_px = segment.a.x;
    float s_py = segment.a.y;
    float s_dx = segment.b.x - segment.a.x;
    float s_dy = segment.b.y - segment.a.y;

    // Are they parallel? If so, no intersect
    float r_mag = (float) Math.sqrt(r_dx * r_dx + r_dy * r_dy);
    float s_mag = (float) Math.sqrt(s_dx * s_dx + s_dy * s_dy);
    if (r_dx / r_mag == s_dx / s_mag && r_dy / r_mag == s_dy / s_mag) {
      // Unit vectors are the same.
      return null;
    }

    // SOLVE FOR T1 & T2
    // r_px+r_dx*T1 = s_px+s_dx*T2 && r_py+r_dy*T1 = s_py+s_dy*T2
    // ==> T1 = (s_px+s_dx*T2-r_px)/r_dx = (s_py+s_dy*T2-r_py)/r_dy
    // ==> s_px*r_dy + s_dx*T2*r_dy - r_px*r_dy = s_py*r_dx + s_dy*T2*r_dx -
    // r_py*r_dx
    // ==> T2 = (r_dx*(s_py-r_py) + r_dy*(r_px-s_px))/(s_dx*r_dy - s_dy*r_dx)
    float T2 = (r_dx * (s_py - r_py) + r_dy * (r_px - s_px)) / (s_dx * r_dy - s_dy * r_dx);
    float T1 = (s_px + s_dx * T2 - r_px) / r_dx;

    // Must be within parametic whatevers for RAY/SEGMENT
    if (T1 < 0 || T2 < 0 || T2 > 1)
      return null;

    // Return the POINT OF INTERSECTION
    return new Intersection(r_px + r_dx * T1, r_py + r_dy * T1, T1);
  }

}
