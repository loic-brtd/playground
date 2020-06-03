package com.picasso.gui;

import java.awt.*;

public class ManualLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        Insets insets = parent.getInsets();
        dim.width = insets.left + insets.right;
        dim.height = insets.top + insets.bottom;
        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component c : parent.getComponents()) {
            if (c.isVisible()) {
                Dimension d = c.getPreferredSize();
                // int x = Math.max(c.getX(), 0);
                // int y = Math.max(c.getY(), 0);
                c.setBounds(c.getX(), c.getY(), d.width, d.height);
            }
        }
    }

}