package is.currency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import is.currency.conf.CurrencyConfiguration;
import is.currency.list.PlayerListener;
import is.currency.syst.Account;
import is.currency.syst.AccountManager;
import is.currency.syst.FormatHelper;
import is.currency.util.ConfigUtils;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

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
public class Currency extends JavaPlugin {
	
	private CurrencyConfiguration currencyConfig;
	private AccountManager accountManager;
	private FormatHelper formatHelper;
	private PlayerListener playerListener;
	
	@Override
	public void onEnable() {
		ConfigUtils.extract(super.getDataFolder(), "config.yml");
		this.currencyConfig = new CurrencyConfiguration(new File(super.getDataFolder(), "config.yml"));
		this.currencyConfig.load();
		this.accountManager = new AccountManager(this);
		this.formatHelper = new FormatHelper(this);
		this.playerListener = new PlayerListener(this);
		try {
			this.accountManager.size();
		} catch(Throwable throwable) {
			this.installDDL();
		}
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, 
				this.playerListener, Event.Priority.Normal, this);
		System.out.println(super.getDescription().getFullName() + " authored by " 
				+ super.getDescription().getAuthors() + " enabled.");
	}
	
	@Override
	public void onDisable() {
		this.currencyConfig = null;
		this.accountManager = null;
		this.formatHelper = null;
		this.playerListener = null;
		System.out.println(super.getDescription().getFullName() + " disabled.");
	}
	
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Account.class);
		return list;
	}
	
	public CurrencyConfiguration getCurrencyConfig() {
		return this.currencyConfig;
	}
	
	public AccountManager getAccountManager() {
		return this.accountManager;
	}
	
	public FormatHelper getFormatHelper() {
		return this.formatHelper;
	}

}
