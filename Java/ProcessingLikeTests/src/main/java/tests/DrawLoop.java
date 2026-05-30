package tests;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class DrawLoop {

  public int width;
  public int height;
  public int mouseX = 0;
  public int mouseY = 0;
  public char key;
  public Graphics2D g;
  private BufferedImage img;

  public void size(int w, int h) {
    width = w;
    height = h;
    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    g = img.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
  }

  public void run() {

    size(100, 100);
    setup();

    @SuppressWarnings("serial")
    JPanel p = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(img, 0, 0, null);
      }
    };
    p.setPreferredSize(new Dimension(width, height));
    p.addMouseListener(new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent e) {
        DrawLoop.this.mousePressed();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        DrawLoop.this.mouseReleased();
      }
    });
    p.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        DrawLoop.this.mouseDragged();
      }
    });
    p.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          System.exit(0);
        }
      }
    });
    p.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        DrawLoop.this.keyTyped();
      }

      @Override
      public void keyReleased(KeyEvent e) {
        DrawLoop.this.keyReleased();
      }

      @Override
      public void keyPressed(KeyEvent e) {
        key = e.getKeyChar();
        DrawLoop.this.keyPressed();
      }
    });
    p.setFocusable(true);
    p.requestFocusInWindow();

    Timer t = new Timer(1000 / 60, e -> {
      g.setTransform(new AffineTransform());
      Point mouse = p.getMousePosition();
      if (mouse != null) {
        mouseX = mouse.x;
        mouseY = mouse.y;
      }
      draw();
      p.repaint();
    });

    JFrame f = new JFrame();
    f.setContentPane(p);
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    t.start();
  }

  public abstract void setup();

  public abstract void draw();

  public void mousePressed() {}

  public void mouseReleased() {}

  public void mouseDragged() {}

  public void keyTyped() {}

  public void keyReleased() {}

  public void keyPressed() {}

}
