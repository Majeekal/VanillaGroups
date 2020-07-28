package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UnmodCommand extends SubCommand {

	public UnmodCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "unmod");

		addHelp(ChatColor.YELLOW + "/g unmod <player>" + ChatColor.RESET + " - remove moderator privileges from a player");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage("You must specify a player to remove as a moderator.");
			return false;
		}
		
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();
		groupManager.removeModerator(player, args[0]);
		
		return true;
	}
	
}
