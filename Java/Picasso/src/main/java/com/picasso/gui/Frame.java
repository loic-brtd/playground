package com.picasso.gui;

import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame {

    private Frame() {
    }

    public static JFrame createMain() {
        JFrame frame = new JFrame();
        frame.setBackground(Theme.getCurrent().getBackground());
        frame.setJMenuBar(MenuBar.createMain());
        frame.setContentPane(Panel.createRoot());
        frame.setSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Picasso");
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_W) {
                    System.exit(0);
                }
            }
        });
        return frame;
    }
}
