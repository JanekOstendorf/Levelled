/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled.listeners;

import me.ozzyfant.levelled.Levelled;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 */
public class EntityListener implements Listener {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public EntityListener(Levelled plugin) {

		this.plugin = plugin;

	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {

		// Has a player killed this entity?
		if(e.getEntity().getKiller() instanceof Player) {

			if(e.getEntity() instanceof Animals) {

				this.plugin.addPoints(e.getEntity().getKiller(), Levelled.pointType.KILLED_ANIMAL);

			}
			else if(e.getEntity() instanceof Monster) {

				this.plugin.addPoints(e.getEntity().getKiller(), Levelled.pointType.KILLED_MONSTER);

			}

		}
	}

}
