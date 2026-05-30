package tests;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class LagrangeBasic extends DrawLoop {

  List<Point> points;

  @Override
  public void setup() {
    size(800, 600);
    points = new ArrayList<Point>();
    replacePoints(5);
  }

  @Override
  public void draw() {
    // Background
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, width, height);
    g.translate(0, height / 2);
    g.scale(1, -1);
    g.setStroke(new BasicStroke(2));
    g.setColor(Color.WHITE);
    Path2D path = new Path2D.Float();
    for (int x = 0; x <= width; x += 2) {
      float y = lagrange(x, points);
      if (x == 0) {
        path.moveTo(x, y);
      } else {
        path.lineTo(x, y);
      }
    }
    g.draw(path);

    for (Point p : points) {
      p.show();
    }
  }

  public float lagrange(float x, List<Point> pts) {
    float sum = 0;
    for (int i = 0; i < pts.size(); i++) {
      float prod = 1;
      for (int j = 0; j < pts.size(); j++) {
        if (i != j) {
          prod *= (x - pts.get(j).x) / (pts.get(i).x - pts.get(j).x);
        }
      }
      sum += pts.get(i).y * prod;
    }
    return sum;
  }

  public void replacePoints(float n) {
    points.clear();
    for (int i = 0; i < n; i++) {
      points.add(new Point(i / n * width + width / (2 * n), 0));
    }
  }

  Point selected;

  public void mousePressed() {
    for (Point p : points) {
      if (p.touched(mouseX, -(mouseY - height / 2))) {
        selected = p;
        break;
      }
    }
  }

  public void mouseReleased() {
    selected = null;
  }

  public void mouseDragged() {
    if (selected != null) {
      selected.set(mouseX, -(mouseY - height / 2));
    }
  }

  public void keyPressed() {
    if (key == '+') {
      replacePoints(points.size() + 1);
    } else if (key == '-' && points.size() > 1) {
      replacePoints(points.size() - 1);
    }
  }

  class Point {
    float x, y, d, touchD;

    public Point(float x, float y) {
      this.x = x;
      this.y = y;
      d = 10;
      touchD = 30;
    }

    public void set(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public boolean touched(int x, int y) {
      double d = Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
      return d < touchD / 2;
    }

    public void show() {
      g.setColor(Color.WHITE);
      g.fill(new Ellipse2D.Float(x - d / 2, y - d / 2, d, d));
      if (selected == this) {
        g.setColor(new Color(255, 255, 255, 100));
      } else {
        g.setColor(new Color(255, 255, 255, 20));

      }
      g.fill(new Ellipse2D.Float(x - touchD / 2, y - touchD / 2, touchD, touchD));
    }

  }

  public static void main(String[] args) {
    new LagrangeBasic().run();
  }

}
