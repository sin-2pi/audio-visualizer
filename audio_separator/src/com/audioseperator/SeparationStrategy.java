package com.audioseperator;

/**
 * Common interface for any audio‐separation algorithm.
 */
public interface SeparationStrategy {
    /**
     * Called for each buffer of audio samples.
     * @param buffer the raw audio samples (mono, normalized to –1…+1)
     */
    void process(float[] buffer);

    /**
     * After all buffers have been fed to process(...), return
     * the full, concatenated result.
     *
     * @return an array of floats containing the processed audio
     */
    float[] getProcessedBuffer();
}
