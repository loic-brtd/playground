package game;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    STATIC(0, 0);

    public static final Direction[] NON_STATIC = new Direction[]{UP, RIGHT, DOWN, LEFT};

    public final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction fromVector(int x, int y) {
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

    public Direction opposite() {
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