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

	public void createAccount(String username) {
		username = username.toLowerCase();
		if(this.hasAccount(username)) {
			return;
		}
		Account account = new Account();
		account.setUsername(username);
		account.setBalance(this.currency.getCurrencyConfig().getAccountBalance());
		this.currency.getDatabase().save(account);
	}

	public void removeAccount(String username) {
		username = username.toLowerCase();
		this.currency.getDatabase().delete(this.currency.getDatabase().find(Account.class).where().eq("username", username).findUnique());
	}


	public boolean hasAccount(String username) {
		username = username.toLowerCase();
		return this.currency.getDatabase().find(Account.class).where().eq("username", username).findRowCount() > 0;
	}

	public AccountContext getAccount(String username) {
		username = username.toLowerCase();
		if(!this.hasAccount(username)) {
			return null;
		}
		return new AccountContext(this.currency, username);
	}
	
	public String[] getAccounts() {
		List<String> accounts = new ArrayList<String>();
		for(Account account : this.currency.getDatabase().find(Account.class).findList()) {
			accounts.add(account.getUsername().toLowerCase());
		}
		return accounts.toArray(new String[0]);
	}
	
	public void clear() {
		this.currency.getDatabase().delete(Account.class);
	}
	
	public int size() {
		return this.currency.getDatabase().find(Account.class).findRowCount();
	}

}
