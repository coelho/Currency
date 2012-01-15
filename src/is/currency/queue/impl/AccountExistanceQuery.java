package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountExistanceQuery extends AccountQuery {

	private Currency currency;
	private String username;
	private boolean existant;
	
	public AccountExistanceQuery(Currency currency, String username) {
		this.currency = currency;
		this.username = username.toLowerCase();
		this.existant = false;
	}
	
	@Override
	public void run() {
		if(this.currency.getDatabase().find(Account.class).where().eq("username", username).findRowCount() > 0) {
			this.existant = true;
		}
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public boolean isExistant() {
		return this.existant;
	}
	
}
