package com.cosc3011;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class ModernUITheme {
    public static final Color BACKGROUND_DARK = new Color(18, 18, 18);
    public static final Color BACKGROUND_MEDIUM = new Color(30, 30, 30);
    public static final Color BACKGROUND_LIGHT = new Color(43, 43, 43);
    public static final Color ACCENT_PRIMARY = new Color(86, 180, 233);
    public static final Color ACCENT_SECONDARY = new Color(230, 159, 0);
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    public static final Color ERROR = new Color(255, 69, 58);
    public static final Color SUCCESS = new Color(48, 209, 88);
    
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    public static void applyTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            UIManager.put("Panel.background", BACKGROUND_DARK);
            UIManager.put("OptionPane.background", BACKGROUND_DARK);
            UIManager.put("TextField.background", BACKGROUND_LIGHT);
            UIManager.put("TextField.foreground", TEXT_PRIMARY);
            UIManager.put("TextField.caretForeground", TEXT_PRIMARY);
            UIManager.put("Button.background", BACKGROUND_LIGHT);
            UIManager.put("Button.foreground", TEXT_PRIMARY);
            UIManager.put("Label.foreground", TEXT_PRIMARY);
            UIManager.put("CheckBox.foreground", TEXT_PRIMARY);
            UIManager.put("CheckBox.background", BACKGROUND_DARK);
            UIManager.put("ComboBox.background", BACKGROUND_LIGHT);
            UIManager.put("ComboBox.foreground", TEXT_PRIMARY);
            UIManager.put("Menu.foreground", TEXT_PRIMARY);
            UIManager.put("Menu.background", BACKGROUND_MEDIUM);
            UIManager.put("MenuBar.foreground", TEXT_PRIMARY);
            UIManager.put("MenuBar.background", BACKGROUND_MEDIUM);
            UIManager.put("MenuItem.foreground", TEXT_PRIMARY);
            UIManager.put("MenuItem.background", BACKGROUND_MEDIUM);
            UIManager.put("FileChooser.background", BACKGROUND_DARK);
            UIManager.put("FileChooser.foreground", TEXT_PRIMARY);
            
            setUIFont(new FontUIResource(NORMAL_FONT));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setUIFont(FontUIResource font) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }
    
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BACKGROUND_LIGHT);
        button.setForeground(TEXT_PRIMARY);
        button.setFont(NORMAL_FONT);
        button.setFocusPainted(false);
        button.setBorder(createButtonBorder());
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_PRIMARY);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BACKGROUND_LIGHT);
            }
        });
        
        return button;
    }
    
    public static JButton createPrimaryButton(String text) {
        JButton button = createButton(text);
        button.setBackground(ACCENT_PRIMARY);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_PRIMARY.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_PRIMARY);
            }
        });
        
        return button;
    }
    
    public static JButton createSecondaryButton(String text) {
        JButton button = createButton(text);
        button.setBackground(ACCENT_SECONDARY);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_SECONDARY.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_SECONDARY);
            }
        });
        
        return button;
    }
    
    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBackground(BACKGROUND_LIGHT);
        textField.setForeground(TEXT_PRIMARY);
        textField.setCaretColor(TEXT_PRIMARY);
        textField.setFont(NORMAL_FONT);
        textField.setBorder(createTextFieldBorder());
        return textField;
    }
    
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_PRIMARY);
        label.setFont(NORMAL_FONT);
        return label;
    }
    
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_PRIMARY);
        label.setFont(TITLE_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
    
    public static JLabel createHeadingLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_PRIMARY);
        label.setFont(HEADING_FONT);
        return label;
    }
    
    public static JCheckBox createCheckBox(String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text, selected);
        checkBox.setBackground(BACKGROUND_DARK);
        checkBox.setForeground(TEXT_PRIMARY);
        checkBox.setFont(NORMAL_FONT);
        checkBox.setFocusPainted(false);
        return checkBox;
    }
    
    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_DARK);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }
    
    public static JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND_DARK);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }
    
    private static Border createButtonBorder() {
        return new CompoundBorder(
            new LineBorder(BACKGROUND_MEDIUM, 1, true),
            new EmptyBorder(8, 15, 8, 15)
        );
    }
    
    private static Border createTextFieldBorder() {
        return new CompoundBorder(
            new LineBorder(BACKGROUND_MEDIUM, 1, true),
            new EmptyBorder(5, 8, 5, 8)
        );
    }
}
