package com.picasso.gui.theme;

import com.picasso.util.Fonts;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

public class Theme {

    private static Theme current = new Theme();

    private Font mainFont;
    private Color background;
    private Color onBackground;
    private Color menu;
    private Color onMenu;
    private Color focusedMenu;
    private Color onFocusedMenu;
    private Color menuBorder;
    private Color thumb;
    private Color focusedThumb;

    private Theme() {
        // Default theme parameters
        setMainFont(Fonts.loadFont("/font/noto_sans/NotoSans-Regular.ttf")
                .map(f -> f.deriveFont(13f))
                .orElseGet(() -> new Font("Arial", Font.PLAIN, 12)));
        setBackground(decode("#222222"));
        setOnBackground(decode("#bbbbbb"));
        setMenu(decode("#333333"));
        setOnMenu(onBackground);
        setFocusedMenu(decode("#0055cc"));
        setOnFocusedMenu(decode("#ffffff"));
        setMenuBorder(decode("#444444"));
        setThumb(decode("#555555"));
        setFocusedThumb(decode("#666666"));
    }

    public static Theme getCurrent() {
        return current;
    }

    public static void setCurrent(Theme current) {
        Theme.current = current;
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

    public void setFocusedMenu(Color focusedMenu) {
        this.focusedMenu = focusedMenu;
        UIManager.put("Menu.selectionBackground", this.focusedMenu);
        UIManager.put("MenuItem.selectionBackground", this.focusedMenu);
    }

    public Color getOnFocusedMenu() {
        return onFocusedMenu;
    }

    public void setOnFocusedMenu(Color onFocusedMenu) {
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

    public Color getThumb() {
        return thumb;
    }

    public void setThumb(Color thumb) {
        this.thumb = thumb;
    }

    public Color getFocusedThumb() {
        return focusedThumb;
    }

    public void setFocusedThumb(Color focusedThumb) {
        this.focusedThumb = focusedThumb;
    }
}
