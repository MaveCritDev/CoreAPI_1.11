package de.mavecrit.coreAPI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.mcstats.Metrics;

import com.bobacadodl.JSONChatLib.JSONChatClickEventType;
import com.bobacadodl.JSONChatLib.JSONChatColor;
import com.bobacadodl.JSONChatLib.JSONChatExtra;
import com.bobacadodl.JSONChatLib.JSONChatFormat;
import com.bobacadodl.JSONChatLib.JSONChatHoverEventType;
import com.bobacadodl.JSONChatLib.JSONChatMessage;

import de.mavecrit.coreAPI.Coupons.Coupons;
import de.mavecrit.coreAPI.Glow.GlowAPI;
import de.mavecrit.coreAPI.Language.Language;
import de.mavecrit.coreAPI.Language.LanguageAPI;
import de.mavecrit.coreAPI.MySQL.EasyMySQL;
import de.mavecrit.coreAPI.MySQL.MySQL;
import de.mavecrit.coreAPI.NoteblockAPI.SongPlayer;
import de.mavecrit.coreAPI.skins.Skin;




public class Main extends JavaPlugin implements Listener {
	
	  private static MySQL con;
	  public static final Language defaultLang = Language.ENGLISH;
	  public static final Coupons defaultCoupon = Coupons.CUBES;
	  public static final boolean debug = true;
	  public static Plugin plugin;
	  public static Main instance;
	  public static List<String> IDS = new ArrayList<String>();
	  public static List<String> CustomIDS = new ArrayList<String>();
      public static Map<String, Skin> clipSkins = new HashMap<String, Skin>();
      public static HashMap<Player, ArrayList<Block>> Line1 = new HashMap<Player, ArrayList<Block>>();
      public static HashMap<Player, ArrayList<Block>> Line2 = new HashMap<Player, ArrayList<Block>>();
      public static HashMap<Player, ArrayList<Block>> Line3 = new HashMap<Player, ArrayList<Block>>();
      public static HashMap<Player, ArrayList<Block>> Line4 = new HashMap<Player, ArrayList<Block>>();
      public static HashMap<Player, ArrayList<Block>> Started = new HashMap<Player, ArrayList<Block>>();
      public static HashMap<String, ArrayList<SongPlayer>> playingSongs = new HashMap<String, ArrayList<SongPlayer>>();
      public static HashMap<String, Byte> playerVolume = new HashMap<String, Byte>();
      
      
      public static SimpleConfigManager manager;
      
      public static SimpleConfig identifiers;
      public static SimpleConfig language;
      public static SimpleConfig achievements;
		public void loadConfiguration(){	
			
			 String hupo = "enable.mysql";
			 plugin.getConfig().addDefault(hupo, true);
			 
			 String jail = "enable.cmd";
			 plugin.getConfig().addDefault(jail, true);
			 	 
			 String lang = "DefaultLanguage";
			 plugin.getConfig().addDefault(lang, "ENGLISH");
			 
			 String sc = "enable.scoreboard";
			 plugin.getConfig().addDefault(sc, true);
			 
			 String sca = "enable.animated_scoreboard";
			 plugin.getConfig().addDefault(sca, true);
			 
			 String p = "enable.tab_ping";
			 plugin.getConfig().addDefault(p, true);
			 
			 String dd = "first_startup";
			 plugin.getConfig().addDefault(dd, true);
			 
		    String or = "enable.nick_mysql";
			 plugin.getConfig().addDefault(or, true);
			 
		     plugin.getConfig().options().copyDefaults(true);
		     plugin.saveConfig();
		     

		}
		
	    public static boolean isReceivingSong(Player p) {
	        return ((Main.playingSongs.get(p.getName()) != null) && (!Main.playingSongs.get(p.getName()).isEmpty()));
	    }

	    public static void stopPlaying(Player p) {
	        if (Main.playingSongs.get(p.getName()) == null) {
	            return;
	        }
	        for (SongPlayer s : Main.playingSongs.get(p.getName())) {
	            s.removePlayer(p);
	        }
	    }

	    public static void setPlayerVolume(Player p, byte volume) {
	    	Main.playerVolume.put(p.getName(), volume);
	    }

	    public static byte getPlayerVolume(Player p) {
	        Byte b = Main.playerVolume.get(p.getName());
	        if (b == null) {
	            b = 100;
	            Main.playerVolume.put(p.getName(), b);
	        }
	        return b;
	    }

