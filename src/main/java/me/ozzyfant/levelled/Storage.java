/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import org.bukkit.configuration.file.YamlConfiguration;

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
	 * Initialize the config
	 * @param storageFile Configuration file
	 */
	public Storage(File storageFile) {

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

}
