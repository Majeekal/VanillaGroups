package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import com.sbezboro.standardplugin.model.StandardPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingPlaceListener extends SubPluginEventListener<VanillaGroups> implements Listener {

	public HangingPlaceListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onHangingBreak(HangingPlaceEvent event) {
		StandardPlayer player = plugin.getStandardPlayer(event.getPlayer());

		Location location = event.getEntity().getLocation();

		GroupManager groupManager = subPlugin.getGroupManager();

		Group victimGroup = groupManager.getGroupByLocation(location);
		Group attackerGroup = groupManager.getPlayerGroup(player);

		if (victimGroup != null) {
			if (!groupManager.playerInGroup(player, victimGroup) && !groupManager.isGroupsAdmin(player)) {
				if (victimGroup.getPower() >= GroupManager.ENTITY_POWER_THRESHOLD) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Cannot place blocks in the territory of " + victimGroup.getName());
					return;
				}
				if (attackerGroup == null) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Cannot place blocks in the territory of " + victimGroup.getName());
					return;
				}
				String attackerGroupUid = attackerGroup.getUid();
				
				double powerRestoration = 0.25;
				if (victimGroup.getPvpPowerLoss(attackerGroupUid) < powerRestoration) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.GOLD + "Cannot yet place this type of block in the territory of " + victimGroup.getName());
					return;
				}
				victimGroup.addPower(powerRestoration);
				victimGroup.reducePvpPowerLoss(attackerGroupUid, powerRestoration);
			}
		}
	}
}
