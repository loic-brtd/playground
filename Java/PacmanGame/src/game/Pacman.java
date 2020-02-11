package game;

import static constants.PConstants.CORNER_MODE;
import static game.Game.*;

import core.PGraphics;

public class Pacman extends MovingEntity {

    Pacman(int x, int y, float d) {
        super(x, y, d);
    }

    void show(PGraphics g) {
        g.ellipseMode(CORNER_MODE);
        g.fill(0xFFFFFF00);
        g.circle(x, y, d);
        // Wrap around the board horizontally
        // (x is always positive, in [0,width-1])
        if (x >= width - unit)
            g.circle(x - width, y, d);
    }

    @Override
    void doAction(Cell c) {
        c.act(this);
    }
}