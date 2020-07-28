package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardgroups.model.Lock;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import java.util.List;

public class BlockPistonExtendListener extends SubPluginEventListener<VanillaGroups> implements Listener {
	public BlockPistonExtendListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		GroupManager groupManager = subPlugin.getGroupManager();

		for (Block block : event.getBlocks()) {
			if (VanillaPlugin.BED_BLOCKS.contains(block.getType())) {
				event.setCancelled(true);
				return;
			}

			List<Lock> locks = groupManager.getLocksAffectedByBlock(block.getLocation());

			if (!locks.isEmpty()) {
				event.setCancelled(true);
				return;
			}

			Location oldLocation = block.getLocation();
			Location newLocation = block.getRelative(event.getDirection()).getLocation();

			Group oldGroup = groupManager.getGroupByLocation(oldLocation);
			Group newGroup = groupManager.getGroupByLocation(newLocation);

			if (oldGroup != newGroup && newGroup != null) {
				event.setCancelled(true);
			}
		}
	}

}
