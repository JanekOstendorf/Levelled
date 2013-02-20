/**
 * @author    Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license   http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Level
 */
public class Level {

    /**
     * Name of this level
     */
    protected String name;

    /**
     * Order number of this level
     */
    protected int number;

    /**
     * Needed points for this level
     */
    protected double neededPoints;

    /**
     * Init the level
     * @param configurationSection Configuration section for this level
     * @param number Number of this level in the order of levels
     */
    public Level(ConfigurationSection configurationSection, int number) {

        this.name = configurationSection.getString("name");
        this.neededPoints = configurationSection.getDouble("points");

        this.number = number;

    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getNeededPoints() {
        return neededPoints;
    }
}
