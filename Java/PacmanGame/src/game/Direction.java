package game;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    STATIC(0, 0);

    final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    static Direction fromVector(int x, int y) {
        int normX = Integer.compare(x, 0);
        int normY = Integer.compare(y, 0);

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

    public static final Direction[] NON_STATIC = new Direction[]{UP, RIGHT, DOWN, LEFT};

    Direction[] rotatedLeftRight() {
        switch (this) {
            case UP:
            case DOWN:
                return new Direction[]{LEFT, RIGHT};
            case RIGHT:
            case LEFT:
                return new Direction[]{UP, DOWN};
            default:
                return new Direction[]{UP, DOWN, LEFT, RIGHT};
        }
    }

    Direction opposite() {
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