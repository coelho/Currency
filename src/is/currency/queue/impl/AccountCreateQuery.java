package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountCreateQuery extends AccountQuery {

	private Currency currency;
	private String username;
	
	public AccountCreateQuery(Currency currency, String username) {
		this.currency = currency;
		this.username = username.toLowerCase();
	}
	
	@Override
	public void run() {
		if(this.currency.getDatabase().find(Account.class).where().eq("username", username).findRowCount() > 0) {
			return;
		}
		Account account = new Account();
		account.setUsername(username);
		account.setBalance(this.currency.getCurrencyConfig().getAccountBalance());
		this.currency.getDatabase().save(account);
	}
	
	public String getUsername() {
		return this.username;
	}

}
