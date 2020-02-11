package game;

import static game.Game.*;

import core.PGraphics;

import javax.swing.*;

public abstract class Cell {

    public abstract void show(PGraphics g, int x, int y);

    public abstract void doAction(PacMan p);

    public abstract boolean isAccessible();

    public static Cell from(char c, int x, int y) {
        switch (c) {
            case ' ':
                return new Empty(null);
            case '.':
                return new Empty(new Gem.Dot(x, y));
            case 'x':
                return new Fortress();
            case 'o':
                return new Empty(new Gem.Ball(x, y));
            default:
                return new Wall();
        }
    }

    // Implementations

    public static class Fortress extends Cell {

        boolean accessible;

        public Fortress() {
            accessible = true;
            Timer timer = new Timer(2000, e -> {
                accessible = false;
            });
            timer.setRepeats(false);
            timer.start();
        }

        @Override
        public void show(PGraphics g, int x, int y) {
            g.fill(accessible ? 0 : 0xFF222222);
            g.rect(x * UNIT, y * UNIT, UNIT, UNIT);
        }

        @Override
        public void doAction(PacMan p) {

        }

        @Override
        public boolean isAccessible() {
            return accessible;
        }
    }

    public static class Wall extends Cell {

        public void show(PGraphics g, int x, int y) {
//            g.fill(0xFF1919A6);
//            g.rect(x * UNIT, y * UNIT, UNIT, UNIT);
        }

        public boolean isAccessible() {
            return false;
        }

        @Override
        public void doAction(PacMan p) {

        }

    }

    public static class Empty extends Cell {
        public Gem gem;

        public Empty(Gem gem) {
            this.gem = gem;
        }

        public void show(PGraphics g, int x, int y) {
            g.fill(0);
            g.rect(x * UNIT, y * UNIT, UNIT, UNIT);
            if (gem != null) {
                gem.show(g);
            }
        }

        public boolean isAccessible() {
            return true;
        }

        @Override
        public void doAction(PacMan p) {
            if (gem != null) {
                gem.doEffect();
                gem = null;
                p.score++;
            }
        }

    }
}
