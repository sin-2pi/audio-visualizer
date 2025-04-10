package com.audioseperator;
// Tyson Limato
// Intro to Software Design
// Spring 2025
// 3/20/25
// Audio Vizualizer Group Project

public interface VocalSeparationStrategy {
    void initialize(int bufferSize, int overlap, int sampleRate); // generic initialization step
    void processFrame(float[] frame); //Process a "Frame" of the mp3
    float[] getSeparatedSignal(); // return the sig
}

