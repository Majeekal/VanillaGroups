package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Lock;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class InventoryMoveListener  extends SubPluginEventListener<VanillaGroups> implements Listener {
	public InventoryMoveListener(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin, subPlugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onInventoryMoveItem(InventoryMoveItemEvent event) {
		Lock sourceLock = getLock(event.getSource().getHolder());
		Lock destinationLock = getLock(event.getDestination().getHolder());

		if (sourceLock != null && destinationLock != null) {
			String sourceOwner = sourceLock.getOwnerUuid();
			String destinationOwner = destinationLock.getOwnerUuid();

			if (!sourceOwner.equals(destinationOwner)) {
				event.setCancelled(true);
			}
		} else if (sourceLock != null || destinationLock != null) {
			event.setCancelled(true);
		}
	}

	private Lock getLock(InventoryHolder holder) {
		GroupManager groupManager = subPlugin.getGroupManager();

		if (holder instanceof DoubleChest) {
			holder = ((DoubleChest) holder).getLeftSide();
		}

		if (holder instanceof BlockState) {
			Block block = ((BlockState) holder).getBlock();
			if (GroupManager.isBlockTypeProtected(block)) {
				List<Lock> locks = groupManager.getLocksAffectedByBlock(block.getLocation());
				if (locks.isEmpty()) {
					return null;
				}

				return locks.get(0);
			}
		}

		return null;
	}
}
