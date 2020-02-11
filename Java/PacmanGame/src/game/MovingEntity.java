package game;

import static game.Game.*;

import java.util.ArrayList;
import java.util.List;

import core.PGraphics;
import core.PVector;

public abstract class MovingEntity {

    protected int x, y;
    public static final float DIAMETER = UNIT * 1.3f;
    protected int color;
    protected float speed;

    protected Direction direction;
    private Direction desiredDirection;

    private int targetX, targetY;
    private float animationProgress;
    private boolean isAnimating;

    public MovingEntity(int x, int y, float speed, int color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.desiredDirection = Direction.STATIC;
        this.direction = Direction.STATIC;
        this.targetX = x;
        this.targetY = y;
        this.animationProgress = 1;
        this.isAnimating = false;
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

    public PVector computeDrawingCornerPosition(int x, int y) {
        float offset = UNIT * 0.15f;
        float drawnX = lerp(x * UNIT, targetX * UNIT, animationProgress) - offset;
        float drawnY = lerp(y * UNIT, targetY * UNIT, animationProgress) - offset;
        return createVector(drawnX, drawnY);
    }

    public void show(PGraphics g) {
        float offset = UNIT * 0.15f;
        float drawnX = lerp(x * UNIT, targetX * UNIT, animationProgress) - offset;
        float drawnY = lerp(y * UNIT, targetY * UNIT, animationProgress) - offset;

        g.fill(color);
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.arc(drawnX, drawnY, DIAMETER, DIAMETER, 30, 330);
        // Wrap around the board horizontally
        if (drawnX > WIDTH - UNIT) {
            g.circle(drawnX - WIDTH, drawnY, DIAMETER);
        } else if (drawnX < UNIT) {
            g.circle(drawnX + WIDTH, drawnY, DIAMETER);
        }
    }

}