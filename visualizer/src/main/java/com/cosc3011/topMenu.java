package com.cosc3011;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class topMenu {
    JMenuBar menuBar;
    
    public topMenu() {
        menuBar = new JMenuBar();
        menuBar.setBackground(ModernUITheme.BACKGROUND_MEDIUM);
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JMenu fileMenu = createFileMenu();
        menuBar.add(fileMenu);
        
        JMenu settingsMenu = createSettingsMenu();
        menuBar.add(settingsMenu);
        
        JMenu recordingMenu = createRecordingMenu();
        menuBar.add(recordingMenu);
        
        JMenu helpMenu = createHelpMenu();
        menuBar.add(helpMenu);
    }
    
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(ModernUITheme.TEXT_PRIMARY);
        
        JMenuItem newItem = new JMenuItem("New Project");
        JMenuItem openItem = new JMenuItem("Open Project");
        JMenuItem saveItem = new JMenuItem("Save Project");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        styleMenuItem(newItem);
        styleMenuItem(openItem);
        styleMenuItem(saveItem);
        styleMenuItem(exportItem);
        styleMenuItem(exitItem);
        
        newItem.addActionListener(e -> System.out.println("New project created"));
        openItem.addActionListener(e -> System.out.println("Open project dialog"));
        saveItem.addActionListener(e -> System.out.println("Project saved"));
        exportItem.addActionListener(e -> System.out.println("Export dialog"));
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        return fileMenu;
    }
    
    private JMenu createSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");
        settingsMenu.setForeground(ModernUITheme.TEXT_PRIMARY);
        
        JMenuItem preferencesItem = new JMenuItem("Preferences");
        JMenuItem visualizerItem = new JMenuItem("Visualizer Settings");
        JMenuItem audioItem = new JMenuItem("Audio Settings");
        JMenuItem themesItem = new JMenuItem("Themes");
        
        styleMenuItem(preferencesItem);
        styleMenuItem(visualizerItem);
        styleMenuItem(audioItem);
        styleMenuItem(themesItem);
        
        preferencesItem.addActionListener(e -> System.out.println("Preferences dialog"));
        visualizerItem.addActionListener(e -> System.out.println("Visualizer settings dialog"));
        audioItem.addActionListener(e -> System.out.println("Audio settings dialog"));
        themesItem.addActionListener(e -> System.out.println("Themes dialog"));
        
        settingsMenu.add(preferencesItem);
        settingsMenu.addSeparator();
        settingsMenu.add(visualizerItem);
        settingsMenu.add(audioItem);
        settingsMenu.add(themesItem);
        
        return settingsMenu;
    }
    
    private JMenu createRecordingMenu() {
        JMenu recordingMenu = new JMenu("Recording");
        recordingMenu.setForeground(ModernUITheme.TEXT_PRIMARY);
        
        JMenuItem startItem = new JMenuItem("Start Recording");
        JMenuItem stopItem = new JMenuItem("Stop Recording");
        JMenuItem settingsItem = new JMenuItem("Recording Settings");
        
        styleMenuItem(startItem);
        styleMenuItem(stopItem);
        styleMenuItem(settingsItem);
        
        startItem.addActionListener(e -> System.out.println("Start recording"));
        stopItem.addActionListener(e -> System.out.println("Stop recording"));
        settingsItem.addActionListener(e -> System.out.println("Recording settings dialog"));
        
        recordingMenu.add(startItem);
        recordingMenu.add(stopItem);
        recordingMenu.addSeparator();
        recordingMenu.add(settingsItem);
        
        return recordingMenu;
    }
    
    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(ModernUITheme.TEXT_PRIMARY);
        
        JMenuItem userGuideItem = new JMenuItem("User Guide");
        JMenuItem aboutItem = new JMenuItem("About");
        
        styleMenuItem(userGuideItem);
        styleMenuItem(aboutItem);
        
        userGuideItem.addActionListener(e -> System.out.println("User guide"));
        aboutItem.addActionListener(e -> showAboutDialog());
        
        helpMenu.add(userGuideItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        
        return helpMenu;
    }
    
    private void styleMenuItem(JMenuItem item) {
        item.setBackground(ModernUITheme.BACKGROUND_MEDIUM);
        item.setForeground(ModernUITheme.TEXT_PRIMARY);
    }
    
    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog();
        aboutDialog.setTitle("About Audio Visualizer");
        aboutDialog.setSize(400, 300);
        aboutDialog.setLocationRelativeTo(null);
        aboutDialog.setModal(true);
        
        JPanel panel = ModernUITheme.createPanel(new BoxLayout(ModernUITheme.createPanel(), BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = ModernUITheme.createTitleLabel("Audio Visualizer");
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        JLabel versionLabel = ModernUITheme.createLabel("Version 1.0");
        versionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        versionLabel.setForeground(ModernUITheme.TEXT_SECONDARY);
        
        JLabel descriptionLabel = ModernUITheme.createLabel("A modern audio visualization application");
        descriptionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        JButton closeButton = ModernUITheme.createButton("Close");
        closeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> aboutDialog.dispose());
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(versionLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(descriptionLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(closeButton);
        
        aboutDialog.add(panel);
        aboutDialog.setVisible(true);
    }
}
