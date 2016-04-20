package com.samdlc.blackjack.model;

public class Card {	
	private int index;
	private Suit suit;
	private boolean isFaceUp;
	
	public Card(int index, int suit) {
		this.index = index;
		this.suit = Suit.values()[suit];
		this.isFaceUp = false;
	}
	
	public String getLongName() {
		return getNumberLongStr() + " of " + getSuitLongStr();
	}
	
	public String getShortName() {
		return getNumberStr() + getSuitStr();
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getSuit() {
		return suit.ordinal();
	}
	
	public Card setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
		return this;
	}
	
	public boolean getFaceUp(){
		return this.isFaceUp;
	}
	
	public int getValue(boolean high) {
		// Ace - 1 when low, 11 when high
		if(index == 0) {
			return 1 + (high ? 10 : 0);
		}
		
		// 2 -> 10
		if(index < 10) {
			return index + 1;
		}
		
		// J -> K
		return 10;
	}
	
	public String getNumberStr() {
		switch(index){
		case 0:
			return "A";
		case 9:
			return "T";
		case 10:
			return "J";
		case 11:
			return "Q";
		case 12:
			return "K";
		default:
			return String.valueOf(index + 1);
		}		
	}
	
	public String getNumberLongStr() {
		switch(index){
		case 0:
			return "Ace";
		case 9:
			return "10";
		case 10:
			return "Jack";
		case 11:
			return "Queen";
		case 12:
			return "King";
		default:
			return String.valueOf(index + 1);
		}		
	}
	
	public String getSuitStr(){
		return suit.getShortName();
	}
	
	public String getSuitLongStr(){
		return suit.getLongName();
	}
	
	public enum Suit {
		HEARTS("Hearts", "H"),
		DIAMONDS("Diamonds", "D"),
		CLUBS("Clubs", "C"),
		SPADES("Spades", "S"),
		WILD("", "X");
		
		private String longName;
		private String shortName;
		
		private Suit(String longName, String shortName) {
			this.longName = longName;
			this.shortName = shortName;
		}
		
		public String getLongName() {
			return this.longName;
		}
		
		public String getShortName() {
			return this.shortName;
		}
		
		public String toString(){
			return this.longName;
		}
	}
	
	public String toString() {
		return this.getShortName();
	}

}
