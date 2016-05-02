package com.samdlc.blackjack.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import com.samdlc.blackjack.hud.BetPlacementHUDState;
import com.samdlc.blackjack.hud.HUDState;
import com.samdlc.blackjack.hud.NewGameHUDState;
import com.samdlc.blackjack.hud.PlayerActionHUDState;
import com.samdlc.blackjack.main.Main;
import com.samdlc.blackjack.main.resources.CardSpriteManager;
import com.samdlc.blackjack.main.resources.Colors;
import com.samdlc.blackjack.model.Card;
import com.samdlc.blackjack.model.Deck;
import com.samdlc.blackjack.model.Game;
import com.samdlc.blackjack.model.Hand;
import com.samdlc.blackjack.model.Player;
import com.samdlc.blackjack.model.Wallet;
import com.samdlc.blackjack.resources.SpriteManager;
import com.samdlc.blackjack.util.Logger;

public class MainGameState extends GameState {
	
	public SpriteManager sprites;
	public BufferedImage bg;
	public State state;
	public HUDState hudState;
	private boolean showCoords = true;
	private Point mouseAt;
	
	private Game game;
	private Player player;
	private Player dealer;
	private Wallet wallet;
	private Deck deck;
	
	protected double dt;

	public MainGameState(GameStateManager gsm) {
		super(gsm);
		this.mouseAt = new Point();
		this.dt = 20.0;
	}

	@Override
	public void init() {
		this.state = State.NEW_GAME;
		this.hudState = new NewGameHUDState(this);
		
		this.wallet = new Wallet();
		this.wallet.add(100000000);
		
		this.player = new Player("Player", wallet);
		this.dealer = new Player("Dealer", null);
		
		this.deck = new Deck(6);
		
		this.game = new Game(player, dealer, deck);
	}

	@Override
	public void tick(double deltaTime) {
		if(state == State.DEALER_TURN) {
			if(dt < 20) { dt += deltaTime; }
			else {
				dt = 0.0;
				dealerAct();
				if(game.getState() != com.samdlc.blackjack.model.Game.State.DEALER_TURN) {
					this.state = State.EVALUATE;
					Logger.info("EVALUATE HANDS");
					evaluateHands();
					dt = 20.0;
				}
			}
		}
	}

	private void evaluateHands() {
		boolean dealerBust = dealer.getActiveHand().isBust();
		int dealerVal = dealer.getActiveHand().getValue();
		
		for(Hand h : player.getHands()) {
			if(h.isBust()) {
				Logger.info("Player hand BUST");
				continue;
			}
			
			if(dealerBust) {
				int stake = h.getStake();
				int winnings = 2 * stake;
				Logger.info("Player won "+winnings);
				wallet.add(winnings);
				continue;
			}
			
			if(h.getValue() > dealerVal) {
				int stake = h.getStake();
				int winnings = 2 * stake;
				Logger.info("Player won "+winnings);
				wallet.add(winnings);
				continue;
			}
			
			if(h.getValue() == dealerVal) {
				int stake = h.getStake();
				int winnings = stake;
				Logger.info("Player won "+winnings+" (PUSH)");
				wallet.add(winnings);
				continue;
			}
			
			Logger.info("Player hand lost");
		}
		
		this.state = State.NEW_GAME;
		this.hudState = new NewGameHUDState(this);
	}

	@Override
	public void render(Graphics2D g) {
		// render bg
		g.setColor(Colors.BOARD_GREEN);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.WHITE);
		// render cards
		// render gui
		g.setColor(Colors.GUI_BG);
		g.fillRect(0, Main.HEIGHT - (Main.HEIGHT / 4), Main.WIDTH, Main.HEIGHT / 4);
		
