package org.skunion.smallru8.BungeeDynamicSync.Hub.redis;

import org.skunion.smallru8.BungeeDynamicSync.Hub.Hub;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub{

	private Thread t;
	private JedisPubSub jedispubsub;
	
	public Subscriber() {
		jedispubsub = this;
		t = new Thread() {
			@Override
			public void run() {
				Hub.JEDIS.subscribe(jedispubsub, Hub.PUB_SUB_CHANNEL);
			}
		};
		t.start();
    }
	
	@Override
    public void onMessage(String channel, String message) {
		if(channel.equalsIgnoreCase(Hub.PUB_SUB_CHANNEL)) {
			String[] cmd = message.split(" ");
			int len = cmd.length;
			if(cmd[0].equals("SERVER")&&len>=2) {
				if(cmd[1].equals("ADD")&&len==6) {
					//TODO add to sign
				}else if(cmd[1].equals("DEL")&&len==3) {
					//TODO del from sign
				}else if(cmd[1].equals("STARTED")&&len==3) {//From Spigot plugin tell everyone its game has started 
					//TODO del from sign
				}
			}
		}
    }

	@Override
    public void onSubscribe(String channel, int subscribedChannels) { 
    }

	@Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
    }
	
}
