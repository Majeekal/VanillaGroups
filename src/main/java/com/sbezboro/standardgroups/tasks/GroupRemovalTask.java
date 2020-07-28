package com.sbezboro.standardgroups.tasks;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardgroups.model.Group;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.model.StandardPlayer;
import com.sbezboro.standardplugin.tasks.BaseTask;
import org.bukkit.ChatColor;

public class GroupRemovalTask extends BaseTask {
	private VanillaGroups subPlugin;

	public GroupRemovalTask(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin);

		this.subPlugin = subPlugin;
	}

	@Override
	public void run() {
		subPlugin.getLogger().info("Running GroupRemovalTask");

		long curTime = System.currentTimeMillis();

		GroupManager groupManager = subPlugin.getGroupManager();

		for (Group group : groupManager.getGroups()) {
			for (StandardPlayer player : group.getPlayers()) {
				if (!player.isOnline()) {
					long diff = curTime - player.getLastPlayed();
					long kickPeriod = subPlugin.getGroupAutoKickDays() * 86400000l;
					long warnPeriodMin = kickPeriod - 86400000;

					if (diff != curTime) {
						if (diff >= kickPeriod) {
							subPlugin.getLogger().info("Kicking " + player.getDisplayName(false) + " from " + group.getName());

							groupManager.autoKickPlayer(player);

							group.sendGroupMessage(ChatColor.YELLOW + player.getDisplayName(false) + " has been " +
									"auto-removed from your group for being offline for " +
									subPlugin.getGroupAutoKickDays() + " days.");
						}
					}
				}
			}
		}
	}
}
