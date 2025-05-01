package com.audioseperator;

import java.util.Arrays;
import org.jtransforms.fft.FloatFFT_1D;

/**
 * Filters out everything except the 150–4000 Hz band to isolate brass.
 */
public class BrassSeparationStrategy implements SeparationStrategy {
    private static final int BUFFER_SIZE = 4096;
    private static final int OVERLAP     = 0;
    private static final int SAMPLE_RATE = 44100;

    private final FloatFFT_1D fft;
    private final float[] window;
    private final float[] outputBuffer;
    private int bufferOffset = 0;

    private final float LOW_CUTOFF  = 150f;
    private final float HIGH_CUTOFF = 4000f;

    public BrassSeparationStrategy() {
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

        // window + load real part
        for (int i = 0; i < BUFFER_SIZE; i++) {
            fftData[2*i]     = frame[i] * window[i];
            fftData[2*i + 1] = 0f;
        }

        fft.complexForward(fftData);
        float binRes = SAMPLE_RATE / (float)BUFFER_SIZE;

        // zero-out out-of-band
        for (int i = 0; i < BUFFER_SIZE; i++) {
            float freq = i * binRes;
            if (freq < LOW_CUTOFF || freq > HIGH_CUTOFF) {
                fftData[2*i] = fftData[2*i+1] = 0f;
            }
        }

        fft.complexInverse(fftData, true);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            outputBuffer[bufferOffset + i] += fftData[2*i];
        }
        bufferOffset += BUFFER_SIZE - OVERLAP;
    }

    @Override
    public float[] getProcessedBuffer() {
        return Arrays.copyOf(outputBuffer, bufferOffset + BUFFER_SIZE);
    }
}
