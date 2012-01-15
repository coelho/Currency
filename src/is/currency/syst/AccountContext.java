package is.currency.syst;

import is.currency.Currency;
import is.currency.queue.impl.AccountBalanceQuery;
import is.currency.queue.impl.AccountModifyQuery;

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
	
	public double getBalance() {
		AccountBalanceQuery query = new AccountBalanceQuery(this.currency, this.username);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
		return query.getBalance();
	}
	
	public void setBalance(double balance) {
		AccountModifyQuery query = new AccountModifyQuery(this.currency, this.username, balance);
		this.currency.getAccountQueue().submit(query);
		query.awaitUninterruptedly();
	}
	
	public void addBalance(double balance) {
		this.setBalance(this.getBalance() + balance);
	}
	
	public void subtractBalance(double balance) {
		this.setBalance(this.getBalance() - balance);
	}
	
	public boolean hasBalance(double balance) {
		return this.getBalance() >= balance;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String toString() {
		return this.currency.getFormatHelper().format(this.getBalance());
	}
	
}
