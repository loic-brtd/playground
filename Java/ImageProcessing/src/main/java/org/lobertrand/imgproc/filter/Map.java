package org.lobertrand.imgproc.filter;

import org.lobertrand.imgproc.core.Image;

import java.util.function.IntUnaryOperator;

public class Map implements ImageFilter {

    private final IntUnaryOperator unaryOperator;

    private Map(IntUnaryOperator horizontal) {
        this.unaryOperator = horizontal;
    }

    @Override
    public Image applyTo(Image image) {
        return image.map((pixel, y, x) -> applyOnRGB(pixel));
    }

    private int[] applyOnRGB(int[] pixel) {
        for (int i = 0; i < 3; i++) {
            pixel[i] = unaryOperator.applyAsInt(pixel[i]);
        }
        return pixel;
    }

    public static Map unaryOperator(IntUnaryOperator function) {
        return new Map(function);
    }

}
