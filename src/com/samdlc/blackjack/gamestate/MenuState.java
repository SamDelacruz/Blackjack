package com.samdlc.blackjack.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import com.samdlc.blackjack.hud.Action;
import com.samdlc.blackjack.hud.ActionButton;
import com.samdlc.blackjack.main.Main;
import com.samdlc.blackjack.main.resources.Colors;

public class MenuState extends GameState {
	
	private ArrayList<ActionButton> buttons;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		this.buttons = new ArrayList<ActionButton>();
	}

	@Override
	public void init() {
		this.buttons.add(new ActionButton("START", Main.WIDTH / 2, Main.HEIGHT / 2, 100, 40, new Action() {
			
			@Override
			public void handle(ActionButton a) {
				GameState gameState = new MainGameState(gsm);
				gameState.init();
				gsm.states.push(gameState);
			}
		}));
	}

	@Override
	public void tick(double deltaTime) {

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Colors.BOARD_GREEN);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		drawText("BLACKJACK", 72, Font.BOLD, Main.WIDTH / 4 - 16, Main.HEIGHT / 2 - 32, Color.WHITE, g);
		drawText("By @SamDLC", 18, Font.PLAIN, Main.WIDTH / 2 + 90, Main.HEIGHT / 2, Color.WHITE, g);
		
		for (ActionButton button : buttons) {
			button.render(g);
		}
	}
	
	private void drawText(String s, int size, int style, int x, int y, Color c, Graphics2D g) {
		Font fontBefore = g.getFont();
		Color colorBefore = g.getColor();
		g.setFont(new Font(fontBefore.getFontName(), style, size));
		g.setColor(c);
		g.drawString(s, x, y);
		g.setFont(fontBefore);
		g.setColor(colorBefore);
	}

	@Override
	public void handleClick(int x, int y) {
		Point p = new Point(x, y);
		for (ActionButton button : buttons) {
			if(button.contains(p)) {
				button.click();
			}
		}
	}

	@Override
	public void handleAction(String action, Object data) {
		
	}

}
