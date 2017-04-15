package de.mavecrit.coreAPI.MySQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.mavecrit.coreAPI.MySQL.Queries.Query;

public class MySQL extends Database
{
  private Connection con;

  public MySQL(String host, int port, String user, String pass, String database)
  {
    super(host, port, user, pass, database);
  }

  public void connect() throws Exception
  {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      String url = "jdbc:mysql://" + getHost() + ":" + 
        getPort() + "/" + getDatabase() + 
        "?autoReconnect=true";
      this.con = DriverManager.getConnection(url, getUser(), 
        getPassword());
    } catch (Exception e) {
      throw new Exception("[CoreAPI] Could not connect to database: " + e.getMessage());
    }
  }

  public void disconnect() throws Exception
  {
    try {
      this.con.close();
    } catch (SQLException e) {
      throw new Exception("[CoreAPI] Could not disconnect from database: " + e.getMessage());
    }
  }

  public boolean hasConnection()
  {
    try {
      return (this.con != null) && (!this.con.isClosed()); } catch (Exception e) {
    }
    return false;
  }

  public Database.QueryReturnData query(Query q)
    throws SQLException
  {
    return query(q.toQuery());
  }

  public Database.QueryReturnData query(String q) throws SQLException
  {
    if (!hasConnection()) return null;
    Statement stmt = this.con.createStatement();
    ResultSet rs = stmt.executeQuery(q);
    return new Database.QueryReturnData(rs, stmt);
  }

  public void update(Query q) throws SQLException
  {
    update(q.toQuery());
  }

  public void update(String qry) throws SQLException
  {
    if (!hasConnection()) return;
    Statement stmt = this.con.createStatement();
    stmt.executeUpdate(qry);
    stmt.close();
  }
}