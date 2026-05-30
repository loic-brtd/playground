package reprod_cc;

import java.util.List;

import core.PApplet;
import qtree.Boundary;
import qtree.Point;
import qtree.QuadTree;
import qtree.Rectangle;
import tests.Particle;

public class QuadTreeCC extends PApplet {

	int total = 300;
	Particle[] particles;
	QuadTree<Particle> tree;

	@Override
	protected void setup() {
		size(800, 600);
		particles = new Particle[total];
		for (int i = 0; i < total; i++) {
			int w = floor(random(width));
			int h = floor(random(height));
			particles[i] = new Particle(w, h, 5);
		}
		tree = new QuadTree<>(new Rectangle(0, 0, width, height), 4);
	}

	@Override
	protected void draw() {
		background(0);
		for (Particle p : particles) {
			p.show(this);
			p.move();
			p.highlighted = false;
			tree.insert(new Point<>(p.x, p.y, p));
		}
		for (Particle p : particles) {
//			for (Particle other : particles) {
//				if (p != other && p.intersects(other)) {
//					p.highlighted = true;
//					break;
//				}
//			}
			int radius = p.r * 2;
			Boundary range = new Rectangle(p.x - radius, p.y - radius, radius * 2, radius * 2);
			List<Point<Particle>> others = tree.query(range);
			for (Point<Particle> other : others) {
				if (p != other.data && p.intersects(other.data)) {
					p.highlighted = true;
					break;
				}
			}
		}
		//noFill();
		//stroke(0, 255, 0);
		// tree.show(this);
		// tree.clear();
	}

	public static void main(String[] args) {
		new QuadTreeCC().start();
	}

}
