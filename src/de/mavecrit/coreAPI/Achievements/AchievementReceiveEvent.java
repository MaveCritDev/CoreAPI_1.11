package de.mavecrit.coreAPI.Achievements;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class AchievementReceiveEvent extends Event{

	Player p;
	String ach;
    private static final HandlerList handlers = new HandlerList();
	
	public AchievementReceiveEvent(Player p, String key){
		this.p = p;
		this.ach = key;
	}
	
	public Player getPlayer(){
		return this.p;
	}
	
	public String getAch(){
		return this.ach;
	}


    
  
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

