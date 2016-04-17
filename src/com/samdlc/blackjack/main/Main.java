package com.samdlc.blackjack.main;

import com.samdlc.blackjack.core.GameLoop;
import com.samdlc.blackjack.core.GameWindow;

public class Main {
	
	private static final Object V_MAJ = 0;
	private static final Object V_MIN = 0;
	private static final Object V_REV = 1;
	private static final String TITLE = String.format("Blackjack | %d.%d.%d", V_MAJ, V_MIN, V_REV);
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static void main(String[] args) {
		
		GameWindow window = new GameWindow(TITLE, WIDTH, HEIGHT);
		GameLoop gameLoop = new GameLoop(WIDTH, HEIGHT);
		window.add(gameLoop);
		window.setVisible(true);

	}

}
