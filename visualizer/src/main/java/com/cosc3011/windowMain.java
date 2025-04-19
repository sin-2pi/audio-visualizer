package com.cosc3011;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("unused")
public class windowMain {
    private JFrame frame;
    private JLabel errorMessageLabel;

    public windowMain() {
        initialize();
    }

    public void initialize() {
        ModernUITheme.applyTheme();

        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 0));
        frame.setTitle("Audio Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(ModernUITheme.BACKGROUND_DARK);

        JPanel welcomePanel = createWelcomePanel();
        frame.add(welcomePanel, BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = ModernUITheme.createPanel(new BorderLayout());
        welcomePanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = ModernUITheme.createTitleLabel("Audio Visualizer");
        welcomePanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel imagePanel = ModernUITheme.createPanel(new BorderLayout());
        
        BufferedImage blankImage = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        ImageIcon blankIcon = new ImageIcon(blankImage);
        JLabel imageLabel = new JLabel(blankIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        welcomePanel.add(imagePanel, BorderLayout.CENTER);
        
        return welcomePanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = ModernUITheme.createPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(new EmptyBorder(0, 100, 50, 100));
        
        JPanel buttonPanel = ModernUITheme.createPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        JButton createButton = ModernUITheme.createPrimaryButton("Create New Project");
        createButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        createButton.addActionListener(e -> showCreateProjectDialog());
        
        JButton openButton = ModernUITheme.createSecondaryButton("Open Existing Project");
        openButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        openButton.addActionListener(e -> openFileChooser());
        
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonPanel.add(openButton);
        buttonPanel.add(Box.createHorizontalGlue());
        
        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(ModernUITheme.ERROR);
        errorMessageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        actionPanel.add(buttonPanel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(errorMessageLabel);
        
        return actionPanel;
    }

    private void showCreateProjectDialog() {
        JFrame dialogFrame = new JFrame();
        dialogFrame.setTitle("Create New Project");
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogFrame.setSize(450, 180);
        dialogFrame.setLocationRelativeTo(frame);
        dialogFrame.setAlwaysOnTop(true);
        dialogFrame.getContentPane().setBackground(ModernUITheme.BACKGROUND_DARK);
        
        JPanel contentPanel = ModernUITheme.createPanel(new BorderLayout(0, 20));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = ModernUITheme.createHeadingLabel("Create New Project");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = ModernUITheme.createPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JLabel nameLabel = ModernUITheme.createLabel("Project Name:");
        JTextField nameField = ModernUITheme.createTextField(20);
        
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = ModernUITheme.createPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton createButton = ModernUITheme.createPrimaryButton("Create");
        JButton cancelButton = ModernUITheme.createButton("Cancel");
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = nameField.getText().trim();
                if (projectName.isEmpty()) {
                    JLabel errorLabel = new JLabel("Project name cannot be empty");
                    errorLabel.setForeground(ModernUITheme.ERROR);
                    formPanel.add(errorLabel);
                    formPanel.revalidate();
                    return;
                }
                
                dialogFrame.dispose();
                frame.setVisible(false);
                new programwindow(projectName);
            }
        });
        
        cancelButton.addActionListener(e -> dialogFrame.dispose());
        
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialogFrame.add(contentPanel);
        dialogFrame.setVisible(true);
    }

    private void openFileChooser() {
        String cwd = System.getProperty("user.dir");
        File workingDir = new File(cwd);
        File projectsDir = new File(cwd + File.separator + "projects");
        if (projectsDir.exists() && projectsDir.isDirectory()) {
            workingDir = projectsDir;
        }

        JFileChooser fileChooser = new JFileChooser(workingDir);
        fileChooser.setDialogTitle("Open Audio File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Audio Files (*.wav, *.mp3)", "wav", "mp3"));

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String fileExtension = getFileExtension(fileName);

            if (isValidFileType(fileExtension)) {
                errorMessageLabel.setText("");
                System.out.println("Valid file selected: " + selectedFile.getAbsolutePath());
                frame.setVisible(false);
                new programwindow("Current Project");
            } else {
                showErrorDialog("Invalid File Type", "Only .wav and .mp3 files are allowed.");
            }
        }
    }

    private void showErrorDialog(String title, String message) {
        JFrame errorFrame = new JFrame(title);
        errorFrame.setSize(400, 150);
        errorFrame.setLocationRelativeTo(frame);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setAlwaysOnTop(true);
        errorFrame.getContentPane().setBackground(ModernUITheme.BACKGROUND_DARK);

        JPanel panel = ModernUITheme.createPanel(new BorderLayout(0, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(ModernUITheme.ERROR);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(errorLabel, BorderLayout.CENTER);

        JButton okButton = ModernUITheme.createButton("OK");
        okButton.addActionListener(e -> errorFrame.dispose());
        
        JPanel buttonPanel = ModernUITheme.createPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        errorFrame.add(panel);
        errorFrame.setVisible(true);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private static boolean isValidFileType(String fileExtension) {
        return fileExtension.equals("wav") || fileExtension.equals("mp3");
    }

    public class programwindow {
        private static JFrame programFrame;
        
        public programwindow(String projectName) {
            programFrame = new JFrame();
            programFrame.setTitle(projectName + " - Audio Visualizer");
            programFrame.setSize(1000, 800);
            programFrame.setLocationRelativeTo(null);
            programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            programFrame.getContentPane().setBackground(ModernUITheme.BACKGROUND_DARK);
            
            JPanel mainPanel = ModernUITheme.createPanel(new BorderLayout(10, 10));
            
            JPanel sidebarPanel = createSidebarPanel();
            mainPanel.add(sidebarPanel, BorderLayout.WEST);
            
            JPanel visualizerPanel = createVisualizerPanel();
            mainPanel.add(visualizerPanel, BorderLayout.CENTER);
            
            JPanel controlPanel = createControlPanel();
            mainPanel.add(controlPanel, BorderLayout.SOUTH);
            
            programFrame.add(mainPanel);
            programFrame.setJMenuBar(new topMenu().menuBar);
            programFrame.setVisible(true);
        }
        
        private JPanel createSidebarPanel() {
            JPanel sidebarPanel = ModernUITheme.createPanel();
            sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
            sidebarPanel.setPreferredSize(new Dimension(200, 0));
            sidebarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            JLabel titleLabel = ModernUITheme.createHeadingLabel("Instruments");
            titleLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            sidebarPanel.add(titleLabel);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            String[] instruments = {"Bass", "Keys", "Percussion", "Strings", "Vocals", "Winds"};
            for (String instrument : instruments) {
                JCheckBox checkBox = ModernUITheme.createCheckBox(instrument, true);
                checkBox.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
                sidebarPanel.add(checkBox);
                sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            JLabel vizLabel = ModernUITheme.createHeadingLabel("Visualization");
            vizLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            sidebarPanel.add(vizLabel);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            String[] vizTypes = {"Bars", "Waveform", "Circular", "Particles"};
            for (String type : vizTypes) {
                JCheckBox checkBox = ModernUITheme.createCheckBox(type, type.equals("Bars"));
                checkBox.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
                sidebarPanel.add(checkBox);
                sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            
            return sidebarPanel;
        }
        
        private JPanel createVisualizerPanel() {
            JPanel visualizerPanel = new JPanel(new BorderLayout());
            visualizerPanel.setBackground(ModernUITheme.BACKGROUND_DARK);
            visualizerPanel.setBorder(new EmptyBorder(10, 0, 10, 10));
            
            JLabel placeholderLabel = new JLabel("Visualization will appear here");
            placeholderLabel.setForeground(ModernUITheme.TEXT_SECONDARY);
            placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
            visualizerPanel.add(placeholderLabel, BorderLayout.CENTER);
            
            return visualizerPanel;
        }
        
        private JPanel createControlPanel() {
            JPanel controlPanel = ModernUITheme.createPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            
            JButton playButton = ModernUITheme.createPrimaryButton("Play");
            JButton pauseButton = ModernUITheme.createButton("Pause");
            JButton stopButton = ModernUITheme.createButton("Stop");
            JButton recordButton = ModernUITheme.createSecondaryButton("Record");
            
            controlPanel.add(playButton);
            controlPanel.add(pauseButton);
            controlPanel.add(stopButton);
            controlPanel.add(recordButton);
            
            return controlPanel;
        }
        
        public static JFrame getFrame() {
            return programFrame;
        }
    }

    public static void main(String[] args) {
        new windowMain();
    }
}
