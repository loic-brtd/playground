package shadow_casting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.PApplet;

public class Demo2 extends PApplet {

    List<Segment> segments = new ArrayList<>();
    ShadowCasting shadowCasting;
    int cols, rows, scl;
    Cell[][] grid;

    int posX, posY;

    public void keyPressed() {
        switch (keyCode) {
        case ARROW_UP:
            posY = constrain(posY - 1, 0, rows - 1);
            break;
        case ARROW_RIGHT:
            posX = constrain(posX + 1, 0, cols - 1);
            break;
        case ARROW_DOWN:
            posY = constrain(posY + 1, 0, rows - 1);
            break;
        case ARROW_LEFT:
            posX = constrain(posX - 1, 0, cols - 1);
            break;
        default:
            break;
        }
    }

    public void setup() {
        // size(630, 360);
        fullScreen();
        // Grid
        scl = 30;
        cols = width / scl;
        rows = height / scl;
        grid = new Cell[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                boolean filled = x == 0 || y == 0 || x == cols - 1 || y == rows - 1;
                grid[y][x] = new Cell(filled);
            }
        }
        // Shadow Casting object
        shadowCasting = new ShadowCasting(segments);
        posX = cols / 2;
        posY = rows / 2;
    }

    public List<Segment> computeSegments(Cell[][] grid) {
        // Remove all
        List<Segment> newSegments = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                grid[y][x].resetEdges();
            }
        }

        // Segmentation
        HashMap<Integer, Segment> edgePool = new HashMap<>();
        int currentId = 1;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                Cell current = getCell(x, y);
                // We only consider filled cells
                if (current == null) {
                    continue;
                }
                Cell west = getCell(x - 1, y);
                Cell east = getCell(x + 1, y);
                Cell north = getCell(x, y - 1);
                Cell south = getCell(x, y + 1);

                if (west == null) {
                    // No west cell -> needs an edge
                    if (north == null || north.westEdgeId == 0) {
                        // New west edge
                        edgePool.put(currentId, new Segment(x * scl, y * scl, x * scl, (y + 1) * scl));
                        current.westEdgeId = currentId;
                        currentId++;
                    } else {
                        // North neighbour : extend edge
                        edgePool.get(north.westEdgeId).b.y += scl;
                        current.westEdgeId = north.westEdgeId;
                    }
                }

                if (east == null) {
                    // No east cell -> needs an edge
                    if (north == null || north.eastEdgeId == 0) {
                        // New east edge
                        edgePool.put(currentId, new Segment((x + 1) * scl, y * scl, (x + 1) * scl, (y + 1) * scl));
                        current.eastEdgeId = currentId;
                        currentId++;
                    } else {
                        // North neighbour : extend edge
                        edgePool.get(north.eastEdgeId).b.y += scl;
                        current.eastEdgeId = north.eastEdgeId;
                    }
                }

                if (north == null) {
                    // No north cell -> needs an edge
                    if (west == null || west.northEdgeId == 0) {
                        // New north edge
                        edgePool.put(currentId, new Segment(x * scl, y * scl, (x + 1) * scl, y * scl));
                        current.northEdgeId = currentId;
                        currentId++;
                    } else {
                        // West neighbour : extend edge
                        edgePool.get(west.northEdgeId).b.x += scl;
                        current.northEdgeId = west.northEdgeId;
                    }
                }

                if (south == null) {
                    // No south cell -> needs an edge
                    if (west == null || west.southEdgeId == 0) {
                        // New south edge
                        edgePool.put(currentId, new Segment(x * scl, (y + 1) * scl, (x + 1) * scl, (y + 1) * scl));
                        current.southEdgeId = currentId;
                        currentId++;
                    } else {
                        // West neighbour : extend edge
                        edgePool.get(west.southEdgeId).b.x += scl;
                        current.southEdgeId = west.southEdgeId;
                    }
                }

            }
        }

        newSegments.addAll(edgePool.values());
        return newSegments;
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && y >= 0 && x < cols && y < rows && grid[y][x].filled) {
            return grid[y][x];
        } else {
            return null;
        }
    }

    public void draw() {
        background(0);

        Point source = new Point(posX * scl + scl / 2, posY * scl + scl / 2);
        segments = computeSegments(grid);
        shadowCasting.setSegments(segments);
        List<Point> vertices = shadowCasting.getVertices(source);

        // // Draw segments
        // strokeWeight(5);
        // for (Segment s : segments) {
        // stroke(255, 0, 0);
        // line(s.a.x, s.a.y, s.b.x, s.b.y);
        // stroke(0, 0, 255);
        // point(s.a.x, s.a.y);
        // point(s.b.x, s.b.y);
        // }

        // Draw light as a polygon
        fill(255);
        noStroke();
        beginShape();
        for (Point p : vertices) {
            vertex(p.x, p.y);
        }
        endShape(CLOSE);

        // Draw grid state
        noStroke();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x].show(x, y);
            }
        }

        stroke(0);
        strokeWeight(5);
        point(posX * scl + scl / 2, posY * scl + scl / 2);
    }

    public void mousePressed() {
        if (mouseX >= 0 && mouseX < scl * cols && mouseY >= 0 && mouseY < scl * rows) {
            int x = mouseX / scl;
            int y = mouseY / scl;
            grid[y][x].toggle();
        }
    }

    class Cell {

        boolean filled;
        int northEdgeId, southEdgeId, eastEdgeId, westEdgeId;

        Cell(boolean filled) {
            this.filled = filled;
        }

        void toggle() {
            filled = !filled;
        }

        void resetEdges() {
            northEdgeId = southEdgeId = eastEdgeId = westEdgeId = 0;
        }

        void show(int x, int y) {
            stroke(0, 10);
            strokeWeight(1);
            if (filled) {
                fill(100, 100, 200);
            } else {
                noFill();
            }
            // fill(filled ? color(20, 20, 255) : color(0, 0));
            rect(x * scl, y * scl, scl, scl);

            // textAlign(CENTER_H, CENTER_V);
            // textSize(8);
            // noStroke();
            // fill(255);
            // text(westEdgeId, x * scl + 5, y * scl + scl / 2);
            // text(eastEdgeId, (x + 1) * scl - 5, y * scl + scl / 2);
            // text(northEdgeId, x * scl + scl / 2, y * scl + 5);
            // text(southEdgeId, x * scl + scl / 2, (y + 1) * scl - 5);
        }

    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
