package tests;

import java.util.ArrayList;
import java.util.List;

import core.PApplet;

public class Ripples extends PApplet {

    float maxSize;
    float rest = 20;
    List<Ripple> ripples = new ArrayList<Ripple>();

    @Override
    protected void setup() {
        size(800, 500);
        // fullScreen();
        colorMode(HSB);
        cursor(HAND);
        noStroke();
        maxSize = max(width, height);
    }

    @Override
    protected void draw() {
        background(0, 0, 0);

        for (int i = ripples.size() - 1; i >= 0; i--) {
            Ripple r = ripples.get(i);
            r.update();
            r.show();
            if (r.finished()) {
                ripples.remove(i);
            }
        }

        if (random(100) < 3) {
            ripples.add(new Ripple(floor(random(width)), floor(random(height))));
        }

    }

    @Override
    protected void mousePressed() {
        ripples.add(new Ripple(mouseX, mouseY));
    }

    class Ripple {

        int x, y;
        float size;
        int clr;
        boolean over;

        Ripple(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = rest;
            this.clr = color(random(360), 100, 100);
            this.over = false;
        }

        void update() {
            float amount = 0.1f;
            if (mouseIsPressed && !over) {
                amount = 0.01f;
            } else {
                over = true;
            }
            size = lerp(size, maxSize, amount);
        }

        void show() {
            fill(clr, map(size, rest, maxSize, 1, 0));
            ellipse(x, y, size, size);
        }

        boolean finished() {
            return size == maxSize;
        }

    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
