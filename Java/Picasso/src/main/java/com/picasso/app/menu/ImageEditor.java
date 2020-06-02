package com.picasso.app.menu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor {

    private File originalFile;
    private BufferedImage bufferedImage;

    public ImageEditor(File file) {
        this.originalFile = file;

        try {
            bufferedImage = ImageIO.read(originalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return originalFile.getName();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
