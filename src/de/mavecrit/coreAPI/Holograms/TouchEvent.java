package de.mavecrit.coreAPI.Holograms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TouchEvent
{
  private String attachment;
  private Player p;
  private Location loc;
  private TouchScreen screen;
  private Hologram h;

  public TouchEvent(String attachment, TouchScreen screen, Hologram h, Player p, Location loc)
  {
    this.h = h;
    this.screen = screen;
    this.attachment = attachment;
    this.p = p;
    this.loc = loc;
  }

  public Hologram getHologram()
  {
    return this.h;
  }

  public String getAttachment() {
    return this.attachment;
  }

  public Player getPlayer() {
    return this.p;
  }

  public Location getLocation() {
    return this.loc;
  }

  public TouchScreen getScreen() {
    return this.screen;
  }
}