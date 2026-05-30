package reprod_cc;

import core.PApplet;

public class Sandpiles extends PApplet {

    int[][] sandpiles;
    int cols;
    int rows;
    int zoom = 10;
    final int[] colors = { color(255, 255, 0), color(0, 185, 63), color(0, 104, 255), color(122, 0, 229) };

    @Override
    protected void setup() {
        size(800, 400);
        noStroke();
        cols = width / zoom;
        rows = height / zoom;
        sandpiles = new int[cols][rows];
        sandpiles[cols / 2][rows / 2] = 10000000;
    }

    void topple() {
        int[][] nextpiles = new int[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int num = sandpiles[x][y];
                if (num < 4) {
                    nextpiles[x][y] = sandpiles[x][y];
                }
            }
        }
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int num = sandpiles[x][y];
                if (num >= 4) {
                    nextpiles[x][y] += num - 4;
                    if (x + 1 < cols)
                        nextpiles[x + 1][y]++;
                    if (x - 1 >= 0)
                        nextpiles[x - 1][y]++;
                    if (y + 1 < rows)
                        nextpiles[x][y + 1]++;
                    if (y - 1 >= 0)
                        nextpiles[x][y - 1]++;
                }
            }
        }
        sandpiles = nextpiles;
    }

    void render() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int num = sandpiles[x][y];
                fill(num < 4 ? colors[num] : 255);
                rect(x * zoom, y * zoom, zoom, zoom);
            }
        }
    }

    @Override
    protected void draw() {
        render();
        // for (int i = 0; i < 1000; i++) {
        topple();
        // }

        // if (frameCount == 80) {
        // save("sand.jpeg");
        // close();
        // }
    }

    @Override
    protected void mousePressed() {
        switch (mouseButton) {
        case LEFT:
            noLoop();
            println("noLoop");
            break;
        case RIGHT:
            loop();
            println("loop");
            break;
        default:
            break;
        }
    }

    public static void main(String[] args) {
        new Sandpiles().start();
    }

}
