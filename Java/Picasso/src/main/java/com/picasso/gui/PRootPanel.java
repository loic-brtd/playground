package com.picasso.gui;

import com.picasso.gui.theme.Theme;

import javax.swing.*;
import java.awt.*;

public class PRootPanel extends JPanel {

    private static PRootPanel rootPanel;

    private final PDesktop desktop;

    private PRootPanel() {
        setLayout(new BorderLayout());
        setBackground(Theme.getCurrent().getBackground());
        desktop = new PDesktop();
        add(desktop, BorderLayout.CENTER);
    }

    public static PRootPanel create() {
        rootPanel = new PRootPanel();
        return rootPanel;
    }

    public PDesktop getDesktop() {
        return desktop;
    }

    public static PRootPanel getRootPanel() {
        return rootPanel;
    }
}
