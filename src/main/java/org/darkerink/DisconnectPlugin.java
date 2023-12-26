package org.darkerink;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

/**
 * Example rusherhack plugin
 *
 * @author John200410
 */
public class DisconnectPlugin extends Plugin {
	
	@Override
	public void onLoad() {
		this.getLogger().info("Disconnect Plugin loaded!");
		RusherHackAPI.getModuleManager().registerFeature(new DisconnectModule());
	}

	@Override
	public void onUnload() {
		this.getLogger().info("Disconnect Plugin unloaded!");
	}
	
	@Override
	public String getName() {
		return "Disconnect Plugin";
	}
	
	@Override
	public String getVersion() {
		return "v1.0";
	}
	
	@Override
	public String getDescription() {
		return "Disconnects you at a specific interval";
	}
	
	@Override
	public String[] getAuthors() {
		return new String[]{"DarkerInk"};
	}
}
