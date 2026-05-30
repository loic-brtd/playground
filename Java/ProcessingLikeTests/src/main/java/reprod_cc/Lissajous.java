package reprod_cc;

import java.util.ArrayList;
import java.util.List;

import core.PApplet;
import core.PVector;

public class Lissajous extends PApplet {

  float pointWidth = 8;
  float lineWidth = 1;

  float angle;
  int side;
  int cols;
  int rows;
  Curve[][] curves;

  @Override
  protected void setup() {
    fullScreen();
    angle = -HALF_PI;
    side = 120;
    cols = width / side - 1;
    rows = height / side - 1;
    curves = new Curve[rows][cols];
    for (int j = 0; j < rows; j++) {
      for (int i = 0; i < cols; i++) {
        curves[j][i] = new Curve();
      }
    }
  }

  @Override
  protected void draw() {
    background(0);
    noFill();
    float offset = side / 2;
    float diameter = side * 0.7f;
    float radius = diameter / 2;

    for (int j = 0; j < rows; j++) {
      stroke(255);
      strokeWeight(lineWidth);
      circle(offset, offset + side * (j + 1), diameter);
      float x = offset + radius * cos(angle * (j + 1));
      float y = offset + side * (j + 1) + radius * sin(angle * (j + 1));
      strokeWeight(pointWidth);
      point(x, y);
      for (int i = 0; i < cols; i++) {
        curves[j][i].setY(y);
      }
      strokeWeight(lineWidth);
      stroke(255, 80);
      line(0, y, width, y);
    }
    for (int i = 0; i < cols; i++) {
      stroke(255);
      strokeWeight(lineWidth);
      circle(offset + side * (i + 1), offset, diameter);
      float x = offset + side * (i + 1) + radius * cos(angle * (i + 1));
      float y = offset + radius * sin(angle * (i + 1));
      strokeWeight(pointWidth);
      point(x, y);
      for (int j = 0; j < rows; j++) {
        curves[j][i].setX(x);
        curves[j][i].saveCurrent();
        curves[j][i].show();
      }
      strokeWeight(lineWidth);
      stroke(255, 80);
      line(x, 0, x, height);
    }

    angle -= 0.02;
    if (angle < -(TWO_PI + HALF_PI)) {
      for (int j = 0; j < rows; j++) {
        for (int i = 0; i < cols; i++) {
          curves[j][i].reset();
        }
      }
      angle = -HALF_PI;
    }
  }

  class Curve {

    List<PVector> points;
    PVector current;

    public Curve() {
      points = new ArrayList<>();
      current = createVector();
    }

    void setX(float x) {
      current.x = x;
    }

    void setY(float y) {
      current.y = y;
    }

    void addPoint(float x, float y) {
      points.add(createVector(x, y));
    }

    void saveCurrent() {
      points.add(current);
      current = createVector();
    }

    void show() {
      stroke(255);
      strokeWeight(lineWidth);
      noFill();
      beginShape();
      for (PVector p : points) {
        vertex(p.x, p.y);
      }
      endShape();
      if (!points.isEmpty()) {
        strokeWeight(pointWidth);
        PVector last = points.get(points.size() - 1);
        point(last.x, last.y);
      }
    }

    void reset() {
      points.clear();
      current = createVector();
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
