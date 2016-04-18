package com.samdlc.blackjack.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import com.samdlc.blackjack.main.resources.Colors;

public class ActionButton extends Rectangle {

	private static final long serialVersionUID = -3921854229178985624L;
	
	private Action action;
	private String label;
	private boolean visible;
	private boolean enabled;
	private boolean hover;
	
	public ActionButton(String label, int x, int y, int width, int height, Action action) {
		super(x, y, width, height);
		this.label = label;
		this.action = action;
		this.visible = true;
		this.enabled = true;
		this.hover = false;
		this.setBounds(x, y, width, height);
	}
	
	public void click() {
		if(enabled) {
			this.action.handle(this);
			this.hover = false;
		}
	}
	
	public void hover(boolean hover) {
		this.hover = hover;
	}
	
	public void render(Graphics2D g) {
		Area area = new Area(new Rectangle(x, y, (int)getWidth(), (int)getHeight()));
	    area = new Area(new RoundRectangle2D.Float(x, y, (int)getWidth(), (int)getHeight(), 5, 5));

	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		if(visible) {
			if(enabled) {
				if(hover) {
					g.setColor(Colors.GUI_BTN_BG_HOVER);
					g.fill(area);
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Colors.GUI_BTN_BG);
					g.fill(area);
					g.setColor(Color.WHITE);
				}
			} else {
				g.setColor(Colors.GUI_BTN_BG_DISABLED);
				g.fill(area);
				g.setColor(Color.GRAY);
			}
			
			renderLabel(g);
		}
	}

	private void renderLabel(Graphics2D g) {
		Color prevColor = g.getColor();
		if(this.enabled) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(100,100,100));
		}
		
		
		int centerX = (int) (getWidth()/2);
		int centerY = (int) (getHeight()/2);
					
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle stringBounds = fontMetrics.getStringBounds(this.label, g).getBounds();
		
		Font font = g.getFont();
		FontRenderContext renderContext = g.getFontRenderContext();
		GlyphVector glyphVector = font.createGlyphVector(renderContext, this.label);
		Rectangle visualBounds = glyphVector.getVisualBounds().getBounds();
		
		int textX = centerX - stringBounds.width/2;
		int textY = centerY - visualBounds.height/2 - visualBounds.y;

		g.drawString(this.label, textX + x, textY + y);
		
		g.setColor(prevColor);
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		this.enabled = visible;
	}
	
}
