package tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import core.PApplet;
import core.PImage;

public class ExpansionDynamique extends PApplet {

    @Override
    protected void setup() {
        // PImage img = loadImage("res/kitten.jpg");
        PImage img = loadImage("res/cellules.jpg");
        img.resize(500, 0);
        img.grayScale();
        PImage h1 = histogramme(img);
        PImage img2 = expansionDynamique(img);
        PImage h2 = histogramme(img2);

        // img = applyFilter(img2, new int[][] {
        // { -1, 0, 1 },
        // { -1, 0, 1 },
        // { -1, 0, 1 }
        // });

        size(img.width * 2, img.height + 256);
        background(0);
        image(img, 0, 0);
        image(h1, 0, img.height);
        image(img2, width / 2, 0);
        image(h2, width / 2, img.height);
    }

    public PImage applyFilter(PImage img, int[][] filter) {
        PImage res = img.copy();
        res.loadPixels();
        for (int x = 1; x < res.width - 1; x++) {
            for (int y = 1; y < res.height - 1; y++) {
                int index = y * res.width + x;
                int ng = filter[2][2] * red(res.pixels[(y - 1) * res.width + (x - 1)])
                        + filter[2][1] * red(res.pixels[(y - 1) * res.width + (x)])
                        + filter[2][0] * red(res.pixels[(y - 1) * res.width + (x + 1)])
                        + filter[1][2] * red(res.pixels[(y) * res.width + (x - 1)])
                        + filter[1][1] * red(res.pixels[(y) * res.width + (x)])
                        + filter[1][2] * red(res.pixels[(y) * res.width + (x + 1)])
                        + filter[0][2] * red(res.pixels[(y + 1) * res.width + (x - 1)])
                        + filter[0][1] * red(res.pixels[(y + 1) * res.width + (x)])
                        + filter[0][0] * red(res.pixels[(y + 1) * res.width + (x + 1)]);
                ng = constrain(ng, 0, 255);
                res.pixels[index] = color(ng);
            }
        }
        res.updatePixels();
        return res;
    }

    public PImage histogramme(PImage img) {
        img.loadPixels();
        int[] histo = new int[256];
        for (int i = 0; i < img.pixels.length; i++) {
            int ng = red(img.pixels[i]);
            ng = constrain(ng, 0, 255);
            histo[ng]++;
        }
        int h = max(histo);
        PImage res = new PImage(256, 256);
        res.loadPixels();
        for (int x = 0; x < res.width; x++) {
            int normalized = 256 - (int) (histo[x] / (float) h * 256);
            for (int y = 0; y < normalized; y++) {
                int i = y * res.width + x;
                res.pixels[i] = color(255);
            }
        }
        res.updatePixels();
        return res;
    }

    public PImage expansionDynamique(PImage img) {
        PImage res = img.copy();
        res.loadPixels();
        int min = red(res.pixels[0]);
        int max = red(res.pixels[0]);
        for (int i = 0; i < res.pixels.length; i++) {
            if (red(res.pixels[i]) < min) {
                min = red(res.pixels[i]);
            }
            if (red(res.pixels[i]) > max) {
                max = red(res.pixels[i]);
            }
        }
        for (int i = 0; i < res.pixels.length; i++) {
            int ng = 255 * (red(res.pixels[i]) - min) / (max - min);
            ng = constrain(ng, 0, 255);
            res.pixels[i] = color(ng, alpha(res.pixels[i]));
        }
        res.updatePixels();
        return res;
    }

    public static void main(String[] args) {
        PApplet.run();
    }

    public void sayHello() {
        sleep((int) random(2000));
        println("Hello");
    }

    public void thread(String methodName) {
        new Thread(() -> {
            Method m = null;
            try {
                m = getClass().getMethod(methodName);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            if (m != null) {
                try {
                    m.invoke(this);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }).run();
    }

}
