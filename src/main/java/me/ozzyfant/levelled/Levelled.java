/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import me.ozzyfant.levelled.listeners.BlockListener;
import me.ozzyfant.levelled.listeners.PlayerListener;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

/**
 * One class to rules them all
 */
public class Levelled extends JavaPlugin {

	/**
	 * Logging logger
	 */
	public static final Logger logger = new Logger();

	/**
	 * Vault permissions
	 */
	public static Permission permission = null;

	/**
	 * Levels
	 */
	protected List<Level> levels;

	/**
	 * Level names
	 */
	protected String[] levelNames;

	/**
	 * Storage
	 */
	protected Storage storage;

	/**#@+
	 * Hash maps (cache)
	 */

	/**
	 * Activity points
	 */
	protected Map<Player, Double> mActivityPoints = new HashMap<Player, Double>();

	/**
	 * Placed blocks
	 */
	protected Map<Player, Integer> mPlacedBlocks = new HashMap<Player, Integer>();

	/**
	 * Broken blocks
	 */
	protected Map<Player, Integer> mBrokenBlocks = new HashMap<Player, Integer>();

	/**
	 * Online time
	 */
	protected Map<Player, Integer> mMinutesPlayed = new HashMap<Player, Integer>();

	/**
	 * Login time of the user, used for time measurement
	 */
	protected Map<Player, Integer> mLoginTime = new HashMap<Player, Integer>();

	/**#@-*/

	public static enum pointType {

		PLACED_BLOCKS,
		BROKEN_BLOCKS,
		TIME,
		KILLED_ANIMAL,
		KILLED_MONSTER

	}

	/**
	 * Start this plugin up
	 */
	@Override
	public void onEnable() {

		Levelled.logger.info("Plugin enabled.");

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();

		// Load levels
		this.levels = this.fetchLevels();

		// Initialize the storage
		this.storage = new Storage(this, new File(this.getDataFolder().getAbsolutePath() + File.separator + "storage.yml"));

		// Event listeners
		new BlockListener(this);
		new PlayerListener(this);

		// Command
		this.getCommand("level").setExecutor(new LevelledCommandExecutor(this));

		// Setup thread
		int period = this.getConfig().getInt("updatePeriod");
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new LevelledThread(this), period * 20, period * 20);

