package is.currency.queue.impl;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountAmountQuery extends AccountQuery {

	private Currency currency;
	private int amount;
	
	public AccountAmountQuery(Currency currency) {
		this.currency = currency;
		this.amount = 0;
	}
	
	@Override
	public void run() {
		this.amount = this.currency.getDatabase().find(Account.class).findRowCount();
	}

	public int getAmount() {
		return this.amount;
	}

}
