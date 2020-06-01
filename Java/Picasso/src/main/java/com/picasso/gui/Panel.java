package com.picasso.gui;

import javax.swing.*;

public class Panel {

    private Panel() {
    }

    public static JPanel createRoot() {
        JPanel panel = new JPanel();
        panel.setBackground(Theme.getCurrent().getBackground());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JMenuBar());
        return panel;
    }

}
