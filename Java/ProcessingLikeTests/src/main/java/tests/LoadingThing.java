package tests;

import core.PApplet;

public class LoadingThing extends PApplet {

    public void setup() {
        size(400, 400);
    }

    public void draw() {
        background(0);
        translate(width / 2, height / 2);
        rotate(-0.002f * frameCount);

        colorMode(HSB);
        strokeWeight(6);
        noFill();

        stroke(frameCount % 360, 100, 100, 0.7f);
        machin(120, 0);
        stroke((frameCount + 50) % 360, 100, 100, 0.7f);
        machin(120, PI);
    }

    public void machin(float radius, float offset) {
        int n = 200; // vertices
        int k = 6; // number of waves

        beginShape();

        for (int i = 0; i < n; i++) {
            float angle = i * (TWO_PI / n); // angle (0..TWO_PI)
            float rotation = (i + frameCount) % n; // rotation (0..n)

            float wave = sin(angle * k + offset) * 12;
            float fade = pow(
                    (rotation < n / 2) ? rotation / (n / 2) : (n - rotation) / (n / 2),
                    2);
            float delta = wave * fade;

            // vertex position
            float x = (radius + delta) * sin(angle);
            float y = (radius + delta) * cos(angle);
            vertex(x, y);
        }

        endShape(CLOSE);
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
