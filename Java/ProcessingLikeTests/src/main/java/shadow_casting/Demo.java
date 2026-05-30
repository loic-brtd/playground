package shadow_casting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.PApplet;

public class Demo extends PApplet {

  List<Segment> segments = new ArrayList<>();
  ShadowCasting shadowCasting;

  public void setup() {
    size(630, 360);

    List<Polygon> polygons = Arrays.asList(
      new Polygon(0, 0, width, height), // Border
      new Polygon(new Point(100, 150), new Point(120, 50), new Point(200, 80), new Point(140, 210)),
      new Polygon(new Point(100, 200), new Point(120, 250), new Point(60, 300)),
      new Polygon(new Point(200, 260), new Point(220, 150), new Point(300, 200), new Point(350, 320)),
      new Polygon(new Point(340, 60), new Point(360, 40), new Point(370, 70)),
      new Polygon(new Point(450, 190), new Point(560, 170), new Point(540, 270), new Point(430, 290)),
      new Polygon(new Point(400, 95), new Point(580, 50), new Point(480, 150)));

    for (Polygon p : polygons) {
      segments.addAll(Arrays.asList(p.segments));
    }

    shadowCasting = new ShadowCasting(segments);
  }

  public void draw() {
    background(0);

    Point source = new Point(mouseX, mouseY);
    List<Point> vertices = shadowCasting.getVertices(source);
    // Draw segments
    strokeWeight(1);
    stroke(51);
    for (Segment s : segments) {
      line(s.a.x, s.a.y, s.b.x, s.b.y);
    }

    // Draw light as a polygon
    fill(255);
    noStroke();
    beginShape();
    for (Point p : vertices) {
      vertex(p.x, p.y);
    }
    endShape(CLOSE);

    // // Draw debug lines
    // for (Point p : vertices) {
    // stroke(200);
    // strokeWeight(1);
    // line(source.x, source.y, p.x, p.y);
    // strokeWeight(5);
    // stroke(255, 0, 0);
    // point(p.x, p.y);
    // }

    // // Draw source point
    // stroke(0);
    // strokeWeight(5);
    // point(source.x, source.y);

    // Draw shape in construction
    if (!currentShape.isEmpty()) {
      stroke(0);
      fill(51, 100);
      strokeWeight(1);
      beginShape();
      for (Point p : currentShape) {
        vertex(p.x, p.y);
      }
      endShape(CLOSE);
    }

  }

  List<Point> currentShape = new ArrayList<>();

  public void mousePressed() {
    // Add point
    currentShape.add(new Point(mouseX, mouseY));
  }

  public void keyPressed() {
    if (key == ' ' && currentShape.size() > 2) {
      // Close shape
      Polygon p = new Polygon(currentShape);
      segments.addAll(Arrays.asList(p.segments));
      currentShape.clear();
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
