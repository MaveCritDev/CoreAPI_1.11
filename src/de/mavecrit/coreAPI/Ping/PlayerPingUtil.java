package de.mavecrit.coreAPI.Ping;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerPingUtil {
	
	public static int getPingPre(Player p) {
		Object entityPlayer;
		try {
			entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
			int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
			return ping;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | NoSuchFieldException e) {
			return -1;
		}
	}
	public static ChatColor getPingColor(int ping) {
		if(ping < 50) {
			return ChatColor.GREEN;
		} else if(ping < 100) {
			return ChatColor.YELLOW;
		} else if(ping > 100) {
			return ChatColor.RED;
		}
		return ChatColor.GRAY;
	}
}
