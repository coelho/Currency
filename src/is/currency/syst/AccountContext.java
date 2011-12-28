package is.currency.syst;

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
public class AccountContext {

	private Currency currency;
	private String username;
	
	protected AccountContext(Currency currency, String username) {
		this.currency = currency;
		this.username = username;
	}
	
	public double get() {
		return this.getAccount().getBalance();
	}
	
	public void set(double balance) {
		Account account = this.getAccount();
		account.setBalance(balance);
		this.saveAccount(account);
	}
	
	public void add(double balance) {
		Account account = this.getAccount();
		account.setBalance(account.getBalance() + balance);
		this.saveAccount(account);
	}
	
	public void subtract(double balance) {
		Account account = this.getAccount();
		account.setBalance(account.getBalance() - balance);
		this.saveAccount(account);
	}
	
	public boolean has(double balance) {
		return this.get() >= balance;
	}
	
	private Account getAccount() {
		return this.currency.getDatabase().find(Account.class).where().eq("username", this.username).findUnique();
	}
	
	private void saveAccount(Account account) {
		this.currency.getDatabase().save(account);
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String toString() {
		return this.currency.getFormatHelper().format(this.get());
	}
	
}
