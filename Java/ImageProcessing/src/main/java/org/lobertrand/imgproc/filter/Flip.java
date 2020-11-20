package org.lobertrand.imgproc.filter;

import org.lobertrand.imgproc.core.Image;

import java.awt.image.WritableRaster;

public class Flip implements ImageFilter {

    private final boolean horizontal;

    private Flip(boolean horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public Image applyTo(Image image) {
        int[] pixel = new int[4];
        Image resultImage = image.copy();
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                int flippedX = horizontal ? image.width() - 1 - x : x;
                int flippedY = horizontal ? y : image.height() - 1 - y;
                pixel = image.getPixel(flippedX, flippedY, pixel);
                resultImage.setPixel(x, y, pixel);
            }
        }
        return resultImage;
    }

    public static Flip horizontally() {
        return new Flip(true);
    }

    public static Flip vertically() {
        return new Flip(false);
    }
}
