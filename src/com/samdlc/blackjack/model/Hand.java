package com.samdlc.blackjack.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Hand {
	public final static int MAX_VAL = 21;
	
	private List<Card> cards;
	private EnumSet<Flag> state;
	private int stake;
	
	public Hand() {
		this.cards = new ArrayList<Card>();
		this.state = EnumSet.noneOf(Flag.class);
		this.state.add(Flag.EMPTY);
		this.stake = 0;
	}
	
	public void placeBet(int stake) {
		this.stake += stake;
		this.state.add(Flag.HAS_BET);
	}
	
	public int getStake() {
		return this.stake;
	}
	
	public void add(Card c) {
		cards.add(c);
		int value = this.getValue();
		
		int cardVal = c.getValue(!this.state.contains(Flag.HAS_ACE));
		
		if(cardVal == 1) {
			this.state.add(Flag.HAS_ACE);
		}
		
		if(value == MAX_VAL) {
			this.state.add(Flag.IS_21);
		}
		
		if(cards.size() == 2) {
			if(this.state.contains(Flag.IS_21)) {
				this.state.add(Flag.BLACKJACK);
			} else if(cards.get(0).getValue(true) == cards.get(1).getValue(true)){
				this.state.add(Flag.CAN_SPLIT);
			}
		} else if(value > MAX_VAL) {
			this.state.add(Flag.BUST);
		} else {
			this.state.remove(Flag.CAN_SPLIT);
		}
		
		this.state.remove(Flag.EMPTY);
	}
	
	public Card split() {
		if(this.state.contains(Flag.CAN_SPLIT)) {
			this.state.remove(Flag.CAN_SPLIT);
			return this.cards.remove(1);
		}
		
		return null;
	}

	public List<Card> getCards() {
		return cards;
	}

	public int getValue() {
		int val = 0;
		boolean hasAce = false;
		
		for(Card c : cards) {
			int cval = c.getValue(!hasAce);
			hasAce |= (cval == 11);
			val += cval;
		}
		
		if((val > 21) && hasAce) {
			val -= 10;
		}
				
		return val;
	}

	public boolean hasAce() {
		return this.state.contains(Flag.HAS_ACE);
	}

	public boolean isBust() {
		return this.state.contains(Flag.BUST);
	}

	public boolean isBlackjack() {
		return this.state.contains(Flag.BLACKJACK);
	}

	public boolean is21() {
		return this.state.contains(Flag.IS_21);
	}

	public boolean isSplittable() {
		return this.state.contains(Flag.CAN_SPLIT);
	}
	
	public boolean isEmpty() {
		return this.state.contains(Flag.EMPTY);
	}
	
	public boolean betPlaced() {
		return this.state.contains(Flag.HAS_BET);
	}
	
	public enum Flag {
		HAS_ACE, BLACKJACK, IS_21, CAN_SPLIT, BUST, EMPTY, HAS_BET, WIN, LOSE, PUSH;
		
		public final static EnumSet<Flag> ALL_FLAGS = EnumSet.allOf(Flag.class);
	}
	
	public String toString() {
		String tos = "HAND\n";
		tos += "Value: " + this.getValue() + "\n";
		tos += "Stake: " + this.stake + "\n";
		tos += this.state + "\n[";
		
		for(Card c : cards) {
			tos += c.toString() + ",";
		}
		
		tos += "]\n";
		
		return tos;
	}
}
