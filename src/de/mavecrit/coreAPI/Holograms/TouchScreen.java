package de.mavecrit.coreAPI.Holograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_11_R1.EntityBat;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_11_R1.WorldServer;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TouchScreen
  implements Listener
{
  private Location loc;
  private List<TouchListener> listeners = new ArrayList<TouchListener>();
  private String att;
  private List<String> pl = new ArrayList<String>();
  private EntityBat bat;
  private Plugin plugin;
  private TouchScreen ins;
  private Hologram h;
  private static HashMap<TouchScreen, Plugin> s = new HashMap<TouchScreen, Plugin>();

  public TouchScreen(Location loc, String att, Plugin pl)
  {
    this.loc = loc;
    this.ins = this;
    this.att = att;
    this.plugin = pl;
    register(pl, this);
    setup();
  }

  public void attachToHologram(Hologram holo)
  {
    this.h = holo;
    this.h.addTouchScreen(this);
  }
  public void addTouchListener(TouchListener l) {
    this.listeners.add(l);
  }
  private void setup() {
    WorldServer w = ((CraftWorld)this.loc.getWorld()).getHandle();
    this.bat = new EntityBat(w);

    this.bat.setLocation(this.loc.getX(), this.loc.getY(), this.loc.getZ(), 0.0F, 0.0F);
    this.bat.setInvisible(true);

    registerListener();
  }
  public void sendToPlayer(Player p) {
    if (this.pl.contains(p.getName())) return;
    PacketPlayOutSpawnEntityLiving pa = new PacketPlayOutSpawnEntityLiving(this.bat);
    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pa);
    this.pl.add(p.getName());
  }
  public void removeFromPlayer(Player p) {
    if (!this.pl.contains(p.getName())) return;
    PacketPlayOutEntityDestroy ed = new PacketPlayOutEntityDestroy(new int[] { this.bat.getId() });
    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ed);
    this.pl.remove(p.getName());
  }
  private void registerListener() {
    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.USE_ENTITY })
    {
      public void onPacketReceiving(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
          Player p = event.getPlayer();
          PacketContainer packet = event.getPacket();
          if (TouchScreen.this.bat.getId() == ((Integer)packet.getIntegers().read(0)).intValue())
            for (TouchListener l : TouchScreen.this.listeners)
              l.onTouch(new TouchEvent(TouchScreen.this.att, TouchScreen.this.ins, (TouchScreen.this).h, p, TouchScreen.this.loc));
        }
      }
    });
  }

  private static void register(Plugin pl, TouchScreen t)
  {
    s.put(t, pl);
  }
  public static Collection<TouchScreen> getTouchScreens() {
    return s.keySet();
  }
  public static List<TouchScreen> getTouchScreens(Plugin pl) {
    List<TouchScreen> screen = new ArrayList<TouchScreen>();
    for (TouchScreen sc : s.keySet()) {
      if (s.get(sc) == pl) {
        screen.add(sc);
      }
    }
    return screen;
  }
}