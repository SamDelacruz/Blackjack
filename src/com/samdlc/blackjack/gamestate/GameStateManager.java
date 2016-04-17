package com.samdlc.blackjack.gamestate;

import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager {
	public Stack<GameState> states;
	
	public GameStateManager() {
		states = new Stack<GameState>();
		//states.push(new MainGameState(this));
		states.push(new MenuState(this));
	}
	
	public void tick(double deltaTime){
		states.peek().tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		states.peek().render(g);
	}

	public void init() {
		states.peek().init();
	}
}
