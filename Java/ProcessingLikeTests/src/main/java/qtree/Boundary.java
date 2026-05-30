package qtree;

public interface Boundary {

	boolean contains(Point<?> p);
	
	boolean intersects(Rectangle r);
	
}
