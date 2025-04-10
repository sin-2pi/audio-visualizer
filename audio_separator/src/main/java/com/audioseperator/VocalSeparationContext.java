package com.audioseperator;
// Tyson Limato
// Intro to Software Design
// Spring 2025
// 3/21/25
// Audio Vizualizer Group Project

public class VocalSeparationContext {
    private final VocalSeparationStrategy strategy;

    public VocalSeparationContext(VocalSeparationStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(float[] audioBuffer, int bufferSize, int overlap, int sampleRate) {
        strategy.initialize(bufferSize, overlap, sampleRate);

        int hop = bufferSize - overlap;
        for (int i = 0; i < audioBuffer.length - bufferSize; i += hop) {
            float[] frame = new float[bufferSize];
            System.arraycopy(audioBuffer, i, frame, 0, bufferSize);
            strategy.processFrame(frame);
        }
    }

    public float[] getSeparatedSignal() {
        return strategy.getSeparatedSignal();
    }
}
