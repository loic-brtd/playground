package reprod_cc;

import core.PApplet;

public class BarnsleyFern extends PApplet {

	float x = 0;
	float y = 0;

	@Override
	protected void setup() {
		size(900, 900);
		background(0);
	}

	@Override
	protected void draw() {
		for (int i = 0; i < frameCount * 2; i++) {
			nextPoint();
		}
	}

	void nextPoint() {
		float px = map(x, -2.1820f, 2.6558f, 0, width);
		float py = map(y, 0, 9.9983f, height, 0);

		strokeWeight(1);
		stroke(255, 150);
		point(px, py);

		double nextX;
		double nextY;

		float r = random(1.0f);

		if (r < 0.1) {
			nextX = 0.00;
			nextY = 0.16 * y;
		} else if (r < 0.86) {
			nextX = 0.85 * x + 0.04 * y;
			nextY = -0.04 * x + 0.85 * y + 1.60;
		} else if (r < 0.93) {
			nextX = 0.20 * x - 0.26 * y;
			nextY = 0.23 * x + 0.22 * y + 1.60;
		} else {
			nextX = -0.15 * x + 0.28 * y;
			nextY = 0.26 * x + 0.24 * y + 0.44;
		}

		x = (float) nextX;
		y = (float) nextY;
	}

	public static void main(String[] args) {
		new BarnsleyFern().start();
	}

}
