package com.sbezboro.standardgroups.tasks;

import com.sbezboro.standardgroups.VanillaGroups;
import com.sbezboro.standardgroups.managers.GroupManager;
import com.sbezboro.standardplugin.VanillaPlugin;
import com.sbezboro.standardplugin.tasks.BaseTask;

public class PurgeCooldownsTask extends BaseTask {
	private VanillaGroups subPlugin;

	// Removes old entries tracking the usage of anti-macro protected commands
	
	public PurgeCooldownsTask(VanillaPlugin plugin, VanillaGroups subPlugin) {
		super(plugin);

		this.subPlugin = subPlugin;
	}

	@Override
	public void run() {
		GroupManager groupManager = subPlugin.getGroupManager();

		groupManager.purgePlayerCommandCooldowns();
	}

}
