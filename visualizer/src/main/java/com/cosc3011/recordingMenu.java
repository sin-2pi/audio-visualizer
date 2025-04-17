package com.cosc3011;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.io.File;



public class recordingMenu extends JMenu {
    public recordingMenu() {
        super("Recording");

        GifCapture recording = new GifCapture();

        Path currentDir = Paths.get("").toAbsolutePath();
        String filePathString = currentDir.toString();
        String savePath = filePathString + "/save/";
        createSaveFolder(savePath);

        // Add menu items
        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem stopItem = new JMenuItem("Stop");
        JMenuItem settingsItem = new JMenuItem("Settings");

        // Add action listeners to menu items
        startItem.addActionListener(e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDateTime = now.format(formatter);
            String finalPath = savePath + formattedDateTime + ".gif";
            recording.updatePath(finalPath);
            try {
                // frame is not correctly capturing right now so there is nothing to encode
                recording.startCapture(windowMain.programwindow.getFrame());
            } catch (InterruptedException p) {
                p.printStackTrace();
                System.out.println("Gif recording failed!");
            }
        });

        stopItem.addActionListener(e -> {
            recording.stopCapture();
            String message = "Visualization successfully saved to: " + savePath;
            Color dark_gray = new Color(60, 63, 65);
            JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE ,JOptionPane.DEFAULT_OPTION);
            pane.setBackground(dark_gray);
            JDialog jd = pane.createDialog(this, "Message");
            jd.setVisible(true);
        });

        settingsItem.addActionListener(e -> System.out.println("Opened recording settings"));

        // Add the menu items to Settings menu
        add(startItem);
        add(stopItem);
        add(settingsItem);
    }

    private void recordingSettingsMenu() {
        
    }

    private void createSaveFolder(String path) {
        String filePath = path + "tmp.txt";

        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Directories created successfully.");
            } else {
                System.err.println("Failed to create directories.");
            }
        }
    }
}