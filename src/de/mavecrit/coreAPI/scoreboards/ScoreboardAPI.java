package de.mavecrit.coreAPI.scoreboards;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class ScoreboardAPI {
	
	public static boolean FlyScoreboard(Player p, String title, String[] lines)
	{
		lines = Scoreboards.Fly(lines);

		try
		{
			if(p.getScoreboard() == null || p.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard() || p.getScoreboard().getObjectives().size() != 1)
			{
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}

			if(p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)) == null)
			{
				p.getScoreboard().registerNewObjective(p.getUniqueId().toString().substring(0, 16), "dummy");
				p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).setDisplaySlot(DisplaySlot.SIDEBAR);
			}



			p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(title);

			for(int i = 0; i < lines.length; i++)
				if(lines[i] != null)
					if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(lines[i]).getScore() != 16 - i)
					{
						p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(lines[i]).setScore(16 - i);
						for(String string : p.getScoreboard().getEntries())
							if(p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(string).getScore() == 16 - i)
								if(!string.equals(lines[i]))
									p.getScoreboard().resetScores(string);

					}

			for(String entry : p.getScoreboard().getEntries())
			{
				boolean toErase = true;
				for(String element : lines)
				{
					if(element != null && element.equals(entry) && p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(entry).getScore() == 16 - Arrays.asList(lines).indexOf(element))
					{
						toErase = false;
						break;
					}
				}

				if(toErase)
					p.getScoreboard().resetScores(entry);

			}

			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean FlyScoreboard(Collection<Player> players, String title, String[] elements)
	{
		for(Player player :  players)
			if(!FlyScoreboard(player, title, elements))
				return false;

		return true;
	}
}
