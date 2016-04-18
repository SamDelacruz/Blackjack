package com.samdlc.blackjack.gamestate;

import java.awt.Graphics2D;

public abstract class GameState {
	public GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public abstract void init();
	public abstract void tick(double deltaTime);
	public abstract void render(Graphics2D g);
	public abstract void handleClick(int x, int y);
	public abstract void handleAction(String action, Object data);
	public abstract void handleHover(int x, int y);
}
