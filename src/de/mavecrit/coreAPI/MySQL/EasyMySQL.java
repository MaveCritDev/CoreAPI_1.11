package de.mavecrit.coreAPI.MySQL;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

public class EasyMySQL
{
  private MySQL con;

  public EasyMySQL(File folder)
  {
    if (!folder.exists()) folder.mkdirs();
    File f = new File(folder, "mysql.yml");
    if (!f.exists()) try { f.createNewFile(); } catch (Exception localException) {
      } YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
    cfg.options().copyDefaults(true);
    cfg.addDefault("host", "localhost");
    cfg.addDefault("port", Integer.valueOf(3306));
    cfg.addDefault("user", "user");
    cfg.addDefault("password", "password");
    cfg.addDefault("database", "database");

    String host = cfg.getString("host");
    String user = cfg.getString("user");
    String pass = cfg.getString("password");
    String data = cfg.getString("database");
    int port = cfg.getInt("port");
    try {
      cfg.save(f);
    } catch (IOException e) {
      e.printStackTrace();
    }
    cfg = YamlConfiguration.loadConfiguration(f);

    this.con = new MySQL(host, port, user, pass, data);
  }

  public MySQL getMySQL() {
    return this.con;
  }
}