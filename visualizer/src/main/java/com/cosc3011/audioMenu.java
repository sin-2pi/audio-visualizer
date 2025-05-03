package com.cosc3011;

import javax.swing.*;
import com.audioseperator.*;
import com.cosc3011.windowMain.programwindow;

public class audioMenu extends JMenu {
    FileManager myFM;
    FileMenu fileMenu;
    public audioMenu() {
        super("Audio");
        // Add menu items
        JMenuItem parseItem = new JMenuItem("Parse");
        JMenuItem visualizeItem = new JMenuItem("Visualize");

        // Add action listeners to menu items
        parseItem.addActionListener(e -> {
            // implement audio seperation logic
            if (fileMenu.getAudioFile() == "||||") {
                int result = JOptionPane.showConfirmDialog(null,
                    "Must open a .wav file first",
                    "Error", JOptionPane.DEFAULT_OPTION);
            } else {
                audioSeperator.run(fileMenu.getAudioFile(), myFM.getProjectDir());
            }
        });
        visualizeItem.addActionListener(e -> {
            programwindow.startVisualization(myFM.getProjectDir(), "brass");
            programwindow.startVisualization(myFM.getProjectDir(), "vocals");
        });

        // Add the menu items to Settings menu
        add(parseItem);
        add(visualizeItem);
    }
    public void setFileManager(FileManager fm) {
        myFM = fm;
    }
    public void setFileMenu(FileMenu fm) {
        fileMenu = fm;
    }
}
