package org.lobertrand.imgproc.filter;


import org.lobertrand.imgproc.core.Image;

import java.awt.*;

public class Superpose implements ImageFilter {

    private final Image other;
    private final float amount;

    private Superpose(Image other, float amount) {
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be a float between 0 and 1");
        }
        this.other = other;
        this.amount = amount;
    }

    @Override
    public Image applyTo(Image image) {
        Image resultImage = image.copy();
        Graphics2D g2 = (Graphics2D) resultImage.toBufferedImage().getGraphics();
        Composite old = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, amount));
        g2.drawImage(other.toBufferedImage(), 0, 0, null);
        g2.setComposite(old);
        return resultImage;

        // int[] pixel = new int[4];
        // int[] otherPixel = new int[4];
        // WritableRaster originalRaster = image.getWritableRaster();
        // PImage resultImage = image.copy();
        // WritableRaster resultRaster = resultImage.getWritableRaster();
        // WritableRaster otherRaster = other.getWritableRaster();
        // int w = Math.min(image.width, other.width);
        // int h = Math.min(image.height, other.height);
        // for (int x = 0; x < w; x++) {
        //     for (int y = 0; y < h; y++) {
        //         pixel = originalRaster.getPixel(x, y, pixel);
        //         otherPixel = otherRaster.getPixel(x, y, otherPixel);
        //         superposeInPlace(pixel, otherPixel, amount);
        //         resultRaster.setPixel(x, y, pixel);
        //     }
        // }
        // return resultImage;
    }

    private void superposeInPlace(int[] originalPixel, int[] otherPixel, float amount) {
        for (int i = 0; i < 4; i++) {
            originalPixel[i] = Math.round(originalPixel[i] * (1 - amount) + otherPixel[i] * amount);
        }
    }

    public static Superpose image(Image other, float amount) {
        return new Superpose(other, amount);
    }

    public static Superpose image(Image other) {
        return new Superpose(other, 1);
    }
}
