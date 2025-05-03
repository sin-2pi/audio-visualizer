package com.audioseperator;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/* 
    creates a pop up window with an audio player that displays the files waveform
    using this to display each parsed audio file

    USAGE:
    new audioPlayer(filePath, windowName);
*/

public class audioPlayer extends JFrame {
    private Clip audioClip;
    private WaveformPanel waveformPanel;
    private File audioFile;
    private int currentFrame = 0;

    public audioPlayer(String filePath, String windowName) {
        // setup window
        super(windowName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLayout(new BorderLayout());

        // start audio stream
        audioFile = new File(filePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            // create waveform panel
            waveformPanel = new WaveformPanel(audioFile);
            add(waveformPanel, BorderLayout.CENTER);

            // add buttons
            JPanel buttonPanel = new JPanel();
            JButton playButton = new JButton("Play");
            JButton pauseButton = new JButton("Pause");
            JButton exitButton = new JButton("Exit");

            // add listeners
            playButton.addActionListener(e -> {
                audioClip.setFramePosition(currentFrame);
                audioClip.start();
            });
            pauseButton.addActionListener(e -> {
                currentFrame = audioClip.getFramePosition();
                audioClip.stop();
            });
            exitButton.addActionListener(e -> {
                audioClip.stop();
                dispose();
            });

            buttonPanel.add(playButton);
            buttonPanel.add(pauseButton);
            buttonPanel.add(exitButton);
            add(buttonPanel, BorderLayout.SOUTH);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            int result = JOptionPane.showConfirmDialog(null,
                "Failed to open .wav file",
                "Error", JOptionPane.DEFAULT_OPTION);
            e.printStackTrace();
            dispose();
        }
        setVisible(true);
    }
}

// JPanel to display waveform
class WaveformPanel extends JPanel {
    private byte[] audioData;

    public WaveformPanel(File audioFile) {
        try {
            // get audio data to build the waveform
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            long frames = audioStream.getFrameLength();
            int frameSize = format.getFrameSize();
            audioData = new byte[(int) frames * frameSize];
            audioStream.read(audioData);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    // paint waveform
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (audioData == null) return;

        int width = getWidth();
        int height = getHeight();
        int dataLength = audioData.length;
        int samplesPerPixel = dataLength / width;

        g.setColor(Color.BLACK);
        for (int x = 0; x < width; x++) {
            int start = x * samplesPerPixel;
            int end = Math.min(start + samplesPerPixel, dataLength);
            int max = 0;
            int min = 0;

            for (int i = start; i < end; i += 2) {
                int sample = (short) (((audioData[i + 1] & 0xFF) << 8) | (audioData[i] & 0xFF));
                max = Math.max(max, sample);
                min = Math.min(min, sample);
            }

            int yMax = height / 2 - (int) (max / (double) Short.MAX_VALUE * height / 2);
            int yMin = height / 2 - (int) (min / (double) Short.MAX_VALUE * height / 2);

            g.drawLine(x, yMax, x, yMin);
        }
    }
}