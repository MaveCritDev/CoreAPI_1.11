package de.mavecrit.coreAPI.Holograms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;



public class HologramResetter implements Listener {

	private static HashMap<String, List<Hologram>> hs = new HashMap<>();
	private static HashMap<String, List<TouchScreen>> ts = new HashMap<>();
	
	public static void register(Player p, Hologram h) {
		if(hs.containsKey(p.getName())) {
			List<Hologram> holograms = hs.get(p.getName());
			holograms.add(h);
			hs.put(p.getName(), holograms);
		} else {
			List<Hologram> holograms = new ArrayList<>();
			holograms.add(h);
			hs.put(p.getName(), holograms);
		}
	}
	public static void register(Player p, TouchScreen h) {
		if(ts.containsKey(p.getName())) {
			List<TouchScreen> holograms = ts.get(p.getName());
			holograms.add(h);
			ts.put(p.getName(), holograms);
		} else {
			List<TouchScreen> holograms = new ArrayList<>();
			holograms.add(h);
			ts.put(p.getName(), holograms);
		}
	}
	public static void destroyAll(Player p) throws Exception {
		if(hs.containsKey(p.getName())) {
			List<Hologram> holos = hs.get(p.getName());
			for(Hologram holo : holos) {
				holo.removeFromPlayer(p);
			}
		}
		if(ts.containsKey(p.getName())) {
			List<TouchScreen> touchs = ts.get(p.getName());
			for(TouchScreen screen : touchs) {
				screen.removeFromPlayer(p);
			}
		}
	}
	public static void destroyTouch(Player p) {
		if(ts.containsKey(p.getName())) {
			List<TouchScreen> touchs = ts.get(p.getName());
			for(TouchScreen screen : touchs) {
				screen.removeFromPlayer(p);
			}
		}
	}
	public static void destroyHolograms(Player p) {
		if(hs.containsKey(p.getName())) {
			List<Hologram> holos = hs.get(p.getName());
			for(Hologram holo : holos) {
				holo.removeFromPlayer(p);
			}
		}
	}
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if(hs.keySet().contains(e.getPlayer().getName()) || ts.keySet().contains(e.getPlayer().getName())) {
			try {
				destroyAll(e.getPlayer());
			} catch(Exception ex) {}
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(hs.keySet().contains(e.getPlayer().getName()) || ts.keySet().contains(e.getPlayer().getName())) {
			try {
				destroyAll(e.getPlayer());
			} catch(Exception ex) {}
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(hs.keySet().contains(e.getEntity().getName()) || ts.keySet().contains(e.getEntity().getName())) {
			try {
				destroyAll(e.getEntity());
			} catch(Exception ex) {}
		}
	}
}