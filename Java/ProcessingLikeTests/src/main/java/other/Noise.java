package other;

import core.PApplet;

public class Noise extends PApplet {

  public void setup() {
    size(600, 600);
  }

  public void draw() {
    background(0);
    for (int i = 0; i < width; i++) {
      float n = noise(i * 0.01f) * 255;
      println(n);
      noStroke();
      fill(n);
      rect(i, 0, 1, height);
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
