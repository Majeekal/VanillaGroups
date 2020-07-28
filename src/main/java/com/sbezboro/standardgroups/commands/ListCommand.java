package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Comparator;

public class ListCommand extends SubCommand {

	public ListCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "list");

		addHelp(ChatColor.YELLOW + "/g list [page]" + ChatColor.RESET + " - show all active groups");
		addHelp(ChatColor.YELLOW + "/g list p [page]" + ChatColor.RESET + " - show groups ordered by current power");
		addHelp(ChatColor.YELLOW + "/g list mp [page]" + ChatColor.RESET + " - show groups ordered by maximum power");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();
		
		int page;
		Comparator<Group> comparator;

		if (args.length == 2) {
			if (args[0].equals("p")) {
				comparator = new GroupManager.PowerComparator();
			} else if (args[0].equals("mp")) {
				comparator = new GroupManager.MaxPowerComparator();
			} else {
				comparator = null;
			}
			
			try {
				page = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				page = 1;
			}
		} else if (args.length == 1) {
			if (args[0].equals("p")) {
				comparator = new GroupManager.PowerComparator();
				page = 1;
			} else if (args[0].equals("mp")) {
				comparator = new GroupManager.MaxPowerComparator();
				page = 1;
			} else {
				comparator = null;
				try {
					page = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					page = 1;
				}
			}
		} else {
			comparator = null;
			page = 1;
		}

		groupManager.groupList(sender, page, comparator);
		
		return true;
	}

}
