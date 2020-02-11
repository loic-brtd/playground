package game;

public enum Direction {
    UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0), STATIC(0, 0);

    final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    static Direction fromVector(int x, int y) {
        int normX = x < 0 ? -1 : x == 0 ? 0 : 1;
        int normY = y < 0 ? -1 : y == 0 ? 0 : 1;

        if (normX == 0) {
            if (normY == 1)
                return DOWN;
            else
                return UP;
        } else if (normX == 1) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }

    static Direction[] nonStatic() {
        return new Direction[] { UP, RIGHT, DOWN, LEFT };
    }

    Direction[] rotated() {
        switch (this) {
        case UP:
        case DOWN:
            return new Direction[] { LEFT, RIGHT };
        case RIGHT:
        case LEFT:
            return new Direction[] { UP, DOWN };
        default:
            return new Direction[] { UP, DOWN, LEFT, RIGHT };
        }
    }

    Direction reversed() {
        switch (this) {
        case UP:
            return DOWN;
        case DOWN:
            return UP;
        case RIGHT:
            return LEFT;
        case LEFT:
            return RIGHT;
        default:
            return STATIC;
        }
    }
}