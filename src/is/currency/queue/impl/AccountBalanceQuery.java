package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountBalanceQuery extends AccountQuery {

	private Currency currency;
	private String username;
	private double balance;
	
	public AccountBalanceQuery(Currency currency, String username) {
		this.currency = currency;
		this.username = username.toLowerCase();
		this.balance = 0;
	}
	
	@Override
	public void run() {
		Account account = this.currency.getDatabase().find(Account.class).where().eq("username", this.username).findUnique();
		this.balance = account.getBalance();
	}
	
	public String getUsername() {
		return this.username;
	}

	public double getBalance() {
		return this.balance;
	}
	
}
