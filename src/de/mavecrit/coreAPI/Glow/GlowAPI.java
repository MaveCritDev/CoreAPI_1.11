package de.mavecrit.coreAPI.Glow;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import de.mavecrit.coreAPI.Main;
import de.mavecrit.coreAPI.Ping.PlayerPingUtil;

public class GlowAPI {

	public static List<String> isGlowing = new ArrayList<String>(); 
	public static boolean PlayerGlows = false;
	
	@SuppressWarnings("deprecation")
	public static void set(Player p, ChatColor color, boolean namecolor) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

		if(isGlowing.contains(p)){
			stopGlow(p);
			isGlowing.remove(p);
		}
		
		isGlowing.add(p.getName());
		if (board.getTeam("glowred") != null) {
			Team red = board.getTeam("glowred");
			Team yellow = board.getTeam("glowyellow");
			Team blue = board.getTeam("glowblue");
			Team black = board.getTeam("glowblack");
			Team purple = board.getTeam("glowpurple");
			Team green = board.getTeam("glowgreen");
			Team white = board.getTeam("glowwhite");
			Team orange = board.getTeam("gloworange");
			Team dark_aqua = board.getTeam("glowaqua_dark");
			Team dark_purple = board.getTeam("glowpurple_dark");
			Team dark_blue = board.getTeam("glowblue_dark");
			Team dark_green = board.getTeam("glowgreen_dark");
			Team dark_gray = board.getTeam("glowgray_dark");
			Team dark_red = board.getTeam("glowred_dark");
			
			if (color.equals(ChatColor.RED)) {
				board.getTeam("glowred").setPrefix(ChatColor.RED + "");
				board.getTeam("glowred").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.YELLOW)) {
				board.getTeam("glowyellow").setPrefix(ChatColor.YELLOW + "");
				board.getTeam("glowyellow").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_RED)) {
				board.getTeam("glowred_dark").setPrefix(ChatColor.DARK_RED + "");
				board.getTeam("glowred_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_GRAY)) {
				board.getTeam("glowgray_dark").setPrefix(ChatColor.DARK_GRAY + "");
				board.getTeam("glowgray_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_GREEN)) {
				board.getTeam("glowgreen_dark").setPrefix(ChatColor.DARK_GREEN + "");
				board.getTeam("glowgreen_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_BLUE)) {
				board.getTeam("glowblue_dark").setPrefix(ChatColor.DARK_BLUE + "");
				board.getTeam("glowblue_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.BLUE)) {
				board.getTeam("glowblue").setPrefix(ChatColor.BLUE + "");
				board.getTeam("glowblue").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.BLACK)) {
				board.getTeam("glowblack").setPrefix(ChatColor.BLACK + "");
				board.getTeam("glowblack").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.LIGHT_PURPLE)) {
				board.getTeam("glowpurple").setPrefix(ChatColor.LIGHT_PURPLE + "");
				board.getTeam("glowpurple").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_PURPLE)) {
				board.getTeam("glowpurple_dark").setPrefix(ChatColor.DARK_PURPLE + "");
				board.getTeam("glowpurple_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_AQUA)) {
				board.getTeam("glowaqua_dark").setPrefix(ChatColor.DARK_AQUA + "");
				board.getTeam("glowaqua_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.GREEN)) {
				board.getTeam("glowgreen").setPrefix(ChatColor.GREEN + "");
				board.getTeam("glowgreen").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.WHITE)) {
				board.getTeam("glowwhite").setPrefix(ChatColor.WHITE + "");
				board.getTeam("glowwhite").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.GOLD)) {
				board.getTeam("gloworange").setPrefix(ChatColor.GOLD + "");
				board.getTeam("gloworange").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			}
			
			if(!namecolor){
				p.setDisplayName("§r" + p.getName());
				p.setPlayerListName("§r" + p.getName());
			}
		} else {

			board.registerNewTeam("glowred").setDisplayName("Red");
			board.registerNewTeam("glowyellow").setDisplayName("Yellow");
			board.registerNewTeam("glowblue").setDisplayName("Blue");
			board.registerNewTeam("glowblack").setDisplayName("Black");
			board.registerNewTeam("glowpurple").setDisplayName("Purple");
			board.registerNewTeam("glowgreen").setDisplayName("Green");
			board.registerNewTeam("glowwhite").setDisplayName("White");
			board.registerNewTeam("gloworange").setDisplayName("Orange");
			board.registerNewTeam("glowaqua_dark").setDisplayName("Aqua_Dark");
			board.registerNewTeam("glowpurple_dark").setDisplayName("Purple_Dark");
			board.registerNewTeam("glowblue_dark").setDisplayName("Blue_Dark");
			board.registerNewTeam("glowgreen_dark").setDisplayName("Green_Dark");
			board.registerNewTeam("glowgray_dark").setDisplayName("Gray_Dark");
			board.registerNewTeam("glowred_dark").setDisplayName("Red_Dark");
			
			
			Team red = board.getTeam("glowred");
			Team yellow = board.getTeam("glowyellow");
			Team blue = board.getTeam("glowblue");
			Team black = board.getTeam("glowblack");
			Team purple = board.getTeam("glowpurple");
			Team green = board.getTeam("glowgreen");
			Team white = board.getTeam("glowwhite");
			Team orange = board.getTeam("gloworange");
			Team dark_aqua = board.getTeam("glowaqua_dark");
			Team dark_purple = board.getTeam("glowpurple_dark");
			Team dark_blue = board.getTeam("glowblue_dark");
			Team dark_green = board.getTeam("glowgreen_dark");
			Team dark_gray = board.getTeam("glowgray_dark");
			Team dark_red = board.getTeam("glowred_dark");
			
			if(isGlowing.contains(p)){
				stopGlow(p);
				isGlowing.remove(p);
			}
			
			isGlowing.add(p.getName());
			if (color.equals(ChatColor.RED)) {
				board.getTeam("glowred").setPrefix(ChatColor.RED + "");
				board.getTeam("glowred").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.YELLOW)) {
				board.getTeam("glowyellow").setPrefix(ChatColor.YELLOW + "");
				board.getTeam("glowyellow").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_RED)) {
				board.getTeam("glowred_dark").setPrefix(ChatColor.DARK_RED + "");
				board.getTeam("glowred_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_GRAY)) {
				board.getTeam("glowgray_dark").setPrefix(ChatColor.DARK_GRAY + "");
				board.getTeam("glowgray_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_GREEN)) {
				board.getTeam("glowgreen_dark").setPrefix(ChatColor.DARK_GREEN + "");
				board.getTeam("glowgreen_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_BLUE)) {
				board.getTeam("glowblue_dark").setPrefix(ChatColor.DARK_BLUE + "");
				board.getTeam("glowblue_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.BLUE)) {
				board.getTeam("glowblue").setPrefix(ChatColor.BLUE + "");
				board.getTeam("glowblue").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.BLACK)) {
				board.getTeam("glowblack").setPrefix(ChatColor.BLACK + "");
				board.getTeam("glowblack").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.LIGHT_PURPLE)) {
				board.getTeam("glowpurple").setPrefix(ChatColor.LIGHT_PURPLE + "");
				board.getTeam("glowpurple").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_PURPLE)) {
				board.getTeam("glowpurple_dark").setPrefix(ChatColor.DARK_PURPLE + "");
				board.getTeam("glowpurple_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.DARK_AQUA)) {
				board.getTeam("glowaqua_dark").setPrefix(ChatColor.DARK_AQUA + "");
				board.getTeam("glowaqua_dark").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.GREEN)) {
				board.getTeam("glowgreen").setPrefix(ChatColor.GREEN + "");
				board.getTeam("glowgreen").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.WHITE)) {
				board.getTeam("glowwhite").setPrefix(ChatColor.WHITE + "");
				board.getTeam("glowwhite").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			} else if (color.equals(ChatColor.GOLD)) {
				board.getTeam("gloworange").setPrefix(ChatColor.GOLD + "");
				board.getTeam("gloworange").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			}
			
			if(!namecolor){
				p.setDisplayName("§r" + p.getName());
				p.setPlayerListName("§r" + p.getName());
			}
			
		  Bukkit.getServer().getPluginManager().callEvent(new GlowReceiveEvent(p, color.toString()));  
				
		}
	}

	public static void stopGlow(Player p) {
		p.removePotionEffect(PotionEffectType.GLOWING);
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

		if (board.getTeam("glowred") != null) {
			Team red = board.getTeam("glowred");
			Team yellow = board.getTeam("glowyellow");
			Team blue = board.getTeam("glowblue");
			Team black = board.getTeam("glowblack");
			Team purple = board.getTeam("glowpurple");
			Team green = board.getTeam("glowgreen");
			Team white = board.getTeam("glowwhite");
			Team orange = board.getTeam("gloworange");
			Team dark_aqua = board.getTeam("glowaqua_dark");
			Team dark_purple = board.getTeam("glowpurple_dark");
			Team dark_blue = board.getTeam("glowblue_dark");
			Team dark_green = board.getTeam("glowgreen_dark");
			Team dark_gray = board.getTeam("glowgray_dark");
			Team dark_red = board.getTeam("glowred_dark");

			red.removePlayer(p);
			white.removePlayer(p);
			yellow.removePlayer(p);
			blue.removePlayer(p);
			black.removePlayer(p);
			orange.removePlayer(p);
			purple.removePlayer(p);
			green.removePlayer(p);
			dark_aqua.removePlayer(p);
			dark_purple.removePlayer(p);
			dark_blue.removePlayer(p);
			dark_green.removePlayer(p);
			dark_gray.removePlayer(p);
			dark_red.removePlayer(p);
			
			
				p.setDisplayName("§r" + p.getName());
				p.setPlayerListName("§r" + p.getName());
			
		} else {

			board.registerNewTeam("glowred").setDisplayName("Red");
			board.registerNewTeam("glowyellow").setDisplayName("Yellow");
			board.registerNewTeam("glowblue").setDisplayName("Blue");
			board.registerNewTeam("glowblack").setDisplayName("Black");
			board.registerNewTeam("glowpurple").setDisplayName("Purple");
			board.registerNewTeam("glowgreen").setDisplayName("Green");
			board.registerNewTeam("glowwhite").setDisplayName("White");
			board.registerNewTeam("gloworange").setDisplayName("Orange");
			board.registerNewTeam("glowaqua_dark").setDisplayName("Aqua_Dark");
			board.registerNewTeam("glowpurple_dark").setDisplayName("Purple_Dark");
			board.registerNewTeam("glowblue_dark").setDisplayName("Blue_Dark");
			board.registerNewTeam("glowgreen_dark").setDisplayName("Green_Dark");
			board.registerNewTeam("glowgray_dark").setDisplayName("Gray_Dark");
			board.registerNewTeam("glowred_dark").setDisplayName("Red_Dark");
			
			
			Team red = board.getTeam("glowred");
			Team yellow = board.getTeam("glowyellow");
			Team blue = board.getTeam("glowblue");
			Team black = board.getTeam("glowblack");
			Team purple = board.getTeam("glowpurple");
			Team green = board.getTeam("glowgreen");
			Team white = board.getTeam("glowwhite");
			Team orange = board.getTeam("gloworange");
			Team dark_aqua = board.getTeam("glowaqua_dark");
			Team dark_purple = board.getTeam("glowpurple_dark");
			Team dark_blue = board.getTeam("glowblue_dark");
			Team dark_green = board.getTeam("glowgreen_dark");
			Team dark_gray = board.getTeam("glowgray_dark");
			Team dark_red = board.getTeam("glowred_dark");

			red.removePlayer(p);
			white.removePlayer(p);
			yellow.removePlayer(p);
			blue.removePlayer(p);
			black.removePlayer(p);
			orange.removePlayer(p);
			purple.removePlayer(p);
			green.removePlayer(p);
			dark_aqua.removePlayer(p);
			dark_purple.removePlayer(p);
			dark_blue.removePlayer(p);
			dark_green.removePlayer(p);
			dark_gray.removePlayer(p);
			dark_red.removePlayer(p);
			
			
				p.setDisplayName("§r" + p.getName());
				p.setPlayerListName("§r" + p.getName());
			
					isGlowing.remove(p);
				
				
				
		}
	}

	public static void PingGlow(final Player p, final boolean namecolor) {
		final Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

		if(isGlowing.contains(p)){
			stopGlow(p);
			isGlowing.remove(p);
		}
		
		isGlowing.add(p.getName());
		if (board.getTeam("glowred") != null) {
			Team red = board.getTeam("glowred");
			Team yellow = board.getTeam("glowyellow");
			Team blue = board.getTeam("glowblue");
			Team black = board.getTeam("glowblack");
			Team purple = board.getTeam("glowpurple");
			Team green = board.getTeam("glowgreen");
			Team white = board.getTeam("glowwhite");
			Team orange = board.getTeam("gloworange");

			Bukkit.getServer().getScheduler()
		      .scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
		    	  public void run() {
		    		 
			int ping = PlayerPingUtil.getPingPre(p);
			if (ping < 50) {
				board.getTeam("glowgreen").setPrefix(ChatColor.GREEN + "");
				board.getTeam("glowgreen").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
				
			} else if (ping < 100) {
				board.getTeam("gloworange").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
				
			} else {
				board.getTeam("glowred").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			
			}
			if(!namecolor){
				p.setDisplayName("§r" + p.getName());
				p.setPlayerListName("§r" + p.getName());
			}
		    	  }
			    }
			    , 1L, 140L);
			
		} else {
			board.registerNewTeam("glowred");
			board.registerNewTeam("glowyellow");
			board.registerNewTeam("glowblue");
			board.registerNewTeam("glowblack");
			board.registerNewTeam("glowpurple");
			board.registerNewTeam("glowgreen");
			board.registerNewTeam("glowwhite");
			board.registerNewTeam("gloworange");
			
			Team red = board.getTeam("glowred");
			Team yellow = board.getTeam("glowyellow");
			Team blue = board.getTeam("glowblue");
			Team black = board.getTeam("glowblack");
			Team purple = board.getTeam("glowpurple");
			Team green = board.getTeam("glowgreen");
			Team white = board.getTeam("glowwhite");
			Team orange = board.getTeam("gloworange");
			 Bukkit.getServer().getScheduler()
		      .scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
		    	  public void run() {
		    		  
			int ping = PlayerPingUtil.getPingPre(p);
			if (ping < 50) {
				board.getTeam("glowgreen").setPrefix(ChatColor.GREEN + "");
				board.getTeam("glowgreen").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
				
			} else if (ping < 100) {
				board.getTeam("gloworange").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
				
			} else {
				board.getTeam("glowred").addPlayer(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						2147483647, 0));
			
			}
			if(!namecolor){
				p.setDisplayName("§r" + p.getName());
				p.setPlayerListName("§r" + p.getName());
			}
		    	  }
			    }
			    , 1L, 140L);
		    
		}
	}
	
	public static boolean isGlowing(Player p){
		return p.hasPotionEffect(PotionEffectType.GLOWING);
	}
	
	public static String getColorName(Player p){
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		return board.getPlayerTeam(p).getPrefix() + board.getPlayerTeam(p).getDisplayName();
	}
}
