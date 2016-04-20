package com.samdlc.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	public final static int MAX_VAL = 21;
	
	private List<Card> cards;
	private int value;
	private boolean hasAce;
	private boolean isBust;
	private boolean isBlackjack;
	private boolean is21;
	private boolean isSplittable;
	
	public Hand() {
		this.cards = new ArrayList<Card>();
		this.value = 0;
		this.hasAce = false;
		this.isBlackjack = false;
		this.isBust = false;
		this.isSplittable = false;
	}
	
	public void add(Card c) {
		cards.add(c);
		
		int cardVal = c.getValue(!hasAce);
		value += cardVal;
		
		if(cardVal == 1) {
			hasAce = true;
		}
		
		if(value == MAX_VAL) {
			is21 = true;
		}
		
		if(cards.size() == 2) {
			if(is21) {
				isBlackjack = true;
			} else if(cards.get(0).getIndex() == cards.get(1).getIndex()){
				isSplittable = true;
			}
		} else if(value > MAX_VAL) {
			isBust = true;
		}
	}

	public List<Card> getCards() {
		return cards;
	}

	public int getValue() {
		return value;
	}

	public boolean hasAce() {
		return hasAce;
	}

	public boolean isBust() {
		return isBust;
	}

	public boolean isBlackjack() {
		return isBlackjack;
	}

	public boolean is21() {
		return is21;
	}

	public boolean isSplittable() {
		return isSplittable;
	}
}
