package game;

import static core.PCanvas.*;
import static core.PCanvas.loadStrings;
import static game.Game.board;
import static game.Game.unit;

import java.util.ArrayList;
import java.util.List;

import core.PGraphics;

public class Board {

    Cell[][] cells;
    int cols, rows;
    int halfUnit;

    List<MovingEntity> movingEntities = new ArrayList<>();

    Board(String file) {
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

    Cell get(int x, int y) {
        return cells[y][x];
    }

    void add(MovingEntity e) {
        movingEntities.add(e);
    }

    public boolean isPointAccessible(int xx, int yy) {
        // Wrap around
        // xx = (xx + board.cols) % board.cols;

        int cellX = constrain(xx / unit, 0, cols - 1);
        int cellY = constrain(yy / unit, 0, rows - 1);

        // println(cellX, cellY);

        return board.get(cellX, cellY).isAccessible();
    }

    void update() {
        for (MovingEntity e : movingEntities)
            e.update();
    }

    void show(PGraphics g) {
        g.background(0);
        g.noStroke();
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                cells[y][x].show(g, x, y);
        g.fill(255, 255, 0);
        for (MovingEntity e : movingEntities)
            e.show(g);
    }

}
