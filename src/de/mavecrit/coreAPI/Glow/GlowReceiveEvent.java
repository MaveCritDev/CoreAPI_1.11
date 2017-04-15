package de.mavecrit.coreAPI.Glow;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.mavecrit.coreAPI.Main;

public class GlowReceiveEvent extends Event {
	
	Player p;
	String color;
	
    private static final HandlerList handlers = new HandlerList();
	
	public GlowReceiveEvent(Player p, String color){
		this.p = p;
		this.color = color;
		
	}
	
	public Player getPlayer(){
		return this.p;
	}
	
	
	public String getColor(){
		return this.color;
	}
  
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
