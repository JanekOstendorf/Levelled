/**
 * @author Janek Ostendorf <ozzy2345de@gmail.com>
 * @copyright Copyright (c) Janek Ostendorf
 * @license http://opensource.org/licenses/gpl-3.0.html GNU General Public License, version 3
 */
package me.ozzyfant.levelled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * Command executor executes commands executing commands
 */
public class LevelledCommandExecutor implements CommandExecutor {

	/**
	 * Plugin reference
	 */
	protected Levelled plugin;

	public LevelledCommandExecutor(Levelled plugin) {

		this.plugin = plugin;

	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        // Is this a player or the console?
        boolean console = false;
        Player player = null;

        // Is this a player?
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player!");
            console = true;
		}

        else{
		 player = (Player) sender;
        }

		// Our argument commands:
		if(args.length == 0) {

			// Eww, console
			if(console) {
				player.sendMessage(ChatColor.RED + "This command may only be run by a player!");
				return true;
			}

			this.cmdStatus(player, command, label, args);

		}
		else if(args.length == 1) {


			String[] cmdArgs = new String[args.length - 1];
			cmdArgs = Arrays.asList(args).subList(1, args.length).toArray(cmdArgs);

			// Help command
			if(args[0].equalsIgnoreCase("help")) {

				this.cmdHelp(player, command, label, cmdArgs);

			}
			else if(args[0].equalsIgnoreCase("status")) {

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&6Usage: &7/"+command.getName()+" status <player>"
				));

			}

		}
		else if(args.length == 2) {

			String[] cmdArgs = new String[args.length - 1];
			cmdArgs = Arrays.asList(args).subList(1, args.length).toArray(cmdArgs);

			// Status command for other players
			if(args[0].equalsIgnoreCase("status")) {

				// Get player
				OfflinePlayer cmdPlayer = plugin.getServer().getOfflinePlayer(args[1]);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&7-- &6Level Status of " + cmdPlayer.getName() + " &7--"
				));
				// TODO: really do something!
				// TODO: check for permission levelled.status.others

			}

		}

		return true;

	}

	private void cmdStatus(Player player, Command command, String label, String[] args) {

		// Check permissions
		if(plugin.storage.playerExists(player)) {

			if(player.hasPermission("levelled.status")) {

				// Update the hashmaps
				plugin.flushCache(player);

				int bblocks, pblocks, minutes;
				double points;
				Level currentLevel, nextLevel;

				DecimalFormat dfPoints = (DecimalFormat)DecimalFormat.getInstance(Locale.ENGLISH);
				dfPoints.applyPattern("#0.00");

				bblocks = plugin.mBrokenBlocks.get(player);
				pblocks = plugin.mPlacedBlocks.get(player);
				minutes = plugin.mMinutesPlayed.get(player);
				points = plugin.mActivityPoints.get(player);

				currentLevel = plugin.getPlayerLevel(player);

				if(currentLevel.getNumber() + 1 >= plugin.getLevels().size())
					nextLevel = currentLevel;
				else
					nextLevel = plugin.getLevels().get(currentLevel.getNumber() + 1);


				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&7-- &6Level Status of " + player.getName() + " &7--"
				));

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"  &7Current level: &6" + currentLevel.getName() + " &7(" + currentLevel.getNeededPoints() + " points)"
				));

				if(nextLevel.equals(currentLevel)) {

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"  &7Next level: &6None. You've reached the maximum."
					));

				}
				else {

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"  &7Next level: &6" + nextLevel.getName() + " &7(" + nextLevel.getNeededPoints() + " points)"
					));

				}

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"  &7Activity points: &6" + dfPoints.format(points)
				));

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"  &7Online time: &6" + (int) Math.floor(minutes / 60.0f) + ":" + new DecimalFormat("00").format(minutes % 60)
				));

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"  &7Placed blocks: &6" + pblocks
				));

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"  &7Broken blocks: &6" + bblocks
				));

			}
			else {

				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&7This user is unknown to my level database."
				));

			}

		}

	}

	private void cmdHelp(Player player, Command command, String label, String[] args) {

		// Send our help text
		player.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&7-- &6Levelled help &7--"
		));

		player.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"  &7<> = needed, [] = optional"
		));

		// Level status
		if(player.hasPermission("levelled.status")) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"  &6/" + command.getName()
			));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"    &7Show current level status"
			));
		}

	}

}
