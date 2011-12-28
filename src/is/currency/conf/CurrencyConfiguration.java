package is.currency.conf;

import java.io.File;
import java.util.List;

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
public class CurrencyConfiguration extends Configuration {

	private List<String> currencyMajor;
	private List<String> currencyMinor;
	private double accountBalance;
	private boolean formatMinor;
	private boolean formatSeperate;
	private boolean formatSingle;
	
	public CurrencyConfiguration(File file) {
		super(file);
	}
	
	@Override
	public void load() {
		super.load();
		this.currencyMajor = super.getStringList("Currency.Major", null);
		this.currencyMinor = super.getStringList("Currency.Minor", null);
		this.accountBalance = super.getDouble("Account.Balance", 0.0);
		this.formatMinor = super.getBoolean("Formatting.Minor", false);
		this.formatSeperate = super.getBoolean("Formatting.Seperate", false);
		this.formatSingle = super.getBoolean("Formatting.Single", false);
	}
	
	public List<String> getCurrencyMajor() {
		return this.currencyMajor;
	}
	
	public List<String> getCurrencyMinor() {
		return this.currencyMinor;
	}
	
	public double getAccountBalance() {
		return this.accountBalance;
	}
	
	public boolean isFormatMinor() {
		return this.formatMinor;
	}
	
	public boolean isFormatSeperate() {
		return this.formatSeperate;
	}
	
	public boolean isFormatSingle() {
		return this.formatSingle;
	}
	
}
