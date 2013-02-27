/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled.listeners;

import me.ozzyfant.levelled.Levelled;
import org.bukkit.Achievement;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener for player events
 */
public class PlayerListener implements Listener {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public PlayerListener(Levelled plugin) {

		this.plugin = plugin;

		// Register events
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent evt) {

		plugin.fetchPlayerData(evt.getPlayer());
		plugin.startMeasure(evt.getPlayer());

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent evt) {

		this.playerDisconnect(evt.getPlayer());

	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent evt) {

		this.playerDisconnect(evt.getPlayer());

	}

	/**
	 * Stuff to do on disconnect
	 * @param player Player disconnected
	 */
	protected void playerDisconnect(Player player) {

		plugin.stopMeasure(player);
		plugin.savePlayerData(player);
		plugin.clearCache(player);

	}

    /**
     *  Checking if the player has killed an entity
     */
    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity().getKiller() instanceof Player){
            Player player = (Player) e.getEntity().getKiller();
            Entity mob = null;
            if(e.getEntity() instanceof Blaze) {
                mob = (Blaze) e.getEntity();
            }
            else if (e.getEntity() instanceof Zombie){
                mob = (Zombie) e.getEntity();
            }
            else if (e.getEntity() instanceof Skeleton){
                mob = (Skeleton) e.getEntity();
            }
            else if (e.getEntity() instanceof Spider){
                mob = (Spider) e.getEntity();
            }
            else if (e.getEntity() instanceof CaveSpider){
                mob = (CaveSpider) e.getEntity();
            }
            else if (e.getEntity() instanceof Creeper){
                mob = (Creeper) e.getEntity();
            }
            else if (e.getEntity() instanceof Enderman){
                mob = (Enderman) e.getEntity();
            }
            else if (e.getEntity() instanceof Ghast){
                mob = (Ghast) e.getEntity();
            }
            else if (e.getEntity() instanceof Giant){
                mob = (Giant) e.getEntity();
            }
            else if (e.getEntity() instanceof Witch){
                mob = (Witch) e.getEntity();
            }
            else if (e.getEntity() instanceof Wither){
                mob = (Wither) e.getEntity();
            }
            else if (e.getEntity() instanceof WitherSkull){
                mob = (WitherSkull) e.getEntity();
            }
            else if (e.getEntity() instanceof PigZombie){
                mob = (PigZombie) e.getEntity();
            }
            else if (e.getEntity() instanceof NPC){
                mob = (NPC) e.getEntity();
            }
            else if (e.getEntity() instanceof Sheep){
                mob = (Sheep) e.getEntity();
            }
            else if (e.getEntity() instanceof MushroomCow){
                mob = (MushroomCow) e.getEntity();
            }
            else if (e.getEntity() instanceof Cow){
                mob = (Cow) e.getEntity();
            }
            else if (e.getEntity() instanceof Squid){
                mob = (Squid) e.getEntity();
            }
            else if (e.getEntity() instanceof Chicken){
                mob = (Chicken) e.getEntity();
            }
            else if (e.getEntity() instanceof Pig){
                mob = (Pig) e.getEntity();
            }
            else if (e.getEntity() instanceof Bat){
                mob = (Bat) e.getEntity();
            }
            else if (e.getEntity() instanceof Golem){
                mob = (Golem) e.getEntity();
            }
            else if (e.getEntity() instanceof Ocelot){
                mob = (Ocelot) e.getEntity();
            }
            else if (e.getEntity() instanceof Wolf){
                mob = (Wolf) e.getEntity();
            }
            else{

            }

            if(mob != null){
                this.plugin.addPoints(player, Levelled.pointType.KILLED_MOBS);
            }

        }
    }


}
