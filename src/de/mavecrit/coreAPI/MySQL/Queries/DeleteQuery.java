package de.mavecrit.coreAPI.MySQL.Queries;

public class DeleteQuery
  implements Query
{
  private String table;
  private String conRow;
  private String conVal;

  public void setCondition(String row, String val)
  {
    this.conRow = row;
    this.conVal = val;
  }
  public void setTable(String table) {
    this.table = table;
  }

  public String toQuery() {
    return "DELETE FROM " + this.table + " WHERE " + this.conRow + "='" + this.conVal + "'";
  }
}