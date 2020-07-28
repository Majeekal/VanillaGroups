package com.sbezboro.standardgroups.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;

import java.util.ArrayList;

public class PowerCommand extends SubCommand {

	public PowerCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "power", new ArrayList<String>() {{
			add("p");
		}});

		addHelp(ChatColor.YELLOW + "/g power [name]" + ChatColor.RESET + " - show a group's current power");
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
		
		groupManager.groupPower(sender, name);
		
		return true;
	}

}
