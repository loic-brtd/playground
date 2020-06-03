package com.picasso.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PCanvas extends JPanel {

    private BufferedImage image;

    public PCanvas(BufferedImage image) {
        this.image = image;
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
