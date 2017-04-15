package de.mavecrit.coreAPI.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mavecrit.coreAPI.Main;
import de.mavecrit.coreAPI.MySQL.Database;
import de.mavecrit.coreAPI.MySQL.MySQL;
import de.mavecrit.coreAPI.MySQL.Queries.InsertQuery;
import de.mavecrit.coreAPI.MySQL.Queries.SelectQuery;
import de.mavecrit.coreAPI.MySQL.Queries.UpdateQuery;

public class LanguageAPI {
	public static Language getLanguage(Player p) {

		if (Main.plugin.getConfig().getBoolean("enable.mysql")) {
			try {
				MySQL sql = Main.getMySQL();
				SelectQuery q = new SelectQuery();
				q.setTable("language");
				q.setCondition("uuid", p.getUniqueId().toString());
				Database.QueryReturnData data = sql.query(q);
				ResultSet s = data.getResultSet();

				String lang = null;
				if (s.next()) {
					lang = s.getString("language");
				} else {
					s.close();
					return Main.defaultLang;
				}
				s.close();
				return Language.valueOf(lang);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String id = p.getUniqueId().toString();
			String lang_file = Main.language.getString(id + ".language");
			return Language.valueOf(lang_file);
		}
		return Main.defaultLang;
	}

	public static void setLanguage(Player p, Language l) throws ClassNotFoundException, SQLException {
		if (Main.plugin.getConfig().getBoolean("enable.mysql")) {
		SelectQuery q = new SelectQuery();
		MySQL sql = Main.getMySQL();
		q.setTable("language");
		q.setCondition("uuid", p.getUniqueId().toString());
		Database.QueryReturnData data = sql.query(q);
		ResultSet s = data.getResultSet();
		if ((s != null) && (s.next())) {
			UpdateQuery q1 = new UpdateQuery();
			q1.setTable("language");
			q1.setCondition("uuid", p.getUniqueId().toString());
			q1.addUpdate("language", l.toString());
			Main.getMySQL().update(q1.toQuery());
		} else {
			InsertQuery q1 = new InsertQuery();
			q1.setTable("language");
			q1.addValue(p.getUniqueId().toString());
			q1.addValue(l.toString());
			Main.getMySQL().update(q1.toQuery());
		}
		
	  } else {
		  String id = p.getUniqueId().toString();
		  String lang_file = Main.language.getString(id + ".language");
		  Main.language.set(lang_file, l.toString());
		  Main.language.saveConfig();
	  }
		Bukkit.getServer().getPluginManager().callEvent(new LanguageReceiveEvent(p, l));
	}

}