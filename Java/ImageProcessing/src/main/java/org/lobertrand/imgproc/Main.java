package org.lobertrand.imgproc;

import org.lobertrand.imgproc.core.ArrayImage;
import org.lobertrand.imgproc.filter.Grayscale;
import org.lobertrand.imgproc.tools.Show;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var path = "/home/loic/Images/wallpapers/vaporwave.jpg";

        var image = ArrayImage.load(path)
                .resize(700, -1)
                .filter(Grayscale.averageRGB());

        Show.frame(image);
    }
}