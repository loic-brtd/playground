package tests;

import components.Spinner;
import core.PApplet;

public class ArcTest extends PApplet {

    Spinner start;
    Spinner stop;

    @Override
    protected void setup() {
        size(600, 600);
        fill(255);
        textSize(30);
        textAlign(LEFT, CENTER);

        createHtml("start:").color(255);
        start = createSpinner(0, 10).changed(e -> redraw());
        createHtml("stop:").color(255);
        stop = createSpinner(0, 10).changed(e -> redraw());

        noLoop();

        angleMode(DEGREES);
    }

    @Override
    protected void draw() {
        background(0);
        noStroke();

        push();
        translate(width / 2, height / 2);
        int delta = 45;
        for (int i = 0; i < 8; i++) {
            if (i != 0)
                rotate(delta);
            text(i * delta, 170, 0);
        }
        pop();

        stroke(255, 0, 0);
        strokeWeight(10);
        arc(300, 300, 300, 300, start.value(), stop.value());
    }

    public static void main(String[] args) {
        new ArcTest().start();
    }

}
