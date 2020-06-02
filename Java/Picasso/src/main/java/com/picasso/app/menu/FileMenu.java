package com.picasso.app.menu;

import com.picasso.gui.PFrame;
import com.picasso.gui.PImageEditorPanel;
import com.picasso.gui.PImagePoolPanel;
import com.picasso.gui.PRootPanel;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class FileMenu {

    public static void open() {
        File[] images = chooseImageFiles();

        PRootPanel rootPanel = PRootPanel.getRootPanel();
        PImagePoolPanel imagePoolPanel = rootPanel.getImagePoolPanel();

        for (File image : images) {
            System.out.println("image = " + image);
            ImageEditor imageEditor = new ImageEditor(image);
            imagePoolPanel.addImageEditor(new PImageEditorPanel(imageEditor));
        }
    }

    private static File[] chooseImageFiles() {
        FileDialog fileDialog = new FileDialog(PFrame.getMain());
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setMultipleMode(true);
        fileDialog.setFilenameFilter((dir, name) -> name.matches(".*\\.(jpg|jpeg|png|bmp)"));
        fileDialog.setVisible(true);
        return fileDialog.getFiles();
    }

}
