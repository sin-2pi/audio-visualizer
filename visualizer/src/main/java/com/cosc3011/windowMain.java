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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class windowMain {
    private JFrame frame;
    private JLabel errorMessageLabel; // Label to display error messages

    // Hardcoded colors
    private Color white = Color.WHITE;
    private Color black = new Color(43, 43, 43);
    private Color dark_gray = new Color(60, 63, 65);
    private Color red = Color.RED;

    public windowMain() {
        initialize();
    }

    public void initialize() {
        try {
            // Set system look and feel first
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Override with dark colors
            UIManager.put("Panel.background", black);
            UIManager.put("OptionPane.background", black);
            UIManager.put("TextField.background", dark_gray);
            UIManager.put("TextField.foreground", white);
            UIManager.put("TextField.caretForeground", white);
            UIManager.put("Button.background", dark_gray);
            UIManager.put("Button.foreground", white);
            UIManager.put("Label.foreground", white);
            UIManager.put("FileChooser.background", black);
            UIManager.put("FileChooser.foreground", white);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame = new JFrame();
        this.frame.setLayout(new BorderLayout(0, 0));
        this.frame.setTitle("Audio Visualizer");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1000, 800);
        this.frame.setLocationRelativeTo(null);
        

        // Set dark colors for the frame
        frame.getContentPane().setBackground(black);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
        mainPanel.setBackground(black);

        Button create = new Button("Create a new project");
        create.setBackground(dark_gray);
        create.setForeground(white);

        JLabel or = new JLabel("Or");
        or.setForeground(white);

        Button openExisting = new Button("Open an existing project");
        openExisting.setBackground(dark_gray);
        openExisting.setForeground(white);


        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(red);


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

                fileName.getContentPane().setBackground(black);
        
                JPanel filePanel = new JPanel();
                filePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
                filePanel.setBackground(black);
        
                JLabel label = new JLabel("Enter new file name: ");
                label.setForeground(white);

                JTextField nameEnter = new JTextField(20);
                nameEnter.setBackground(dark_gray);
                nameEnter.setForeground(white);
                nameEnter.setCaretColor(white);

                Button enter = new Button("Enter");
                enter.setBackground(dark_gray);
                enter.setForeground(white);
        
                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newFileName = nameEnter.getText();
                        frame.setTitle("Audio Visualizer - " + newFileName);
                        fileName.setVisible(false);
        
                        FileManager fileManager = new FileManager("projects");
                        boolean created = fileManager.createProjectDirectory(newFileName);
        
                        if (created) {
                            System.out.println("Project created successfully!");
                        }
                    }
                });
        
                filePanel.add(label);
                filePanel.add(nameEnter);
                filePanel.add(enter);
        
                fileName.add(filePanel);
            }
        });

        // Adds file explorer functionality to open file button
        // Validates file type as .wav
        openExisting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get cwd
                String cwd = System.getProperty("user.dir");

                // Create file object for this directory
                File workingDir = new File(cwd);

                // Navigate to 'projects' subdirectory
                File projectsDir = new File(cwd + File.separator + "projects");
                if (projectsDir.exists() && projectsDir.isDirectory()) {
                    workingDir = projectsDir;
                }

                // Create the file chooser with the working directory
                final JFileChooser chooseFile = new JFileChooser(workingDir);

                // Apply dark theme styling
                chooseFile.setBackground(new Color(43, 43, 43));
                chooseFile.setForeground(Color.WHITE);

                int returnValue = chooseFile.showOpenDialog(openExisting);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooseFile.getSelectedFile();
                    String fileName = selectedFile.getName();
                    String fileExtension = getFileExtension(fileName);

                    if (isValidFileType(fileExtension)) {
                        errorMessageLabel.setText(""); // Clear any previous error message
                        System.out.println("Valid .wav file selected: " + selectedFile.getAbsolutePath());
                        // Add your logic for processing the selected .wav file
                    } else {
                        errorMessageLabel.setText("Invalid file type. Only .wav files are allowed.");
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

        this.frame.setVisible(true);
    }

    // Helper method to get the file extension
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    // Helper method to check if the file extension is valid (.wav)
    private static boolean isValidFileType(String fileExtension) {
        return fileExtension.equals("wav");
    }
}

