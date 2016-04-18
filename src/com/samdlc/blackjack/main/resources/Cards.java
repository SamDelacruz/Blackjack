package com.samdlc.blackjack.main.resources;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.samdlc.blackjack.core.ImageService;
import com.samdlc.blackjack.util.Logger;

public class Cards {
	public static BufferedImage spritesheet;
	public final static int W = 72;
	public final static int H = 100;
	private static Map<String, BufferedImage> cache = new HashMap<>();
	
	/**
	 * 
	 * @param value 1 - 13 (A - K)
	 * @param suit
	 * 	0 - hearts
	 *  1 - diamonds
	 *  2 - clubs
	 *  3 - spades
	 *  4 - misc / back
	 * @return
	 */
	public static BufferedImage getCard(int value, int suit) {
		String key = "" + value + suit;
		if(cache.containsKey(key)) {
			return cache.get(key);
		}
		
		Logger.info("CARD CACHE MISS: "+key);
		
		int y = suit * H;
		int x = (value - 1) * W;
		BufferedImage cardImg = spritesheet.getSubimage(x, y, W, H);
		cache.put(key, cardImg);
		return cardImg;
	}
	
	public static void init() {
		spritesheet = ImageService.loadFromFile(Cards.class, "cards.gif");
		for(int suit = 0; suit <= 4; suit++) {
			for(int value = 1; value <= 13; value++) {
				getCard(value, suit);
			}
		}
	}
}
