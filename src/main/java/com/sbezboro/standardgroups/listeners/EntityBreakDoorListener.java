package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardgroups.model.Lock;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;

import java.util.List;

public class EntityBreakDoorListener extends SubPluginEventListener<VanillaGroups> implements Listener {

	public EntityBreakDoorListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityBreakDoor(EntityBreakDoorEvent event) {
		GroupManager groupManager = subPlugin.getGroupManager();

		Location location = event.getBlock().getLocation();

		Group group = groupManager.getGroupByLocation(location);
		
		if (group != null) {
			List<Lock> locks = groupManager.getLocksAffectedByBlock(location);
	
			if (!locks.isEmpty()) {
				if (group.getPower() >= groupManager.LOCK_POWER_THRESHOLD) {
					event.setCancelled(true);
				}
			}
		}
	}
}
