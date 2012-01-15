package is.currency.syst;

import java.util.List;

import is.currency.Currency;
import is.currency.queue.impl.AccountAmountQuery;
import is.currency.queue.impl.AccountClearQuery;
import is.currency.queue.impl.AccountCreateQuery;
import is.currency.queue.impl.AccountDeleteQuery;
import is.currency.queue.impl.AccountExistanceQuery;
import is.currency.queue.impl.AccountListQuery;

/*
 * Currency created by IsCoelho
 * @author Coelho <robertcoelho@live.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class AccountManager {

	private Currency currency;

	public AccountManager(Currency currency) {
		this.currency = currency;
	}

	public void createAccount(String username) {
		AccountCreateQuery query = new AccountCreateQuery(this.currency, username);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
	}

	@Deprecated
	public void removeAccount(String username) {
		this.deleteAccount(username);
	}
	
	public void deleteAccount(String username) {
		AccountDeleteQuery query = new AccountDeleteQuery(this.currency, username);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
	}

	public boolean hasAccount(String username) {
		AccountExistanceQuery query = new AccountExistanceQuery(this.currency, username);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
		return query.isExistant();
	}

	public AccountContext getAccount(String username) {
		if(!this.hasAccount(username)) {
			return null;
		}
		return new AccountContext(this.currency, username);
	}
	
	public String[] getAccounts() {
		return this.getAccountList().toArray(new String[0]);
	}
	
	public List<String> getAccountList() {
		AccountListQuery query = new AccountListQuery(this.currency);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
		return query.getListing();
	}
	
	public void clear() {
		AccountClearQuery query = new AccountClearQuery(this.currency);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
	}
	
	public int size() {
		AccountAmountQuery query = new AccountAmountQuery(this.currency);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
		return query.getAmount();
	}

}
