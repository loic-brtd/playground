package game;

import static core.PCanvas.*;

public class PacMan extends MovingEntity {

    public int score;

    PacMan(int x, int y, float d) {
        super(x, y, d, 0xFFFFFF00);
        score = 0;
    }

    @Override
    protected void onPositionUpdated() {
        Game.board.get(x, y).doAction(this);
    }

}