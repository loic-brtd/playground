package org.lobertrand.imgproc.filter;

import org.lobertrand.imgproc.core.Image;

import java.util.Arrays;

public class Convolution implements ImageFilter {

    private final int[][] matrix;
    private final int radius;

    private Convolution(int[][] matrix) {
        this.matrix = matrix;
        if (matrix.length % 2 == 0) {
            throw new IllegalArgumentException("Matrix must have odd dimensions");
        }
        radius = matrix.length / 2;
    }

    @Override
    public Image applyTo(Image image) {
        int[] localPixel = new int[4];
        Image resultImage = image.copy();
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                int[] averagePixel = new int[]{0, 0, 0, 255};
                int n = 0;
                for (int xOffset = -radius; xOffset <= radius; xOffset++) {
                    for (int yOffset = -radius; yOffset <= radius; yOffset++) {
                        int xLookup = x + xOffset;
                        int yLookup = y + yOffset;
                        if (isInBounds(image, xLookup, yLookup)) {
                            int coefficient = this.matrix[yOffset + radius][xOffset + radius];
                            localPixel = image.getPixel(xLookup, yLookup, localPixel);
                            weightedSumInPlace(averagePixel, localPixel, coefficient);
                            n += coefficient;
                        }
                    }
                }
                if (n != 0) {
                    divideInPlace(averagePixel, n);
                }
                resultImage.setPixel(x, y, averagePixel);
            }
        }
        return resultImage;
    }

    private void weightedSumInPlace(int[] averagePixel, int[] localPixel, int coefficient) {
        for (int i = 0; i < 3; i++) {
            averagePixel[i] += localPixel[i] * coefficient;
        }
    }

    private static void divideInPlace(int[] averagePixels, int n) {
        for (int i = 0; i < 3; i++) {
            averagePixels[i] /= n;
        }
    }

    private boolean isInBounds(Image image, int xLookup, int yLookup) {
        return xLookup >= 0 && xLookup < image.width() && yLookup >= 0 && yLookup < image.height();
    }

    public static Convolution matrix(int[][] matrix) {
        return new Convolution(matrix);
    }

    public static Convolution square(int radius, int content) {
        int size = radius * 2 + 1;
        int[][] matrix = new int[size][size];
        for (int[] rows : matrix) {
            Arrays.fill(rows, content);
        }
        return new Convolution(matrix);
    }

    public static Convolution circle(int radius) {
        int radiusSq = radius * radius;
        int size = radius * 2 + 1;
        int[][] matrix = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int distSq = (x - radius) * (x - radius) + (y - radius) * (y - radius);
                matrix[y][x] = distSq < radiusSq ? 1 : 0;
            }
        }
        return new Convolution(matrix);
    }

    public static Convolution donut(int innerRadius, int outerRadius) {
        int inRadSq = outerRadius * outerRadius;
        int outRadSq = innerRadius * innerRadius;
        int size = outerRadius * 2 + 1;
        int[][] matrix = new int[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int distSq = (x - outerRadius) * (x - outerRadius) + (y - outerRadius) * (y - outerRadius);
                matrix[y][x] = between(distSq, outRadSq, inRadSq) ? 1 : 0;
            }
        }
        return new Convolution(matrix);
    }

    private static boolean between(int value, int min, int max) {
        return min <= value && value <= max;
    }

}
