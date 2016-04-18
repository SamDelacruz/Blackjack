package com.samdlc.blackjack.hud;

import java.util.HashMap;
import java.util.Map;

import com.samdlc.blackjack.gamestate.GameState;
import com.samdlc.blackjack.main.Main;
import com.samdlc.blackjack.main.resources.Chips;
import com.samdlc.blackjack.util.Logger;

public class BetPlacementHUDState extends HUDState {
	
	private ActionButton dealButton;
	private Map<ActionButton, Integer> chipButtons;

	public BetPlacementHUDState(GameState gameState) {
		super(gameState);
		this.chipButtons = new HashMap<>();
	
		dealButton = new ActionButton("Deal", Main.WIDTH / 2 - 40, 500, 80, 40, null);
		dealButton.setEnabled(false);
		this.buttons.add(dealButton);
		
		int nChips = Chips.CHIP_VALS.length;
		for (int i = 0; i < nChips; i++) {
			int value = Chips.CHIP_VALS[i];
			String dollarVal = String.format("%.2f", value / 100f);
			
			int w = 60;
			int pad = 10;
			int off = (Main.WIDTH - nChips * (w + pad)) / 2;
			
			int y = 460;
			
			
			Action onClick = new ChipAction(value);
			ActionButton chipBtn = new ActionButton(dollarVal, i*(w+pad) + off, y, w, 30, onClick);
			this.buttons.add(chipBtn);
			this.chipButtons.put(chipBtn, value);
		}
		
		
	}
	
	private class ChipAction implements Action {
		
		int value;
		
		public ChipAction(int value) {
			this.value = value;
		}

		@Override
		public void handle(ActionButton a) {
			Logger.info(String.valueOf(this.value));
		}
		
	}

}
