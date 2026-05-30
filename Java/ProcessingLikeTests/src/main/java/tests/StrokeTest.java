package tests;

import core.PApplet;

public class StrokeTest extends PApplet {

	@Override
	protected void setup() {
		noLoop();
	}

	@Override
	protected void draw() {
		background(220);
		noFill();
		stroke(0);
		strokeWeight(12);
		strokeCap(ROUND);
		line(20, 30, 80, 30);
		strokeCap(SQUARE);
		line(20, 50, 80, 50);
		strokeCap(PROJECT);
		line(20, 70, 80, 70);
	}

	public static void main(String[] args) {
		new StrokeTest().start();
	}

}
