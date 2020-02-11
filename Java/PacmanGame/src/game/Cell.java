package game;

import static game.Game.*;

import core.PGraphics;

public abstract class Cell {

    abstract void show(PGraphics g, int x, int y);

    abstract void doAction(PacMan p);

    abstract boolean isAccessible();

    public static Cell from(char c, int x, int y) {
        switch (c) {
            case ' ':
                return new Empty(null);
            case '·':
                return new Empty(new Gem.Dot(x, y));
            case 'o':
                return new Empty(new Gem.Ball(x, y));
            default:
                return new Wall();
        }
    }

    // Implementations

    public static class Wall extends Cell {

        void show(PGraphics g, int x, int y) {
            g.fill(0xFF1919A6);
            g.rect(x * unit, y * unit, unit, unit);
        }

        boolean isAccessible() {
            return false;
        }

        @Override
        void doAction(PacMan p) {

        }

    }

    public static class Empty extends Cell {
        Gem gem;

        Empty(Gem gem) {
            this.gem = gem;
        }

        void show(PGraphics g, int x, int y) {
            g.fill(0);
            g.rect(x * unit, y * unit, unit, unit);
            if (gem != null) {
                gem.show(g);
            }
        }

        boolean isAccessible() {
            return true;
        }

        @Override
        void doAction(PacMan p) {
            if (gem != null) {
                gem.doEffect();
                gem = null;
                p.score++;
            }
        }

    }
}
