package game;

import static core.PCanvas.*;
import static core.PCanvas.loadStrings;
import static game.Game.board;
import static game.Game.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import core.PGraphics;

public class Board {

    public Cell[][] cells;
    public int cols, rows;
    private PacMan player;
    private List<Ghost> ghosts = new ArrayList<>();

    public Board(String file) {
        String[] lines = loadStrings(file);
        this.cols = lines[0].length();
        this.rows = lines.length;
        this.cells = new Cell[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                char c = lines[y].charAt(x);
                cells[y][x] = Cell.from(c, floor((x + 0.5f) * unit), floor((y + 0.5f) * unit));
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
        g.background(0);
        g.noStroke();
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                cells[y][x].show(g, x, y);
        g.fill(255, 255, 0);
        for (Ghost ghost : ghosts)
            ghost.show(g);
        player.show(g);
    }

}
