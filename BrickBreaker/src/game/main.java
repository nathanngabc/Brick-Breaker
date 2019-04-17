package game;

import java.awt.*;        

import java.awt.event.*;
import javax.swing.*;

public class main {
	 public static void main(String[] args) {
		 JFrame window = new JFrame("brick breaker");  
		 window.setSize(600, 500);

		 panel frame = new panel(600, 500);
		 
		 listener listener = new listener(frame);
		 window.setContentPane(frame);
		 window.addKeyListener(listener);
	     
	     window.setLocation(100,100);
	     window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	     window.setResizable(false);  // User can't change the window's size.
	     window.setVisible(true);
	 }
}
