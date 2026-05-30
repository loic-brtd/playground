package reprod_cc;

import core.PApplet;

public class BeesAndBombs extends PApplet {

  int n, scl;
  int offset;

  @Override
  protected void setup() {
    size(700, 700, P3D);
    ortho();
    n = 12;
    scl = width / n;
    offset = 0;
  }

  @Override
  protected void draw() {
    background(0);
    stroke(255);
    strokeWeight(2);
    noFill();
    translate(125, height / 2);
    scale(.5f);
    rotateX(PI / 5);
    rotateY(PI / 4);
    for (int x = 0; x < n; x++) {
      for (int z = 0; z < n; z++) {
        offset += 1;
        push();
        translate(x * scl, 0, z * scl);
        float d = dist(x, z, n / 2, n / 2);
        float h = map(sin(d + offset), -1, 1, 100, 800);
        box(scl, h, scl);
        pop();
      }
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
