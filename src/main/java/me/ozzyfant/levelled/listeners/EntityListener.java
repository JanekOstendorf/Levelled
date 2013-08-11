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
 * Entity Listener
 */
public class EntityListener implements Listener {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public EntityListener(Levelled plugin) {

		this.plugin = plugin;

		// Register events
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {

		if(e.getEntity() instanceof Animals) {
                    if(e.getEntity().getKiller() instanceof Player){
			// Did this guy really kill the monster?
			//if(e.getEntity().getLastDamageCause().getEntity().equals(e.getEntity().getKiller()))
			this.plugin.addPoints(e.getEntity().getKiller(), Levelled.PointType.KILLED_ANIMAL);
                    }
		}
		else if(e.getEntity() instanceof Monster) {
                    if(e.getEntity().getKiller() instanceof Player){
			// Did this guy really kill the monster?
			//if(e.getEntity().get)
			this.plugin.addPoints(e.getEntity().getKiller(), Levelled.PointType.KILLED_MONSTER);
                    }
                }


	}

}
