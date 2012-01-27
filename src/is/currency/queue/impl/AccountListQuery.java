package is.currency.queue.impl;

import java.util.ArrayList;
import java.util.List;

import is.currency.Currency;
import is.currency.queue.AccountQuery;
import is.currency.syst.Account;

public class AccountListQuery extends AccountQuery {

	private Currency currency;
	private List<Account> listing;
	
	public AccountListQuery(Currency currency) {
		this.currency = currency;
		this.listing = new ArrayList<Account>();
	}
	
	@Override
	public void run() {
		for(Account account : this.currency.getDatabase().find(Account.class).findList()) {
			this.listing.add(account);
		}
	}

	public List<Account> getListing() {
		return this.listing;
	}
	
}
