package com.cosc3011;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ThemeToggle {
    private static boolean dark_mode = true; // Default to dark mode
    private static List<JFrame> trackedFrames = new ArrayList<>();

    // Define color schemes
    private static final Color DARK_BACKGROUND = new Color(43, 43, 43);
    private static final Color DARK_GRAY = new Color(60, 63, 65);
    private static final Color WHITE = Color.WHITE;
    private static final Color LIGHT_BACKGROUND = Color.WHITE;
    private static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    private static final Color BLACK = Color.BLACK;
    private static final Color RED = Color.RED;

    public static void toggleTheme() {
        dark_mode = !dark_mode;

        // Apply theme to all tracked frames
        for (JFrame frame : trackedFrames) {
            applyThemeToFrame(frame);
        }
    }

    public static void applyThemeToFrame(JFrame frame) {
        if (frame == null) return;

        if (dark_mode) {
            // Dark mode colors
            applyDarkModeColors();
        } else {
            // Light mode colors
            applyLightModeColors();
        }

        // Update frame's content pane background
        frame.getContentPane().setBackground(dark_mode ? DARK_BACKGROUND : LIGHT_BACKGROUND);

        // Update UI for all components
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private static void applyDarkModeColors() {
        UIManager.put("Panel.background", DARK_BACKGROUND);
        UIManager.put("OptionPane.background", DARK_BACKGROUND);
        UIManager.put("TextField.background", DARK_GRAY);
        UIManager.put("TextField.foreground", WHITE);
        UIManager.put("TextField.caretForeground", WHITE);
        UIManager.put("Button.background", DARK_GRAY);
        UIManager.put("Button.foreground", WHITE);
        UIManager.put("Label.foreground", WHITE);
        UIManager.put("FileChooser.background", DARK_BACKGROUND);
        UIManager.put("FileChooser.foreground", WHITE);
        UIManager.put("CheckBox.foreground", WHITE);
    }

    private static void applyLightModeColors() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("Panel.background", LIGHT_BACKGROUND);
        UIManager.put("OptionPane.background", LIGHT_BACKGROUND);
        UIManager.put("TextField.background", LIGHT_GRAY);
        UIManager.put("TextField.foreground", BLACK);
        UIManager.put("TextField.caretForeground", BLACK);
        UIManager.put("Button.background", LIGHT_GRAY);
        UIManager.put("Button.foreground", BLACK);
        UIManager.put("Label.foreground", BLACK);
        UIManager.put("FileChooser.background", LIGHT_BACKGROUND);
        UIManager.put("FileChooser.foreground", BLACK);
        UIManager.put("CheckBox.foreground", BLACK);
    }

    public static boolean isDark() {
        return dark_mode;
    }

    // Method to track frames for theme toggling
    public static void trackFrame(JFrame frame) {
        if (frame != null && !trackedFrames.contains(frame)) {
            trackedFrames.add(frame);
            applyThemeToFrame(frame);
        }
    }
}