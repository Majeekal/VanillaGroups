package com.sbezboro.standardgroups.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;

public class JoinCommand extends SubCommand {

	public JoinCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "join");

		addHelp(ChatColor.YELLOW + "/g join <name>" + ChatColor.RESET + " - attempt to join a group");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		if (player == null) {
			command.showPlayerOnlyMessage(sender);
			return false;
		}
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();
		
		if (groupManager.hasCommandCooldown(new String(player.getUuidString()), true)) {
			groupManager.enableCommandCooldown(new String(player.getUuidString()));
			return false;
		}
		
		if (args.length != 1) {
			sender.sendMessage("You must specify which group name you would like to join.");
			return false;
		}
		
		groupManager.joinGroup(player, args[0]);
		
		groupManager.enableCommandCooldown(new String(player.getUuidString()));
		
		return true;
	}
	
}
