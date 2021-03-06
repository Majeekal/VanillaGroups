package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener extends SubPluginEventListener<VanillaGroups> implements Listener {
	public EntityChangeBlockListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		GroupManager groupManager = subPlugin.getGroupManager();

		Location location = event.getBlock().getLocation();

		Group group = groupManager.getGroupByLocation(location);

		if (group != null && (group.isSafeArea() || group.isWarArea())) {
			event.setCancelled(true);
		}
	}
	
}
