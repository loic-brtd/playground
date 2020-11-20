package org.lobertrand.imgproc.core;

import org.lobertrand.imgproc.filter.ImageFilter;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Image {

    int width();

    int height();

    Image resize(int width, int height);

    Image copy();

    BufferedImage toBufferedImage();

    default int[][][] to3DArray() {
        int[][][] array = new int[height()][width()][4];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                var pixel = getPixel(j, i, new int[4]);
                System.arraycopy(pixel, 0, array[i][j], 0, 4);
            }
        }
        return array;
    }

    default int[][] toMonochrome2DArray() {
        int[][] array = new int[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                var pixel = getPixel(j, i, new int[4]);
                var average = (pixel[0] + pixel[1] + pixel[2]) / 3;
                array[i][j] = average;
            }
        }
        return array;
    }

    Image filter(ImageFilter filter);

    void setPixel(int x, int y, int[] rgba);

    int[] getPixel(int x, int y, int[] rgba);
}
