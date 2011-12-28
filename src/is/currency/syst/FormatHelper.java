package is.currency.syst;

import java.text.DecimalFormat;
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
public class FormatHelper {

	private Currency currency;

	public FormatHelper(Currency currency) {
		this.currency = currency;
	}

	public String format(double amount) {
		DecimalFormat formatter = new DecimalFormat("#,##0.00");
		String formatted = formatter.format(amount);
		if (formatted.endsWith(".")) {
			formatted = formatted.substring(0, formatted.length() - 1);
		}
		return this.formatted(formatted);
	}

	private String formatted(String amount) {
		List<String> maj = this.currency.getCurrencyConfig().getCurrencyMajor();
		List<String> min = this.currency.getCurrencyConfig().getCurrencyMinor();
		if(this.currency.getCurrencyConfig().isFormatSingle()) {
			if(amount.contains(".")) {
				amount = amount.split("\\.")[0];
			}
		}
		String formatted = "";
		String famount = amount.replace(",", "");
		if(this.currency.getCurrencyConfig().isFormatMinor()) {
			String[] pieces = null;
			String[] fpieces = null;
			if(amount.contains(".")) {
				pieces = amount.split("\\.");
				fpieces = new String[] { pieces[0].replace(",", ""), pieces[1] };
			} else {
				pieces = new String[] { amount, "0" };
				fpieces = new String[] { amount.replace(",", ""), "0" };
			}
			if(this.currency.getCurrencyConfig().isFormatSeperate()) {
				String major = "";
				String minor = "";
				try {
					major = maj.get(plural(Integer.valueOf(fpieces[0])));
					minor = min.get(plural(Integer.valueOf(fpieces[1])));
				} catch (NumberFormatException exception) {
					major = maj.get(plural(Long.valueOf(fpieces[0])));
					minor = min.get(plural(Long.valueOf(fpieces[1])));
				}
				if(pieces[1].startsWith("0") && !pieces[1].equals("0")) {
					pieces[1] = pieces[1].substring(1, pieces[1].length());
				}
				if(pieces[0].startsWith("0") && !pieces[0].equals("0")) {
					pieces[0] = pieces[0].substring(1, pieces[0].length());
				}
				try {
					if(Integer.valueOf(fpieces[1]) != 0 && Integer.valueOf(fpieces[0]) != 0) {
						formatted = pieces[0] + " " + major + ", " + pieces[1] + " " + minor;
					} else if(Integer.valueOf(fpieces[0]) != 0) {
						formatted = pieces[0] + " " + major;
					} else {
						formatted = pieces[1] + " " + minor;
					}
				} catch(NumberFormatException e) {
					if(Long.valueOf(fpieces[1]) != 0 && Long.valueOf(fpieces[0]) != 0) {
						formatted = pieces[0] + " " + major + ", " + pieces[1] + " " + minor;
					} else if(Long.valueOf(fpieces[0]) != 0) {
						formatted = pieces[0] + " " + major;
					} else {
						formatted = pieces[1] + " " + minor;
					}
				}
			} else {
				String currency = "";
				if(Double.valueOf(famount) < 1 || Double.valueOf(famount) > -1) {
					try {
						currency = min.get(plural(Integer.valueOf(fpieces[1])));
					} catch (NumberFormatException e) {
						currency = min.get(plural(Long.valueOf(fpieces[1])));
					}
				} else {
					currency = maj.get(1);
				}
				formatted = amount + " " + currency;
			}
		} else {
			int plural = plural(Double.valueOf(famount));
			String currency = maj.get(plural);
			formatted = amount + " " + currency;
		}
		return formatted;
	}

	private int plural(double amount) {
		if(amount > 1 || amount < -1) {
			return 1;
		}
		return 0;
	}

	private int plural(int amount) {
		if(amount != 1 || amount != -1) {
			return 1;
		}
		return 0;
	}

	private int plural(long amount) {
		if(amount != 1 || amount != -1) {
			return 1;
		}
		return 0;
	}

}
