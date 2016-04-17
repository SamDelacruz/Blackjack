package com.samdlc.blackjack.core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class BaseGameLoop extends JPanel implements Runnable {

	private static final long serialVersionUID = 8523103721186780091L;

	private Thread thread;
	private boolean running;
	
	private static int fps;
	private static int tps;
	
	private int width;
	private int height;
	
	public static double currFPS = 120D;
	public Graphics2D graphics2D;
	private BufferedImage img;
	
	public BaseGameLoop(int width, int height) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setFocusable(false);
		this.requestFocus();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / currFPS;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double deltaTime = 0;
		
		while(running) {
			long now = System.nanoTime();
			deltaTime += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while(deltaTime >= 1) {
				ticks++;
				tick(deltaTime);
				deltaTime -= 1;
				shouldRender = true;
			}
			
			if(shouldRender == true) {
				frames++;
				render();
			}
			
			try {
				Thread.sleep(2);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				tps = ticks;
				fps = frames;
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void init() {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics2D = (Graphics2D) img.getGraphics();
		running = true;
	}
	
	public void tick(double deltaTime) {
		
	}
	
	public void render() {
		graphics2D.clearRect(0, 0, width, height);
		
	}
	
	public void clear() {
		Graphics g2 = getGraphics();
		if(img != null) {
			g2.drawImage(img, 0, 0, null);
		}
		g2.dispose();
	}
	
	public static int getFps() {
		return fps;
	}
	
	public static int getTps() {
		return tps;
	}


}
