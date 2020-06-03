package com.picasso.gui;

import javax.swing.*;
import java.awt.*;

public class PRootPanel extends JPanel {

    private static PRootPanel rootPanel;

    private final PDesktop imagePoolPanel;

    private PRootPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.getCurrent().getBackground());
        imagePoolPanel = new PDesktop();
        add(imagePoolPanel, BorderLayout.CENTER);
    }

    public static PRootPanel create() {
        rootPanel = new PRootPanel();
        return rootPanel;
    }

    public PDesktop getImagePoolPanel() {
        return imagePoolPanel;
    }

    public static PRootPanel getRootPanel() {
        return rootPanel;
    }
}
