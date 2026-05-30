package tests;

import core.PApplet;

public class TimesTable extends PApplet {

  float r, n, k;

  @Override
  protected void setup() {
    fullScreen();
    textSize(15);
    textAlign(CENTER, CENTER);
 
    r = min(width, height) * 0.4f;
    n = 0;
    k = 2;
  }

  @Override
  protected void draw() {
    background(0);
    translate(width / 2, height / 2);
    rotate(-HALF_PI);

    stroke(255);
    noFill();
    ellipse(0, 0, r * 2, r * 2);

    for (int i = 0; i < n; i++) {
      float p = i * k;

      float a1 = map(i, 0, n, 0, TWO_PI) + PI;
      float x1 = r * cos(a1);
      float y1 = r * sin(a1);

      float a2 = map(p, 0, n, 0, TWO_PI) + PI;
      float x2 = r * cos(a2);
      float y2 = r * sin(a2);

      line(x1, y1, x2, y2);
      
      // float x3 = r * 1.1f * cos(a2);
      // float y3 = r * 1.1f * sin(a2);
      //
      // text(i, x3, y3);
    }
    
    n += 0.12;
    
    if (frameCount == 2) {
      sleep(4000);
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
