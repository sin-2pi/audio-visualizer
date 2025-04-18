package com.cosc3011;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import java.io.File;



public class recordingMenu extends JMenu {
    GifCapture recording = new GifCapture();
    Path currentDir = Paths.get("").toAbsolutePath();
    String filePathString = currentDir.toString();
    String savePath = filePathString + "/save/";
    public recordingMenu() {
        super("Recording");

        // Add menu items
        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem stopItem = new JMenuItem("Stop");
        JMenuItem settingsItem = new JMenuItem("Settings");

        // Add action listeners to menu items
        startItem.addActionListener(e -> {
            if (!createSaveFolder(savePath)) {
                int result = JOptionPane.showConfirmDialog(null,
                    "Failed to create a save filepath",
                    "Exit Program", JOptionPane.DEFAULT_OPTION);
                if (result == 0) System.exit(0);
            }
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
            if (recording.stopCapture()) {
                String message = "Visualization successfully saved to: " + savePath;
                Color dark_gray = new Color(60, 63, 65);
                JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE ,JOptionPane.DEFAULT_OPTION);
                pane.setBackground(dark_gray);
                JDialog jd = pane.createDialog(this, "Message");
                jd.setVisible(true);
            }
        });

        settingsItem.addActionListener(e -> recordingSettingsMenu());

        // Add the menu items to Settings menu
        add(startItem);
        add(stopItem);
        add(settingsItem);
    }

    private void recordingSettingsMenu() {
        // display menu pop up
        // contains boxes to set fps and display current fps that is set
        // allows you to change the save filepath
        String fps = Integer.toString(recording.getFPS());
        JTextField field1 = new JTextField(fps);
        JTextField field2 = new JTextField(savePath);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Set FPS for gif capture (min 10, max 60, 30 recommended):"));
        panel.add(field1);
        panel.add(new JLabel("Set filepath for saving visualizations:"));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Recording Settings",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // update values in gif capture
            String newPath = field2.getText();
            if (!createSaveFolder(newPath)) {
                int result2 = JOptionPane.showConfirmDialog(null,
                    "Failed to create a save filepath, reverting to default",
                    "Error", JOptionPane.DEFAULT_OPTION);
            } else {
                savePath = newPath;
            }
            int newFPS = Integer.parseInt(field1.getText());
            if (newFPS < 10 || newFPS > 60) {
                int result3 = JOptionPane.showConfirmDialog(null,
                    "FPS must be between 10 and 60. 30 FPS recommended for stability",
                    "Error", JOptionPane.DEFAULT_OPTION);
            } else {
                recording.updateFPS(newFPS);
            }
            return;
        } else {
            return;
        }
    }

    private boolean createSaveFolder(String path) {
        String filePath = path + "tmp.txt";

        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                return false;
            }
        } else if (parentDir == null) {
            return false;
        }
        return true;
    }
}