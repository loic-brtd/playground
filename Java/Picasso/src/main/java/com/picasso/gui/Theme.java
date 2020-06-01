package com.picasso.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public class Theme {

    private static Theme current = createDefault();

    private Font mainFont;
    private Color background;
    private Color onBackground;
    private Color menu;
    private Color onMenu;
    private Color focusedMenu;
    private Color onFocusedMenu;
    private Color menuBorder;

    private Theme() {
        // Default theme parameters
        setMainFont(loadFont("/font/noto_sans/NotoSans-Regular.ttf")
                .map(f -> f.deriveFont(13f))
                .orElseGet(() -> new Font("Arial", Font.PLAIN, 12)));
        setBackground(Color.decode("#222222"));
        setOnBackground(Color.decode("#bbbbbb"));
        setMenu(Color.decode("#333333"));
        setOnMenu(onBackground);
        setFocusedMenu(Color.decode("#0055cc"));
        setOnFocusedMenu(Color.decode("#ffffff"));
        setMenuBorder(Color.decode("#444444"));
    }

    private Optional<Font> loadFont(String name) {
        try {
            InputStream inputStream = Theme.class.getResourceAsStream(name);
            Objects.requireNonNull(inputStream);
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return Optional.of(font);
        } catch (NullPointerException | FontFormatException | IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static Theme createDefault() {
        return new Theme();
    }

    public static Theme getCurrent() {
        return current;
    }

    public Font getMainFont() {
        return mainFont;
    }

    public void setMainFont(Font mainFont) {
        this.mainFont = mainFont;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getOnBackground() {
        return onBackground;
    }

    public void setOnBackground(Color onBackground) {
        this.onBackground = onBackground;
    }

    public Color getMenu() {
        return menu;
    }

    public void setMenu(Color menu) {
        this.menu = menu;
    }

    public Color getOnMenu() {
        return onMenu;
    }

    public void setOnMenu(Color onMenu) {
        this.onMenu = onMenu;
    }

    public Color getFocusedMenu() {
        return focusedMenu;
    }

    private void setFocusedMenu(Color focusedMenu) {
        this.focusedMenu = focusedMenu;
        UIManager.put("Menu.selectionBackground", this.focusedMenu);
        UIManager.put("MenuItem.selectionBackground", this.focusedMenu);
    }

    public Color getOnFocusedMenu() {
        return onFocusedMenu;
    }

    private void setOnFocusedMenu(Color onFocusedMenu) {
        this.onFocusedMenu = onFocusedMenu;
        UIManager.put("Menu.selectionForeground", this.onFocusedMenu);
        UIManager.put("MenuItem.selectionForeground", this.onFocusedMenu);
    }

    public Color getMenuBorder() {
        return menuBorder;
    }

    public void setMenuBorder(Color menuBorder) {
        this.menuBorder = menuBorder;
    }
}
