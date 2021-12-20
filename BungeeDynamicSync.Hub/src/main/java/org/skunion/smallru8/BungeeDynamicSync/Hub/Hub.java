package org.skunion.smallru8.BungeeDynamicSync.Hub;

import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.JedisPool;

public class Hub extends JavaPlugin{

	public static Hub HUB;
	public static JedisPool REDIS_POOL;
	
	@Override
	public void onEnable() {
		HUB = this;
		saveDefaultConfig();
		REDIS_POOL = new JedisPool(getConfig().getString("redis-server"), getConfig().getInt("redis-port"));
	}
	
	@Override
	public void onDisable() {
		REDIS_POOL.close();
	}
	
}
