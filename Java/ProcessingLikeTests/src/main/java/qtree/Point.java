package qtree;

public class Point<T> {

  public float x, y;
  public T data;

  public Point(float x, float y, T data) {
    this.x = x;
    this.y = y;
    this.data = data;
  }

  @Override
  public String toString() {
    return "Point [x=" + x + ", y=" + y + ", data=" + data + "]";
  }

}
