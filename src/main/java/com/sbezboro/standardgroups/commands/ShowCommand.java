package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ShowCommand extends SubCommand {

	public ShowCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "show");

		addHelp(ChatColor.YELLOW + "/g show claims" + ChatColor.RESET + " - show a list of claimed chunks for your group");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("claims")) {
				groupManager.showClaims(player);
			} else {
				showHelp(sender);
				return false;
			}
		} else {
			showHelp(sender);
			return false;
		}

		return true;
	}
	
}
