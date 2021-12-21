package org.skunion.smallru8.BungeeDynamicSync.Hub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.skunion.smallru8.util.Pair;

public class SignData implements Runnable{

	class Sign{
		public String world,type;
		public int x,y,z;
		
		public String serverName = "";
		public String roomId = "";
		public String status = "0/0";
	}
	
	private JSONArray signLocation;
	
	private ArrayList<Pair<String,String>> waitForAdd = new ArrayList<Pair<String,String>>();
	private Queue<String> waitForDel = new LinkedList<String>();
	
	//type:Sign
	private Map<String,ArrayList<Sign>> sign_list = new HashMap<String,ArrayList<Sign>>();
	
	private File f_signLocation;
	
	public SignData() {
		//load signs.json
		f_signLocation = new File(Hub.HUB.getDataFolder(),"signs.json");
		
		try {
			if(!f_signLocation.exists()) {
				f_signLocation.createNewFile();
				FileWriter fw = new FileWriter(f_signLocation);
				fw.write("[]");
				fw.flush();
				fw.close();
				signLocation = new JSONArray("[]");
			}else {
				FileReader fr = new FileReader(f_signLocation);
				BufferedReader br = new BufferedReader(fr);
				String lines = "",sum = "";
				while((lines = br.readLine())!=null) {
					sum+=lines+"\n";
				}
				br.close();
				fr.close();
				signLocation = new JSONArray(sum);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		for(int i=0;i<signLocation.length();i++) {
			JSONObject jo = signLocation.getJSONObject(i);
			if(!sign_list.containsKey(jo.getString("type")))
				sign_list.put(jo.getString("type"), new ArrayList<Sign>());
			Sign s = new Sign();
			s.world = jo.getString("world");
			s.type = jo.getString("type");
			s.x = jo.getInt("x");
			s.y = jo.getInt("y");
			s.z = jo.getInt("z");
			sign_list.get(s.type).add(s);
		}
	}
	
	public void addRoom(String dynServerName,String motd_type) {
		Pair<String,String> p = new Pair<String,String>();
		p.makePair(dynServerName, motd_type);
		waitForAdd.add(p);
	}
	
	public void delRoom(String dynServerName) {
		waitForDel.add(dynServerName);
	}
	
	public void addSignLoc(String world,String type,int x,int y,int z) {
		JSONObject jo = new JSONObject("{}");
		jo.put("world", world);
		jo.put("type", type);
		jo.put("x", x);
		jo.put("y", y);
		jo.put("z", z);
		signLocation.put(jo.toString());
		save();
	}
	
	public void delSignLoc(String world,String type,int x,int y,int z) {
		for(int i=0;i<signLocation.length();i++) {
			JSONObject jo = signLocation.getJSONObject(i);
			if(jo.getString("world").equalsIgnoreCase(world)&&jo.getInt("x")==x&&jo.getInt("y")==y&&jo.getInt("z")==z) {
				Sign s = new Sign();
				s.world = jo.getString("world");
				s.type = jo.getString("type");
				s.x = jo.getInt("x");
				s.y = jo.getInt("y");
				s.z = jo.getInt("z");
				sign_list.get(s.type).remove(s);
				signLocation.remove(i);
				save();
				break;
			}
		}
	}
	
	public void save() {
		try {
			FileWriter fw = new FileWriter(f_signLocation);
			fw.write(signLocation.toString());
			fw.flush();
			fw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void run() {
		//DEL
		String dynName = "";
		while(waitForDel.size()!=0) {
			dynName = waitForDel.poll();
			for(Pair<String,String> p:waitForAdd) {
				if(p.first.equalsIgnoreCase(dynName)) {
					waitForAdd.remove(p);
					break;
				}
			}
			
			for(Entry<String, ArrayList<Sign>> e:sign_list.entrySet()) {
				if(dynName.startsWith(e.getKey())) {
					for(Sign sign:e.getValue()) {
						if(sign.serverName.equalsIgnoreCase(dynName)) {
							sign.serverName = "";
							sign.roomId = "";
							sign.status = "0/0";
							break;
						}
					}
					break;
				}
			}
		}
		
		//ADD
		Pair<String,String> pair;//server name, type
		Iterator<Pair<String,String>> it = waitForAdd.iterator();
		while(it.hasNext()) {
			pair = it.next();
			for(Sign sign:sign_list.get(pair.second)) {
				if(sign.serverName=="") {
					sign.serverName = pair.first;
					break;
				}
			}
		}
		
		//UPDATE Sign info and show
		//TODO how to get server ping?
		
	}
	
}
