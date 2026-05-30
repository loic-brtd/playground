package reprod_cc;

import core.PApplet;

public class WaterRipples extends PApplet {

	int cols;
	int rows;
	int zoom = 3;
	float[][] current;
	float[][] previous;
	float[][] temp;
	float damping = 0.98f;

	@Override
	protected void setup() {
		size(800, 600);
		// fullScreen();
		noStroke();
		cols = width / zoom;
		rows = height / zoom;
		current = new float[cols][rows];
		previous = new float[cols][rows];
		noSmooth();
	}

	@Override
	protected void draw() {
		background(0);
		for (int i = 1; i < cols - 1; i++) {
			for (int j = 1; j < rows - 1; j++) {
				current[i][j] = (previous[i - 1][j] + previous[i + 1][j] + previous[i][j - 1] + previous[i][j + 1]) / 2
						- current[i][j];
				current[i][j] *= damping;
				fill(current[i][j]);
				rect(i * zoom, j * zoom, zoom, zoom);
			}
		}

		temp = previous;
		previous = current;
		current = temp;
		
	}

	@Override
	protected void mouseDragged() {
		int x = constrain(mouseX / zoom, 5, cols - 5);
		int y = constrain(mouseY / zoom, 5, rows - 5);
		previous[x][y] = 255;
	}

	public static void main(String[] args) {
		PApplet.run();
	}

}
