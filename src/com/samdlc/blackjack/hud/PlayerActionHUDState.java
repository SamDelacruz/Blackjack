package com.samdlc.blackjack.hud;

import com.samdlc.blackjack.gamestate.GameState;
import com.samdlc.blackjack.main.Main;

public class PlayerActionHUDState extends HUDState {
	
	public PlayerActionHUDState(GameState gameState) {
		super(gameState);
		
		this.buttons.add(new ActionButton("HIT", Main.WIDTH / 2 - 40, 500, 80, 40, new Action() {

			@Override
			public void handle(ActionButton a) {
				gameState.handleAction("HIT", null);
			}
			
		}));
	}

}
