package reprod_cc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.PApplet;

public class RecamanSequence extends PApplet {

	int currentX;
	int step;
	int biggest;
	float scl;
	Set<Integer> seen;
	List<Arc> arcs;

	@Override
	protected void setup() {
		fullScreen();
		noFill();
		stroke(255);
		strokeWeight(0.5f);

		arcs = new ArrayList<>();
		seen = new HashSet<>();
		currentX = 0;
		step = 1;
		biggest = 1;
		scl = width;

		background(0);
	}

	@Override
	protected void draw() {
		background(0);
		translate(0, height / 2);
		scl = lerp(scl, width / (float) biggest, 0.2f);
		scale(scl);
		for (Arc a : arcs) {
			a.show();
			if (!a.isDone()) {
				a.animate();
			}
		}

		if (arcs.isEmpty() || arcs.get(arcs.size() - 1).isDone()) {
			int newX = next();
			if (step > 0) {
				boolean up = step % 2 == 0;
				arcs.add(new Arc(currentX, newX, up));
			}
			currentX = newX;
		}
	}

	public int next() {
		int newX;
		if (currentX - step <= 0 || seen.contains(currentX - step)) {
			newX = currentX + step;
		} else {
			newX = currentX - step;
		}
		seen.add(newX);

		if (newX > biggest) {
			biggest = newX;
		}

		step++;
		return newX;
	}

	@Override
	protected void keyPressed() {
		if (key == ' ')
			noLoop();
	}

	class Arc {
		private int start;
		private int end;
		private float progress; // Between 0 and 1
		private boolean up;
		private boolean leftToRight;
		private boolean done;

		public Arc(int start, int end, boolean up) {
			this.start = start;
			this.end = end;
			this.up = up;
			this.progress = 0;
			this.done = false;
			this.leftToRight = start < end;
		}

		public boolean isDone() {
			return done;
		}

		public void animate() {
			progress += 0.15;
			if (progress > 1) {
				progress = 1;
				done = true;
			}
		}

		public void show() {
			float d = abs(start - end);
			float x = (start + end) / 2f;

			if (done) {
				if (up) {
					arc(x, 0, d, d, PI, 0);
				} else {
					arc(x, 0, d, d, 0, PI);
				}
			} else {
				float arcStart, arcStop;
				if (up) {
					arcStart = leftToRight ? PI : lerp(PI, TWO_PI, 1 - progress);
					arcStop = leftToRight ? lerp(PI, TWO_PI, progress) : TWO_PI;
					arc(x, 0, d, d, arcStart, arcStop);
				} else {
					arcStart = leftToRight ? PI * (1 - progress) : 0;
					arcStop = leftToRight ? PI : PI * progress;
					arc(x, 0, d, d, arcStart, arcStop);
				}
			}

		}

	}

	public static void main(String[] args) {
		PApplet.run();
	}

}
