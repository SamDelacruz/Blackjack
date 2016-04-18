package com.samdlc.blackjack.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.MouseInputListener;

import com.samdlc.blackjack.gamestate.GameStateManager;

public class MouseInputHandler implements MouseInputListener {
	
	GameStateManager gsm;
	
	public MouseInputHandler(GameStateManager gsm) {
		this.gsm = gsm;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		gsm.states.peek().handleClick(e.getX(), e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gsm.states.peek().handleHover(e.getX(), e.getY());
	}

}
