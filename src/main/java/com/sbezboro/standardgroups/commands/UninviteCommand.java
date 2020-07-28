package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UninviteCommand extends SubCommand {

	public UninviteCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "uninvite");

		addHelp(ChatColor.YELLOW + "/g uninvite <player>" + ChatColor.RESET + " - revoke an existing invitation");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage("You must specify a player to un-invite.");
			return false;
		}
		
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();
		groupManager.uninvitePlayer(player, args[0]);
		
		return true;
	}

}
