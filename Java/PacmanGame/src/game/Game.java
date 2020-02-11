package game;

import core.PApplet;

public class Game extends PApplet {

    static int unit = 18;
    static int halfUnit = unit / 2;
    static int width, height;
    static Board board;
    static MovingEntity player;

    public void setup() {
        board = new Board("res/board.txt");
        size(board.cols * unit, board.rows * unit);
        noSmooth();
        width = board.cols * unit;
        height = board.rows * unit;

        player = new Pacman(unit * (board.cols / 2), unit * (board.rows - 7), unit);
        Ghost redGhost = new Ghost(unit * (board.cols / 2), unit * 10, unit, 0xFFFC0204);
        Ghost cyanGhost = new Ghost(unit * (board.cols / 2), unit * 10, unit, 0xFF04FEDC);
        Ghost orangeGhost = new Ghost(unit * (board.cols / 2), unit * 10, unit, 0xFFFCBA44);
        Ghost pinkGhost = new Ghost(unit * (board.cols / 2), unit * 10, unit, 0xFFFCBADC);

        board.add(player);
        board.add(redGhost);
        board.add(cyanGhost);
        board.add(orangeGhost);
        board.add(pinkGhost);
    }

    public void draw() {
        if (!frozen)
            board.update();
        board.show(this);

        // strokeWeight(10);
        // boolean accessible = board.isPointAccessible(mouseX, mouseY);

        // textAlign(CENTER, CENTER);
        // fill(255, 150);
        // for (int y = 0; y < rows; y++)
        // text(y, unit / 2, (y + 0.5f) * unit);
        // for (int x = 0; x < cols; x++)
        // text(x, (x + 0.5f) * unit, unit / 2);
    }

    public void keyPressed() {
        if (keyCode == ARROW_UP)
            player.changeDirection(Direction.UP);
        else if (keyCode == ARROW_RIGHT)
            player.changeDirection(Direction.RIGHT);
        else if (keyCode == ARROW_DOWN)
            player.changeDirection(Direction.DOWN);
        else if (keyCode == ARROW_LEFT)
            player.changeDirection(Direction.LEFT);
        else if (keyCode == SPACE)
            frozen = !frozen;
    }

    boolean frozen = false;

    public static void main(String[] args) {
        PApplet.run();
    }

}
