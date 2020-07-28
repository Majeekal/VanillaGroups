package com.sbezboro.standardgroups.commands;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.commands.BaseCommand;
import com.sbezboro.standardplugin.commands.SubCommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CreateCommand extends SubCommand {

	public CreateCommand(VanillaPlugin plugin, BaseCommand command) {
		super(plugin, command, "create");

		addHelp(ChatColor.YELLOW + "/g create <name>" + ChatColor.RESET + " - create a group");
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
			sender.sendMessage("You must provide a name for your group.");
			return false;
		}

		groupManager.createGroup(player, args[0]);

		groupManager.enableCommandCooldown(new String(player.getUuidString()));

		return true;
	}

}
