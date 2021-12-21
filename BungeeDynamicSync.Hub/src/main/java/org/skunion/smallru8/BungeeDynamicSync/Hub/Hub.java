package org.skunion.smallru8.BungeeDynamicSync.Hub;

import org.bukkit.plugin.java.JavaPlugin;
import org.skunion.smallru8.BungeeDynamicSync.Hub.redis.Subscriber;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//TODO bdstool command, right click on sign event

public class Hub extends JavaPlugin{

	public static final String PUB_SUB_CHANNEL = "BDS_MESSAGE";
	public static final String PLUGIN_MSG_CHANNEL = "BDS:channel";
	
	public static Hub HUB;
	public static JedisPool REDIS_POOL;
	public static Jedis JEDIS;
	public static SignData SIGN_DATA;
	
	private static Subscriber REDIS_SUB;
	
	@Override
	public void onEnable() {
		HUB = this;
		saveDefaultConfig();
		REDIS_POOL = new JedisPool(getConfig().getString("redis-server"), getConfig().getInt("redis-port"));
		JEDIS = REDIS_POOL.getResource();
		JEDIS.auth(getConfig().getString("redis-passwd"));
		SIGN_DATA = new SignData();
		REDIS_SUB = new Subscriber();
		REDIS_SUB.start();
	}
	
	@Override
	public void onDisable() {
		REDIS_POOL.close();
	}
	
}
