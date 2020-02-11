package game;

import static core.PCanvas.random;
import static core.PCanvas.shuffle;
import static game.Game.unit;
import static game.Game.width;

import javax.swing.Timer;

import constants.ShapeMode;
import core.PGraphics;

public class Ghost extends MovingEntity {

    protected int color;

    Ghost(int x, int y, float d, int color) {
        super(x, y, d);
        this.color = color;
        new Timer(10, actionEvent -> {
            // float distance = dist(this.x, this.y, player.x, player.y);
            // if (distance > unit * 8) {

            changeDirection(random(findPossibleMoves()));

            // Direction rand = random(currDir.rotated());
            // changeDirection(rand);

            // int dirX = player.x - this.x;
            // int dirY = player.y - this.y;
            // Direction dir = Direction.fromVector(dirX, dirY);
            // changeDirection(dir);
        }).start();
    }

    void show(PGraphics g) {
        g.ellipseMode(ShapeMode.CORNER);
        g.fill(color);
        g.circle(x, y, d);
        // Wrap around the board horizontally
        // (x is always positive, in [0,width-1])
        if (x >= width - unit)
            g.circle(x - width, y, d);
    }

    @Override
    void doAction(Cell c) {
        c.getActionFrom(this);
    }
}