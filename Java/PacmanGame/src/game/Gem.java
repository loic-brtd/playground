package game;

import static constants.PConstants.*;
import static game.Game.*;

import core.PGraphics;

public abstract class Gem {
    // Coordinates of the gem's center
    final int x, y;
    final float d;
    final float r;

    Gem(int x, int y, float d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.r = d / 2;
    }

    abstract void doEffect();

    abstract void show(PGraphics g);

    // Implementations

    public static class Dot extends Gem {
        Dot(int x, int y) {
            super(x, y, unit * 0.2f);
        }

        @Override
        void doEffect() {

        }

        void show(PGraphics g) {
            g.ellipseMode(CENTER_MODE);
            g.fill(0xFFFCBA94);
            g.circle(x, y, d);
        }
    }

    public static class Ball extends Gem {
        Ball(int x, int y) {
            super(x, y, unit * 0.6f);
        }

        @Override
        void doEffect() {
            board.forEachGhost(Ghost::makeBlueForAMoment);
        }

        void show(PGraphics g) {
            g.ellipseMode(CENTER_MODE);
            g.fill(0xFFFCBA94);
            g.circle(x, y, d);
        }
    }
}
