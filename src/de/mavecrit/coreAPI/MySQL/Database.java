package de.mavecrit.coreAPI.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.mavecrit.coreAPI.MySQL.Queries.Query;

public abstract class Database
{
  private String host;
  private int port;
  private String user;
  private String pass;
  private String database;

  public Database(String host, int port, String user, String pass, String database)
  {
    this.host = host;
    this.port = port;
    this.user = user;
    this.pass = pass;
    this.database = database; } 
  public abstract void connect() throws Exception;

  public abstract void disconnect() throws Exception;

  public abstract boolean hasConnection();

  public abstract QueryReturnData query(Query paramQuery) throws SQLException;

  public abstract QueryReturnData query(String paramString) throws SQLException;

  public abstract void update(Query paramQuery) throws SQLException;

  public abstract void update(String paramString) throws SQLException;

  public String getHost() { return this.host; }

  public int getPort() {
    return this.port;
  }
  public String getUser() {
    return this.user;
  }
  public String getPassword() {
    return this.pass;
  }
  public String getDatabase() {
    return this.database;
  }
  public static class QueryReturnData {
    private ResultSet rs;
    private Statement stmt;

    public QueryReturnData(ResultSet rs, Statement stmt) { this.rs = rs;
      this.stmt = stmt; }

    public ResultSet getResultSet() {
      return this.rs;
    }
    public Statement getStatement() {
      return this.stmt;
    }
    public void closeAll() {
      try {
        this.stmt.close();
      } catch (Exception localException) {
      }
      try {
        this.rs.close();
      }
      catch (Exception localException1)
      {
      }
    }
  }
}