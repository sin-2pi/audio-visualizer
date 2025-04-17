package com.cosc3011;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.madgag.gif.fmsware.*;;

/*
 * USAGE:
 * 
 * GifCapture variable_name = new GifCapture(frame_count, "Save/Path/name.gif");
    try {
        gif.startCapture(frame);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    still working on the frames per second
 */

public class GifCapture {
    private ArrayList<BufferedImage> frames;
    private CountDownLatch captureSync;
    private AnimatedGifEncoder encoder = new AnimatedGifEncoder();
    private String filePath;

    public GifCapture(int frameCount, String filePath) {
        frames = new ArrayList<>();
        captureSync = new CountDownLatch(frameCount);
        this.filePath = filePath;
    }

    public void encodeGif() {
        encoder.start(filePath);
        encoder.setDelay(10);
        for (BufferedImage frame : frames) {
            encoder.addFrame(frame);
        }
        encoder.finish();
        System.out.println("Gif successfully encoded");
    }

    public void startCapture(JFrame window) throws InterruptedException {
        // capture frames until it reaches the frame count
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (captureSync.getCount() != 0) {
                    captureFrame(window);
                    captureSync.countDown();
                } else {
                    encodeGif();
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

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
            captureSync.countDown();
        });
    }

    /*
     SEPERATE TESTING CODE
     DO NOT MODIFY
     MOVING RED SQUARE - from stackoverflow

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
        
        GifCapture gif = new GifCapture(300, "test.gif");
        try {
            gif.startCapture(frame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    */
}