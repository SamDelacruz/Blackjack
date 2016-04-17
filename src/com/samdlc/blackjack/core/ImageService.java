package com.samdlc.blackjack.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageService {
	public static BufferedImage loadFromFile(Class<?> classFile, String path) {
		URL url = classFile.getResource(path);
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(url);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
}
