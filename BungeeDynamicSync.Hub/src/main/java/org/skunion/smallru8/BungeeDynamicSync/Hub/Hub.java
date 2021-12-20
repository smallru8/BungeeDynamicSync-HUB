package org.skunion.smallru8.BungeeDynamicSync.Hub;

import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.Jedis;

public class Hub extends JavaPlugin{

	public static Jedis REDIS_CHANNEL;
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
