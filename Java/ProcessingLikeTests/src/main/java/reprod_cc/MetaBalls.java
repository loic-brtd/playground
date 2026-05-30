package reprod_cc;

import core.PApplet;
import core.PVector;

public class MetaBalls extends PApplet {

  Blob[] blobs = new Blob[5];

  @Override
  protected void setup() {
    size(500, 300);
    colorMode(HSB);
    for (int i = 0; i < blobs.length; i++) {
      blobs[i] = new Blob(random(width), random(height), random(10, 50));
    }
  }

  @Override
  protected void draw() {
    background(255);

    for (Blob b : blobs) {
      b.move();
    }

    loadPixels();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        float sum = 0;
        for (Blob b : blobs) {
          float d = dist(i, j, b.pos.x, b.pos.y);
          sum += b.r * 800 / d;
        }
        pixels[i + width * j] = color(sum % 255, 255, 255);
      }
    }
    updatePixels();
  }

  public static void main(String[] args) {
    PApplet.run();
  }

  class Blob {

    PVector pos, vel;
    float r;

    Blob(float x, float y, float r) {
      this.pos = createVector(x, y);
      this.vel = PVector.random2D().mult(5);
      this.r = r;
    }

    void show() {
      noFill();
      strokeWeight(4);
      stroke(0);
      ellipse(pos.x, pos.y, r * 2, r * 2);
    }

    void move() {
      pos.add(vel);
      if (pos.x > width || pos.x < 0) {
        vel.x *= -1;
      }
      if (pos.y > height || pos.y < 0) {
        vel.y *= -1;
      }
    }

  }

}
