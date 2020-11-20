package org.lobertrand.imgproc.filter;

import org.lobertrand.imgproc.core.Image;

import java.awt.image.WritableRaster;

public class Blur implements ImageFilter {

    private final int radius;
    private final int radiusSquared;
    private final boolean circleShape;

    private Blur(int radius, boolean circleShape) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be a positive integer");
        }
        this.radius = radius;
        radiusSquared = radius * radius;
        this.circleShape = circleShape;
    }

    @Override
    public Image applyTo(Image image) {
        int[] localPixel = new int[4];
        Image resultImage = image.copy();
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                int[] averagePixel = new int[]{0, 0, 0, 0};
                int n = 0;
                for (int xOffset = -radius; xOffset <= radius; xOffset++) {
                    for (int yOffset = -radius; yOffset <= radius; yOffset++) {
                        int xLookup = x + xOffset;
                        int yLookup = y + yOffset;
                        if (considerNeighbour(image, x, y, xLookup, yLookup)) {
                            localPixel = image.getPixel(xLookup, yLookup, localPixel);
                            addInPlace(averagePixel, localPixel);
                            n++;
                        }
                    }
                }
                divideInPlace(averagePixel, n);
                resultImage.setPixel(x, y, averagePixel);
            }
        }
        return resultImage;
    }

    private boolean isInBounds(Image image, int xLookup, int yLookup) {
        return xLookup >= 0 && xLookup < image.width() && yLookup >= 0 && yLookup < image.height();
    }

    private boolean isInCircle(int x, int y, int xLookup, int yLookup) {
        float distanceSquared = (x - xLookup) * (x - xLookup) + (y - yLookup) * (y - yLookup);
        return distanceSquared < radiusSquared;
    }

    private boolean considerNeighbour(Image image, int x, int y, int xLookup, int yLookup) {
        if (circleShape) {
            return isInBounds(image, xLookup, yLookup) && isInCircle(x, y, xLookup, yLookup);
        } else {
            return isInBounds(image, xLookup, yLookup);
        }
    }

    private static void addInPlace(int[] pixelA, int[] pixelB) {
        for (int i = 0; i < 4; i++) {
            pixelA[i] += pixelB[i];
        }
    }

    private static void divideInPlace(int[] averagePixel, int n) {
        for (int i = 0; i < 4; i++) {
            averagePixel[i] /= n;
        }
    }

    public static Blur square(int radius) {
        return new Blur(radius, false);
    }

    public static Blur circle(int radius) {
        return new Blur(radius, true);
    }
}
