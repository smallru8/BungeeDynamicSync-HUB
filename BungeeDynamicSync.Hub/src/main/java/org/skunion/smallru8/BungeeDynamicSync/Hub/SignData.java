package org.skunion.smallru8.BungeeDynamicSync.Hub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignData {

	private JSONArray signLocation;
	
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
		
	}
	
	public void addSign(String world,String type,int x,int y,int z) {
		JSONObject jo = new JSONObject("{}");
		jo.put("world", world);
		jo.put("type", type);
		jo.put("x", x);
		jo.put("y", y);
		jo.put("z", z);
		signLocation.put(jo.toString());
	}
	
}
