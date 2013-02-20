/**
 * @author    Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license   http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * One class to rules them all
 */
public class Levelled extends JavaPlugin {

    public static final Logger logger = new Logger();

    /**
     * Start this plugin up
     */
    @Override
    public void onEnable() {

        Levelled.logger.info("Plugin enabled.");

    }

    /**
     * Shut this plugin down
     */
    @Override
    public void onDisable() {

        Levelled.logger.info("Plugin disabled.");

    }

}
