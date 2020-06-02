package com.picasso.gui;

import com.picasso.app.menu.ImageEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PImageEditorPanel extends JPanel {

    private JComponent titleBar;
    private JPanel imageView;

    public PImageEditorPanel(ImageEditor imageEditor) {
        // Overall layout
        setLayout(new BorderLayout());
        setBackground(Color.RED);
        setBorder(new LineBorder(Theme.getCurrent().getMenuBorder()));

        // Title bar
        titleBar = new JPanel(new GridBagLayout());
        titleBar.setBackground(Theme.getCurrent().getMenu());
        JLabel titleLabel = new JLabel(imageEditor.getName());
        titleLabel.setForeground(Theme.getCurrent().getOnMenu());
        titleLabel.setFont(Theme.getCurrent().getMainFont());
        titleLabel.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        titleBar.add(titleLabel);
        add(titleBar, BorderLayout.NORTH);

        // Image
        BufferedImage img = imageEditor.getBufferedImage();
        imageView = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        };
        imageView.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        add(imageView, BorderLayout.CENTER);
        updateUI();
    }

    public JComponent getTitleBar() {
        return titleBar;
    }
}
