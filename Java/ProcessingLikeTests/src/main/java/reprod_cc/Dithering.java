package reprod_cc;

import core.PApplet;
import core.PImage;

public class Dithering extends PApplet {

    PImage kitten;

    public void setup() {
        size(1200, 600);
        kitten = loadImage("res/kitten.jpg")
                .get(200, 0, 640, 640);
        kitten.resize(600, 600);
        kitten.grayScale();
        image(kitten, 0, 0);
        noLoop();
    }

    public void draw() {
        kitten.loadPixels();
        for (int y = 1; y < kitten.height - 1; y++) {
            for (int x = 1; x < kitten.width - 1; x++) {
                int pix = kitten.pixels[index(x, y)];

                float oldR = red(pix);
                float oldG = green(pix);
                float oldB = blue(pix);

                int factor = 1;
                int newR = round(factor * oldR / 255) * (255 / factor);
                int newG = round(factor * oldG / 255) * (255 / factor);
                int newB = round(factor * oldB / 255) * (255 / factor);
                kitten.pixels[index(x, y)] = color(newR, newG, newB);

                float errR = oldR - newR;
                float errG = oldG - newG;
                float errB = oldB - newB;

                int index = index(x + 1, y);
                int c = kitten.pixels[index];
                float r = red(c);
                float g = green(c);
                float b = blue(c);
                r = r + errR * 7 / 16f;
                g = g + errG * 7 / 16f;
                b = b + errB * 7 / 16f;
                kitten.pixels[index] = color(r, g, b);

                index = index(x - 1, y + 1);
                c = kitten.pixels[index];
                r = red(c);
                g = green(c);
                b = blue(c);
                r = r + errR * 3 / 16f;
                g = g + errG * 3 / 16f;
                b = b + errB * 3 / 16f;
                kitten.pixels[index] = color(r, g, b);

                index = index(x, y + 1);
                c = kitten.pixels[index];
                r = red(c);
                g = green(c);
                b = blue(c);
                r = r + errR * 4 / 16f;
                g = g + errG * 4 / 16f;
                b = b + errB * 4 / 16f;
                kitten.pixels[index] = color(r, g, b);

                index = index(x + 1, y + 1);
                c = kitten.pixels[index];
                r = red(c);
                g = green(c);
                b = blue(c);
                r = r + errR * 1 / 16f;
                g = g + errG * 1 / 16f;
                b = b + errB * 1 / 16f;
                kitten.pixels[index] = color(r, g, b);
            }
        }
        kitten.updatePixels();
        image(kitten, 600, 0);
    }

    int index(int x, int y) {
        return x + y * kitten.width;
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}
