package org.skunion.smallru8.BungeeDynamicSync.Hub;

import org.bukkit.plugin.java.JavaPlugin;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Hub extends JavaPlugin{

	public static final String PUB_SUB_CHANNEL = "BDS_MESSAGE";
	public static final String PLUGIN_MSG_CHANNEL = "BDS:channel";
	
	public static Hub HUB;
	public static JedisPool REDIS_POOL;
	public static Jedis JEDIS;
	
	@Override
	public void onEnable() {
		HUB = this;
		saveDefaultConfig();
		REDIS_POOL = new JedisPool(getConfig().getString("redis-server"), getConfig().getInt("redis-port"));
		JEDIS = REDIS_POOL.getResource();
	}
	
	@Override
	public void onDisable() {
		REDIS_POOL.close();
	}
	
}
