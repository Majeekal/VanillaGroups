package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import com.sbezboro.standardplugin.integrations.EssentialsIntegration;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener extends SubPluginEventListener<VanillaGroups> implements Listener {

	public PlayerChatListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEarlyPlayerChat(AsyncPlayerChatEvent event) {
		StandardPlayer player = plugin.getStandardPlayer(event.getPlayer());

		GroupManager groupManager = subPlugin.getGroupManager();

		Group chatPlayerGroup = groupManager.getPlayerGroup(player);

		String format = event.getFormat();
		String message = event.getMessage();

		event.setCancelled(true);

		if (chatPlayerGroup != null && chatPlayerGroup.isGroupChat(player)) {
			format = ChatColor.GREEN + "(To group) " + ChatColor.AQUA + "%s" + ChatColor.RESET + ": %s";

			Bukkit.getConsoleSender().sendMessage(String.format(
					format.replace("(To group)", "(To group " + chatPlayerGroup.getName() + ")"),
					EssentialsIntegration.getNickname(player), message));

			for (StandardPlayer otherPlayer : chatPlayerGroup.getPlayers()) {
				if (otherPlayer.isOnline()) {
					otherPlayer.sendMessage(String.format(format, EssentialsIntegration.getNickname(player), message));
				}
			}
		} else if (chatPlayerGroup != null && chatPlayerGroup.isFriendChat(player)) {
			format = ChatColor.DARK_AQUA + "(To friends) " + ChatColor.AQUA + "%s" + ChatColor.RESET + ": %s";

			Bukkit.getConsoleSender().sendMessage(String.format(
					format.replace("(To friends)", "(To friends of " + chatPlayerGroup.getName() + ")"),
					EssentialsIntegration.getNickname(player), message));

			for (StandardPlayer otherPlayer : chatPlayerGroup.getPlayers()) {
				if (otherPlayer.isOnline()) {
					otherPlayer.sendMessage(String.format(format, EssentialsIntegration.getNickname(player), message));
				}
			}
			for (Group friend : chatPlayerGroup.getMutuallyFriendlyGroups()) {
				for (StandardPlayer otherPlayer : friend.getPlayers()) {
					if (otherPlayer.isOnline()) {
						otherPlayer.sendMessage(String.format(format, EssentialsIntegration.getNickname(player), message));
					}
				}
			}
		} else {
			String identifier = groupManager.getGroupIdentifier(player);

			if (chatPlayerGroup != null) {
				identifier += "[" + chatPlayerGroup.getName() + "]" + ChatColor.RESET;
			}

			format = format.replace("[GROUP]", identifier);

			Bukkit.getConsoleSender().sendMessage(String.format(format, EssentialsIntegration.getNickname(player) + ChatColor.RESET, message));

			for (Player recipient : event.getRecipients()) {
				StandardPlayer onlinePlayer = plugin.getStandardPlayer(recipient);

				Group onlinePlayerGroup = groupManager.getPlayerGroup(onlinePlayer);

				if (onlinePlayerGroup != null && onlinePlayerGroup == chatPlayerGroup) {
					format = format.replace("[", ChatColor.GREEN + "[");
				} else if (onlinePlayerGroup != null && chatPlayerGroup != null && onlinePlayerGroup.isMutualFriendship(chatPlayerGroup)) {
					format = format.replace("[", ChatColor.DARK_AQUA + "[");
				} else {
					format = format.replace("[", ChatColor.YELLOW + "[");
				}

				onlinePlayer.sendMessage(String.format(format, EssentialsIntegration.getNickname(player) + ChatColor.RESET, message));
			}
		}
	}
	
}
