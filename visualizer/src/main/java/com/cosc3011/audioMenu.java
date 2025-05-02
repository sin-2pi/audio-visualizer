package com.cosc3011;

import javax.swing.*;

public class audioMenu extends JMenu {
    public audioMenu() {
        super("Audio");
        // Add menu items
        JMenuItem parseItem = new JMenuItem("Parse");
        JMenuItem visualizeItem = new JMenuItem("Visualize");

        // Add action listeners to menu items
        parseItem.addActionListener(e -> {
            // implement audio seperation logic
        });
        visualizeItem.addActionListener(e -> System.out.println("Visualize opened"));

        // Add the menu items to Settings menu
        add(parseItem);
        add(visualizeItem);
    }
}
