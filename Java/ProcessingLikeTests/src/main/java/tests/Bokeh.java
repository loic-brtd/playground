package tests;

import java.util.ArrayList;
import java.util.List;

import core.PApplet;
import core.PVector;

public class Bokeh extends PApplet {

    List<Circle> circles = new ArrayList<Circle>();

    @Override
    protected void setup() {
//        size(800, 500);
         fullScreen();
        noStroke();
        colorMode(HSB);

        for (int i = 0; i < 100; i++) {
            circles.add(new Circle());
        }
    }

    @Override
    protected void draw() {
        background(0, 0, 100);

        for (Circle c : circles) {
            c.update();
            c.show();
        }

    }

    class Circle {

        float x, y;
        float size;
        float offset;
        int clr;
        float dx, dy;

        Circle() {
            this.x = random(width);
            this.y = random(height);
            this.offset = random(100);
            this.size = makeSize();
            this.clr = color(random(300, 400), 100, 100);
            PVector v = PVector.random2D();
            this.dx = v.x;
            this.dy = v.y;
        }

        void update() {
            size = makeSize();
            x += dx;
            y += dy;
            if (x > width + 200) {
                x = -200;
            } else if (x < -200) {
                x = width + 200;
            }
            if (y > height + 200) {
                y = -200;
            } else if (y < -200) {
                y = height + 200;
            }
        }

        float makeSize() {
            return abs(cos(frameCount * 0.01f + offset) * 130);
        }

        void show() {
            fill(clr, map(size, 0, 130, 0, 0.8f));
            circle(x, y, size);
        }

    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
