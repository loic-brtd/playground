
package tests;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MiniCanvas {

  public int width, height;
  public int mouseX, mouseY;
  private JPanel panel;
  private Graphics2D g;
  private Timer timer;
  private Consumer<Graphics2D> setup;
  private Consumer<Graphics2D> draw;

  public MiniCanvas(int width, int height) {
    this.width = width;
    this.height = height;

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    g = (Graphics2D) img.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    this.panel = new JPanel() {
      private static final long serialVersionUID = 1L;

      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
      }
    };
    panel.setPreferredSize(new Dimension(width, height));

    panel.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          System.exit(0);
        }
      }
    });

    panel.setFocusable(true);

    timer = new Timer(1000 / 60, e -> {
      Point mouse = panel.getMousePosition();
      if (mouse != null) {
        mouseX = mouse.x;
        mouseY = mouse.y;
      }
      if (draw != null) {
        draw.accept(g);
        panel.repaint();
      }
    });
  }

  public void setup(Consumer<Graphics2D> setup) {
    this.setup = setup;
  }

  public void draw(Consumer<Graphics2D> draw) {
    this.draw = draw;
  }

  public void run(int fps) {
    JFrame f = new JFrame();
    f.setContentPane(panel);
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);

    timer.setInitialDelay(1000 / fps);
    timer.setDelay(1000 / fps);
    if (setup != null) {
      setup.accept(g);
      panel.repaint();
    }
    timer.start();
  }

}