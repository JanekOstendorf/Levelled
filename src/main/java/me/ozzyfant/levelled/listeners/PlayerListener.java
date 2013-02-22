/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled.listeners;

import me.ozzyfant.levelled.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener for player events
 */
public class PlayerListener implements Listener {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public PlayerListener(Levelled plugin) {

		this.plugin = plugin;

		// Register events
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent evt) {

		plugin.fetchPlayerData(evt.getPlayer());
		plugin.startMeasure(evt.getPlayer());

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {

		this.playerDisconnect(evt.getPlayer());

	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent evt) {

		this.playerDisconnect(evt.getPlayer());

	}

	/**
	 * Stuff to do on disconnect
	 * @param player Player disconnected
	 */
	protected void playerDisconnect(Player player) {

		plugin.stopMeasure(player);
		plugin.savePlayerData(player);
		plugin.clearCache(player);

	}

}
