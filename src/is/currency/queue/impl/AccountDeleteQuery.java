package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountDeleteQuery extends AccountQuery {

	private Currency currency;
	private String username;
	
	public AccountDeleteQuery(Currency currency, String username) {
		this.currency = currency;
		this.username = username.toLowerCase();
	}
	
	@Override
	public void run() {
		this.currency.getDatabase().delete(this.currency.getDatabase().find(Account.class).where().eq("username", username).findUnique());
	}
	
	public String getUsername() {
		return this.username;
	}
	
}
