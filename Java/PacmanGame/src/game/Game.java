package game;

import core.PApplet;

public class Game extends PApplet {

    public static int unit = 20;
    public static int halfUnit = unit / 2;
    public static int width, height;
    public static Board board;
    public static PacMan player;
    public boolean frozen = false;

    @Override
    public void setup() {
        board = new Board("res/board.txt");
        size(board.cols * unit, board.rows * unit + unit * 2);
        noSmooth();
        width = board.cols * unit;
        height = board.rows * unit;

        player = new PacMan(board.cols / 2, board.rows - 7, unit);
        Ghost red = new Ghost(board.cols / 2, 10, unit, 0xFFFC0204);
        Ghost cyan = new Ghost(board.cols / 2, 10, unit, 0xFF04FEDC);
        Ghost orange = new Ghost(board.cols / 2, 10, unit, 0xFFFCBA44);
        Ghost pink = new Ghost(board.cols / 2, 10, unit, 0xFFFCBADC);

        board.setPlayer(player);
        board.addGhost(red);
        board.addGhost(cyan);
        board.addGhost(orange);
        board.addGhost(pink);

        textSize(unit);
        textAlign(LEFT, CENTER);
        textFont("Monospaced");
    }

    @Override
    public void draw() {
        if (!frozen) board.update();
        board.show(this);
        fill(255);
        text("SCORE: " + player.score, halfUnit, height + unit);
    }

    @Override
    public void keyPressed() {
        println(frameCount);
        if (keyCode == ARROW_UP) {
            player.changeDirection(Direction.UP);
        } else if (keyCode == ARROW_RIGHT) {
            player.changeDirection(Direction.RIGHT);
        } else if (keyCode == ARROW_DOWN) {
            player.changeDirection(Direction.DOWN);
        } else if (keyCode == ARROW_LEFT) {
            player.changeDirection(Direction.LEFT);
        } else if (keyCode == SPACE) {
            frozen = !frozen;
        }
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
