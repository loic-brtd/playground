package com.picasso.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PAsset extends JPanel {

    BufferedImage bufferedImage;

    public PAsset(String resourcePath) {
        setOpaque(false);
        InputStream inputStream = PAsset.class.getResourceAsStream(resourcePath);
        try {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            System.err.println("Can't create Image for " + resourcePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
    }
}
