package com.samdlc.blackjack.hud;

import com.samdlc.blackjack.gamestate.GameState;
import com.samdlc.blackjack.main.Main;

public class PlayerActionHUDState extends HUDState {
	
	
	public PlayerActionHUDState(GameState gameState) {
		super(gameState);
		this.buttons.add(new PlayerActionButton("HIT", Main.WIDTH / 2 - 85, 500));
		this.buttons.add(new PlayerActionButton("STAND", Main.WIDTH / 2 + 5, 500));
	}
	
	@Override
	public void handleAction(String action, Object data) {
		switch(action) {
		case "HIDE":
			String toHide = (String) data;
			for(ActionButton btn : buttons) {
				if(("ALL" == toHide) || (btn.getLabel() == toHide)) {
					btn.setVisible(false);
					btn.setEnabled(false);
				}
			}
			break;
			
		case "SHOW":
			String toShow = (String) data;
			for(ActionButton btn : buttons) {
				if(("ALL" == toShow) || (btn.getLabel() == toShow)) {
					btn.setVisible(true);
					btn.setEnabled(true);
				}
			}
			break;
		}
	}
	
	private class PlayerActionButton extends ActionButton {

		private static final long serialVersionUID = 3095983120143260768L;

		public PlayerActionButton(final String label, int x, int y) {
			super(label, x, y, 80, 40, new Action() {

				@Override
				public void handle(ActionButton a) {
					PlayerActionHUDState.this.getGameState().handleAction(label, null);
				}
				
			});
		}
		
	}

}
