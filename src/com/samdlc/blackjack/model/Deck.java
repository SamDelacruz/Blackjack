package com.samdlc.blackjack.model;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	public final static int N_SUITS = 4;
	public final static int N_VALS = 13;
	
	private int startSize;
	private int cardsDealt;
	private boolean isShuffled;
	private Stack<Card> cards;
	
	public Deck(int multiplier) {
		multiplier = (multiplier < 1) ? 1 : multiplier;
		this.startSize = multiplier * N_SUITS * N_VALS;
		this.cardsDealt = 0;
		this.isShuffled = false;
		this.cards = new Stack<>();
		
		for(int suit = 0; suit < N_SUITS; suit++) {
			for(int i = 0; i < N_VALS; i++) {
				for(int n = 0; n < multiplier; n++) {
					cards.push(new Card(i, suit));
				}
			}
		}
		
	}
	
	public Deck() {
		this(1);
	}
	
	public int getSize() {
		return this.cards.size();
	}
	
	public int getStartSize() {
		return this.startSize;
	}
	
	public int getCardsDealt() {
		return this.cardsDealt;
	}
	
	public Card draw(boolean isFaceUp) {
		Card c = this.cards.pop().setFaceUp(isFaceUp);
		this.cardsDealt++;
		return c;
	}
	
	public void shuffle() {
		Collections.shuffle(this.cards);
		this.isShuffled = true;
	}
	
	public boolean isShuffled() {
		return this.isShuffled;
	}
}
