package game;

import static game.Game.board;
import static game.Game.unit;
import static game.Game.width;

import java.util.ArrayList;
import java.util.List;

import core.PGraphics;

public abstract class MovingEntity {
    protected int x, y; // Coordinates of the player's top left corner
    protected final float d; // Diameter
    protected final float r; // Radius
    protected Direction currDir;
    protected Direction nextDir;
    // protected float speed;

    MovingEntity(int x, int y, float d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.r = d / 2;
        this.nextDir = Direction.STATIC;
        this.currDir = nextDir;
    }

    void changeDirection(Direction dir) {
        this.nextDir = dir;
    }

    private void setPos(int x, int y) {
        this.x = (x + width) % width;
        this.y = y;
    }

    void update() {
        int factor = 2;
        // Calculate new position
        int nextDirTargetX = (x + (nextDir.dx * factor));
        int nextDirTargetY = (y + (nextDir.dy * factor));
        int currDirTargetX = (x + (currDir.dx * factor));
        int currDirTargetY = (y + (currDir.dy * factor));

        if (isAccessible(nextDirTargetX, nextDirTargetY)) {
            // Go there
            setPos(nextDirTargetX, nextDirTargetY);
            // Update direction
            currDir = nextDir;
            doAction(board.get(x / unit, y / unit));
        } else if (isAccessible(currDirTargetX, currDirTargetY)) {
            // Go there
            setPos(currDirTargetX, currDirTargetY);
            doAction(board.get(x / unit, y / unit));
        } else {
            // Don't move, you're stuck
        }
    }

    public Direction[] findPossibleMoves() {
        List<Direction> found = new ArrayList<>();
        for (Direction dir : Direction.nonStatic()) {
            if (dir != currDir.reversed()) {
                int posX = x + dir.dx * unit;
                int posY = y + dir.dy * unit;
                if (board.isPointAccessible(posX, posY)) {
                    found.add(dir);
                }
            }
        }
        // Cul de sac
        if (found.isEmpty()) {
            return new Direction[] { currDir.reversed() };
        }
        return found.toArray(new Direction[found.size()]);
    }

    private boolean isAccessible(int newX, int newY) {
        // Coordinates of the top, right, bottom and left
        // inner borders of the player
        int topY = newY / unit;
        int rightX = (newX + unit - 1) / unit;
        int bottomY = (newY + unit - 1) / unit;
        int leftX = newX / unit;

        // Wrap around
        rightX = (rightX + board.cols) % board.cols;
        leftX = (leftX + board.cols) % board.cols;

        // Are those four pixels in a cell which is accessible ?
        return (board.get(leftX, topY).isAccessible()
                && board.get(rightX, topY).isAccessible()
                && board.get(rightX, bottomY).isAccessible()
                && board.get(leftX, bottomY).isAccessible());
    }

    abstract void show(PGraphics g);

    abstract void doAction(Cell c);
}