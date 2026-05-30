package tests;

import components.Button;
import components.Input;
import components.Radio;
import core.PApplet;

public class InputTest extends PApplet {

	Input input;
	Radio radio;
	Button button;

	protected void setup() {
		size(1000, 300);

		input = createInput();
		breakLine();
		radio = createRadio();
		radio.option("un");
		radio.option("deux");
		radio.option("trois");
		breakLine();
		button = createButton("Button");
		button.mousePressed(() -> button.text("Pressed"));
		button.mouseReleased(() -> button.text("Released"));

		textAlign(CENTER, CENTER);
		textSize(30);
		textFont("Arial Bold");
	}

	protected void draw() {
		background(0, 150, 200);
		translate(width / 2, height / 2);
		text(input.value().toUpperCase(), 0, 0);
	}

	public static void main(String[] args) {
		new InputTest().start();
	}

}
