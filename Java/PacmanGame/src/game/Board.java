package game;

import static game.Game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import core.PGraphics;

public class Board {

    public final Cell[][] cells;
    public final int cols, rows;
    public Ghost.Behaviour ghostsBehaviour;
    private PacMan player;
    private final List<Ghost> ghosts = new ArrayList<>();
    private final PGraphics boardBackground;

    public Board(String file) {
        String[] lines = loadStrings(file);
        this.cols = lines[0].length();
        this.rows = lines.length;
        this.cells = new Cell[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                char c = x < lines[y].length() ? lines[y].charAt(x) : ' ';
                cells[y][x] = Cell.from(c, floor((x + 0.5f) * UNIT), floor((y + 0.5f) * UNIT));
            }
        }

        this.boardBackground = createGraphics(cols * UNIT, rows * UNIT);
        PGraphics g = boardBackground;
        g.background(0);
        g.stroke(0xFF1919A6);
        g.strokeWeight(UNIT * 0.02f);
        g.translate(HALF_UNIT, HALF_UNIT);
        g.scale(UNIT);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (cells[y][x] instanceof Cell.Wall) {
                    if (x < cols - 1 && cells[y][x + 1] instanceof Cell.Wall) {
                        g.line(x, y, x + 1, y);
                    }
                    if (y < rows - 1 && cells[y + 1][x] instanceof Cell.Wall) {
                        g.line(x, y, x, y + 1);
                    }
                }
            }
        }
    }

    public void setPlayer(PacMan player) {
        this.player = player;
    }

    public Cell get(int x, int y) {
        x = (x + board.cols) % board.cols;
        return cells[y][x];
    }

    public void addGhost(Ghost e) {
        ghosts.add(e);
    }

    public void forEachGhost(Consumer<Ghost> consumer) {
        ghosts.forEach(consumer);
    }

    public void update() {
        for (Ghost ghost : ghosts)
            ghost.update();
        player.update();
    }

    public void show(PGraphics g) {
        g.image(boardBackground, 0, 0);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cells[y][x].show(g, x, y);
            }
        }
        for (Ghost ghost : ghosts)
            ghost.show(g);
        player.show(g);
    }

    public boolean positionIntersectsPlayer(int x, int y) {
        return player != null && player.x == x && player.y == y;
    }
}
