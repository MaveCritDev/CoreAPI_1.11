package de.mavecrit.coreAPI.MySQL.Queries;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery
  implements Query
{
  private List<String> rows = new ArrayList<String>();
  private String table;
  private String conRow;
  private String conVal;

  public void setTable(String table)
  {
    this.table = table;
  }
  public void setCondition(String row, String value) {
    this.conRow = row;
    this.conVal = value;
  }
  public void addRow(String row) {
    this.rows.add(row);
  }

  public String toQuery() {
    return "SELECT " + (this.rows.isEmpty() ? "*" : toRows()) + " FROM " + this.table + " WHERE " + this.conRow + "='" + this.conVal + "'";
  }

  public String toRows() {
    String r = "";
    boolean first = true;
    for (String s : this.rows) {
      r = r + (!first ? ", " : "") + "`" + s + "`";
      first = false;
    }
    return r;
  }
}