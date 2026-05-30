package tests;

import core.PApplet;

public class Test3D extends PApplet {

  float angle = 1.25f;
  float s = 100;

  @Override
  protected void setup() {
    size(800, 800, P3D);
    // ortho();
  }

  @Override
  protected void draw() {
    background(0);
    strokeWeight(5);
    
    translate(0, 0, mouseX);
    rotateX(angle);
    rotateY(angle * 0.50f);
    rotateZ(angle * 0.20f);

    stroke(255);
    // box(200);
 
    translate(200, 0);
    box(200);
    translate(-400, 0);
    box(200);
    translate(200, 200);
    box(200);
    translate(0, -400);
    box(200);
    translate(0, 200, -200);
    box(200);
    translate(0, 0, 400);
    box(200);

    // point(-s, -s, -s);
    // point(-s, s, -s);
    // point(s, -s, -s);
    // point(s, s, -s);
    //
    // point(-s, -s, s);
    // point(-s, s, s);
    // point(s, -s, s);
    // point(s, s, s);
    //
    // line(-s, -s, -s, s, -s, -s);
    // line(-s, -s, -s, -s, s, -s);
    // line(-s, -s, -s, -s, -s, s);
    //
    // line(s, s, s, -s, s, s);
    // line(s, s, s, s, -s, s);
    // line(s, s, s, s, s, -s);
    //
    // line(s, -s, -s, s, s, -s);
    // line(s, -s, -s, s, -s, s);
    // line(-s, -s, s, s, -s, s);
    // line(-s, -s, s, -s, s, s);
    // line(-s, s, -s, s, s, -s);
    // line(-s, s, -s, -s, s, s);

    angle += 0.02;
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
