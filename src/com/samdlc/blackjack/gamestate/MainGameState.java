package com.samdlc.blackjack.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.samdlc.blackjack.hud.BetPlacementHUDState;
import com.samdlc.blackjack.hud.HUDState;
import com.samdlc.blackjack.hud.NewGameHUDState;
import com.samdlc.blackjack.main.Main;
import com.samdlc.blackjack.main.resources.Colors;
import com.samdlc.blackjack.resources.SpriteManager;

public class MainGameState extends GameState {
	
	public SpriteManager sprites;
	public BufferedImage bg;
	public State state;
	public HUDState hudState;

	public MainGameState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		this.state = State.NEW_GAME;
		this.hudState = new NewGameHUDState(this);
	}

	@Override
	public void tick(double deltaTime) {
		switch(this.state) {
		case BET_PLACEMENT:
			break;
		case DEALER_TURN:
			break;
		case LOSE:
			break;
		case NEW_GAME:
			break;
		case PLAYER_TURN:
			break;
		case WIN:
			break;
		default:
			break;
		
		}

	}

	@Override
	public void render(Graphics2D g) {
		// render bg
		g.setColor(Colors.BOARD_GREEN);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.WHITE);
		// render cards
		// render gui
		g.setColor(Colors.GUI_BG);
		g.fillRect(0, Main.HEIGHT - (Main.HEIGHT / 4), Main.WIDTH, Main.HEIGHT / 4);
		
		g.setColor(Colors.GUI_BG);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT / 16);
		
		// render hud
		this.hudState.render(g);
		
		
	}
	
	public enum State {
		NEW_GAME,
		BET_PLACEMENT,
		PLAYER_TURN,
		DEALER_TURN,
		WIN,
		LOSE
	}

	@Override
	public void handleClick(int x, int y) {
		Point p = new Point(x, y);
		this.hudState.handleClick(p);
	}

	@Override
	public void handleAction(String action, Object data) {
		switch (action) {
		case "DEAL":
			if(this.state == State.NEW_GAME) {
				// Deal some cards
				// Set next state
				this.state = State.BET_PLACEMENT;
				this.hudState = new BetPlacementHUDState(this);
			}
			break;
		case "QUIT":
			this.gsm.states.pop();
			break;
		default:
			break;
		}
		
	}

}
