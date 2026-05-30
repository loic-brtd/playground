package tests;

import java.util.Random;

import core.PCanvas;

public class Particle {

	public int x;
	public int y;
	public int r;
	public boolean highlighted;

	public Particle(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.highlighted = false;
	}

	public void show(PCanvas c) {
		c.noStroke();
		c.fill(255, highlighted ? 200 : 30);
		c.circle(x, y, r);
	}

	public void move() {
		Random r = new Random();
		x += r.nextInt(5) - 2;
		y += r.nextInt(5) - 2;
	}

	public boolean intersects(Particle p) {
		return ((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y)) <= ((r * 2) * (r * 2));
	}

	@Override
	public String toString() {
		return "Particle [x=" + x + ", y=" + y + ", r=" + r + ", highlighted=" + highlighted + "]";
	}

}
