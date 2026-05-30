package reprod_cc;

import core.PApplet;

public class MandelbrotSet extends PApplet {

  float minval = -2;
  float maxval = 2;
  float step = 0.01f;
  int px, py;

  float ca = 0;
  float cb = 0;
  float angle;

  public void setup() {
    size(500, 500);
    colorMode(HSB);
    px = mouseX;
    py = mouseY;
  }

  public void draw() {
    ca = sin(angle);
    cb = cos(angle);
    angle += 0.05f;
    int maxiterations = 100;

    loadPixels();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        float a = map(x, 0, width, minval, maxval);
        float b = map(y, 0, height, minval, maxval);

        // float ca = a;
        // float cb = b;

        int n = 0;

        while (n < maxiterations) {
          float aa = a * a;
          float bb = b * b;
          float twoab = 2 * a * b;
          a = aa - bb + ca;
          b = twoab + cb;
          if (a * a + b * b > 16) {
            break;
          }
          n++;
        }

        float bright = map(n, 0, maxiterations, 0, 1);
        bright = map(sqrt(bright), 0, 1, 0, 255);

        if (n == maxiterations) {
          bright = 0;
        }

        int pix = x + y * width;
        pixels[pix] = color(bright, 255, 255, 255);
      }
    }
    updatePixels();

    textAlign(CENTER, BOTTOM);
    fill(255, 255, 255);
    textSize(24);
    text(step, width / 2, height - 10);
  }

  @Override
  protected void mouseDragged() {
    minval -= (mouseX - px) * step;
    maxval -= (mouseY - py) * step;
    px = mouseX;
    py = mouseY;
  }

  @Override
  protected void mousePressed() {
    px = mouseX;
    py = mouseY;
  }

  @Override
  protected void keyPressed() {
    if (keyCode == ARROW_DOWN) {
      step /= 2f;
    } else if (keyCode == ARROW_UP) {
      step *= 2f;
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
