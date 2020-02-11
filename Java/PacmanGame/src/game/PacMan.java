package game;

import com.sun.org.apache.regexp.internal.RE;
import core.PGraphics;
import core.PVector;

import static game.Game.*;

public class PacMan extends MovingEntity {

    public int score;

    public PacMan(int x, int y, float speed) {
        super(x, y, speed, 0xFFFFFF00);
        score = 0;
    }

    @Override
    protected void onPositionUpdated() {
        board.get(x, y).doAction(this);
    }

    @Override
    public void show(PGraphics g) {
        PVector drawn = computeDrawingCornerPosition(x, y);
        PGraphics img = getImage();
        g.fill(color);
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.image(img, drawn.x, drawn.y);
        // Wrap around the board horizontally
        if (drawn.x > WIDTH - UNIT) {
            g.image(img, drawn.x - WIDTH, drawn.y);
        } else if (drawn.x < UNIT) {
            g.image(img, drawn.x + WIDTH, drawn.y);
        }
    }

    private PGraphics getImage() {
        switch (direction) {
            case UP:
                return UP_IMAGE;
            case DOWN:
                return DOWN_IMAGE;
            case LEFT:
                return LEFT_IMAGE;
            default:
                return RIGHT_IMAGE;
        }
    }

    private static PGraphics createRightImage() {
        PGraphics g = createGraphics(ceil(DIAMETER), ceil(DIAMETER));
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.angleMode(DEGREES);
        g.fill(0xFFFFFF00);
        g.arc(0, 0, DIAMETER, DIAMETER, 30, 330);
        return g;
    }

    private static PGraphics createLeftImage() {
        PGraphics g = createGraphics(ceil(DIAMETER), ceil(DIAMETER));
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.angleMode(DEGREES);
        g.fill(0xFFFFFF00);
        g.arc(0, 0, DIAMETER, DIAMETER, 210, 150);
        return g;
    }

    private static PGraphics createUpImage() {
        PGraphics g = createGraphics(ceil(DIAMETER), ceil(DIAMETER));
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.angleMode(DEGREES);
        g.fill(0xFFFFFF00);
        g.arc(0, 0, DIAMETER, DIAMETER, 300, 240);
        return g;
    }

    private static PGraphics createDownImage() {
        PGraphics g = createGraphics(ceil(DIAMETER), ceil(DIAMETER));
        g.noStroke();
        g.ellipseMode(CORNER_MODE);
        g.angleMode(DEGREES);
        g.fill(0xFFFFFF00);
        g.arc(0, 0, DIAMETER, DIAMETER, 120, 60);
        return g;
    }

    private static final PGraphics RIGHT_IMAGE = createRightImage();
    private static final PGraphics LEFT_IMAGE = createLeftImage();
    private static final PGraphics UP_IMAGE = createUpImage();
    private static final PGraphics DOWN_IMAGE = createDownImage();
}