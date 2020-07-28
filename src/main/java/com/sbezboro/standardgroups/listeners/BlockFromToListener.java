package com.sbezboro.standardgroups.listeners;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.SubPluginEventListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener extends SubPluginEventListener<VanillaGroups> implements Listener {
    public BlockFromToListener(VanillaPlugin plugin, VanillaGroups subPlugin){
        super(plugin, subPlugin);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockFromToEvent(BlockFromToEvent event){
        if(event.getBlock().getType() == Material.LAVA){
            GroupManager groupManager = subPlugin.getGroupManager();
            Group from = groupManager.getGroupByLocation(event.getBlock().getLocation());
            Group to = groupManager.getGroupByLocation(event.getToBlock().getLocation());

            if(from == null && to != null){
                event.setCancelled(true);
            }
        }
    }
}
