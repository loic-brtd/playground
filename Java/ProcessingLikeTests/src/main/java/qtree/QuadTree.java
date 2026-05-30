package qtree;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import core.PCanvas;

public class QuadTree<T> {

  private int capacity;
  private Rectangle rectangle;
  private Point<T>[] points;
  private int index;

  private QuadTree<T> northeast;
  private QuadTree<T> northwest;
  private QuadTree<T> southeast;
  private QuadTree<T> southwest;

  private boolean divided;

  @SuppressWarnings("unchecked")
  public QuadTree(Rectangle boundary, int capacity) {
    this.rectangle = boundary;
    this.capacity = capacity;
    this.points = new Point[capacity];
    this.index = 0;
    this.divided = false;
  }

  public boolean insert(Point<T> p) {
    if (!rectangle.contains(p)) {
      return false;
    }
    if (index < capacity) {
      points[index] = p;
      index++;
      return true;
    } else {
      if (!divided) {
        subdivide();
      }
      return northeast.insert(p) || northwest.insert(p) || southeast.insert(p) || southwest.insert(p);
    }
  }

  private void subdivide() {
    float x = rectangle.x,
      y = rectangle.y,
      w = rectangle.w,
      h = rectangle.h;
    northeast = new QuadTree<>(new Rectangle(x + w / 2, y, w / 2, h / 2), capacity);
    northwest = new QuadTree<>(new Rectangle(x, y, w / 2, h / 2), capacity);
    southeast = new QuadTree<>(new Rectangle(x + w / 2, y + h / 2, w / 2, h / 2), capacity);
    southwest = new QuadTree<>(new Rectangle(x, y + h / 2, w / 2, h / 2), capacity);
    divided = true;
  }

  private List<Point<T>> query(Boundary range, List<Point<T>> found) {
    if (rectangle.intersects(range)) {
      for (Point<T> p : points) {
        if (p != null && range.contains(p)) {
          found.add(p);
        }
      }
      if (divided) {
        northeast.query(range, found);
        northwest.query(range, found);
        southeast.query(range, found);
        southwest.query(range, found);
      }
    }
    return found;
  }

  public List<Point<T>> query(Boundary range) {
    return query(range, new ArrayList<>());
  }

  @SuppressWarnings("unchecked")
  public void clear() {
    points = new Point[capacity];
    divided = false;
    index = 0;
    northeast = null;
    northwest = null;
    southeast = null;
    southwest = null;
  }

  public void show(Graphics2D g) {
    g.setColor(Color.WHITE);
    g.draw(new Rectangle2D.Float(rectangle.x, rectangle.y, rectangle.w, rectangle.y));

    if (divided) {
      northeast.show(g);
      northwest.show(g);
      southeast.show(g);
      southwest.show(g);
    }
  }

  public void show(PCanvas c) {
    c.rect(rectangle.x, rectangle.y, rectangle.w, rectangle.h);

    if (divided) {
      northeast.show(c);
      northwest.show(c);
      southeast.show(c);
      southwest.show(c);
    }
  }

}
