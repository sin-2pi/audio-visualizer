package com.audioseperator;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class audioSeperator {
    public static void run(String audioFile, String outputPath) {
        String inputPath = audioFile;
        // create output names with formatted timestamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        String vocalsPath = outputPath + "vocals_" + formattedDateTime + ".wav";
        String brassPath = outputPath + "brass_" + formattedDateTime + ".wav";

        try {
            // 1) Vocals
            SeparationStrategy vocalStrat = StrategySelector.getStrategy("vocal");
            StemProcessor vocalProc = new StemProcessor(vocalStrat);
            vocalProc.process(new File(inputPath), vocalsPath);

            // 2) Brass
            SeparationStrategy brassStrat = StrategySelector.getStrategy("brass");
            StemProcessor brassProc = new StemProcessor(brassStrat);
            brassProc.process(new File(inputPath), brassPath);

            String message = "Successfully parsed to: " + outputPath;
            int result = JOptionPane.showConfirmDialog(null,
                    message, "Success", JOptionPane.DEFAULT_OPTION);
        } catch (Exception e) {
            int result = JOptionPane.showConfirmDialog(null,
                    "Processing failed, ensure that the .wav file is not too long",
                    "Error", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
        }
    }
}
