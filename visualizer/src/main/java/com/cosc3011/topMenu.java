package com.cosc3011;

import javax.swing.JMenuBar;

public class topMenu {
    JMenuBar menuBar = new JMenuBar();
    FileManager myFM;
    public topMenu(FileManager fm) {
        myFM = fm;
        // Create the menu bar and add it to the frame
        menuBar.add(new FileMenu());
        menuBar.add(new audioMenu());
        menuBar.add(new SettingsMenu());
        recordingMenu rm = new recordingMenu();
        rm.setFileManager(myFM);
        menuBar.add(rm);
        
    }

}