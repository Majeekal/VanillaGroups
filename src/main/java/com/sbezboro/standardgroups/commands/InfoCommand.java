package com.sbezboro.standardgroups.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class InfoCommand extends SubCommand {

	public InfoCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "info");

		addHelp(ChatColor.YELLOW + "/g info [name]" + ChatColor.RESET + " - show group info");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();

		String name = null;
		
		if (args.length == 0) {
			if (player == null) {
				command.showPlayerOnlyMessage(sender);
				return false;
			}
		} else if (args.length == 1) {
			name = args[0];
		} else {
			command.showUsageInfo(sender);
			return false;
		}
		
		groupManager.groupInfo(sender, name);
		
		return true;
	}

}
