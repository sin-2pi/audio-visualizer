package com.cosc3011;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.madgag.gif.fmsware.*;;

/* 
    gifCapture.java
    written by Aaron Krapes
    COSC 3011 Final Project
    
    USAGE:
    GifCapture name = new GifCapture()
    name.updatePath() changes where the gif is saved
    name.startCapture() starts capturing frames continuously
    name.stopCapture() stops frame capture and starts the encoding process

    try {
        gif.startCapture(frame);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
*/

public class GifCapture {
    private ArrayList<BufferedImage> frames;
    private volatile boolean started = false;
    private volatile boolean recording = true;
    private AnimatedGifEncoder encoder;
    private String filePath;
    private int delay;
    public int frameRate = 30; // set to 30 fps by default

    // constructor
    public GifCapture() {
        delay = 1000 / frameRate; // ms between frames
        frames = new ArrayList<>();
        encoder = new AnimatedGifEncoder();
        filePath = "temp.gif";
    }

    // encode function
    // uses external gif encoding library created by other authors
    // com.madgag.gif.fmsware package
    public void encodeGif() {
        encoder.start(filePath);
        encoder.setDelay(delay);
        for (BufferedImage frame : frames) {
            encoder.addFrame(frame);
        }
        encoder.finish();
    }

    // start capture function
    // launches a new thread that captures frames with a given delay between each
    public void startCapture(JFrame window) throws InterruptedException {
        started = true;
        new Thread(() -> {
            while (recording) {
                captureFrame(window);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println("Thread sleep failure");
                    return;
                }
            }
            encodeGif();
            return;
       }).start();
    }

    // stop capture function
    // returns true if stop was successful
    // returns false when recording is not already started
    public boolean stopCapture() {
        if (!started) {
            int result = JOptionPane.showConfirmDialog(null,
                    "Recording not yet started",
                    "Error", JOptionPane.DEFAULT_OPTION);
            return false;
        } else {
            recording = false;
            return true;
        }
    }

    // capture frame function
    // captures buffered image of jframe and adds to arraylist of frames for later
    // uses swing's invokeLater function for safe asynchronous execution
    public void captureFrame(JFrame window) {
        SwingUtilities.invokeLater(() -> {
            BufferedImage frame = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = frame.createGraphics();
            try {
                window.paint(g);
                frames.add(frame);
            } finally {
                g.dispose();
            }
        });
    }

    // function to update the filepath
    public void updatePath(String newPath) {
        filePath = newPath;
    }

    // function to update the fps for encoding
    public void updateFPS(int fps) {
        frameRate = fps;
        delay = 1000 / fps;
    }

    // function to get the current fps value
    public int getFPS() {
        return frameRate;
    }

    /*
     SEPERATE TESTING CODE
     DO NOT MODIFY
     MOVING RED SQUARE - from stackoverflow
    */
    private static final JPanel square = new JPanel();
    private static int x = 20;

    public static void createAndShowGUI(){
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(square);
        square.setBounds(20,200,100,100);
        square.setBackground(Color.RED);

        Timer timer = new Timer(1000/60,new MyActionListener());
        timer.start();
        frame.setVisible(true);
        
        // this class being used
        GifCapture gif = new GifCapture();
        try {
            gif.startCapture(frame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // allows the recording to capture 3 seconds worth of frames
        // then stops the capture
        Timer stop = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              gif.stopCapture();
            }
          });
          stop.start();
    }

    public static class MyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0) {
            square.setLocation(x++, 200);
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                createAndShowGUI();
            }
        });
    }
}