package com.audioseperator;

import java.io.*;
import javax.sound.sampled.*;

// Utility class for writing a float array of audio samples to a WAV file
public class AudioWriter {

    /**
     * Converts floating-point audio samples to 16-bit PCM and writes them to a WAV file.
     *
     * @param samples     An array of float audio samples in range [-1.0, 1.0]
     * @param outputFile  Destination WAV file
     * @param sampleRate  Audio sample rate (e.g., 44100 Hz)
     * @throws IOException              If writing the file fails
     * @throws LineUnavailableException For potential future audio playback integration
     */
    public static void writeWav(float[] samples, File outputFile, int sampleRate) throws IOException, LineUnavailableException {
        // Allocate byte buffer: 2 bytes per 16-bit PCM sample
        byte[] byteBuffer = new byte[samples.length * 2];

        // Convert float samples to little-endian 16-bit signed PCM format
        for (int i = 0; i < samples.length; i++) {
            short val = (short) (samples[i] * 32767.0); // Scale float to short range
            byteBuffer[2 * i] = (byte) (val & 0xff);         // Low byte
            byteBuffer[2 * i + 1] = (byte) ((val >> 8) & 0xff); // High byte
        }

        // Define audio format: PCM signed, little-endian, 16-bit, mono
        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);

        // Wrap byte buffer in an input stream to use with AudioInputStream
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);

        // Create audio stream using the buffer and specified format
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, samples.length);

        // Write the audio stream to disk as a WAV file
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
    }
}
