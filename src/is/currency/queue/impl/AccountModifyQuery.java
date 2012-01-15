package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountModifyQuery extends AccountQuery {

	private Currency currency;
	private String username;
	private double balance;
	
	public AccountModifyQuery(Currency currency, String username, double balance) {
		this.currency = currency;
		this.username = username.toLowerCase();
		this.balance = balance;
	}
	
	@Override
	public void run() {
		Account account = this.currency.getDatabase().find(Account.class).where().eq("username", this.username).findUnique();
		account.setBalance(this.balance);
		this.currency.getDatabase().save(account);
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public double getBalance() {
		return this.balance;
	}

}
