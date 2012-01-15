package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountClearQuery extends AccountQuery {

	private Currency currency;
	
	public AccountClearQuery(Currency currency) {
		this.currency = currency;
	}
	
	@Override
	public void run() {
		this.currency.getDatabase().delete(Account.class);
	}
	
}
