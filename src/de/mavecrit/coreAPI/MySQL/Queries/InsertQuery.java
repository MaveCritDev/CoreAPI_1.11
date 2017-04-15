package de.mavecrit.coreAPI.MySQL.Queries;

import java.util.ArrayList;
import java.util.List;

public class InsertQuery
  implements Query
{
  private List<String> values = new ArrayList<String>();
  private String table;

  public void addValue(String vl)
  {
    this.values.add(vl);
  }
  public void setTable(String tbl) {
    this.table = tbl;
  }

  public String toQuery() {
    String qry = "INSERT INTO " + this.table + " VALUES(";
    boolean first = true;
    for (String s : this.values) {
      qry = qry + (!first ? "," : "") + "'" + s + "'";
      first = false;
    }
    qry = qry + ")";
    return qry;
  }
}