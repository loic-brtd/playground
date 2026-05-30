package qtree;

public class Circle implements Boundary {

	public float x, y, r;

	public Circle(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	@Override
	public boolean contains(Point<?> p) {
		return Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y)) <= r;
	}

	@Override
	public boolean intersects(Rectangle rect) {
		
		float circleDistanceX = Math.abs(x - rect.x);
		float circleDistanceY = Math.abs(y - rect.y);

		if (circleDistanceX > (rect.w / 2 + r)) {
			return false;
		}
		if (circleDistanceY > (rect.h / 2 + r)) {
			return false;
		}

		if (circleDistanceX <= (rect.w / 2)) {
			return true;
		}
		if (circleDistanceY <= (rect.h / 2)) {
			return true;
		}

		float cornerDistance_sq = (circleDistanceX - rect.w / 2) * (circleDistanceX - rect.w / 2)
				+ (circleDistanceY - rect.h / 2) * (circleDistanceY - rect.h / 2);

		return (cornerDistance_sq <= (r * r));

	}

}
