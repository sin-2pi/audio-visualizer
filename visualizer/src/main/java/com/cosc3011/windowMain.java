package com.cosc3011;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class windowMain {
    private JFrame frame;
    private JLabel errorMessageLabel; // Label to display error messages

    public windowMain() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        this.frame.setLayout(new BorderLayout(0, 0));
        this.frame.setTitle("Audio Visualizer");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1000, 800);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
        mainPanel.setBackground(Color.WHITE);

        Button create = new Button("Create a new project");
        JLabel or = new JLabel("Or");
        Button openExisting = new Button("Open an existing project");


        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);


        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fileName = new JFrame();
                fileName.setTitle("Create New Project");
                fileName.setLayout(new BorderLayout(0, 0));
                fileName.setSize(450, 100);
                fileName.setLocationRelativeTo(frame);
                fileName.setAlwaysOnTop(true);
                fileName.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fileName.setVisible(true);
        
                JPanel filePanel = new JPanel();
                filePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        
                JLabel label = new JLabel("Enter new file name: ");
                JTextField nameEnter = new JTextField(20);
                Button enter = new Button("Enter");
        
                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newFileName = nameEnter.getText();
                        frame.setTitle("Audio Visualizer - " + newFileName);
                        fileName.setVisible(false);
                        // Hide the main window and open the program window with the new name
                        frame.setVisible(false);
                        new programwindow(newFileName); // Pass the project name to the program window
                    }
                });
        
                filePanel.add(label);
                filePanel.add(nameEnter);
                filePanel.add(enter);
        
                fileName.add(filePanel);
            }
        });

        // Adds file explorer functionality to open file button
        // Validates file type as .wav or .mp3
        openExisting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser chooseFile = new JFileChooser();
                int returnValue = chooseFile.showOpenDialog(openExisting);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooseFile.getSelectedFile();
                    String fileName = selectedFile.getName();
                    String fileExtension = getFileExtension(fileName);

                    if (isValidFileType(fileExtension)) {
                        errorMessageLabel.setText(""); // Clear any previous error message
                        System.out.println("Valid file selected: " + selectedFile.getAbsolutePath());
                        // Hide the main window and open the program window
                        frame.setVisible(false);
                        new programwindow("Current Project"); // Pass a default project name
                    } else {
                        // If invalid file, show the error message and do NOT hide the main window
                        errorMessageLabel.setText("Invalid file type. Only .wav and .mp3 files are allowed.");
                    }
                }
            }
        });

        // Add panel to frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Add buttons to panel
        mainPanel.add(create);
        mainPanel.add(or);
        mainPanel.add(openExisting);
        mainPanel.add(errorMessageLabel); // Add the error label to the window
    }

    // Helper method to get the file extension
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    // Helper method to check if the file extension is valid (.wav or .mp3)
    private static boolean isValidFileType(String fileExtension) {
        return fileExtension.equals("wav") || fileExtension.equals("mp3");
    }

    // Program window class that represents the new frame
    public class programwindow {
        public programwindow(String projectName) {
            JFrame programFrame = new JFrame();
            programFrame.setTitle("Program Window - " + projectName); // Set the title to the project name
            programFrame.setSize(800, 600);
            programFrame.setLocationRelativeTo(null);
            programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            programFrame.setVisible(true);

            // Add components to program window as needed
            JPanel programPanel = new JPanel();
            programPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            programFrame.add(programPanel);

            JLabel programLabel = new JLabel(projectName);
            programPanel.add(programLabel);
        }
    }

    public static void main(String[] args) {
        new windowMain(); // Start the main window
    }
}
