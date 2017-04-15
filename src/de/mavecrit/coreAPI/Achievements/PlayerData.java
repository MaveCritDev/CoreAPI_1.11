package de.mavecrit.coreAPI.Achievements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import de.mavecrit.coreAPI.Main;
import de.mavecrit.coreAPI.MySQL.Database;
import de.mavecrit.coreAPI.MySQL.MySQL;
import de.mavecrit.coreAPI.MySQL.Queries.InsertQuery;
import de.mavecrit.coreAPI.MySQL.Queries.Query;
import de.mavecrit.coreAPI.MySQL.Queries.SelectQuery;
import de.mavecrit.coreAPI.MySQL.Queries.UpdateQuery;

public class PlayerData {
	private String uuid;
	private String name;
	private String archievements;
	private List<String> archievementList;

	public PlayerData(String uuid, String name, String archievements) {
		this.uuid = uuid;
		this.name = name;
		this.archievements = archievements;
		this.archievementList = new ArrayList<String>(Arrays.asList(archievements.split(";")));
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getName() {
		return this.name;
	}

	public String getArchievements() {
		return this.archievements;
	}

	public List<String> getArchievementList() {
		return this.archievementList;
	}

	public void addArchievement(Achievements a) {
		if (hasArchievement(a))
			return;
		this.archievementList.add(a.getKey());
		this.archievements = (this.archievements + ";" + a.getKey());
		setPlayerData(this);
	}

	public boolean hasArchievement(Achievements a) {
		return this.archievementList.contains(a.getKey());
	}

	public void removeArchievement(Achievements a) {
		if (!hasArchievement(a))
			return;
		this.archievementList.remove(a.getKey());
		this.archievements = "";
		for (int i = 0; i < this.archievementList.size(); i++) {
			this.archievements += (i != 0 ? ";"
					: new StringBuilder().append((String) this.archievementList.get(i)).toString());
		}
		setPlayerData(this);
	}

	public static String getUUIDbyName(String name) {
			try {
				MySQL sql = Main.getMySQL();
				SelectQuery q = new SelectQuery();
				q.setTable("archievements");
				q.setCondition("name", name);
				Database.QueryReturnData data = sql.query(q);
				ResultSet rs = data.getResultSet();
				String uuid = null;
				if (rs.next()) {
					uuid = rs.getString("uuid");
				}
				data.closeAll();
				return uuid;
			}

			catch (Exception e) {
			}


		return null;

	}

	public static PlayerData getDataByName(String name) {
		return getDataByUUID(getUUIDbyName(name));
	}

	public static PlayerData getDataByUUID(String uuid) {
		if (uuid == null)
			return null;
		return getDataByUUID(UUID.fromString(uuid));
	}

	public static PlayerData getDataByUUID(UUID uuid) {
		try {
			MySQL sql = Main.getMySQL();
			SelectQuery q = new SelectQuery();
			q.setTable("archievements");
			q.setCondition("uuid", uuid.toString());
			Database.QueryReturnData data = sql.query(q);
			ResultSet rs = data.getResultSet();
			PlayerData ret = null;
			if (rs.next()) {
				ret = new PlayerData(rs.getString("uuid"), rs.getString("name"), rs.getString("archievements"));
			}
			data.closeAll();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setPlayerData(PlayerData d) {
		PlayerData da = getDataByUUID(d.getUUID());
		Query q;

		if (da == null) {
			InsertQuery qa = new InsertQuery();
			qa.setTable("archievements");
			qa.addValue(d.getName());
			qa.addValue(d.getUUID());
			qa.addValue(d.getArchievements());
			q = qa;
		} else {
			UpdateQuery qa = new UpdateQuery();
			qa.setTable("archievements");
			qa.addUpdate("name", d.getName());
			qa.addUpdate("archievements", d.getArchievements());
			qa.setCondition("uuid", d.getUUID());
			q = qa;
		}
		MySQL sql = Main.getMySQL();
		try {
			sql.update(q);
		} catch (SQLException localSQLException) {
		}
	}
}