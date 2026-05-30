package qtree;

public class Rectangle implements Boundary {

	public float x, y, w, h;

	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public boolean contains(Point<?> p) {
		return p.x >= x && p.x <= x + w && p.y >= y && p.y <= y + h;
	}

	@Override
	public boolean intersects(Rectangle other) {
		return !(x + w < other.x || x > other.w + other.x || y + h < other.y || y > other.h + other.y);
	}
	
	public boolean intersects(Boundary b) {
		return b.intersects(this);
	}

	@Override
	public String toString() {
		return "Rectangle [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + "]";
	}

}
