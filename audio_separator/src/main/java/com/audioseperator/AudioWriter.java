package com.audioseperator;
import java.io.*;
import javax.sound.sampled.*;

public class AudioWriter {
    public static void writeWav(float[] samples, File outputFile, int sampleRate) throws IOException, LineUnavailableException {
        byte[] byteBuffer = new byte[samples.length * 2];
        for (int i = 0; i < samples.length; i++) {
            short val = (short) (samples[i] * 32767.0);
            byteBuffer[2 * i] = (byte) (val & 0xff);
            byteBuffer[2 * i + 1] = (byte) ((val >> 8) & 0xff);
        }

        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, samples.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
    }
}