	  public void onEnable()
	  {
		  plugin = this;
		    instance = this;
		  loadConfiguration();
		  if(this.getConfig().getBoolean("enable.mysql")){
	    con = new EasyMySQL(getDataFolder()).getMySQL();
	    try {
	      con.connect();
	    } catch (Exception e1) {
	      e1.printStackTrace();
	    }
	    try {
	    	con.update("CREATE TABLE IF NOT EXISTS language (uuid VARCHAR(64), language VARCHAR(64))");
	    
	    	con.update("CREATE TABLE IF NOT EXISTS archievements (name VARCHAR(100), uuid VARCHAR(100), archievements LONGTEXT)");
	    } catch (SQLException e) {
	      e.printStackTrace();
	      }
	    } else {
	        String[] comments = {"DO NOT TOUCH THIS CONFIG!"};
	        String[] header = {"DO NOT TOUCH THIS CONFIG!"};
	        
	        Main.manager = new SimpleConfigManager(this);
	        
	        Main.language = manager.getNewConfig("languages.yml");
	        Main.language.set("Example_UUID" + ".language", "ENGLISH");
	        Main.language.saveConfig();
	        
	      //  Main.achievements = manager.getNewConfig("achievements.yml");
	       // Main.achievements.set("Example_UUID" + ".achievements", "vote");
	      //  Main.achievements.saveConfig();
	        
	        Main.identifiers = manager.getNewConfig("identifiers.yml", header);
	        Main.identifiers.set("Example_UUID", "example_identifier", comments);
	        Main.identifiers.saveConfig();
	    }
	  
		
		System.out.print("CoreAPI enabled.");
		getServer().getPluginManager().registerEvents(this, this);

    
		
		try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }
	  }

	  public void onDisable()
	  {
	    try {
	      con.disconnect(); } catch (Exception localException) {
	    }
	    Bukkit.getScheduler().cancelTasks(this);
	  }

	  public static MySQL getMySQL() {
	    return con;
	  }
	  
	  @EventHandler
	  public void Liog(PlayerLoginEvent e){
		  Player p = e.getPlayer();

		  if(this.getConfig().getBoolean("enable.mysql")){  	
		  } else {
			  if(!Main.language.contains(p.getUniqueId().toString())){
			  Main.language.set(p.getUniqueId().toString() + ".language", Language.ENGLISH.toString().toUpperCase());
			  Main.language.saveConfig();
			 
			  } else {
				  Language lang = LanguageAPI.getLanguage(p);
				  Main.language.set(p.getUniqueId().toString() + ".language", lang.toString().toUpperCase());
				  Main.language.saveConfig();
			  }
		  }
		  
	  }
	  
	    @EventHandler
	    public void onBlockFromTo(BlockFadeEvent event) {
	        if(event.getBlock().getType() == Material.ICE || event.getBlock().getType() == Material.FROSTED_ICE){
	            event.setCancelled(true);
	        }
	    }
	    
	  public void Joo(PlayerJoinEvent e){
		  Player p = e.getPlayer();
		
		 	Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		 	
		 	
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
			
			if(board.getTeam("glowred") != null){
			red.unregister();}
			if(board.getTeam("glowwhite") != null){
				white.unregister();}
			if(board.getTeam("glowyellow") != null){
				yellow.unregister();}
			if(board.getTeam("glowblue") != null){
				blue.unregister();}
			if(board.getTeam("glowblack") != null){
				black.unregister();}
			
			if(board.getTeam("glowpurple") != null){
				purple.unregister();}
			if(board.getTeam("glowgreen") != null){
				green.unregister();}
			if(board.getTeam("glowaqua_dark") != null){
				dark_aqua.unregister();}
			if(board.getTeam("glowpurple_dark") != null){
				dark_purple.unregister();}
			if(board.getTeam("glowblue_dark") != null){
				dark_blue.unregister();}
			if(board.getTeam("glowgreen_dark") != null){
				dark_green.unregister();}
			if(board.getTeam("glowgray_dark") != null){
				dark_gray.unregister();}
			if(board.getTeam("glowred_dark") != null){
				dark_red.unregister();}
			if(board.getTeam("gloworange") != null){
				orange.unregister();}
		  if(this.getConfig().getBoolean("first_startup")){
			  Bukkit.broadcastMessage("�aHey here is Mavecrit, author of CoreAPI, please give CoreAPI a good rating if you like it!");
			  Bukkit.broadcastMessage("This message will only appear one time, NOW!");
			  Bukkit.broadcastMessage("");
			  Bukkit.broadcastMessage("https://goo.gl/ek4ef5");
			  getConfig().set("first_startup", false);
			  saveConfig();
		  }
	  }
	 
	  
		public boolean onCommand(CommandSender sender, Command cmd,
				String CommandLabel, String[] args) {
			final Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("coreapi")){
				if(player.hasPermission("coreapi.use")){
				if(args.length == 0){
					player.sendMessage("§7CoreAPI by §aMaveCrit§7, Version String: §a" + plugin.getDescription().getVersion());
				
				}
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("enable.cmd")){
						plugin.getConfig().set("enable.cmd", true);
						player.sendMessage("§aThe §7/language §acommand is now enabled");
						plugin.saveConfig();
						
					
						
					}
				}
				}
				
				
				} 
			
			if(cmd.getName().equalsIgnoreCase("glow")){
				if(GlowAPI.isGlowing(player)){
					
				}
				GlowAPI.set(player, ChatColor.BLUE, false);
			}
			
			
			if (cmd.getName().equalsIgnoreCase("language")){
					if(plugin.getConfig().getBoolean("enable.cmd")){
					   if(args.length == 0){
					//ENGLISH
					    JSONChatMessage eng = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra engextra = new JSONChatExtra("English", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        engextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to English Language");
				        engextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language english");
				        eng.addExtra(engextra);
				        eng.sendToPlayer(player);
				        
					//GERMAN
					    JSONChatMessage de = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra deextra = new JSONChatExtra("German", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        deextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to German Language");
				        deextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language german");
				        de.addExtra(deextra);
				        de.sendToPlayer(player);
				        
					//Spanish
					    JSONChatMessage esp = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra espextra = new JSONChatExtra("Spanish", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        espextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Spanish Language");
				        espextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language spanish");
				        esp.addExtra(espextra);
				        esp.sendToPlayer(player);
				      
					//DUTCH
					    JSONChatMessage du = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra duextra = new JSONChatExtra("Dutch", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        duextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Dutch Language");
				        duextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language dutch");
				        du.addExtra(duextra);
				        du.sendToPlayer(player);
				        
					//SWEDISH
					    JSONChatMessage swe = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra sweextra = new JSONChatExtra("Swedish", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        sweextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Swedish Language");
				        sweextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language swedish");
				        swe.addExtra(sweextra);
				        swe.sendToPlayer(player);
				        
					//ARABIC
					    JSONChatMessage ara = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra araextra = new JSONChatExtra("Arabic", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        araextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Arabic Language");
				        araextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language arabic");
				        ara.addExtra(araextra);
				        ara.sendToPlayer(player);
				        
					//POLISH
					    JSONChatMessage po = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra poextra = new JSONChatExtra("Polish", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        poextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Polish Language");
				        poextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language polish");
				        po.addExtra(poextra);
				        po.sendToPlayer(player);
				        
					//RUSSIAN
					    JSONChatMessage ru = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra ruextra = new JSONChatExtra("Russian", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        ruextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Russian Language");
				        ruextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language russian");
				        ru.addExtra(ruextra);
				        ru.sendToPlayer(player);
				        
					//ITALIAN
					    JSONChatMessage it = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra itextra = new JSONChatExtra("Italian", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        itextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Italian Language");
				        itextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language italian");
				        it.addExtra(itextra);
				        it.sendToPlayer(player);
				        
					//FRENCH
					    JSONChatMessage fr = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra frextra = new JSONChatExtra("French", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        frextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to French Language");
				        frextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language french");
				        fr.addExtra(frextra);
				        fr.sendToPlayer(player);
				        
					//CHINESE
					    JSONChatMessage chi = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra chiextra = new JSONChatExtra("Chinese", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        chiextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Chinese Language");
				        chiextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language chinese");
				        chi.addExtra(chiextra);
				        chi.sendToPlayer(player);
				        
					//TURKISH
					    JSONChatMessage trk = new JSONChatMessage("Language: ", JSONChatColor.GRAY, null);
				        JSONChatExtra trkextra = new JSONChatExtra("Turkish", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        trkextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Switch to Turkish Language");
				        trkextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/language turkish");
				        trk.addExtra(trkextra);
				        trk.sendToPlayer(player);
					}
					 
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("turkish") || args[0].equalsIgnoreCase("chinese") || args[0].equalsIgnoreCase("french") || 
								args[0].equalsIgnoreCase("italian") || args[0].equalsIgnoreCase("russian") || args[0].equalsIgnoreCase("polish") || 
								args[0].equalsIgnoreCase("arabic") || args[0].equalsIgnoreCase("swedish") || args[0].equalsIgnoreCase("dutch") || 
								args[0].equalsIgnoreCase("spanish") || args[0].equalsIgnoreCase("german") || args[0].equalsIgnoreCase("english")){
							String lang = args[0].toUpperCase();
							player.sendMessage("§7Previous language:§a " + LanguageAPI.getLanguage(player).toString());
							try {
								LanguageAPI.setLanguage(player, Language.valueOf(lang));
							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							player.sendMessage("§7New language: §a" + lang);
							
						} else {
							player.sendMessage("§cThe language §7" + args[0] + " §cis not supported.");
						}
							
					} if(args.length > 1) {
						player.sendMessage("§cUsage: §7/language <lang>");
					 }
					} else {
						player.sendMessage("§cYou typed §7/language §cbut enable.cmd is disabled in config, want to enable it?");
					  
						JSONChatMessage trk = new JSONChatMessage("Enable: ", JSONChatColor.GRAY, null);
				        JSONChatExtra trkextra = new JSONChatExtra("YES", JSONChatColor.GREEN, Arrays.asList(JSONChatFormat.BOLD));
				        trkextra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Enable language cmd");
				        trkextra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/coreapi enable.cmd");
				        trk.addExtra(trkextra);
				        trk.sendToPlayer(player);
				        
					    JSONChatMessage trk2 = new JSONChatMessage("Enable: ", JSONChatColor.GRAY, null);
				        JSONChatExtra trkextra2 = new JSONChatExtra("NO", JSONChatColor.RED, Arrays.asList(JSONChatFormat.BOLD));
				        trkextra2.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Dont enable language cmd");
				        trk2.addExtra(trkextra2);
				        trk2.sendToPlayer(player);
					}
			}
				return false;
		}
		
		
	
}