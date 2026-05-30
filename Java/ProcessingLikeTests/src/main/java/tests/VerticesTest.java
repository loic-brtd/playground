package tests;

import core.PApplet;

public class VerticesTest extends PApplet {

	int example = 1;

	@Override
	protected void setup() {
		size(400, 400);
	}

	@Override
	protected void draw() {

		if (example == 1) {
			background(230);
			double nbPoints = floor(map(mouseX, 0, width, 0, 20));
			fill(0);
			textSize(20);
			text(String.valueOf(nbPoints), 5, 20);
			translate(width / 2, height / 2);
			if (nbPoints >= 0) {
				int radius = 150;
				beginShape();
				for (int i = 0; i < nbPoints; i++) {
				  float angle = (float) (i * TWO_PI / nbPoints);
					float x = radius * cos(angle);
					float y = radius * sin(angle);
					vertex(x, y);
				}
				fill(0, 150, 200);
				strokeWeight(20);
				endShape(CLOSE);
			}
		} else if (example == 2) {
			background(255);
			strokeWeight(10);
			stroke(0);
			fill(0, 250, 100);
			beginShape();
			vertex(161, 210);
		//	vertex(36, 249);
		//	vertex(77, 57);
			endShape(CLOSE);
		}
	}

	public static void main(String[] args) {
		new VerticesTest().start();
	}

}
