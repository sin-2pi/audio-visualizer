package com.cosc3011;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class SettingsMenu extends JMenu {
    public SettingsMenu() {
        super("Settings");
        // Add menu items
        JMenuItem preferencesItem = new JMenuItem("Preferences");
        JMenuItem themesItem = new JMenuItem("Themes");

        // Add action listeners to menu items
        preferencesItem.addActionListener(e -> System.out.println("Preferences opened"));
        themesItem.addActionListener(e -> System.out.println("Themes opened"));

        // Add the menu items to Settings menu
        add(preferencesItem);
        add(themesItem);
    }
}