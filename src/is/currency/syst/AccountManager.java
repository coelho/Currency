package is.currency.syst;

import java.util.ArrayList;
import java.util.List;

import is.currency.Currency;

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

	public void create(String username) {
		username = username.toLowerCase();
		if(this.has(username)) {
			return;
		}
		Account account = new Account();
		account.setUsername(username);
		account.setBalance(this.currency.getCurrencyConfig().getAccountBalance());
		this.currency.getDatabase().save(account);
	}

	public void remove(String username) {
		username = username.toLowerCase();
		this.currency.getDatabase().delete(this.currency.getDatabase().find(Account.class).where().eq("username", username).findUnique());
	}
	
	public void removeAll() {
		this.currency.getDatabase().delete(Account.class);
	}

	public boolean has(String username) {
		username = username.toLowerCase();
		return this.currency.getDatabase().find(Account.class).where().eq("username", username).findRowCount() > 0;
	}

	public AccountContext get(String username) {
		username = username.toLowerCase();
		if(!this.has(username)) {
			return null;
		}
		return new AccountContext(this.currency, username);
	}
	
	public AccountContext[] getAll() {
		List<AccountContext> accounts = new ArrayList<AccountContext>();
		for(Account account : this.currency.getDatabase().find(Account.class).findList()) {
			accounts.add(new AccountContext(this.currency, account.getUsername()));
		}
		return accounts.toArray(new AccountContext[0]);
	}
	
	public int size() {
		return this.currency.getDatabase().find(Account.class).findRowCount();
	}

}
