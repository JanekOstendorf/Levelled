/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Handler for the storage file
 */
public class Storage {

	/**
	 * Configuration to use
	 */
	protected YamlConfiguration configuration;

	/**
	 * File the configuration is saved in
	 */
	protected File file;

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	/**
	 * Initialize the config
	 * @param storageFile Configuration file
	 */
	public Storage(Levelled plugin, File storageFile) {

		this.plugin = plugin;
		this.file = storageFile;

		this.configuration = YamlConfiguration.loadConfiguration(this.file);

	}

	/**
	 * Saves the configuration onto disk
	 * @return Success
	 */
	public boolean save() {

		try {
			this.configuration.save(this.file);
		}
		catch (IOException e) {
			Levelled.logger.severe("Failed to write to the storage file! " + e.getMessage());
			return false;
		}

		return true;

	}

	/**
	 * Get a player's activity points
	 * @param player Player
	 * @return Activity points
	 */
	public double getPlayerPoints(Player player) {

		if(this.configuration.contains("storage." + player.getName() + ".activityPoints"))
			return this.configuration.getDouble("storage." + player.getName() + ".activityPoints");

		return 0;

	}

	/**
	 * Get a player's activity points
	 * @param player OfflinePlayer
	 * @return Activity points
	 */
	public double getPlayerPoints(OfflinePlayer player) {

		if(this.configuration.contains("storage." + player.getName() + ".activityPoints"))
			return this.configuration.getDouble("storage." + player.getName() + ".activityPoints");

		return 0;

	}

	/**
	 * Get a player's number of placed blocks
	 * @param player Player
	 * @return Number of placed blocks
	 */
	public int getPlayerPlacedBlocks(Player player) {

		if(this.configuration.contains("storage." + player.getName() + ".placedBlocks"))
			return this.configuration.getInt("storage." + player.getName() + ".placedBlocks");

		return 0;

	}

	/**
	 * Get a player's number of placed blocks
	 * @param player OfflinePlayer
	 * @return Number of placed blocks
	 */
	public int getPlayerPlacedBlocks(OfflinePlayer player) {

		if(this.configuration.contains("storage." + player.getName() + ".placedBlocks"))
			return this.configuration.getInt("storage." + player.getName() + ".placedBlocks");

		return 0;

	}

	/**
	 * Get a player's number of broken blocks
	 * @param player Player
	 * @return Number of placed blocks
	 */
	public int getPlayerBrokenBlocks(Player player) {

		if(this.configuration.contains("storage." + player.getName() + ".brokenBlocks"))
			return this.configuration.getInt("storage." + player.getName() + ".brokenBlocks");

		return 0;

	}

	/**
	 * Get a player's number of broken blocks
	 * @param player OfflinePlayer
	 * @return Number of placed blocks
	 */
	public int getPlayerBrokenBlocks(OfflinePlayer player) {

		if(this.configuration.contains("storage." + player.getName() + ".brokenBlocks"))
			return this.configuration.getInt("storage." + player.getName() + ".brokenBlocks");

		return 0;

	}

	/**
	 * Get a player's online time in minutes
	 * @param player Player
	 * @return Online time in minutes
	 */
	public int getPlayerOnlineTime(Player player) {

		if(this.configuration.contains("storage." + player.getName() + ".onlineTime"))
			return this.configuration.getInt("storage." + player.getName() + ".onlineTime");

		return 0;

	}

	/**
	 * Get a player's online time in minutes
	 * @param player OfflinePlayer
	 * @return Online time in minutes
	 */
	public int getPlayerOnlineTime(OfflinePlayer player) {

		if(this.configuration.contains("storage." + player.getName() + ".onlineTime"))
			return this.configuration.getInt("storage." + player.getName() + ".onlineTime");

		return 0;

	}

	/**
	 * Get a players actual level
	 * @param player Player
	 * @return Actual level
	 */
	public Level getPlayerLevel(Player player) {

		if(this.configuration.contains("storage." + player.getName() + ".levelNumber")) {

			int levelNumber = this.configuration.getInt("storage." + player.getName() + ".levelNumber");

			return plugin.getLevels().get(levelNumber);

		}

		return plugin.getLevels().get(0);

	}

	/**
	 * Set a player's activity points
	 * @param player Player
	 * @param points Points
	 */
	public void setPlayerActivityPoints(Player player, double points) {

		this.configuration.set("storage." + player.getName() + ".activityPoints", points);

		this.save();

	}

	/**
	 * Set a player's placed blocks
	 * @param player Player
	 * @param blocks Points
	 */
	public void setPlayerPlacedBlocks(Player player, int blocks) {

		this.configuration.set("storage." + player.getName() + ".placedBlocks", blocks);

		this.save();

	}

	/**
	 * Set a player's broken blocks
	 * @param player Player
	 * @param blocks Points
	 */
	public void setPlayerBrokenBlocks(Player player, int blocks) {

		this.configuration.set("storage." + player.getName() + ".brokenBlocks", blocks);

		this.save();

	}

	/**
	 * Set a player's online time in minutes
	 * @param player Player
	 * @param minutes Points
	 */
	public void setPlayerOnlineTime(Player player, int minutes) {

		this.configuration.set("storage." + player.getName() + ".onlineTime", minutes);

		this.save();

	}

	public void setPlayerLevel(Player player, Level level) {

		this.configuration.set("storage."+ player.getName() + ".levelNumber", level.getNumber());

		this.save();

	}

	/**
	 * Does this user exist in the storage file?
	 * @param player Player
	 * @return Does this user exist in the storage file?
	 */
	public boolean playerExists(Player player) {

		try {
			return this.configuration.contains("storage."+player.getName());
		}
		catch(NullPointerException e) {

			return false;

		}

	}

	public YamlConfiguration getConfiguration() {
		return this.configuration;
	}

}
