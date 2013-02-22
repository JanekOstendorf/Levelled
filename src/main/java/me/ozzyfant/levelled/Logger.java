/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import java.util.logging.Level;

/**
 * Simple custom logger
 */
public class Logger {

	protected final java.util.logging.Logger log;

	/**
	 * Constructor
	 */
	public Logger() {

		this.log = java.util.logging.Logger.getLogger("Minecraft");

	}

	/**
	 * Log an info message
	 *
	 * @param message Message to be logged
	 */
	public void info(String message) {

		this.log.log(Level.INFO, "[Levelled]" + message);

	}

	/**
	 * Log an severe message
	 *
	 * @param message Message to be logged
	 */
	public void severe(String message) {

		this.log.log(Level.SEVERE, "[Levelled] " + message);

	}

}
