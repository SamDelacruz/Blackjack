package com.samdlc.blackjack.model;

public class Wallet {
	private int balance;
	
	public Wallet() {
		this.balance = 0;
	}
	
	public void add(int amt) {
		if(amt < 0) { return; }
		this.balance += amt;
	}

	public int getBalance() {
		return balance;
	}

	public void subtract(int amt) {
		if(amt < 0) { return; }
		if(amt > balance) {
			amt = balance;
		}
		balance -= amt;
	}

	public int take(int amt) {
		if(amt < 0) { return 0; }
		if(amt > balance) {
			amt = balance;
		}
		
		balance -= amt;
		
		return amt;
	}
	
	public String toString() {
		String tos = "WALLET\n";
		return tos + this.balance + "\n";
	}

}
