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
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;



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

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    UIManager.put("control", new Color(50, 50, 50));
                    UIManager.put("text", white);
                    UIManager.put("nimbusBase", new Color(18, 30, 49));
                    UIManager.put("nimbusBlueGrey", new Color(40, 40, 40));
                    UIManager.put("nimbusLightBackground", new Color(60, 63, 65));

                    break;
                }
            }
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
                        
                        // Check if the project name is not empty
                        if (newFileName.trim().isEmpty()) {
                            JLabel errorLabel = new JLabel("Project name cannot be empty!");
                            errorLabel.setForeground(red);
                            filePanel.add(errorLabel);
                            filePanel.revalidate();
                            return;
                        }
                        
                        // Create the projects directory if it doesn't exist
                        File projectsDir = new File("projects");
                        if (!projectsDir.exists()) {
                            projectsDir.mkdirs();
                        }
                        
                        // Create the project directory
                        FileManager fileManager = new FileManager("projects");
                        boolean created = fileManager.createProjectDirectory(newFileName);
                        
                        if (created) {
                            System.out.println("Project directory created successfully!");
                            frame.setTitle("Audio Visualizer - " + newFileName);
                            fileName.setVisible(false);
                            
                            // Hide the main window and open the program window with the new name
                            frame.setVisible(false);
                            new programwindow(newFileName); // Pass the project name to the program window
                        } else {
                            // Project creation failed or already exists
                            JLabel errorLabel = new JLabel("Project already exists or couldn't be created!");
                            errorLabel.setForeground(red);
                            filePanel.add(errorLabel);
                            filePanel.revalidate();
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
        // Validates file type as .wav or .mp3
        openExisting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                LookAndFeel currentLF = UIManager.getLookAndFeel();

                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

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
                // chooseFile.setBackground(new Color(43, 43, 43));
                // chooseFile.setForeground(Color.WHITE);

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

                UIManager.setLookAndFeel(currentLF);
                SwingUtilities.updateComponentTreeUI(frame);
            } catch (Exception ex) {
                ex.printStackTrace();
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

    // Helper method to check if the file extension is valid (.wav or .mp3)
    private static boolean isValidFileType(String fileExtension) {
        return fileExtension.equals("wav") || fileExtension.equals("mp3");
    }

    // Program window class that represents the new frame
    public static class programwindow {
        public programwindow(String projectName) {
            JFrame programFrame = new JFrame();
            programFrame.setTitle("Audio Visualizer - " + projectName);
            programFrame.setSize(800, 600);
            programFrame.setLocationRelativeTo(null);
            programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Apply the same dark theme
            programFrame.getContentPane().setBackground(new Color(43, 43, 43));
            
            // Create a panel with BorderLayout
            JPanel programPanel = new JPanel(new BorderLayout());
            programPanel.setBackground(new Color(43, 43, 43));
            programFrame.add(programPanel);
            
            // Add a header panel
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.setBackground(new Color(60, 63, 65));
            
            // Project name label
            JLabel projectLabel = new JLabel("Project: " + projectName);
            projectLabel.setForeground(Color.WHITE);
            headerPanel.add(projectLabel);
            
            // Add some placeholder content
            JPanel contentPanel = new JPanel();
            contentPanel.setBackground(new Color(43, 43, 43));
            JLabel placeholderLabel = new JLabel("Audio visualization");
            placeholderLabel.setForeground(Color.WHITE);
            contentPanel.add(placeholderLabel);
            
            // Add panels to the main panel
            programPanel.add(headerPanel, BorderLayout.NORTH);
            programPanel.add(contentPanel, BorderLayout.CENTER);
            
            programFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new windowMain(); // Start the main window
    }
}
