package com.audioseperator;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String inputPath = args.length > 0 ? args[0] : "input.wav";

        try {
            // 1) Vocals
            SeparationStrategy vocalStrat = StrategySelector.getStrategy("vocal");
            StemProcessor vocalProc = new StemProcessor(vocalStrat);
            vocalProc.process(new File(inputPath), "vocals_output.wav");

            // 2) Brass
            SeparationStrategy brassStrat = StrategySelector.getStrategy("brass");
            StemProcessor brassProc = new StemProcessor(brassStrat);
            brassProc.process(new File(inputPath), "brass_output.wav");

            System.out.println("Done! Check vocals_output.wav and brass_output.wav.");
        } catch (Exception e) {
            System.err.println("Processing failed:");
            e.printStackTrace();
        }
    }
}
