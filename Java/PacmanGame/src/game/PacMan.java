package game;

import static core.PCanvas.*;

public class PacMan extends MovingEntity {

    public int score;

    public PacMan(int x, int y, float d) {
        super(x, y, d, 0xFFFFFF00);
        score = 0;
        speed = 1/10f;
    }

    @Override
    protected void onPositionUpdated() {
        Game.board.get(x, y).doAction(this);
    }

}