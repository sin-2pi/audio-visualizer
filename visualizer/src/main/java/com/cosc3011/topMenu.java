package com.cosc3011;

import javax.swing.JMenuBar;

public class topMenu {
    JMenuBar menuBar = new JMenuBar();
    public topMenu(FileManager fm) {
        // create menus that require objects to be passed
        FileMenu fileMenu = new FileMenu();
        audioMenu am = new audioMenu();
        am.setFileManager(fm);
        am.setFileMenu(fileMenu);
        recordingMenu rm = new recordingMenu();
        rm.setFileManager(fm);

        // add items to menu bar
        menuBar.add(fileMenu);
        menuBar.add(am);
        menuBar.add(new SettingsMenu());
        menuBar.add(rm);
    }

}