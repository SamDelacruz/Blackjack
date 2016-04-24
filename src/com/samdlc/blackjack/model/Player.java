package com.samdlc.blackjack.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
	private final static int MAX_HANDS = 4;
	
	private List<Hand> hands;
	private Wallet wallet;
	private String name;
	private Hand activeHand;
	private Iterator<Hand> handsIterator;
	
	public Player(String name, Wallet wallet) {
		this.wallet = wallet;
		this.name = name;
		clearHands();
	}

	public void clearHands() {
		this.hands = new ArrayList<>();
		this.hands.add(new Hand());
		this.handsIterator = this.hands.iterator();
		nextHand();
	}
	
	public boolean placeBet(Hand h, int amt) {
		if(amt <= wallet.getBalance()) {
			wallet.subtract(amt);
			h.placeBet(amt);
			return true;
		}
		return false;
		
	}
	
	public List<Hand> getHands() {
		return this.hands;
	}
	
	public boolean split() {
		Hand hand = this.activeHand;
		
		if(this.wallet.getBalance() < hand.getStake()) {
			return false;
		}
		
		if(hand.isSplittable()) {
			Card card = hand.split();
			Hand newHand = new Hand();
			newHand.add(card);
			newHand.placeBet(this.wallet.take(hand.getStake()));
			
			// if there are more hands, create new hand after current
			// otherwise add hand to end
			if(handsIterator.hasNext()) {
				this.hands.add(this.hands.indexOf(hand) + 1, hand);
			} else {
				this.hands.add(hand);
			}
		}
		
		return true;
	}
	
	public String toString() {
		String tos = "PLAYER\n";
		tos += this.name + "\n";
		tos += this.wallet + "\n";
		for(Hand h : hands) {
			tos += h.toString();
		}
		
		return tos;
	}
	
	public void dealToActiveHand(Card c) {		
		this.activeHand.add(c);
	}
	
	public Hand getActiveHand() {
		return this.activeHand;
	}
	
	public boolean addHand() {
		if(hands.size() < MAX_HANDS) {
			hands.add(new Hand());
			return true;
		}
		
		return false;
	}
	
	public void nextHand() {
		if(this.handsIterator.hasNext()) {
			this.activeHand = this.handsIterator.next();
		}
	}
	
	public boolean hasMoreHands() {
		return this.handsIterator.hasNext();
	}
	
	public Wallet getWallet() {
		return this.wallet;
	}
	
	public String getName() {
		return this.name;
	}
	
}
