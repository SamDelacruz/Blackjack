package com.samdlc.blackjack.core;

import javax.swing.event.MouseInputListener;

import com.samdlc.blackjack.gamestate.GameStateManager;
import com.samdlc.blackjack.main.resources.Assets;

public class GameLoop extends BaseGameLoop{

	private static final long serialVersionUID = 1824268682939224500L;
	GameStateManager gsm;
	public static Assets assets = new Assets();

	public GameLoop(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void init() {
		assets.init();
		gsm = new GameStateManager();
		gsm.init();
		MouseInputListener mouseInput = new MouseInputHandler(gsm);
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
		super.init();
	}
	
	@Override
	public void tick(double deltaTime) {
		gsm.tick(deltaTime);
	}
	
	@Override
	public void render() {
		super.render();
		gsm.render(graphics2D);
		clear();
	}
	
	@Override
	public void clear() {
		super.clear();
	}
}