		g.setColor(Colors.GUI_BG);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT / 12);
		g.setColor(Color.WHITE);
		this.renderBalance(g);
		
		// render hud
		this.hudState.render(g);
		
		this.renderHands(g);
		
		switch (this.state) {
		case BET_PLACEMENT:
			break;
		case DEALER_TURN:
			break;
		case LOSE:
			break;
		case NEW_GAME:
			break;
		case PLAYER_TURN:
			break;
		case WIN:
			break;
		default:
			break;
		
		}
		
		if(this.showCoords) {
			g.drawString(String.format("[%d,%d]", this.mouseAt.x, this.mouseAt.y), this.mouseAt.x, this.mouseAt.y);
		}
		
		
	}
	
	public void renderHands(Graphics2D g) {
		int yOffD = 75;
		// Render dealer hand
		Hand dealerHand = dealer.getActiveHand();
		renderHand(dealerHand, yOffD, g);
		
		int yOffP = 325;
		Hand playerHand = player.getActiveHand();
		renderHand(playerHand, yOffP, g);
		
	}

	private void renderHand(Hand dealerHand, int yOffset, Graphics2D g) {
		List<Card> dealerCards = dealerHand.getCards();
		int nCards = dealerCards.size();
		int cardWidth = CardSpriteManager.W;
		int xOffset = Main.WIDTH / 2 - (nCards + 1) * cardWidth / 4;
		
		for(int i = 0; i < nCards; i++) {
			Card c = dealerCards.get(i);
			int idx = c.getIndex();
			int suit = c.getSuit();
			BufferedImage cSprite;
			if(c.getFaceUp()) {
				cSprite = CardSpriteManager.getCard(idx, suit);
			} else {
				cSprite = CardSpriteManager.getCard(5, 4);
			}
			g.drawImage(
					cSprite,
					xOffset + cardWidth / 2 * i,
					yOffset,
					null
			);
		}
	}
	
	public void renderBalance(Graphics2D g) {
		int xOff = 650;
		int yOff = 20;
		if(null != this.wallet) {
			String text = String.format("Chips: %.2f", player.getWallet().getBalance() / 100.0d);
			g.drawString(text, xOff, yOff);
		}
	}
	
	
	public enum State {
		NEW_GAME,
		BET_PLACEMENT,
		PLAYER_TURN,
		DEALER_TURN,
		WIN,
		LOSE,
		EVALUATE
	}

	@Override
	public void handleClick(int x, int y) {
		Point p = new Point(x, y);
		this.hudState.handleClick(p);
	}

	@Override
	public void handleAction(String action, Object data) {
		switch (action) {
		case "NG":
			if(this.state == State.NEW_GAME) {
				// Proceed to place bets
				// Set next state
				this.state = State.BET_PLACEMENT;
				this.player.clearHands();
				this.dealer.clearHands();
				this.hudState = new BetPlacementHUDState(this);
			}
			break;
			
		case "BET_PLACE":
			int amt = (int)data;
			this.player.placeBet(player.getActiveHand(), amt);
			int remaining = player.getWallet().getBalance();
			this.hudState.handleAction("ENABLE_DEAL", true);
			this.hudState.handleAction("DISABLE_BETS_ABOVE", remaining);
			Logger.info("Balance: "+ this.wallet.getBalance());
			break;
			
		case "DEAL":
			this.hudState.handleAction("ENABLE_DEAL", false);
			this.game.deal();
			this.hudState = new PlayerActionHUDState(this);
			this.state = State.PLAYER_TURN;
			
			if(this.player.getActiveHand().isBlackjack()) {
				int stake = this.player.getActiveHand().getStake();
				int winnings = (int) Math.floor(stake * 1.5);
				this.wallet.add(winnings);
				Logger.info("PLAYER BLACKJACK");
				this.state = State.NEW_GAME;
				this.hudState = new NewGameHUDState(this);
			}

			break;
		case "HIT":
			if(this.state == State.PLAYER_TURN){
				this.game.hit();
				Logger.info(this.player.getActiveHand().toString());
				// Check for bust
				if(this.playerActiveHandIsBust()) {
					// Game over!
					Logger.info("BUST");
					this.state = State.NEW_GAME;
					this.hudState = new NewGameHUDState(this);
				}
				
				if(this.player.getActiveHand().is21()) {
					Logger.info("Player hand is 21, auto-stand");
					stand();
				}
			}
			
			break;
		case "QUIT":
			this.gsm.states.pop();
			break;
		case "STAND":
			Logger.info("STAND");
			stand();
			break;
		default:
			break;
		}
		
	}

	private void stand() {
		this.state = State.DEALER_TURN;
		game.setState(com.samdlc.blackjack.model.Game.State.DEALER_TURN);
		this.hudState.handleAction("HIDE", "ALL");
	}
	
	private void dealerAct() {
		this.game.dealerTurn();
	}

	private boolean playerActiveHandIsBust() {
		Hand h = this.player.getActiveHand();
		return h.isBust();
	}

	@Override
	public void handleHover(int x, int y) {
		Point p = new Point(x, y);
		this.hudState.handleHover(p);
		this.mouseAt = p;
	}

}
