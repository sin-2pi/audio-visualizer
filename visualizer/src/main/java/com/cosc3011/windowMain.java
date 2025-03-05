package com.cosc3011;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class windowMain {
	private JFrame frame;
	
	public windowMain() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame();
		this.frame.setLayout(new BorderLayout(0, 0));
		this.frame.setTitle("Audio Visualizer");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(1000, 800);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		
		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
		mainPanel.setBackground(Color.WHITE);
		
		Button create = new Button("Create a new project");
		JLabel or = new JLabel("Or");
		Button openExisting = new Button("Open an existing project");
		
		//Adds text field opening to create new file
		//Saves inputed file name to new variable "newFileName"
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame fileName = new JFrame();
				fileName.setTitle("Create New Project");
				fileName.setLayout(new BorderLayout(0, 0));
				fileName.setSize(450, 100);
				fileName.setLocationRelativeTo(frame);
				fileName.setAlwaysOnTop(true);
				fileName.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				fileName.setVisible(true);
				
				JPanel filePanel = new JPanel();
				filePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
				
				JLabel label = new JLabel("Enter new file name: ");
				JTextField nameEnter = new JTextField(20);
				Button enter = new Button("Enter");
				//Makes enter button save to var newFileName and hides window
				//Changes primary window name
				enter.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String newFileName = nameEnter.getText();
						frame.setTitle("Audio Visualizer - " + newFileName);
						fileName.setVisible(false);
					}
				});
				
				filePanel.add(label);
				filePanel.add(nameEnter);
				filePanel.add(enter);
				
				fileName.add(filePanel);
			}
		});
		//Adds file explorer functionality to open file button
		//Saves to return value
		openExisting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooseFile = new JFileChooser();
				//Returns file selection to returnValue and prints returnValue to console
				//returnValue is in binary 
				int returnValue = chooseFile.showOpenDialog(openExisting);
				System.out.print(returnValue);
			}
		});
		
		//Add panel to frame
		frame.add(mainPanel, BorderLayout.CENTER);
		
		//Add buttons to panel
		mainPanel.add(create);
		mainPanel.add(or);
		mainPanel.add(openExisting);
	}
}