package tests;

import core.PApplet;
import core.PImage;

public class GainImage extends PApplet {

  @Override
  protected void setup() {
    // PImage img = loadImage("res/kitten.jpg");
    PImage img = loadImage("res/kitten.jpg");
    img.resize(500, 0);
//    img.grayScale(); // TODO:

    size(img.width * 2, img.height);
    image(img, 0, 0);
    image(gain(img, 0.5f), img.width, 0);
  }

  public PImage gain(PImage img, float scalar) {
    PImage res = img.copy();
    res.loadPixels();
    for (int i = 0; i < res.pixels.length; i++) {
      int ng = red(res.pixels[i]);
      ng = floor(constrain(ng * scalar, 0, 255));
      res.pixels[i] = color(ng);
    }
    res.updatePixels();
    return res;
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
