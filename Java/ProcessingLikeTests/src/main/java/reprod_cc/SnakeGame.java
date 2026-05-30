package reprod_cc;

import java.util.ArrayList;
import java.util.List;

import constants.Alignment;
import core.PVector;
import core.PApplet;

public class SnakeGame extends PApplet {

	final int unit = 30;
	int cols, rows;
	Food food;
	Snake snake;

	@Override
	protected void setup() {
		size(600, 600);
		frameRate(10);
		ellipseMode(CORNER_MODE);
		cols = width / unit;
		rows = height / unit;
		snake = new Snake();
		food = new Food();
	}

	@Override
	protected void draw() {
		background(0);
		snake.update();
		food.show();
		snake.show();
		showScore();
	}

	@Override
	protected void keyPressed() {
		switch (keyCode) {
		case ARROW_UP:
			snake.changeDir(0, -1);
			break;
		case ARROW_RIGHT:
			snake.changeDir(1, 0);
			break;
		case ARROW_DOWN:
			snake.changeDir(0, 1);
			break;
		case ARROW_LEFT:
			snake.changeDir(-1, 0);
			break;
		case SPACE:
			if (snake.dead) {
				snake = new Snake();
				food = new Food();
			}
			break;
		default:
			break;
		}
	}

	void showScore() {
		fill(120);
		textSize(30);
		textAlign(Alignment.LEFT, TOP);
		text(snake.tail.size(), 10, 5);
		if (snake.dead) {
			textSize(50);
			textAlign(CENTER, CENTER);
			text("Game Over", width / 2, height / 2);
		}
	}

	class Food {
		PVector pos;

		Food() {
			do {
				pos = new PVector(floor(random(cols)), floor(random(rows)));
			} while (snake.touches(pos));
		}

		void show() {
			noStroke();
			fill(255, 0, 0);
			ellipse(pos.x * unit, pos.y * unit, unit, unit);
		}
	}

	class Snake {
		PVector pos, dir;
		boolean dead;
		List<PVector> tail;

		Snake() {
			pos = new PVector(rows / 2, cols / 2);
			dir = new PVector(0, -1);
			dead = false;
			tail = new ArrayList<>();
		}

		boolean touches(PVector vec) {
			if (pos.equals(vec)) {
				return true;
			}
			for (PVector t : tail) {
				if (t.equals(vec)) {
					return true;
				}
			}
			return false;
		}

		boolean touchesWalls() {
			return pos.x < 0 || pos.x > cols - 1 || pos.y < 0 || pos.y > rows - 1;
		}

		boolean touchesItself() {
			for (PVector t : tail) {
				if (t.equals(pos)) {
					return true;
				}
			}
			return false;
		}

		void changeDir(int x, int y) {
			if (tail.isEmpty() || !new PVector(x, y).add(dir).equals(new PVector())) {
				dir = new PVector(x, y);
			}
		}

		void update() {
			if (!dead) {
				// Move the tail
				tail.add(0, pos.copy());
				if (!tail.isEmpty()) {
					tail.remove(tail.size() - 1);
				}
				// Move the head
				pos.add(dir);
				if (touchesWalls() || touchesItself()) {
					pos.sub(dir);
					dead = true;
				} else if (touches(food.pos)) {
					tail.add(0, pos.copy());
					food = new Food();
				}
			}
		}

		void show() {
			noStroke();
			fill(255);
			rect(pos.x * unit, pos.y * unit, unit, unit, 10);
			for (PVector t : tail) {
				rect(t.x * unit, t.y * unit, unit, unit, 10);
			}
		}
	}

	public static void main(String[] args) {
		PApplet.run();
	}

}
