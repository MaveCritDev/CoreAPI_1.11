package de.mavecrit.coreAPI.Coupons;

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

public class CouponsAPI {
	public static Coupons getCoupon(String code, String amount) {

			try {
				MySQL sql = Main.getMySQL();
				SelectQuery q = new SelectQuery();
				q.setTable("coupon");
				q.setCondition("code", code);
				q.setCondition("value", amount);
				Database.QueryReturnData data = sql.query(q);
				ResultSet s = data.getResultSet();

				String lang = null;
				if (s.next()) {
					lang = s.getString("coupon");
				} else {
					s.close();
					return Main.defaultCoupon;
				}
				s.close();
				return Coupons.valueOf(lang);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return Main.defaultCoupon;
	}

	public static void setCoupon(String code, Coupons c, String amount) throws ClassNotFoundException, SQLException {
		if (Main.plugin.getConfig().getBoolean("enable.mysql")) {
		SelectQuery q = new SelectQuery();
		MySQL sql = Main.getMySQL();
		q.setTable("coupon");
		q.setCondition("code", code);
		q.setCondition("value", amount);
		Database.QueryReturnData data = sql.query(q);
		ResultSet s = data.getResultSet();
		if ((s != null) && (s.next())) {
			UpdateQuery q1 = new UpdateQuery();
			q1.setTable("coupon");
			q1.setCondition("code", code);
			q1.setCondition("value", amount);
			q1.addUpdate("coupon", c.toString());
			
			Main.getMySQL().update(q1.toQuery());
		} else {
			InsertQuery q1 = new InsertQuery();
			q1.setTable("coupon");
			q1.addValue(code);
			q1.addValue(amount);
			q1.addValue(c.toString());
			Main.getMySQL().update(q1.toQuery());
		}
		
	  }
	}

}