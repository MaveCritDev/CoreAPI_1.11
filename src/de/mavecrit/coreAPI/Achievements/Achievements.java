package de.mavecrit.coreAPI.Achievements;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mavecrit.coreAPI.Main;


public class Achievements
{
  private String key;

  public Achievements(String key)
  {
    this.key = key;
  }

  public String getKey() {
    return this.key;
  }

  public void addToPlayer(Player p) {
    PlayerData pd = PlayerData.getDataByUUID(p.getUniqueId());
    if(Main.plugin.getConfig().getBoolean("enable.mysql")){
    if (pd == null) {
      PlayerData.setPlayerData(new PlayerData(p.getUniqueId().toString(), p.getName(), this.key));
      return;
    }
    pd.addArchievement(this);
    Bukkit.getServer().getPluginManager().callEvent(new AchievementReceiveEvent(p, this.key));
    }
  }

  public boolean hasPlayer(Player p)
  {
	  if(Main.plugin.getConfig().getBoolean("enable.mysql")){
    PlayerData pd = PlayerData.getDataByUUID(p.getUniqueId());
    if (pd == null) return false;
    return pd.hasArchievement(this);}
	  
	  return false;
  }
  public void removeFromPlayer(Player p) {
	  if(Main.plugin.getConfig().getBoolean("enable.mysql")){
    PlayerData pd = PlayerData.getDataByUUID(p.getUniqueId());
    if (pd == null) return;
    pd.removeArchievement(this);
	  }
  }
}