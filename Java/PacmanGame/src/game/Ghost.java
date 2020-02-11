package game;

import javax.swing.*;

import static core.PCanvas.*;

public class Ghost extends MovingEntity {

    final int BASE_COLOR;

    Ghost(int x, int y, float d, int color) {
        super(x, y, d, color);
        BASE_COLOR = color;
        chooseRandomDirection();
    }

    public void makeBlueForAMoment() {
        color = 0xFF0000FF;
        Timer timer = new Timer(3000, e -> {
            color = BASE_COLOR;
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