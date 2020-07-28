package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MapCommand extends SubCommand {

	public MapCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "map");

		addHelp(ChatColor.YELLOW + "/g map" + ChatColor.RESET + " - show a map of the surrounding area");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		StandardPlayer player = plugin.getStandardPlayer(sender);

		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();

		groupManager.toggleMap(player);
		
		return true;
	}

}
