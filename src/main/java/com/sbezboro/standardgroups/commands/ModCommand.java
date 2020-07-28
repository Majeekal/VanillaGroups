package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ModCommand extends SubCommand {

	public ModCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "mod");

		addHelp(ChatColor.YELLOW + "/g mod <player>" + ChatColor.RESET + " - set a player as a moderator");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage("You must specify a player to set as a moderator.");
			return false;
		}
		
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();
		groupManager.addModerator(player, args[0]);
		
		return true;
	}

}
