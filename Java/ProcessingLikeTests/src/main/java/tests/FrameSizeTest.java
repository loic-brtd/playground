package tests;

import core.PApplet;

public class FrameSizeTest extends PApplet {

	float d = 10;

	public void setup() {
		fill(100);
		textSize(50);
		textAlign(CENTER, CENTER);
	}

	public void draw() {
		clear();
		if (mouseIsPressed) {
			d = lerp(d, 200, .5f);
		} else {
			d = lerp(d, 10, .5f);
		}
		fill(255);
		circle(mouseX, mouseY, d);
		fill(100);
		text(round(d), width / 2, height / 2);
	}

	public static void main(String[] args) {
		PApplet.run();
	}

}
