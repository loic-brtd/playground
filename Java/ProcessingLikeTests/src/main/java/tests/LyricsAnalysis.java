package tests;

import core.PApplet;

public class LyricsAnalysis extends PApplet {

  float unit;
  String[] words;

  public void setup() {
    size(700, 700);
    noLoop();

    // Load the lyrics
    String[] text = loadStrings("res/bury_a_friend.txt");
    String joinedLyrics = join(text, " ").toLowerCase();
    words = joinedLyrics.split("\\W+");
    unit = (float) height / words.length;

    // Create the image
    background(0);
    noStroke();
    fill(255);
    for (int i = 0; i < words.length; i++) {
      String wordA = words[i];
      for (int j = i; j < words.length; j++) {
        String wordB = words[j];
        if (wordA.equals(wordB)) {
          rect(i * unit, j * unit, unit, unit);
          if (i != j) {
            rect(j * unit, i * unit, unit, unit);
          }
        }
      }
    }
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
