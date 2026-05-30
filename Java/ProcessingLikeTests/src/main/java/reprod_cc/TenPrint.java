package reprod_cc;

import core.PApplet;

public class TenPrint extends PApplet {

  int scl = 30;
  int x = 0;
  int y = 0;

  @Override
  protected void setup() {
    size(600, 600);
    background(0);
  }

  @Override
  protected void draw() {
    printNext();
  }

  @Override
  protected void keyPressed() {
    background(0);
    x = y = 0;
  }

  void printNext() {
    stroke(255);
    if (random(1) < .5) {
      line(x, y, x + scl, y + scl);
    } else {
      line(x, y + scl, x + scl, y);
    }
    x += scl;
    if (x >= width) {
      y += scl;
      x = 0;
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
