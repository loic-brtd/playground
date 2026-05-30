package reprod_cc;

import core.PApplet;
import core.PVector;

public class ChaosGame extends PApplet {

  PVector[] seeds;
  PVector current;
  PVector target;
  int n = 5;
  float step = 0.5f;

  public void reset() {
    background(0);
    stroke(255);
    strokeWeight(1.2f);
    seeds = new PVector[n];
    current = createVector(random(width), random(height));
    for (int i = 0; i < n; i++) {
      float angle = i * TWO_PI / n;
      seeds[i] = PVector
        .fromAngle(angle)
        .mult(height)
        .add(width / 2, height / 2);
      point(seeds[i].x, seeds[i].y);
    }
  }

  public void setup() {
    fullScreen();
    reset();
  }

  public void draw() {
    stroke(255, 0, 255, 130);
    for (int i = 0; i < 2000; i++) {
      point(current.x, current.y);
      PVector next = null;
      do {
        next = random(seeds);
      } while (next == target);
      target = next;
      current.x = lerp(current.x, target.x, step);
      current.y = lerp(current.y, target.y, step);
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
