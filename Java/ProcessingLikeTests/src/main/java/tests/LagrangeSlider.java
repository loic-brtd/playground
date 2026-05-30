package tests;

import components.Slider;
import core.PApplet;

public class LagrangeSlider extends PApplet {

  Slider s0, s1, s2;
  Polynome p;
  Point[] points;

  public void setup() {
    size(600, 600);
    points = new Point[] { new Point(100, -20), new Point(width / 2, 50), new Point(width - 100, -100) };
    s0 = createSlider(-300, 300, -20).vertical();
    s1 = createSlider(-300, 300, 50).vertical();
    s2 = createSlider(-300, 300, -100).vertical();
  }

  public void draw() {
    background(0);
    noFill();
    points[0].y = s0.value();
    points[1].y = s1.value();
    points[2].y = s2.value();
    strokeWeight(2);
    stroke(255);
    translate(0, height / 2);
    scale(1, -1);
    beginShape();
    for (int x = 0; x < width; x += 3) {
      float y = lagrange(x, points);
      vertex(x, y);
    }
    endShape();

    for (Point p : points) {
      p.show();
    }
  }

  public float lagrange(float x, Point[] pts) {
    float sum = 0;
    for (int i = 0; i < pts.length; i++) {
      float prod = 1;
      for (int j = 0; j < pts.length; j++) {
        if (i != j) {
          prod *= (x - pts[j].x) / (pts[i].x - pts[j].x);
        }
      }
      sum += pts[i].y * prod;
    }
    return sum;
  }

  class Point {
    float x, y, d;

    public Point(float x, float y) {
      this.x = x;
      this.y = y;
      d = 10;
    }

    public void set(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public void show() {
      stroke(255);
      strokeWeight(d);
      point(x, y);
    }

    @Override
    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  class Polynome {
    float[] coeffs;

    public Polynome(float... coeffs) {
      this.coeffs = coeffs;
    }

    public float eval(float x) {
      float res = 0;
      for (int i = 0; i < coeffs.length; i++) {
        res += coeffs[i] * pow(x, i);
      }
      return res;
    }

    public Polynome add(Polynome other) {
      float[] res = new float[max(other.coeffs.length, coeffs.length)];
      for (int i = 0; i < res.length; i++) {
        if (i < coeffs.length) {
          res[i] = coeffs[i];
        }
        if (i < other.coeffs.length) {
          res[i] += other.coeffs[i];
        }
      }
      return new Polynome(res);
    }

    public Polynome sub(Polynome other) {
      float[] res = new float[max(other.coeffs.length, coeffs.length)];
      for (int i = 0; i < res.length; i++) {
        if (i < coeffs.length) {
          res[i] = coeffs[i];
        }
        if (i < other.coeffs.length) {
          res[i] -= other.coeffs[i];
        }
      }
      return new Polynome(res);
    }

    public Polynome mul(float value) {
      float[] res = new float[coeffs.length];
      for (int i = 0; i < res.length; i++) {
        res[i] = coeffs[i] * value;
      }
      return new Polynome(res);
    }

    public Polynome div(float value) {
      float[] res = new float[coeffs.length];
      for (int i = 0; i < res.length; i++) {
        res[i] = coeffs[i] / value;
      }
      return new Polynome(res);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < coeffs.length; i++) {
        String sign = i != 0 && coeffs[i] > 0 ? "+ " : "";
        String coeff = coeffs[i] != 0 ? fmt(coeffs[i]) : "";
        String x = i != 0 ? "x" : "";
        String pow = i > 1 ? "^" + i : "";
        String end = i != coeffs.length - 1 ? " " : "";
        sb.append(sign + coeff + x + pow + end);
      }
      return sb.toString();
    }
  }

  public static String fmt(double d) {
    if (d == (long) d)
      return String.format("%d", (long) d);
    else
      return String.format("%s", d);
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
