package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.managers.MapManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends SubPluginEventListener<VanillaGroups> implements Listener {

	public PlayerMoveListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();

		if (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()) {
			GroupManager groupManager = subPlugin.getGroupManager();
			
			Group fromGroup = groupManager.getGroupByLocation(from);
			Group toGroup = groupManager.getGroupByLocation(to);
			
			if (fromGroup != toGroup) {
				StandardPlayer player = plugin.getStandardPlayer(event.getPlayer());

				Group playerGroup = groupManager.getPlayerGroup(player);
				
				if (toGroup == null) {
					player.sendTitleMessage(ChatColor.GREEN + "Wilderness");
				} else if (toGroup.isSafeArea()) {
					player.sendTitleMessage(ChatColor.GREEN + "Safezone");
					if (player.isInPvp()) {
						player.sendMessage(String.valueOf(ChatColor.RED) + ChatColor.BOLD + "You are still vulnerable to PVP");
					}
				} else if (toGroup.isWarArea()) {
					player.sendTitleMessage(ChatColor.DARK_RED + "Warzone");
				} else if (toGroup == playerGroup) {
					player.sendTitleMessage(ChatColor.GREEN + toGroup.getName());
				} else if (playerGroup != null && toGroup.isMutualFriendship(playerGroup)) {
					player.sendTitleMessage(ChatColor.DARK_AQUA + toGroup.getName());
				} else {
					player.sendTitleMessage(ChatColor.YELLOW + toGroup.getName());
				}
			}

			// New chunk
			if (from.getBlockX() >> 4 != to.getBlockX() >> 4 || from.getBlockZ() >> 4 != to.getBlockZ() >> 4) {
				MapManager mapManager = subPlugin.getMapManager();

				StandardPlayer player = plugin.getStandardPlayer(event.getPlayer());

				mapManager.updateMap(player);
			}
		}
	}
}
