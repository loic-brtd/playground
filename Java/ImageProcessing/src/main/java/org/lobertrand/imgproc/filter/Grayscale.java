package org.lobertrand.imgproc.filter;

import org.lobertrand.imgproc.core.Image;

import java.awt.image.WritableRaster;

public class Grayscale implements ImageFilter {

    private Grayscale() {
    }

    @Override
    public Image applyTo(Image image) {
        int[] rgba = new int[4];
        Image resultImage = image.copy();
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                rgba = resultImage.getPixel(x, y, rgba);
                int average = Math.round((rgba[0] + rgba[1] + rgba[2]) / 3f);
                rgba[0] = average;
                rgba[1] = average;
                rgba[2] = average;
                resultImage.setPixel(x, y, rgba);
            }
        }
        return resultImage;
    }

    public static Grayscale average() {
        return new Grayscale();
    }
}
