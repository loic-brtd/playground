package other;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.PApplet;

public class FlashCards extends PApplet {

  List<Card> cards = new ArrayList<FlashCards.Card>();
  int index = 0;
  boolean recto = true;
  Card current;

  @Override
  protected void setup() {
    fullScreen();
    textSize(50);
    textAlign(CENTER, CENTER);
    try (BufferedReader br = new BufferedReader(new FileReader("cards.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.contains(":::")) {
          String[] content = line.split(":::");
          if (content.length == 2) {
            cards.add(new Card(content[0].trim(), content[1].trim()));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    current = cards.get(0);
  }

  @Override
  protected void keyPressed() {
    if (keyCode == SPACE) {
      recto = !recto;
    } else if (keyCode == ARROW_RIGHT) {
      index = (index + 1) % cards.size();
      current = cards.get(index);
    } else if (keyCode == ARROW_LEFT) {
      index = (index + cards.size() - 1) % cards.size();
      current = cards.get(index);
    } else if (key == 's') {
      Collections.shuffle(cards);
      current = cards.get(index);
    }
  }

  @Override
  protected void draw() {
    background(recto ? 0 : 255);
    translate(width / 2, height / 2);
    current.show(recto);
    fill(recto ? 255 : 0, 100);
    text((index + 1) + "/" + cards.size(), 0, -100);
    text(recto ? "recto" : "verso", 0, 100);
  }

  class Card {

    String recto, verso;

    Card(String recto, String verso) {
      this.recto = recto;
      this.verso = verso;
    }

    void show(boolean recto) {
      fill(recto ? 255 : 0);
      text(recto ? this.recto : this.verso, 0, 0);
    }

  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
