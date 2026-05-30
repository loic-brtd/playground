package tests;

import core.PApplet;

public class ColorModeTest extends PApplet {

    int side = screenWidth / 2 - 100;

    @Override
    protected void setup() {
        size(side * 2, side);
        noLoop();
    }

    @Override
    protected void draw() {
        colorMode(RGB, side);
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                stroke(i, j, 0);
                point(i, j);
            }
        }
        translate(side, 0);
        colorMode(HSB, side);
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                stroke(i, j, side);
                point(i, j);
            }
        }
    }

    public static void main(String[] args) {
        new ColorModeTest().start();
    }

}
