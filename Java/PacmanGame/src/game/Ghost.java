package game;

import core.PGraphics;
import core.PVector;

import javax.swing.*;

import static game.Game.*;

public class Ghost extends MovingEntity {

    private final int BASE_COLOR;
    private final float BASE_SPEED;
    private int chasedTargetX, chasedTargetY;

    public Ghost(int x, int y, float speed, int color) {
        super(x, y, speed, color);
        BASE_COLOR = color;
        BASE_SPEED = speed;
        changeBehaviour(Behaviour.EXITING_FORTRESS);
        onPositionUpdated();
    }

    public void changeBehaviour(Behaviour behaviour) {
        board.ghostsBehaviour = behaviour;
        if (behaviour == Behaviour.FRIGHTENED) {
            enterFrightenedBehaviour();
        } else if (behaviour == Behaviour.EXITING_FORTRESS) {
            enterExitingFortressBehaviour();
        }
    }

    private void enterFrightenedBehaviour() {
        color = 0xFF0000FF;
        speed = 1/30f;
        Timer timer = new Timer(8000, e -> {
            color = BASE_COLOR;
            speed = BASE_SPEED;
            board.ghostsBehaviour = Behaviour.CHASING;
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void enterExitingFortressBehaviour() {
        Timer timer = new Timer(3000, e -> changeBehaviour(Behaviour.CHASING));
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void onPositionUpdated() {
        if (board.ghostsBehaviour != Behaviour.FRIGHTENED && board.positionIntersectsPlayer(x, y)) {
            frozen = true;
            over = true;
            return;
        }
        if (board.ghostsBehaviour == Behaviour.EXITING_FORTRESS) {
            chasedTargetX = 10;
            chasedTargetY = 1;
            chooseDirectionTowardsTarget();
        } else if (board.ghostsBehaviour == Behaviour.FRIGHTENED) {
            chooseRandomDirection();
        } else if (board.ghostsBehaviour == Behaviour.CHASING) {
            chasedTargetX = player.x;
            chasedTargetY = player.y;
            chooseDirectionTowardsTarget();
        }
    }

    private void chooseDirectionTowardsTarget() {
        Direction[] possibleMoves = findPossibleMoves();
        Direction bestMove = possibleMoves[0];
        float shortestDistanceToTarget = board.cols + board.rows;
        for (Direction move : possibleMoves) {
            float d = dist(x + move.dx, y + move.dy, chasedTargetX, chasedTargetY);
            if (d < shortestDistanceToTarget) {
                bestMove = move;
                shortestDistanceToTarget = d;
            }
        }
        changeDirection(bestMove);
    }

    private void chooseRandomDirection() {
        Direction[] possibleMoves = findPossibleMoves();
        Direction move = random(possibleMoves);
        changeDirection(move);
    }

    @Override
    public void show(PGraphics g) {
        PVector drawn = computeDrawingCornerPosition(x, y);
        g.fill(color);
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.circle(drawn.x, drawn.y, DIAMETER);
        // Wrap around the board horizontally
        if (drawn.x > WIDTH - UNIT) {
            g.circle(drawn.x - WIDTH, drawn.y, DIAMETER);
        } else if (drawn.x < UNIT) {
            g.circle(drawn.x + WIDTH, drawn.y, DIAMETER);
        }
    }

    public enum Behaviour {
        FRIGHTENED, CHASING, EXITING_FORTRESS
    }

}