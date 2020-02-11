package game;

import javax.swing.*;

import static core.PCanvas.*;

public class Ghost extends MovingEntity {

    private final int BASE_COLOR;
    private final float BASE_SPEED;

    public Ghost(int x, int y, float d, int color) {
        super(x, y, d, color);
        BASE_COLOR = color;
        BASE_SPEED = 1/15f;
        chooseRandomDirection();
    }

    public void makeBlueForAMoment() {
        color = 0xFF0000FF;
        speed = 1/30f;
        Timer timer = new Timer(3000, e -> {
            color = BASE_COLOR;
            speed = BASE_SPEED;
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void onPositionUpdated() {
        chooseRandomDirection();
    }

    private void chooseRandomDirection() {
        Direction[] possibleMoves = findPossibleMoves();
        Direction move = random(possibleMoves);
        changeDirection(move);
    }

}