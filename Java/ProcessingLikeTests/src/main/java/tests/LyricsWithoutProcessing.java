package tests;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LyricsWithoutProcessing {

  public static void main(String[] args) throws FileNotFoundException {

    MiniCanvas c = new MiniCanvas(600, 600);

    Scanner sc = new Scanner(new File("res/bury_a_friend.txt"));
    ArrayList<String> lines = new ArrayList<>();
    while (sc.hasNext()) {
      lines.add(sc.nextLine());
    }
    sc.close();
    String lyrics = String.join(" ", lines);
    String[] words = lyrics.toUpperCase().split("\\W+");
    float unit = (c.width * 1f) / words.length;

    c.draw(g -> {

      g.setColor(Color.BLACK);
      g.fillRect(0, 0, c.width, c.height);
      g.setColor(Color.WHITE);
      for (int i = 0; i < words.length; i++) {
        for (int j = 0; j < words.length; j++) {
          if (words[i].equals(words[j])) {
            g.fill(new Rectangle.Float(i * unit, j * unit, unit, unit));
          }
        }
      }

      g.setColor(Color.RED);
      g.fillOval(c.mouseX - 25, c.mouseY - 25, 50, 50);

    });
    
    c.run(60);

  }

}
