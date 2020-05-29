package test;

import core.PApplet;

public class Test extends PApplet {

    @Override
    protected void setup() {
        size(400, 400);
        background(0);
    }

    @Override
    protected void draw() {
        noFill();
        strokeWeight(5);
        stroke(255);
        if (mouseIsPressed)
            line(pmouseX, pmouseY, mouseX, mouseY);
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
