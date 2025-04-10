package com.audioseperator;
// Tyson Limato
// Intro to Software Design
// Spring 2025
// 3/19/25
// Audio Vizualizer Group Project

import be.tarsos.dsp.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.core.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class Main {
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 2048;
    private static final int OVERLAP = 1024;

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        File inputFile = new File("input.flac");

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(inputFile, BUFFER_SIZE, OVERLAP);
        float[] fullAudio = new float[(int) inputFile.length()]; // crude
        final int[] offset = {0};

        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] buffer = audioEvent.getFloatBuffer();
                System.arraycopy(buffer, 0, fullAudio, offset[0], buffer.length);
                offset[0] += buffer.length;
                return true;
            }

            @Override
            public void processingFinished() {}
        });

        dispatcher.run();

        float[] audioData = new float[offset[0]];
        System.arraycopy(fullAudio, 0, audioData, 0, offset[0]);

        VocalSeparationStrategy strategy = new SpectralSubtractionStrategy();
        VocalSeparationContext context = new VocalSeparationContext(strategy);
        context.process(audioData, BUFFER_SIZE, OVERLAP, SAMPLE_RATE);

        float[] output = context.getSeparatedSignal();
        AudioWriter.writeWav(output, new File("vocals_output.wav"), SAMPLE_RATE);

        System.out.println("Saved separated vocals to vocals_output.wav");
    }
}
