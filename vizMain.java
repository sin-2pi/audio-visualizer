package com.AudioVisualizer;

import javax.swing.SwingUtilities;

public class vizMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				windowMain frame1 = new windowMain();
			}
		});
	}
}