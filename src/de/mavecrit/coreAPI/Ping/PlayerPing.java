package de.mavecrit.coreAPI.Ping;

import org.bukkit.entity.Player;

public class PlayerPing {
	
	
	public static String getPing(Player p){
		int pug = PlayerPingUtil.getPingPre(p);
		String pingmsg = "" + PlayerPingUtil.getPingColor(pug) + pug; 
		
		return pingmsg;
	}
}
