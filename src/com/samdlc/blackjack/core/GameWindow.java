package com.samdlc.blackjack.core;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 5371750348605005768L;
	
	public static GameWindow window;
	
	public GameWindow(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		window = this;
	}

}
