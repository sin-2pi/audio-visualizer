package com.cosc3011;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FileMenu extends JMenu {
    public FileMenu() {
        super("File");
        // Add menu items
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add action listeners to the menu items
        newItem.addActionListener(e -> System.out.println("New file created"));
        openItem.addActionListener(e -> System.out.println("File opened"));
        saveItem.addActionListener(e -> System.out.println("File saved"));
        exitItem.addActionListener(e -> System.exit(0));

        // Add menu items to File menu
        add(newItem);
        add(openItem);
        add(saveItem);
        add(exitItem);
    }
}
