package com.samdlc.blackjack.util;

public class Logger {
	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("Blackjack");
	
	public static void info(String message) {
		log.info(message);
	}
	
	public static void warn(String msg) {
		log.warning(msg);
	}
	
	public static void error(String msg) {
		log.severe(msg);
	}
}
