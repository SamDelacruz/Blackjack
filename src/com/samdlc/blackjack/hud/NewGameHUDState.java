package com.samdlc.blackjack.hud;

import com.samdlc.blackjack.gamestate.GameState;
import com.samdlc.blackjack.main.Main;

public class NewGameHUDState extends HUDState {

	public NewGameHUDState(GameState gameState) {
		super(gameState);
		this.buttons.add(new ActionButton("Begin", Main.WIDTH / 2 - 40, 510, 80, 40, new DealAction()));
	}
	
	private class DealAction implements Action {

		@Override
		public void handle(ActionButton a) {
			gameState.handleAction("NG", null);
		}
		
	}

}
