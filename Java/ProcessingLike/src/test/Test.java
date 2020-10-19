package test;

import core.PApplet;
import core.PImage;

import java.util.Arrays;

public class Test extends PApplet {

    PImage img;

    @Override
    protected void setup() {
        // img = loadImage("/home/loic/Images/wallpapers/building.jpg");
        img = loadImage("/home/loic/Images/wallpapers/vaporwave.jpg");
        img.resize(600, 0);
        size(img.width, img.height);

        image(img, 0, 0);
    }

    @Override
    protected void draw() {
        img.loadPixels();
        // shuffle(img.pixels);
        Arrays.sort(img.pixels);
        img.updatePixels();
        image(img, 0, 0);
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
