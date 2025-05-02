package com.cosc3011;

import java.io.File;

public class FileManager {
    private String baseDirectory;
    private String projectDir;

    public FileManager(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public boolean createProjectDirectory(String projectName) {
        projectDir = baseDirectory + "/bin/" + projectName + "/";
        // switched to use my createSaveFolder function since its already setup
        // little more robust and creates the save folder without anything in it
        // this way we can save the project folders with .wav files there
        if (createSaveFolder(projectDir)) {
            System.out.println("Project folder created at: " + projectDir);
            return true;
        } else {
            return false;
        }
    }

    public String getProjectDir() {
        return projectDir;
    }

    // attempts to create save folder
    // returns false on failure and true when successful
    public static boolean createSaveFolder(String path) {
        String filePath = path + "tmp.txt";

        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                return false;
            }
        } else if (parentDir == null) {
            return false;
        }
        return true;
    }
}
