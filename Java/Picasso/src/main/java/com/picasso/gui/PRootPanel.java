package com.picasso.gui;

import javax.swing.*;
import java.awt.*;

public class PRootPanel extends JPanel {

    private static PRootPanel rootPanel;

    private final PImagePoolPanel imagePoolPanel;

    private PRootPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.getCurrent().getBackground());
        imagePoolPanel = new PImagePoolPanel();
        add(imagePoolPanel, BorderLayout.CENTER);
    }

    public static PRootPanel create() {
        rootPanel = new PRootPanel();
        return rootPanel;
    }

    public PImagePoolPanel getImagePoolPanel() {
        return imagePoolPanel;
    }

    public static PRootPanel getRootPanel() {
        return rootPanel;
    }
}
