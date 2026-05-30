package tests;

import core.PApplet;

public class TextTest extends PApplet {

	protected void setup() {
		size(800, 500);
		strokeWeight(1);
		textFont("Arial Black");
		textSize(50);
		textAlign(RIGHT);
		fill(0);
	}

	protected void draw() {
		background(255);
		text("Super text !", mouseX, mouseY);
		stroke(255, 0, 0);
		line(mouseX, 0, mouseX, height);
		line(0, mouseY, width, mouseY);
		stroke(0, 255, 0);
		line(0, mouseY + textDescent(), width, mouseY + textDescent());
		stroke(0, 0, 255);
		line(0, mouseY - textAscent(), width, mouseY - textAscent());
	}
	
	@Override
	protected void mouseReleased() {
		saveFrame("text.png");
	}

	public static void main(String[] args) {
		new TextTest().start();
	}

}
