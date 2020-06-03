package com.picasso.gui;

import com.picasso.util.Points;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PDesktop extends JPanel {

    private List<PImageEditorPanel> imagePanels;
    private PImageEditorPanel selectedPanel;
    private Point previousLocation;

    public PDesktop() {
        imagePanels = new ArrayList<>();
        setBackground(Theme.getCurrent().getBackground());
        setLayout(new ManualLayout());
    }

    public void addImageEditor(PImageEditorPanel imageEditorPanel) {
        imagePanels.add(0, imageEditorPanel);
        add(imageEditorPanel, 0);
        Point editorLocation = findInitialLocation(new Point(10, 10), imageEditorPanel);
        imageEditorPanel.setLocation(editorLocation);
        addMouseInteractions(imageEditorPanel);
        updateUI();
    }

    private void addMouseInteractions(PImageEditorPanel panel) {
        JComponent handle = panel.getTitleBar();
        handle.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                previousLocation = e.getPoint();
                selectPanel(panel);
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectPanel(panel);
            }
        });
        handle.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point offset = Points.sub(e.getPoint(), previousLocation);
                Point newLocation = Points.add(panel.getLocation(), offset);
                panel.setLocation(Points.max(newLocation, new Point(-8, 0)));
            }
        });
    }

    private void selectPanel(PImageEditorPanel imageEditorPanel) {
        selectedPanel = imageEditorPanel;
        imagePanels.remove(selectedPanel);
        imagePanels.add(0, selectedPanel);
        setComponentZOrder(imageEditorPanel, 0);
        repaint();
    }

    private Point findInitialLocation(Point tryPos, PImageEditorPanel imagePanel) {
        boolean anyOverlap = false;
        for (PImageEditorPanel p : imagePanels) {
            if (p != imagePanel && p.getLocation().equals(tryPos)) {
                anyOverlap = true;
                break;
            }
        }
        if (anyOverlap) {
            return findInitialLocation(Points.add(tryPos, new Point(20, 30)), imagePanel);
        }
        return tryPos;
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        // Required to overlap children components
        return false;
    }

}
