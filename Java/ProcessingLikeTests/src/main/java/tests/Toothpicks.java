package tests;

import java.util.ArrayList;
import java.util.List;

import core.PApplet;

public class Toothpicks extends PApplet {

  List<Pick> picks = new ArrayList<>();

  @Override
  protected void setup() {
    size(800, 500);
    noLoop();
    background(0);
    picks.add(new Pick(0, 0, true));
  }

  @Override
  protected void mousePressed() {
    redraw();
  }

  @Override
  protected void draw() {
    translate(width / 2, height / 2);

    List<Pick> next = new ArrayList<>();
    for (Pick p : picks) {
      next.addAll(p.getNewPicks(picks));
    }
    picks.addAll(next);

    for (Pick p : picks) {
      p.show();
    }
  }

  class Pick {

    final int ax, ay, bx, by;
    final boolean horizontal;
    final int halfWidth = 20;
    boolean locked;

    Pick(int x, int y, boolean horizontal) {
      this.horizontal = horizontal;

      if (horizontal) {
        ax = x - halfWidth;
        ay = y;
        bx = x + halfWidth + 1;
        by = y;
      } else {
        ax = x;
        ay = y - halfWidth;
        bx = x;
        by = y + halfWidth + 1;
      }
    }

    List<Pick> getNewPicks(List<Pick> others) {
      List<Pick> res = new ArrayList<>();
      Pick a = new Pick(ax, ay, !horizontal);
      Pick b = new Pick(bx, by, !horizontal);
      boolean aOkay = true, bOkay = true;
      for (Pick other : others) {
        if (other != this && a.intersects(other)) {
          aOkay = false;
        }
        if (other != this && b.intersects(other)) {
          bOkay = false;
        }
      }
      if (aOkay) {
        res.add(a);
      }
      if (bOkay) {
        res.add(b);
      }
      return res;
    }

    boolean intersects(Pick other) {
      return ax == other.ax && ay == other.ay
        || bx == other.bx && by == other.by;
    }

    void show() {
      stroke(255);
      strokeWeight(2);
      line(ax, ay, bx, by);
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
