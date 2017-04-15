package de.mavecrit.coreAPI.MySQL.Queries;

import java.util.HashMap;

public class UpdateQuery
  implements Query
{
  private HashMap<String, String> updates = new HashMap<String, String>();
  private String conRow;
  private String conVal;
  private String table;

  public void addUpdate(String row, String value)
  {
    this.updates.put(row, value);
  }
  public void setCondition(String row, String value) {
    this.conRow = row;
    this.conVal = value;
  }
  public void setTable(String tbl) {
    this.table = tbl;
  }

  public String toQuery() {
    String qry = "UPDATE " + this.table + " SET ";
    boolean first = true;
    for (String row : this.updates.keySet()) {
      String val = (String)this.updates.get(row);
      qry = qry + (!first ? ", " : "") + row + "='" + val + "' ";
      first = false;
    }
    qry = qry + "WHERE " + this.conRow + "='" + this.conVal + "'";
    return qry;
  }
}