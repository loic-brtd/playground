package tests;

import core.PApplet;
import core.PVector;
import qtree.Point;
import qtree.QuadTree;
import qtree.Rectangle;

public class ConnectedPoints extends PApplet {

  Particle[] particles = new Particle[100];
  float threshold = 100;
  QuadTree<Particle> qtree;

  @Override
  public void setup() {
    fullScreen();
    qtree = new QuadTree<>(new Rectangle(0, 0, width, height), 3);
    for (int i = 0; i < particles.length; i++) {
      particles[i] = new Particle();
    }
  }

  @Override
  public void draw() {
    background(0);

    for (int i = 0; i < particles.length; i++) {
      Particle p = particles[i];
      p.update();
      p.show();
      qtree.insert(new Point<ConnectedPoints.Particle>(p.pos.x, p.pos.y, p));

      // for (int i = 0; i < particles.length; i++) {
      // Particle p = particles[i];
      //
      // float thr2 = threshold / 2;
      // List<Point<Particle>> around = qtree.query(
      // // new Circle(p.pos.x, p.pos.y, threshold)
      // new Rectangle(p.pos.x - thr2, p.pos.y - thr2, threshold, threshold));
      // for (Point<Particle> otherPoint : around) {
      // Particle other = otherPoint.data;
      // float d = dist(p.pos.x, p.pos.y, other.pos.x, other.pos.y);
      // float alpha = map(d, 0, threshold, 255, 0);
      // stroke(255, alpha);
      // strokeWeight(3);
      // line(p.pos.x, p.pos.y, other.pos.x, other.pos.y);
      // }
      // }

      for (int j = i + 1; j < particles.length; j++) {
        Particle other = particles[j];
        float d = dist(p.pos.x, p.pos.y, other.pos.x, other.pos.y);
        if (d < threshold) {
          float alpha = map(d, 0, threshold, 255, 0);
          stroke(255, alpha);
          strokeWeight(3);
          line(p.pos.x, p.pos.y, other.pos.x, other.pos.y);
        }
      }
    }

    // qtree.clear();

  }

  class Particle {
    float r;
    PVector pos;
    PVector vel;

    public Particle() {
      r = 8;
      pos = new PVector(random(width), random(height));
      vel = PVector.random2D().mult(0.5f);
    }

    void update() {
      pos.add(vel);
      if (isOffscreen()) {
        pos.set(random(width), random(height));
      }
    }

    boolean isOffscreen() {
      return pos.x + r < 0 || pos.x - r > width || pos.y + r < 0 || pos.y - r > height;
    }

    void show() {
      fill(255);
      noStroke();
      ellipse(pos.x, pos.y, r, r);
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
