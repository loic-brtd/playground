package com.picasso.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PFrame extends JFrame {

    private static PFrame mainFrame;

    private PFrame() {
        setBackground(Theme.getCurrent().getBackground());
        setJMenuBar(PMenuBar.createMain());
        setContentPane(PRootPanel.create());
        setPreferredSize(new Dimension(700, 500));
        setMinimumSize(new Dimension(500, 300));
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Picasso");
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_W) {
                    System.exit(0);
                }
            }
        });
    }

    public static PFrame getMain() {
        if (mainFrame == null)
            mainFrame = createMain();
        return mainFrame;
    }

    private static PFrame createMain() {
        return new PFrame();
    }
}
