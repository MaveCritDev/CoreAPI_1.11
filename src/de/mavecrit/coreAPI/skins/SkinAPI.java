package de.mavecrit.coreAPI.skins;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.md_5.bungee.api.ChatColor;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.mavecrit.coreAPI.Main;
import de.mavecrit.coreAPI.Language.LanguageReceiveEvent;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutNamedEntitySpawn;

public class SkinAPI {	
	

	  @SuppressWarnings("deprecation")
	public static Skin getSkinFromString(String name)
	  {
	    OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(name);
	    Skin s = new Skin(p.getUniqueId());
	    return s;
	  }

	  public static void setSkinPlayer(final Player p, final String name, boolean SeeSelf) { 
		    Skin s = getSkinFromString(name);
		    Bukkit.getServer().getPluginManager().callEvent(new SkinReceiveEvent(p, s));
		    if (s.getSkinName() == null) {
		      p.sendMessage("§cError connecting to Mojang (Wait 30 seconds or choose other name)");
		      return;
		    }
		    GameProfile gp = ((CraftPlayer)p).getProfile();
		    p.setPlayerListName(name);
		    gp.getProperties().clear();
		    gp.getProperties().put(s.getSkinName(), new Property(s.getSkinName(), s.getSkinValue(), s.getSkinSignatur()));
		    if (SeeSelf) {
		      Main.clipSkins.remove(p);
		      Main.clipSkins.put(p.getName(), s);
		    }
		    for (Player on : Bukkit.getServer().getOnlinePlayers()) {
		      on.hidePlayer(p);
		    }
		    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		    {
		      public void run()
		      {
		        for (Player on : Bukkit.getServer().getOnlinePlayers())
		          on.showPlayer(p);
		      }
		    }
		    , 20L);
		  }

		  public void setSkinPlayer(final Player p, Skin s, boolean showSelf) {
		    if (s.getSkinName() == null) {
		      p.sendMessage("§cError connecting to Mojang (Wait 30 seconds or choose other name)");
		      return;
		    }
		    GameProfile gp = ((CraftPlayer)p).getProfile();
		    gp.getProperties().clear();
		    gp.getProperties().put(s.getSkinName(), new Property(s.getSkinName(), s.getSkinValue(), s.getSkinSignatur()));
		    if (showSelf) {
		      Main.clipSkins.remove(p);
		      Main.clipSkins.put(p.getName(), s);
		    }
		    for (Player on : Bukkit.getServer().getOnlinePlayers()) {
		      on.hidePlayer(p);
		    }
		    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		    {
		      public void run()
		      {
		        for (Player on : Bukkit.getServer().getOnlinePlayers())
		          on.showPlayer(p);
		      }
		    }
		    , 20L);
		  }
		  
		  
		  
	  public static void resetSkin(final Player p) {
		    Skin s = getSkinFromString(p.getName());
		    if (s.getSkinName() == null) {
		      p.sendMessage("§cError connecting to Mojang (Wait 30 seconds)");
		      return;
		    }
		    GameProfile gp = ((CraftPlayer)p).getProfile();
		    p.setPlayerListName(p.getName());
		    gp.getProperties().clear();
		    gp.getProperties().put(s.getSkinName(), new Property(s.getSkinName(), s.getSkinValue(), s.getSkinSignatur()));
		    Main.clipSkins.remove(p.getName());
		    
		    for (Player on : Bukkit.getServer().getOnlinePlayers()) {
		      on.hidePlayer(p);
		    }
		    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		    {
		      public void run()
		      {
		        for (Player on : Bukkit.getServer().getOnlinePlayers())
		          on.showPlayer(p);
		      }
		    }
		    , 20L);
		  }
	  
}


