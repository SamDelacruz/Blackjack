package com.samdlc.blackjack.model;

import java.util.Scanner;

public class Game {
	private Player player;
	private Player dealer;
	private Deck deck;
	private Player currentPlayer;
	private State state;
	private Outcome outcome;
	
	public Game(Player player, Player dealer, Deck deck) {
		this.player = player;
		this.dealer = dealer;
		this.deck = deck;
		deck.shuffle();
		currentPlayer = player;
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
		currentPlayer = player;
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
	
	public static void main(String[] args) {
		Wallet wallet = new Wallet();
		wallet.add(10000);
		Player p = new Player("Player", wallet);
		Player d = new Player("Dealer", null);
		Deck deck = new Deck(6);
		Game g = new Game(p, d, deck);
		g.deal();
		System.out.print(g.toString());
		g.checkBlackjack();
		System.out.print(g.toString());
		
		if(g.getState() == State.RESULT) {
			System.out.println("Result: " + g.outcome.toString());
			return;
		}
		
		while(g.getState() == State.PLAYER_TURN) {
			System.out.print(g.toString());
			Scanner in = new Scanner(System.in);

			int i = in.nextInt();
			
			switch(i) {
			case 1: // hit
				g.hit();
				break;
			case 2: // stand
				g.stand();
				break;
			case 3: // split
				break;
			case 4: // double
				break;
			default:
				break;
			}
		}
		
		while(g.getState() == State.DEALER_TURN) {
			System.out.println(g.toString());
			g.dealerTurn();
		}
		
		System.out.println(g.toString());
		
	}
}
