package tests;

import core.PApplet;
import core.PGraphics;

public class TwoProjections extends PApplet {

  PGraphics g1;
  PGraphics g2;
  float a = 0;

  @Override
  protected void setup() {
    size(300, 600);
    g1 = createGraphics(width, height / 2, P3D);
    g2 = createGraphics(width, height / 2, P3D);
    g2.ortho();
  }

  @Override
  protected void draw() {
    background(0);
    
    g1.beginDraw();
    g1.clear();
    g1.noFill();
    g1.stroke(255);
    g1.strokeWeight(3);
    g1.rotateX(a);
    g1.rotateY(a);
    g1.rotateZ(a);
    g1.box(width / 2);
    g1.endDraw();

    g2.beginDraw();
    g2.clear();
    g2.noFill();
    g2.stroke(255);
    g2.strokeWeight(3);
    g2.rotateX(a);
    g2.rotateY(a);
    g2.rotateZ(a);
    g2.box(width / 2);
    g2.endDraw();

    image(g1, 0, 0);
    image(g2, 0, height / 2);

    a += 0.01;
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
