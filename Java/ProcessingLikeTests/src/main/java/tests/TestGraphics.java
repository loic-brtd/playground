package tests;

import core.PApplet;
import core.PGraphics;

public class TestGraphics extends PApplet {

  PGraphics g;
  float angle;

  @Override
  protected void setup() {
    size(600, 400);
    angle = 0;
    g = createGraphics(500, 300, P3D);
  }

  @Override
  protected void draw() {
    background(0, 130, 200);

    g.push();
    g.clear();
    g.translate(g.width / 2, g.height / 2);
    g.rotateX(angle);
    g.rotateY(angle);
    g.rotateZ(angle);
    g.noFill();
    g.stroke(0);
    g.strokeWeight(3);
    g.box(100);
    g.pop();

    image(g, 0, 0);
    angle += 0.03;
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}