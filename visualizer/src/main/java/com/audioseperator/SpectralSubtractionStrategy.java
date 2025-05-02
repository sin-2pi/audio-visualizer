package com.audioseperator;

import java.util.Arrays;
import org.jtransforms.fft.FloatFFT_1D;

/**
 * Basic spectral subtraction to suppress background noise.
 */
public class SpectralSubtractionStrategy implements SeparationStrategy {
    private static final int BUFFER_SIZE = 4096;
    private static final int OVERLAP     = 0;
    private static final int SAMPLE_RATE = 44100;

    private final FloatFFT_1D fft;
    private final float[] window;
    private final float[] outputBuffer;
    private float[] noiseEstimate;
    private int bufferOffset = 0;

    public SpectralSubtractionStrategy() {
        this.fft = new FloatFFT_1D(BUFFER_SIZE);
        this.window = new float[BUFFER_SIZE];
        for (int i = 0; i < BUFFER_SIZE; i++) {
            window[i] = 0.54f - 0.46f * (float)Math.cos((2 * Math.PI * i) / (BUFFER_SIZE - 1));
        }
        this.outputBuffer = new float[SAMPLE_RATE * 60];
    }

    @Override
    public void process(float[] frame) {
        float[] fftData = new float[BUFFER_SIZE * 2];
        for (int i = 0; i < BUFFER_SIZE; i++) {
            fftData[2*i]     = frame[i] * window[i];
            fftData[2*i + 1] = 0f;
        }

        fft.complexForward(fftData);

        // first frame => noise profile
        if (noiseEstimate == null) {
            noiseEstimate = estimateMagnitude(fftData);
            return;
        }

        float[] magnitude = estimateMagnitude(fftData);
        float[] phase     = estimatePhase(fftData);

        // subtract noise
        for (int i = 0; i < magnitude.length; i++) {
            magnitude[i] = Math.max(magnitude[i] - noiseEstimate[i], 0);
        }

        // rebuild complex spectrum
        float[] cleaned = new float[BUFFER_SIZE * 2];
        for (int i = 0; i < BUFFER_SIZE; i++) {
            cleaned[2*i]     = magnitude[i] * (float)Math.cos(phase[i]);
            cleaned[2*i + 1] = magnitude[i] * (float)Math.sin(phase[i]);
        }

        fft.complexInverse(cleaned, true);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            outputBuffer[bufferOffset + i] += cleaned[2*i];
        }
        bufferOffset += BUFFER_SIZE - OVERLAP;
    }

    @Override
    public float[] getProcessedBuffer() {
        return Arrays.copyOf(outputBuffer, bufferOffset + BUFFER_SIZE);
    }

    private float[] estimateMagnitude(float[] complex) {
        float[] mag = new float[BUFFER_SIZE];
        for (int i = 0; i < BUFFER_SIZE; i++) {
            float r = complex[2*i], im = complex[2*i+1];
            mag[i] = (float)Math.hypot(r, im);
        }
        return mag;
    }

    private float[] estimatePhase(float[] complex) {
        float[] ph = new float[BUFFER_SIZE];
        for (int i = 0; i < BUFFER_SIZE; i++) {
            ph[i] = (float)Math.atan2(complex[2*i+1], complex[2*i]);
        }
        return ph;
    }
}
