package com.sbezboro.standardgroups.tasks;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.tasks.BaseTask;
import org.bukkit.ChatColor;

public class LandGrowthCheckTask extends BaseTask {
	private VanillaGroups subPlugin;

	public LandGrowthCheckTask(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin);

		this.subPlugin = subPlugin;
	}

	@Override
	public void run() {
		subPlugin.getLogger().info("Running LandGrowthCheckTask");

		long curTime = System.currentTimeMillis();

		GroupManager groupManager = subPlugin.getGroupManager();

		for (Group group : groupManager.getGroups()) {
			if (group.getMaxClaims() >= subPlugin.getGroupLandGrowthLimit()) {
				continue;
			}

			long diff = curTime - group.getLastGrowth();
			long growthPeriod = subPlugin.getGroupLandGrowthDays() * 86400000;

			if (diff >= growthPeriod) {
				subPlugin.getLogger().info("Growing " + group.getName());

				group.grow();
				group.setLastGrowth(group.getLastGrowth() + growthPeriod);

				group.sendGroupMessage(ChatColor.GREEN + "Your group has gained an additional " +
						ChatColor.AQUA + subPlugin.getGroupLandGrowth() +
						ChatColor.GREEN + " land for existing another " +
						ChatColor.AQUA + subPlugin.getGroupLandGrowthDays() +
						ChatColor.GREEN + " days!");
			}
		}
	}

}
