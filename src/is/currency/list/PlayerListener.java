package is.currency.list;

import is.currency.Currency;

import org.bukkit.event.player.PlayerJoinEvent;

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
public class PlayerListener extends org.bukkit.event.player.PlayerListener {

	private Currency currency;
	
	public PlayerListener(Currency currency) {
		this.currency = currency;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.currency.getAccountManager().create(event.getPlayer().getName());
	}
	
}
