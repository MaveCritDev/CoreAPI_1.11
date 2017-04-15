package de.mavecrit.coreAPI.Holograms;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_11_R1.EntityArmorStand;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_11_R1.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Hologram
{
  private List<TouchScreen> scs = new ArrayList<TouchScreen>();
  private Location loc;
  private List<String> text = new ArrayList<String>();
  private List<EntityArmorStand> stands = new ArrayList<EntityArmorStand>();
  private List<String> players = new ArrayList<String>();
  private double height;
  private double startHeight;

  private void calculateLocations()
  {
    this.height = this.startHeight;
    this.height = (this.height - 1.1D + this.text.size() * 0.05D);
  }
  public Hologram(Location loc) {
    this.loc = loc;
    this.startHeight = loc.getY();
    this.height = loc.getY();
  }
  public Hologram(Location loc, String[] text) {
    this.loc = loc;
    this.startHeight = loc.getY();
    this.height = loc.getY();

    for (String s : text) {
      this.text.add(s);
    }
    setup();
  }
  public void addLine(String text) {
    this.text.add(text);
    refresh();
  }
  public void removeLine(int line) {
    this.text.remove(line);
    refresh();
  }
  public void removeLine(String text) {
    this.text.remove(text);
    refresh();
  }

  public void addTouchScreen(TouchScreen s)
  {
    this.scs.add(s);
    refresh();
  }
  public void setLine(int line, String text) {
    this.text.set(line, text);
    refresh();
  }
  public String getLine(int id) {
    return (String)this.text.get(id);
  }
  public void sendToPlayer(Player p) {
    if (this.players.contains(p.getName())) return;
    for (EntityArmorStand stand : this.stands) {
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(stand));
    }
    for (TouchScreen s : this.scs) {
      s.sendToPlayer(p);
    }
    this.players.add(p.getName());
  }
  private void sendToPlayer(Player p, boolean add) {
    for (EntityArmorStand stand : this.stands) {
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(stand));
    }
    if (add) this.players.add(p.getName()); 
  }

  public void removeFromPlayer(Player p) { if (!this.players.contains(p.getName())) return;
    for (EntityArmorStand stand : this.stands) {
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { stand.getId() }));
    }
    for (TouchScreen s : this.scs) {
      s.removeFromPlayer(p);
    }
    this.players.remove(p.getName()); }

  private void removeFromPlayer(Player p, boolean remove) {
    if (!this.players.contains(p.getName())) return;
    for (EntityArmorStand stand : this.stands) {
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { stand.getId() }));
    }
    if (remove) this.players.remove(p.getName()); 
  }

  private void setup() { WorldServer s = ((CraftWorld)this.loc.getWorld()).getHandle();
    calculateLocations();
    for (String text : this.text) {
      EntityArmorStand stand = new EntityArmorStand(s);

      stand.setSmall(true);
      stand.setLocation(this.loc.getX(), this.height, this.loc.getZ(), 0.0F, 0.0F);
      stand.setCustomName(text);
      stand.setNoGravity(false);
      stand.setInvisible(true);
      stand.setCustomNameVisible(true);
      stand.setSmall(true);

      this.height -= 0.3D;
      this.stands.add(stand);
    } }

  public void refresh() {
    for (String s : this.players) {
      Player p = Bukkit.getPlayer(s);
      if (p != null)
      {
        removeFromPlayer(p, false);
      }
    }
    this.stands.clear();
    setup();
    for (String s : this.players) {
      Player p = Bukkit.getPlayer(s);
      if (p != null)
      {
        sendToPlayer(p, false);
      }
    }
    refreshPlayers();
  }
  public void refreshPlayers() {
    List<String> toRemove = new ArrayList<String>();
    for (String s : this.players) {
      Player p = Bukkit.getPlayer(s);
      if (p == null) {
        toRemove.add(s);
      }
      
    }
    for (String s : toRemove)
        this.players.remove(s);
  }

  public List<TouchScreen> getTouchScreens()
  {
    return this.scs;
  }
  
  public void remove() {
    refreshPlayers();
    for (String s : this.players) {
      Player p = Bukkit.getPlayer(s);
      removeFromPlayer(p);
    }
  }

  public Location getLoc()
  {
    return this.loc;
  }
  public void setLoc(Location loc) {
    this.loc = loc;
  }
  public List<String> getText() {
    return this.text;
  }
  public void setText(List<String> text) {
    this.text = text;
  }
  public List<EntityArmorStand> getStands() {
    return this.stands;
  }
  public void setStands(List<EntityArmorStand> stands) {
    this.stands = stands;
  }
  public List<String> getPlayers() {
    return this.players;
  }
  public void setPlayers(List<String> players) {
    this.players = players;
  }
  public double getHeight() {
    return this.height;
  }
  public void setHeight(double height) {
    this.height = height;
  }
}