package com.samdlc.blackjack.model;

public class Game {
	private Player player;
	private Player dealer;
	private Deck deck;
	private State state;
	private Outcome outcome;
	
	public Game(Player player, Player dealer, Deck deck) {
		this.player = player;
		this.dealer = dealer;
		this.deck = deck;
		deck.shuffle();
		this.state = State.PLACE_BETS;
		this.outcome = Outcome.NIL;
	}
	
	public boolean placeBet(Hand h, int amt) {
		if(this.state == State.PLACE_BETS){
			return player.placeBet(h, amt);
		}
		
		return false;
	}
	
	public void deal() {
		this.state = State.DEAL;
		
		// Deal face up card to P & D
		player.dealToActiveHand(deck.draw(true));
		dealer.dealToActiveHand(deck.draw(true));
		
		// Deal face up card to P & face down to D
		player.dealToActiveHand(deck.draw(true));
		dealer.dealToActiveHand(deck.draw(false));
		
		this.state = State.CHECK_BLACKJACK;
	}
	
	public void checkBlackjack() {
		boolean dealerBlackjack = dealer.getActiveHand().isBlackjack();
		boolean playerBlackjack = player.getActiveHand().isBlackjack();
		
		if(dealerBlackjack && !playerBlackjack) {
			outcome = Outcome.LOSE;
		}
		
		if(dealerBlackjack && playerBlackjack) {
			outcome = Outcome.PUSH;
		}
		
		if(!dealerBlackjack && playerBlackjack) {
			outcome = Outcome.BLACKJACK;
		}
		
		switch(outcome) {
		case BLACKJACK:
		case LOSE:
		case PUSH:
			state = State.SETTLE;
			break;
		default:
			state = State.PLAYER_TURN;
			break;
		}
	}
	
	public void stand() {
		if(player.hasMoreHands()) {
			player.nextHand();
		} else {
			state = State.DEALER_TURN;
		}
	}
	
	public void hit() {
		Hand h = player.getActiveHand();
		h.add(deck.draw(true));
		
		if(h.isBust()) {
			if(player.hasMoreHands()) {
				player.nextHand();
			} else {
				state = State.RESULT;
				outcome = Outcome.LOSE;
				return;
			}
		}
		
		if(h.is21()) {
			if(player.hasMoreHands()) {
				player.nextHand();
			} else {
				state = State.DEALER_TURN;
			}
		}
	}
	
	public void dealerTurn() {
		Hand h = dealer.getActiveHand();
		if(!h.getCards().get(1).getFaceUp()) {
			h.getCards().get(1).setFaceUp(true);
			return;
		}
		if(h.getValue() > 16) {
			state = State.RESULT;
			return;
		}
		
		h.add(deck.draw(true));
		
		if(h.getValue() > 21) {
			outcome = Outcome.WIN;
			state = State.RESULT;
		}
	}
	
	public void reset() {
		player.clearHands();
		dealer.clearHands();
		deck.shuffle();
		this.state = State.PLACE_BETS;
	}
	
	public State getState() {
		return this.state;
	}
	
	public enum State {
		PLACE_BETS, DEAL, CHECK_BLACKJACK, PLAYER_TURN, DEALER_TURN, RESULT, SETTLE
	}
	
	public enum Outcome {
		NIL, LOSE, PUSH, WIN, BLACKJACK
	}
	
	public String toString() {
		String tos = "GAME\n";
		tos += this.state.toString() + "\n";
		tos += this.outcome.toString() + "\n";
		tos += this.dealer.toString() + "\n";
		tos += this.player.toString() + "\n";
		return tos;
	}

	public void setState(State state) {
		this.state = state;
	}
}
