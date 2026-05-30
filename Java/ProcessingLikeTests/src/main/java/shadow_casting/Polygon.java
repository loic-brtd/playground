package shadow_casting;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
  Point[] points;
  Segment[] segments;

  Polygon(float x, float y, float w, float h) {
    points = new Point[] {
      new Point(x, y),
      new Point(x + w, y),
      new Point(x + w, y + h),
      new Point(x, y + h)
    };
    segments = new Segment[] {
      new Segment(points[0], points[1]),
      new Segment(points[1], points[2]),
      new Segment(points[2], points[3]),
      new Segment(points[3], points[0])
    };
  }

  Polygon(Point... vectors) {
    points = vectors;
    List<Segment> l = new ArrayList<>();
    for (int i = 0; i < points.length; i++) {
      Point a = points[i];
      Point b = i + 1 < points.length ? points[i + 1] : points[0];
      l.add(new Segment(a, b));
    }
    segments = (Segment[]) l.toArray(new Segment[l.size()]);
  }

  Polygon(List<Point> list) {
    this((Point[]) list.toArray(new Point[list.size()]));
  }

}