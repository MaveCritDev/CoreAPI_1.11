package de.mavecrit.coreAPI.Nametags;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.mojang.authlib.GameProfile;

import de.mavecrit.coreAPI.Main;
import de.mavecrit.coreAPI.skins.SkinAPI;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import net.minecraft.server.v1_11_R1.PlayerInteractManager;
import net.minecraft.server.v1_11_R1.WorldServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;

public class NametagAPI {

	public static List<String> isNamed = new ArrayList<String>();
	public static List<String> CurrentUsedNicks = new ArrayList<String>();


	private static void setName(Player player, String name) throws Exception {
		EntityPlayer entity = ((CraftPlayer) player).getHandle();
		GameProfile profile = entity.getProfile();
		Field profilefield = profile.getClass().getDeclaredField("name");
		profilefield.setAccessible(true);
		profilefield.set(profile, name);
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
		EntityPlayer pNeu = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(player.getUniqueId(), name),
				new PlayerInteractManager(nmsWorld));
		EntityPlayer pl = new EntityPlayer(nmsServer, nmsWorld,
				new GameProfile(player.getUniqueId(), player.getCustomName()), new PlayerInteractManager(nmsWorld));
		for (Player players : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) players).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(
					PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { pl }));
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
					new EntityPlayer[] { pNeu }));
		}
	}

	public static void setNametag(final Player p, String name, boolean tablist, boolean aboveHead) {

		if (!CurrentUsedNicks.contains(name)) {
			CurrentUsedNicks.add(name);

				if (!Main.identifiers.contains(p.getUniqueId().toString())) {
					removeNametag(p);
				}

				isNamed.add(p.getName());

				p.setDisplayName(name);

				p.setCustomNameVisible(true);
				Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
					public void run() {
						for (Player pl : Bukkit.getOnlinePlayers())
							pl.hidePlayer(p);
					}
				}, 20L);
				Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
					public void run() {
						for (Player pl : Bukkit.getOnlinePlayers())
							pl.showPlayer(p);
					}
				}, 40L);
				try {
					setName(p, name);

					p.setDisplayName(name);
					p.setCustomName(name);
				} catch (Exception e) {
					System.out.println(e);
				}
				
			

		} else {
			Bukkit.getLogger().warning(ChatColor.RED + "NAME IS ALREADY IN USE!!!");
			for (Player all : Bukkit.getOnlinePlayers()) {
				for(int i = 0; i < 50; i++){
				all.sendMessage("");
				}
			}
			p.sendMessage("§cName is already in use, choose a new one please.");
			
			SkinAPI.setSkinPlayer(p, Main.identifiers.getString(p.getUniqueId().toString() + ".name"), true);

		}

	}

	public static void removeNametag(final Player p) {
		isNamed.remove(p);
		String oldname = p.getDisplayName();
		CurrentUsedNicks.remove(oldname);
		
			String name = Main.identifiers.getString(p.getUniqueId().toString());
		
			p.setDisplayName(name);
			p.setCustomNameVisible(false);
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				public void run() {
					for (Player pl : Bukkit.getOnlinePlayers())
						pl.hidePlayer(p);
				}
			}, 5L);
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				public void run() {
					for (Player pl : Bukkit.getOnlinePlayers())
						pl.showPlayer(p);
				}
			}, 40L);
			try {
				setName(p, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	public static boolean isNicked(Player p) {
		if (isNamed.contains(p))
			return true;
		return false;
	}

}
