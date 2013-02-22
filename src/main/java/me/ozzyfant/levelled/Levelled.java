/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * One class to rules them all
 */
public class Levelled extends JavaPlugin {

	/**
	 * Logging logger
	 */
	public static final Logger logger = new Logger();

	/**
	 * Levels
	 */
	protected Set<Level> levels;

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
	protected Map<Player, BigInteger> mLoginTime = new HashMap<Player, BigInteger>();

	/**#@-*/

	/**
	 * Start this plugin up
	 */
	@Override
	public void onEnable() {

		Levelled.logger.info("Plugin enabled.");

		// Load levels
		this.levels = this.fetchLevels();

		// Initialize the storage
		this.storage = new Storage(new File(this.getDataFolder().getAbsolutePath() + File.);

	}

	/**
	 * Shut this plugin down
	 */
	@Override
	public void onDisable() {

		Levelled.logger.info("Plugin disabled.");

	}

	/**
	 * Fetch all levels from the config
	 * @return Array of all Levels
	 */
	protected Set<Level> fetchLevels() {

		// The level config node
		ConfigurationSection allLevelsConfig = this.getConfig().getConfigurationSection("levels");

		// Buffer
		Set<Level> allLevels = new TreeSet<Level>();

		int i = 0;

		for(String levelKey : allLevelsConfig.getKeys(false)) {

			ConfigurationSection currentLevelConfig = allLevelsConfig.getConfigurationSection(levelKey);

			allLevels.add(new Level(currentLevelConfig, i++));

		}

		return allLevels;

	}



	/*
	 * Getters and setters
	 */

	public Set<Level> getLevels() {
		return levels;
	}
}
