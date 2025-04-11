package com.cosc3011;

import javax.swing.JMenuBar;

public class topMenu {
    JMenuBar menuBar = new JMenuBar();
    public topMenu() {
        // Create the menu bar and add it to the frame
        menuBar.add(new FileMenu());
        menuBar.add(new SettingsMenu());
    }
}