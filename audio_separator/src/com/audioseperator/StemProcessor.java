package com.audioseperator;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

/**
 * Runs an AudioDispatcher over an input file, applies the given
 * SeparationStrategy, and writes out a WAV.
 */
public class StemProcessor {
    private static final int BUFFER_SIZE   = 4096;
    private static final int OVERLAP       = 0;
    private static final int SAMPLE_RATE   = 44100;

    private final SeparationStrategy strategy;

    /**
     * @param strategy the algorithm to apply (e.g. vocal vs. brass)
     */
    public StemProcessor(SeparationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Process the inputFile through the strategy and write the result to outputPath.
     *
     * @throws UnsupportedAudioFileException if the file format isn't supported
     * @throws IOException                   on file I/O errors
     */
    public void process(File inputFile, String outputPath)
            throws UnsupportedAudioFileException, IOException {

        // 1) Create and configure the dispatcher
        AudioDispatcher dispatcher =
            AudioDispatcherFactory.fromFile(inputFile, BUFFER_SIZE, OVERLAP);

        // 2) Hook in a processor that feeds each buffer to our strategy
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                // Copy buffer (Tarsos reuses internally)
                float[] buf = audioEvent.getFloatBuffer().clone();
                strategy.process(buf);
                return true;
            }

            @Override
            public void processingFinished() {
                // no-op
            }
        });

        // 3) Run the pipeline (blocks until done)
        dispatcher.run();

        // 4) Grab the processed data
        float[] outputBuffer = strategy.getProcessedBuffer();

        // 5) Write it out—catch the LineUnavailableException
        try {
            AudioWriter.writeWav(outputBuffer, new File(outputPath), SAMPLE_RATE);
        } catch (LineUnavailableException e) {
            System.err.println("ERROR: Unable to open audio line for WAV writing");
            e.printStackTrace();
        }
    }
}
