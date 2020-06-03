package com.picasso.gui;

import com.picasso.app.menu.ImageEditor;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PImageEditorPanel extends JXPanel {

    private JComponent titleBar;
    private PCanvas canvas;

    public PImageEditorPanel(ImageEditor imageEditor) {
        // Overall layout
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(createBorder());

        // Title bar
        titleBar = new JPanel(new GridBagLayout());
        titleBar.setBackground(Theme.getCurrent().getMenu());
        JLabel titleLabel = new JLabel(imageEditor.getName());
        titleLabel.setForeground(Theme.getCurrent().getOnMenu());
        titleLabel.setFont(Theme.getCurrent().getMainFont());
        titleLabel.setBorder(new EmptyBorder(new Insets(4, 2, 4, 2)));
        titleBar.add(titleLabel);
        PAsset cross = new PAsset("/image/cross.png");
        titleBar.add(cross);
        cross.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("close " + imageEditor.getName());
            }
        });

        add(titleBar, BorderLayout.NORTH);

        // Image
        canvas = new PCanvas(imageEditor.getBufferedImage());
        add(canvas, BorderLayout.CENTER);
    }

    private Border createBorder() {
        Border lineBorder = new LineBorder(Theme.getCurrent().getMenuBorder());
        DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        // shadow.setShowTopShadow(true);
        shadow.setShadowOpacity(0.3f);
        shadow.setShadowSize(8);
        shadow.setCornerSize(8);
        return new CompoundBorder(shadow, lineBorder);
    }

    public JComponent getTitleBar() {
        return titleBar;
    }

    public PCanvas getCanvas() {
        return canvas;
    }
}
