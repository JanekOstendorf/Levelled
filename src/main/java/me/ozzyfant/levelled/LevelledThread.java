/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

/**
 *
 */
public class LevelledThread extends Thread {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public LevelledThread(Levelled plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {

		plugin.updateAll(true);

	}

}
