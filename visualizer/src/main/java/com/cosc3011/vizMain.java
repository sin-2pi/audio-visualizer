package com.cosc3011;
import javax.swing.SwingUtilities;

public class vizMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new windowMain();
			}
		});
	}
}
