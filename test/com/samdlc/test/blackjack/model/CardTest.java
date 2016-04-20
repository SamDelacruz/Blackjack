package com.samdlc.test.blackjack.model;

import org.junit.Assert;
import org.junit.Test;

import com.samdlc.blackjack.model.Card;

public class CardTest {
	
	public final static int N_SUITS = 4;
	public final static int N_PER_SUIT = 13;

	@Test
	public void testCard() {
		for(int i = 0; i < N_SUITS; i++) {
			for(int j = 0; j < N_PER_SUIT; j++) {
				new Card(j, i);
			}
		}
	}

	@Test
	public void testGetLongName() {
		Card c = new Card(8, Card.Suit.CLUBS.ordinal());
		Assert.assertEquals("9 of Clubs", c.getLongName());
	}

	@Test
	public void testGetShortName() {
		Card c = new Card(8, Card.Suit.CLUBS.ordinal());
		Assert.assertEquals("9C", c.getShortName());
	}

	@Test
	public void testGetIndex() {
		Card c = new Card(2, Card.Suit.DIAMONDS.ordinal());
		Assert.assertEquals(2, c.getIndex());
	}

	@Test
	public void testGetSuit() {
		Card c = new Card(2, Card.Suit.HEARTS.ordinal());
		Assert.assertEquals(0, c.getSuit());
	}

	@Test
	public void testGetValue() {
		Card c = new Card(2, Card.Suit.HEARTS.ordinal());
		Assert.assertEquals(3, c.getValue(true));
		Card c1 = new Card(0, Card.Suit.SPADES.ordinal());
		Assert.assertEquals(1, c1.getValue(false));
		Assert.assertEquals(11, c1.getValue(true));
	}

}
