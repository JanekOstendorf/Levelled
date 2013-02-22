/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled.listeners;

import me.ozzyfant.levelled.Levelled;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener for block events
 */
public class BlockListener implements Listener {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public BlockListener(Levelled plugin) {

		this.plugin = plugin;

		// Register events
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {

		// Is this done with a hoe or anything alike replacing blocks?
		// Prevent very 1337 h4x0r5 from fast levelling

		// Blacklist:
		List<Material> blacklist = new ArrayList<Material>();

		blacklist.add(Material.WOOD_HOE);
		blacklist.add(Material.STONE_HOE);
		blacklist.add(Material.IRON_HOE);
		blacklist.add(Material.GOLD_HOE);
		blacklist.add(Material.DIAMOND_HOE);

		// Do we have a blacklisted item here?
		if(!blacklist.contains(evt.getItemInHand().getType())) {

			this.plugin.addPoints(evt.getPlayer(), Levelled.pointType.PLACED_BLOCKS);

		}

	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {

		this.plugin.addPoints(evt.getPlayer(), Levelled.pointType.BROKEN_BLOCKS);

	}

}
