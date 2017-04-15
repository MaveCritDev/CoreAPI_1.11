package de.mavecrit.coreAPI.skins;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class SkinReceiveEvent extends Event{

	Player p;
	Skin skin;
    private static final HandlerList handlers = new HandlerList();
	
	public SkinReceiveEvent(Player p, Skin s){
		this.p = p;
		this.skin = s;
	}
	
	public Player getPlayer(){
		return this.p;
	}
	
	public Skin getSkin(){
		return this.skin;
	}


    
  
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

