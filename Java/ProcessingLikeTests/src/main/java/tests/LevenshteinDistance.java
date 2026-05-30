package tests;

import components.Html;
import components.Input;
import core.PApplet;

public class LevenshteinDistance extends PApplet {

  Input inputA, inputB;
  Html label;

  @Override
  protected void setup() {
    size(300, 200);
    background(0);
    inputA = createInput();
    inputB = createInput();
    breakLine();
    label = createHtml().color(255);
    inputA.keyPressed(() -> update());
    inputB.keyPressed(() -> update());
    noLoop();
  }

  public void update() {
    int d = lev(inputA.value(), inputB.value());
    label.html(d);
  }

  public int lev(String a, String b) {
    return lev(a, b, a.length(), b.length());
  }

  public int lev(String a, String b, int i, int j) {
    if (min(i, j) == 0) {
      return max(i, j);
    } else {
      return min(lev(a, b, i - 1, j) + 1,
        min(lev(a, b, i, j - 1) + 1,
          lev(a, b, i - 1, j - 1) + indicator(a, b, i, j)));
    }
  }

  public int indicator(String a, String b, int i, int j) {
    return a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
