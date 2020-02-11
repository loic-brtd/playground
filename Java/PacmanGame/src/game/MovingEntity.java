package game;

import static game.Game.*;

import java.util.ArrayList;
import java.util.List;

import core.PGraphics;
import sun.security.jgss.GSSHeader;

public abstract class MovingEntity {

    protected int x, y;
    protected final float diameter;
    protected final float radius;
    protected int color;

    protected Direction direction;
    protected Direction desiredDirection;

    protected int targetX, targetY;
    protected float animationProgress;
    protected boolean isAnimating;
    protected float speed;

    MovingEntity(int x, int y, float diameter, int color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.radius = diameter / 2;
        this.color = color;
        this.desiredDirection = Direction.STATIC;
        this.direction = Direction.STATIC;
        this.targetX = x;
        this.targetY = y;
        this.animationProgress = 1;
        this.isAnimating = false;
        this.speed = 1 / 10f;
    }

    public void changeDirection(Direction dir) {
        this.desiredDirection = dir;
    }

    private void setPos(int x, int y) {
        this.x = (x + board.cols) % board.cols;
        this.y = y;
        onPositionUpdated();
    }

    protected void onPositionUpdated() {
    }

    public void update() {
        if (isAnimating) {
            stepMovement();
        } else {
            if (board.get(x + desiredDirection.dx, y + desiredDirection.dy).isAccessible()) {
                direction = desiredDirection;
                startMovement();
            } else if (board.get(x + direction.dx, y + direction.dy).isAccessible()) {
                startMovement();
            }
        }
    }

    private void stepMovement() {
        animationProgress += speed;
        if (animationProgress >= 1) {
            animationProgress = 1;
            setPos(targetX, targetY);
            isAnimating = false;
        }
    }

    private void startMovement() {
        if (direction == Direction.STATIC) return;
        isAnimating = true;
        animationProgress = 0;
        targetX = x + direction.dx;
        targetY = y + direction.dy;
    }

    public Direction[] findPossibleMoves() {
        List<Direction> found = new ArrayList<>();
        for (Direction dir : Direction.NON_STATIC) {
            if (dir != direction.opposite()) {
                int posX = x + dir.dx;
                int posY = y + dir.dy;
                if (board.get(posX, posY).isAccessible()) {
                    found.add(dir);
                }
            }
        }
        // If no other option than turning around
        if (found.isEmpty()) {
            return new Direction[]{direction.opposite()};
        }
        return found.toArray(new Direction[0]);
    }

    public void show(PGraphics g) {
        float drawnX = lerp(x * unit, targetX * unit, animationProgress);
        float drawnY = lerp(y * unit, targetY * unit, animationProgress);

        g.ellipseMode(CORNER_MODE);
        g.fill(color);
        g.circle(drawnX, drawnY, diameter);
        // Wrap around the board horizontally
        if (drawnX > width - unit) {
            g.circle(drawnX - width, drawnY, diameter);
        } else if (drawnX < unit) {
            g.circle(drawnX + width, drawnY, diameter);
        }
    }

}