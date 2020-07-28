package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerTeleportListener extends SubPluginEventListener<VanillaGroups> implements Listener {
	public PlayerTeleportListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.getCause() == TeleportCause.CHORUS_FRUIT) {
			StandardPlayer player = plugin.getStandardPlayer(event.getPlayer());
			Location from = event.getFrom();
			Location to = event.getTo();
			
			Group group = subPlugin.getGroupManager().getGroupByLocation(to);
			if (group != null && !group.isMember(player)) {
				event.setCancelled(true);
				return;
			}
			group = subPlugin.getGroupManager().getGroupByLocation(from);
			if (group != null && !group.isMember(player)) {
				event.setCancelled(true);
			}
		}
	}
}
