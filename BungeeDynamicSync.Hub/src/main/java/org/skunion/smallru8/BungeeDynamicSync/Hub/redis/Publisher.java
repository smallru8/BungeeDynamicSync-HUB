package org.skunion.smallru8.BungeeDynamicSync.Hub.redis;

import org.skunion.smallru8.BungeeDynamicSync.Hub.Hub;

public class Publisher {

	public static void pub(String msg) {
		Hub.JEDIS.publish(Hub.PUB_SUB_CHANNEL, msg);
	}
	
}
