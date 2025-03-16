package com.cosc3011;

import java.io.File;

public class FileManager {
    private String baseDirectory;

    public FileManager(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public boolean createProjectDirectory(String projectName) {
        File projectDir = new File(baseDirectory + "/" + projectName);
        
        if (!projectDir.exists()) {
            boolean created = projectDir.mkdirs(); 
            if (created) {
                System.out.println("Project folder created at: " + projectDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create project folder.");
            }
            return created;
        } else {
            System.out.println("Project folder already exists.");
            return false;
        }
    }
}
