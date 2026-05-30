package tests;

import java.util.ArrayList;
import java.util.List;

import core.PApplet;

public class LagrangeMouse extends PApplet {

  List<Point> points;

  public void setup() {
    size(800, 600);
    points = new ArrayList<Point>();
    replacePoints(5);
  }

  public void draw() {
    background(0);
    noFill();
    strokeWeight(2);
    stroke(255);
    translate(0, height / 2);
    scale(1, -1);
    beginShape();
    for (int x = 0; x <= width; x += 2) {
      float y = lagrange(x, points);
      vertex(x, y);
    }
    endShape();

    for (Point p : points) {
      p.show();
    }
  }

  public void replacePoints(float n) {
    int old = points.size();
    if (n > 0) {
      if (n > old) {
        for (int i = 0; i < (n - old); i++) {
          points.add(new Point(0, 0));
        }
      } else if (n < old) {
        for (int i = 0; i < (old - n); i++) {
          points.remove(points.size() - 1);
        }
      }
      for (int i = 0; i < points.size(); i++) {
        points.get(i).x = i / n * width + width / (2 * n);
      }
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
    } else if (key == '-') {
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
      float d = dist(x, y, this.x, this.y);
      return d < touchD / 2;
    }

    public void show() {
      stroke(255);
      strokeWeight(d);
      point(x, y);
      stroke(255, selected == this ? 100 : 20);
      strokeWeight(touchD);
      point(x, y);
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
