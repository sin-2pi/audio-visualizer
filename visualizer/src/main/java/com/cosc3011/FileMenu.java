package com.cosc3011;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Color;

public class FileMenu extends JMenu {
    private JFrame frame;
    // setting audio file to |||| so we can check whether it has been opened later on
    private String audioFile = "||||";
    public FileMenu() {
        super("File");
        // Add menu items
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add action listeners to the menu items
        newItem.addActionListener(e -> new windowMain());
        openItem.addActionListener(e ->  {
            File selectedFile = openFileChooser(frame);
            String fileName = selectedFile.getName();
            String fileExtension = getFileExtension(fileName);
            while (!isValidFileType(fileExtension) && !selectedFile.isDirectory()) {
                int result3 = JOptionPane.showConfirmDialog(null,
                    "Invalid file type. Only .wav files are allowed.",
                    "Error", JOptionPane.DEFAULT_OPTION);
                selectedFile = openFileChooser(frame);
                fileName = selectedFile.getName();
                fileExtension = getFileExtension(fileName);
            }
            if (!selectedFile.isDirectory()) {
                audioFile = selectedFile.getAbsolutePath();
            }
        });
        saveItem.addActionListener(e -> System.out.println("File saved"));
        exitItem.addActionListener(e -> System.exit(0));

        // Add menu items to File menu
        add(newItem);
        add(openItem);
        add(saveItem);
        add(exitItem);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private static boolean isValidFileType(String fileExtension) {
        return fileExtension.equals("wav");
    }

    public static File openFileChooser(JFrame frame) {
        String cwd = System.getProperty("user.dir");
        File workingDir = new File(cwd);
        File projectsDir = new File(cwd + File.separator + "bin");
        if (projectsDir.exists() && projectsDir.isDirectory()) {
            workingDir = projectsDir;
        }

        final JFileChooser chooseFile = new JFileChooser(workingDir);
        chooseFile.setBackground(new Color(43, 43, 43));
        chooseFile.setForeground(Color.WHITE);

        int returnValue = chooseFile.showOpenDialog(frame);

        File selectedFile = workingDir;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooseFile.getSelectedFile();
        }
        // if the dialog is canceled we return workingDir which breaks the loop
        return selectedFile;
    }

    public String getAudioFile() {
        return audioFile;
    }
}
