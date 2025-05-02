package com.cosc3011;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("unused")
public class windowMain {
    private JFrame frame;
    private JLabel errorMessageLabel;

    private Color white = Color.WHITE;
    private Color black = new Color(43, 43, 43);
    private Color dark_gray = new Color(60, 63, 65);
    private Color red = Color.RED;

    private FileManager fm;

    public windowMain() {
        initialize();
    }

    public void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
        frame.getContentPane().setBackground(black);

        // Title Label at the top
        JLabel titleLabel = new JLabel("Audio Visualizer");
        titleLabel.setForeground(white);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Blank Image in the center
        BufferedImage blankImage = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        ImageIcon blankIcon = new ImageIcon(blankImage);
        JLabel imageLabel = new JLabel(blankIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(imageLabel, BorderLayout.CENTER);

        // Bottom Panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        bottomPanel.setBackground(black);

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

        bottomPanel.add(create);
        bottomPanel.add(or);
        bottomPanel.add(openExisting);
        bottomPanel.add(errorMessageLabel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

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
                        frame.setVisible(false);
                        // get current directory to pass to file manager
                        Path currentDir = Paths.get("").toAbsolutePath();
                        String filePathString = currentDir.toString();
                        fm = new FileManager(filePathString);
                        // have the file manager create a new save file
                        if (!fm.createProjectDirectory(newFileName)) {
                            int result = JOptionPane.showConfirmDialog(null,
                                "Failed to create a save filepath",
                                "Exit Program", JOptionPane.DEFAULT_OPTION);
                            if (result == 0) System.exit(0);
                        };
                        new programwindow(newFileName);
                    }
                });

                filePanel.add(label);
                filePanel.add(nameEnter);
                filePanel.add(enter);
                fileName.add(filePanel);
            }
        });

        openExisting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFolderChooser();
            }
        });

        this.frame.setVisible(true);
    }

    private void openFolderChooser() {
        String cwd = System.getProperty("user.dir");
        File workingDir = new File(cwd);
        File projectsDir = new File(cwd + File.separator + "bin");
        if (projectsDir.exists() && projectsDir.isDirectory()) {
            workingDir = projectsDir;
        }

        final JFileChooser chooseFile = new JFileChooser(workingDir);
        chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooseFile.setBackground(new Color(43, 43, 43));
        chooseFile.setForeground(Color.WHITE);
        chooseFile.setAcceptAllFileFilterUsed(false);
        int returnValue = chooseFile.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooseFile.getSelectedFile();
            String fileName = selectedFile.getName();

            errorMessageLabel.setText("");
            frame.setVisible(false);
            String filePath = selectedFile.getAbsolutePath() + "/";
            System.out.println(filePath);
            fm = new FileManager(filePath);
            new programwindow(selectedFile.getName());
        }
    }

    private void showErrorWindow(String message) {
        JFrame errorFrame = new JFrame("Error");
        errorFrame.setSize(400, 150);
        errorFrame.setLocationRelativeTo(frame);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setBackground(black);

        JLabel errorLabel = new JLabel(message);
        errorLabel.setForeground(red);
        panel.add(errorLabel);

        Button okButton = new Button("OK");
        okButton.setBackground(dark_gray);
        okButton.setForeground(white);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFrame.dispose();
                openFolderChooser();
            }
        });

        panel.add(okButton);
        errorFrame.add(panel);
        errorFrame.setVisible(true);
    }

    public class programwindow {
        private static JFrame programFrame;
        public programwindow(String projectName) {
            programFrame = new JFrame();
            programFrame.setTitle(projectName + " - Audio Visualizer");
            programFrame.setSize(1000, 800);
            programFrame.setLocationRelativeTo(null);
            programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            programFrame.setVisible(true);

            JPanel programPanel = new JPanel(new BorderLayout(0, 0));
            JPanel controlPanel = new JPanel(new GridBagLayout());
            JPanel displayPanel = new JPanel(new BorderLayout(0, 0));

            GridBagConstraints c = new GridBagConstraints();
            Insets ins = new Insets(25, 50, 25, 25);
            c.insets = ins;
            c.gridx = 0;

            JCheckBox instrument1 = new JCheckBox("Bass", true);
            c.gridy = 0;
            controlPanel.add(instrument1, c);
            JCheckBox instrument2 = new JCheckBox("Keys", true);
            c.gridy = 1;
            controlPanel.add(instrument2, c);
            JCheckBox instrument3 = new JCheckBox("Percussion", true);
            c.gridy = 2;
            controlPanel.add(instrument3, c);
            JCheckBox instrument4 = new JCheckBox("String", true);
            c.gridy = 3;
            controlPanel.add(instrument4, c);
            JCheckBox instrument5 = new JCheckBox("Vocals", true);
            c.gridy = 4;
            controlPanel.add(instrument5, c);
            JCheckBox instrument6 = new JCheckBox("Winds", true);
            c.gridy = 5;
            controlPanel.add(instrument6, c);

            programPanel.add(controlPanel, BorderLayout.LINE_START);
            programPanel.add(displayPanel, BorderLayout.CENTER);
            programFrame.add(programPanel);
            topMenu tm = new topMenu(fm); // passing file manager for recording menu
            programFrame.setJMenuBar(tm.menuBar);
        }

        public static JFrame getFrame() {
            return programFrame;
        }
    }

    public static void main(String[] args) {
        new windowMain();
    }
}
