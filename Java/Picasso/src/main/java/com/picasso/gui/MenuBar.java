package com.picasso.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MenuBar {

    private MenuBar() {
    }

    public static JMenuBar createMain() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Theme.getCurrent().getMenu());
        menuBar.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));

        JMenu file = createMenu("File");
        file.add(createItem("Open..."));
        file.add(createItem("Save"));
        menuBar.add(file);

        JMenu edit = createMenu("Edit");
        menuBar.add(edit);

        JMenu view = createMenu("View");
        menuBar.add(view);

        return menuBar;
    }

    private static JMenu createMenu(String name) {
        JMenu menu = new JMenu(name);
        applyCommonMenuStyle(menu);
        menu.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        JPopupMenu popupMenu = menu.getPopupMenu();
        applyCommonMenuStyle(popupMenu);
        popupMenu.setBorder(new LineBorder(Theme.getCurrent().getMenuBorder()));
        return menu;
    }

    private static JMenuItem createItem(String name) {
        JMenuItem item = new JMenuItem(name);
        applyCommonMenuStyle(item);
        item.setBorder(new EmptyBorder(new Insets(2, 2, 2, 2)));
        return item;
    }

    private static void applyCommonMenuStyle(JComponent menu) {
        menu.setFont(Theme.getCurrent().getMainFont());
        menu.setForeground(Theme.getCurrent().getOnMenu());
        menu.setBackground(Theme.getCurrent().getMenu());
    }

}
