package com.samdlc.blackjack.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.samdlc.blackjack.main.resources.Colors;

public class ActionButton extends Rectangle {

	private static final long serialVersionUID = -3921854229178985624L;
	
	private Action action;
	private String label;
	private boolean visible;
	private boolean enabled;
	
	public ActionButton(String label, int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.label = label;
		this.action = action;
		this.visible = true;
		this.enabled = true;
		this.setBounds(x, y, width, height);
	}
	
	public void click() {
		if(enabled) {
			this.action.handle(this);
		}
	}
	
	public void render(Graphics2D g) {
		if(visible) {
			if(enabled) {
				g.setColor(Colors.GUI_BTN_BG);
				g.fillRect(x, y, width, height);
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Colors.GUI_BTN_BG_DISABLED);
				g.fillRect(x, y, width, height);
				g.setColor(Color.GRAY);
			}
			g.drawString(this.label, x + 5, y + 20);
		}
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		this.enabled = visible;
	}
	
}
