package is.currency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import is.currency.config.CurrencyConfiguration;
import is.currency.listener.PlayerListener;
import is.currency.queue.AccountQueue;
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

	private PlayerListener playerListener;

	private Thread thread;
	private AccountQueue accountQueue;
	private AccountManager accountManager;

	private FormatHelper formatHelper;
	private CurrencyConfiguration currencyConfig;

	@Override
	public void onEnable() {
		ConfigUtils.extract(super.getDataFolder(), "config.yml");
		this.currencyConfig = new CurrencyConfiguration(new File(super.getDataFolder(), "config.yml"));
		this.currencyConfig.load();
		this.formatHelper = new FormatHelper(this);

		this.accountQueue = new AccountQueue(this);
		this.accountManager = new AccountManager(this);
		try {
			this.getDatabase().find(Account.class).findRowCount();
		} catch(Throwable throwable) {
			this.installDDL();
		}

		this.thread = new Thread(this.accountQueue);
		this.thread.setName("account-queue-thread");
		this.thread.start();
		
		this.playerListener = new PlayerListener(this);
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, 
				this.playerListener, Event.Priority.Normal, this);
		
		System.out.println(super.getDescription().getFullName() + " authored by " 
				+ super.getDescription().getAuthors() + " enabled.");
	}

	@Override
	public void onDisable() {
		try {
			if(this.thread != null) {
				this.thread.interrupt();
				while(this.thread.isAlive());
			}
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			this.playerListener = null; 
			this.thread = null;
			this.accountQueue = null;
			this.accountManager = null;
			this.formatHelper = null;
			this.currencyConfig = null;
		}
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

	public AccountQueue getAccountQueue() {
		return this.accountQueue;
	}

	public AccountManager getAccountManager() {
		return this.accountManager;
	}

	public FormatHelper getFormatHelper() {
		return this.formatHelper;
	}

}
