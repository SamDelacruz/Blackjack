package com.samdlc.blackjack.hud;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import com.samdlc.blackjack.gamestate.GameState;

public abstract class HUDState {
	protected ArrayList<ActionButton> buttons;
	protected GameState gameState;
	
	public HUDState(GameState gameState) {
		this.buttons = new ArrayList<ActionButton>();
		this.gameState = gameState;
		this.buttons.add(new ActionButton("Quit", 10, 10, 80, 40, new Action() {

			@Override
			public void handle(ActionButton a) {
				gameState.handleAction("QUIT", null);
			}
			
		}));
	}
	
	public void render(Graphics2D g) {
		for(ActionButton button : buttons) {
			button.render(g);
		}
	}
	
	public void handleClick(Point p) {
		for(ActionButton button : buttons) {
			if(button.contains(p)) {
				button.click();
			}
		}
	}
}
