package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ChatCommand extends SubCommand {

	public ChatCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "chat", new ArrayList<String>() {{
			add("c");
		}});

		addHelp(ChatColor.YELLOW + "/g chat" + ChatColor.RESET + " - switch chat modes");
	}

	@Override
	public boolean handle(CommandSender sender, String[] args) {
		StandardPlayer player = plugin.getStandardPlayer(sender);
		
		GroupManager groupManager = VanillaGroups.getPlugin().getGroupManager();
		
		if (args.length == 0) {
			groupManager.toggleChat(player);
		}
		else {
			char chat = args[0].charAt(0);
			groupManager.setChat(player, chat);
		}
		
		return true;
	}

}
