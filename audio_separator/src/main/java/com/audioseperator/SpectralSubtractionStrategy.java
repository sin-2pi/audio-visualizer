package com.audioseperator;
// Tyson Limato
// Intro to Software Design
// Spring 2025
// 3/21/25
// Audio Vizualizer Group Project

import java.util.Arrays;

import org.jtransforms.fft.FloatFFT_1D;

public class SpectralSubtractionStrategy implements VocalSeparationStrategy {

    private int bufferSize;
    private int overlap;
    private int sampleRate;

    private float[] window;
    private FloatFFT_1D fft;
    private float[] noiseEstimate;
    private float[] outputBuffer;
    private int bufferOffset = 0;

    @Override
    public void initialize(int bufferSize, int overlap, int sampleRate) {
        this.bufferSize = bufferSize;
        this.overlap = overlap;
        this.sampleRate = sampleRate;

        this.fft = new FloatFFT_1D(bufferSize);
        this.window = new float[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            window[i] = 0.54f - 0.46f * (float) Math.cos((2 * Math.PI * i) / (bufferSize - 1)); // Hamming
        }

        this.outputBuffer = new float[sampleRate * 60]; // 1 minute max
    }

    @Override
    public void processFrame(float[] frame) {
        float[] windowed = new float[bufferSize];
        float[] fftData = new float[bufferSize * 2];

        for (int i = 0; i < bufferSize; i++) {
            windowed[i] = frame[i] * window[i];
            fftData[2 * i] = windowed[i]; // real
            fftData[2 * i + 1] = 0.0f;    // imag
        }

        fft.complexForward(fftData);

        if (noiseEstimate == null) {
            noiseEstimate = estimateMagnitude(fftData); // crude init from first frame
            return;
        }

        float[] magnitude = estimateMagnitude(fftData);
        float[] phase = estimatePhase(fftData);

        for (int i = 0; i < magnitude.length; i++) {
            magnitude[i] = Math.max(magnitude[i] - noiseEstimate[i], 0);
        }

        float[] cleaned = new float[bufferSize * 2];
        for (int i = 0; i < bufferSize; i++) {
            cleaned[2 * i] = magnitude[i] * (float) Math.cos(phase[i]);
            cleaned[2 * i + 1] = magnitude[i] * (float) Math.sin(phase[i]);
        }

        fft.complexInverse(cleaned, true);

        for (int i = 0; i < bufferSize; i++) {
            outputBuffer[bufferOffset + i] += cleaned[2 * i]; // real part
        }

        bufferOffset += bufferSize - overlap;
    }

    @Override
    public float[] getSeparatedSignal() {
        return Arrays.copyOf(outputBuffer, bufferOffset + bufferSize);
    }

    private float[] estimateMagnitude(float[] complex) {
        float[] magnitude = new float[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            float real = complex[2 * i];
            float imag = complex[2 * i + 1];
            magnitude[i] = (float) Math.sqrt(real * real + imag * imag);
        }
        return magnitude;
    }

    private float[] estimatePhase(float[] complex) {
        float[] phase = new float[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            float real = complex[2 * i];
            float imag = complex[2 * i + 1];
            phase[i] = (float) Math.atan2(imag, real);
        }
        return phase;
    }
}
