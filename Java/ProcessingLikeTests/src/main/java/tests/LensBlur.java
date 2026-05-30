package tests;

import java.util.ArrayList;

import core.PApplet;
import core.PImage;
import core.PVector;

public class LensBlur extends PApplet {

  PImage img;
  int radius = 10;
  int step = 3;
  int alpha = radius * step * 2;
  ArrayList<PVector> positions;

  @Override
  protected void setup() {
    img = loadImage("res/new_york.jpg");
    size(img.width, img.height);
    noLoop();

    positions = new ArrayList<>();
    for (int x = 0; x < img.width; x += step) {
      for (int y = 0; y < img.height; y += step) {
        positions.add(new PVector(x, y));
      }
    }
  }

  @Override
  protected void draw() {
    // image(img, 0, 0);

    while (!positions.isEmpty()) {
      PVector p = random(positions);
      int col = img.get((int) p.x, (int) p.y);
      fill(col, alpha);
      noStroke();
      ellipse(p.x, p.y, radius, radius);
      positions.remove(p);
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