		// Setup vault permissions
		if(!this.setupPermissions())
			Levelled.logger.severe("Failed to initialize Vault permissions!");

	}

	/**
	 * Shut this plugin down
	 */
	@Override
	public void onDisable() {

		this.updateAll(false);
		Levelled.logger.info("Plugin disabled.");

	}

	/**
	 * Setup Vault permissions
	 * @return Success
	 */
	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	/**
	 * Fetch all levels from the config
	 * @return Array of all Levels
	 */
	protected List<Level> fetchLevels() {

		// The level config node
		ConfigurationSection allLevelsConfig = this.getConfig().getConfigurationSection("levels");

		// Level names
		this.levelNames = new String[allLevelsConfig.getKeys(false).size()];

		// Buffer
		List<Level> allLevels = new ArrayList<Level>();

		int i = 0;

		for(String levelKey : allLevelsConfig.getKeys(false)) {

			ConfigurationSection currentLevelConfig = allLevelsConfig.getConfigurationSection(levelKey);

			allLevels.add(i, new Level(currentLevelConfig, levelKey, i));
			this.levelNames[i] = allLevels.get(i).getName();

			i++;

		}

		return allLevels;

	}

	/**
	 * Fetch data from the storage for this player
	 * @param player Player
	 */
	public void fetchPlayerData(Player player) {

		// Is there an entry?
		if(this.storage.getConfiguration().contains("storage." + player.getName())) {

			// Read from the config and write into the cache
			this.mActivityPoints.put(player, this.storage.getPlayerPoints(player));
			this.mPlacedBlocks.put(player, this.storage.getPlayerPlacedBlocks(player));
			this.mBrokenBlocks.put(player, this.storage.getPlayerBrokenBlocks(player));
			this.mMinutesPlayed.put(player, this.storage.getPlayerOnlineTime(player));

		}
		// Create a new entry
		else {

			this.storage.setPlayerActivityPoints(player, 0);
			this.storage.setPlayerPlacedBlocks(player, 0);
			this.storage.setPlayerBrokenBlocks(player, 0);
			this.storage.setPlayerOnlineTime(player, 0);
			this.storage.setPlayerLevel(player, this.levels.get(0));

			this.mActivityPoints.put(player, (double) 0);
			this.mPlacedBlocks.put(player, 0);
			this.mBrokenBlocks.put(player, 0);
			this.mMinutesPlayed.put(player, 0);
			this.mLoginTime.put(player, 0);

		}

	}

	/**
	 * Write the cache data to disc
	 * @param player Player
	 */
	public void savePlayerData(Player player) {

		// Fetch and write
		if(this.mActivityPoints.containsKey(player))
			this.storage.setPlayerActivityPoints(player, this.mActivityPoints.get(player));

		if(this.mPlacedBlocks.containsKey(player))
			this.storage.setPlayerPlacedBlocks(player, this.mPlacedBlocks.get(player));

		if(this.mBrokenBlocks.containsKey(player))
			this.storage.setPlayerBrokenBlocks(player, this.mBrokenBlocks.get(player));

		if(this.mMinutesPlayed.containsKey(player))
			this.storage.setPlayerOnlineTime(player, this.mMinutesPlayed.get(player));

		this.levelUp(player);

	}

	/**
	 * Clear the cache of this user
	 * @param player Player
	 */
	public void clearCache(Player player) {

		if(this.mActivityPoints.containsKey(player))
			this.mActivityPoints.remove(player);

		if(this.mPlacedBlocks.containsKey(player))
			this.mPlacedBlocks.remove(player);

		if(this.mBrokenBlocks.containsKey(player))
			this.mBrokenBlocks.remove(player);

		if(this.mMinutesPlayed.containsKey(player))
			this.mMinutesPlayed.remove(player);

		if(this.mLoginTime.containsKey(player))
			this.mLoginTime.remove(player);

	}

	/**
	 * Save and rehash the data for this user
	 * @param player Player
	 */
	public void flushCache(Player player) {

		// Save pending data
		this.savePlayerData(player);

		// Clear cache
		this.clearCache(player);

		// Refetch the data
		this.fetchPlayerData(player);

		// Restart the measurement
		this.startMeasure(player);

	}

	/**
	 * Write everything to the storage
	 * @param startAgain Start the measurement again?
	 */
	public void updateAll(boolean startAgain) {

		Player[] onlinePlayers = this.getServer().getOnlinePlayers();

		for(Player player : onlinePlayers) {

			this.stopMeasure(player);

			if(startAgain)
				this.startMeasure(player);

			this.savePlayerData(player);

		}

	}

	/*
	 * Point methods
	 */

	/**
	 * Add points for a player
	 * @param player Player
	 * @param type Type of event
	 */
	public void addPoints(Player player, pointType type) {

		this.addPoints(player, type, 1);

	}

	/**
	 * Add points for a player
	 * @param player Player
	 * @param type Type of event
	 * @param count How many?
	 */
	public void addPoints(Player player, pointType type, int count) {

		// Check the players permission
		if(!player.hasPermission("levelled.level"))
			return;

		double pointsToAdd = 0;

		switch(type) {

			case TIME:

				// Calculate the number of points to add
				// Config's in hours, this is in minutes, so /60
				pointsToAdd = (double) count * (this.getConfig().getDouble("points.time") / 60);

				// Save to the cache
				this.mMinutesPlayed.put(player, this.mMinutesPlayed.get(player) + count);

				break;

			case PLACED_BLOCKS:

				// Calculate the number of points to add
				pointsToAdd = (double) count * this.getConfig().getDouble("points.placed-block");

				// Save to the cache
				this.mPlacedBlocks.put(player, this.mPlacedBlocks.get(player) + count);

				break;

			case BROKEN_BLOCKS:

				// Calculate the number of points to add
				pointsToAdd = (double) count * this.getConfig().getDouble("points.broken-block");

				// Save to the cache
				this.mBrokenBlocks.put(player, this.mBrokenBlocks.get(player) + count);

				break;

			case KILLED_MONSTER:

				// Calculate the number of points to add
				pointsToAdd = (double) count * this.getConfig().getDouble("points.killed-monster");

				break;

			case KILLED_ANIMAL:

				// Calculate the number of points to add
				pointsToAdd = (double) count * this.getConfig().getDouble("points.killed-animal");

				break;



		}

		// Save points
		this.mActivityPoints.put(player, this.mActivityPoints.get(player) + pointsToAdd);

		this.levelUp(player);

	}

	protected void levelUp(Player player) {

		Level levelToGain = this.getPlayerLevel(player);

		// Do we support groups?


		// Is that one higher than ours?
		if(levelToGain.getNumber() <= this.storage.getPlayerLevel(player).getNumber())
			return;

		try {

			// Remove all old groups
			for(String group : permission.getPlayerGroups(player)) {

				if(Arrays.asList(this.levelNames).contains(group)) {

					permission.playerRemoveGroup(player, group);

				}

			}

			// Level up
			permission.playerAddGroup(player, levelToGain.getGroup());

		}
		catch(UnsupportedOperationException e) {

			Levelled.logger.severe("Failed to set permissions for " + levelToGain.getName() + " (perm group " + levelToGain.getGroup() + ")");

		}

		// Write into the storage
		this.storage.setPlayerLevel(player, levelToGain);

		// Do the broadcasting
		if(this.getConfig().getBoolean("messages.global-notify")) {

			this.getServer().broadcastMessage(
					ChatColor.translateAlternateColorCodes('&',
							this.getConfig().getString("messages.global-message")
									.replaceAll("%player%", player.getDisplayName())
									.replaceAll("%level%", levelToGain.getName())
					)
			);

		}
		else {

			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&',
							this.getConfig().getString("messages.message")
									.replaceAll("%level%", levelToGain.getName())
					)
			);

		}

	}

	/*
	 * Measurement
	 */

	/**
	 * Start the time measurement by caching the start time
	 * @param player Player
	 */
	public void startMeasure(Player player) {

		this.mLoginTime.put(player, Math.round(System.currentTimeMillis() / 1000));

	}

	public void stopMeasure(Player player) {

		// Do we have this user?
		if(!this.mLoginTime.containsKey(player))
			return;

		int startTime, minutesToAdd;
		int currentTime = Math.round(System.currentTimeMillis() / 1000);

		startTime = this.mLoginTime.get(player);

		// Evil times?
		if(startTime > currentTime || startTime <= 0 || currentTime <= 0) {

			Levelled.logger.severe("Evil time detected!");
			return;

		}

		// Calc the minutes to add
		minutesToAdd = Math.round((currentTime - startTime) / 60);

		this.addPoints(player, pointType.TIME, minutesToAdd);

		// Clear cache
		this.mLoginTime.remove(player);

	}

	/**
	 * Get theoretic level
	 * @param player Player
	 * @return Level the player should have
	 */
	public Level getPlayerLevel(Player player) {

		Level currentLevel;

		// Begin at the end and get the last level greater than the current
		int i = this.levels.size() - 1;

		while(i >= 0 && this.mActivityPoints.get(player) >= this.levels.get(i).getNeededPoints()) {

			i--;

		}

		currentLevel = this.levels.get(i + 1);

		return currentLevel;

	}

	/*
	 * Getters and setters
	 */

	public List<Level> getLevels() {
		return levels;
	}
}
