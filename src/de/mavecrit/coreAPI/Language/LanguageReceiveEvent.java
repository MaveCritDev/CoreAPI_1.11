package de.mavecrit.coreAPI.Language;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LanguageReceiveEvent extends Event {
	
	Player p;
	Language newLanguage;
    private static final HandlerList handlers = new HandlerList();
	
	public LanguageReceiveEvent(Player p, Language l){
		this.p = p;
		this.newLanguage = l;
	}
	
	public Player getPlayer(){
		return this.p;
	}
	
	public Language getLanguage(){
		return this.newLanguage;
	}


    
  
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
