/*
 * This file is part of Mysgbd.
 *
 * Mysgbd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mysgbd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mysgbd.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sgbd.Mysgbd;

import java.io.*;
import java.util.*;

/**
 * MInsert: an SQL INSERT statement
 */
public class MInsert implements MStatement {

  String table_;
  Vector columns_ = null;
  MExp valueSpec_ = null;

  /**
   * Create an INSERT statement on a given table
   */
  public MInsert(String tab) {
    table_ = new String(tab);
  }

  /**
   * Get the name of the table involved in the INSERT statement.
   * @return A String equal to the table name
   */
  public String getTable() {
    return table_;
  }

  /**
   * Get the columns involved in the INSERT statement.
   * @return A Vector of Strings equal to the column names
   */
  public Vector getColumns() {
    return columns_;
  }

  /**
   * Specify which columns to insert
   * @param c A vector of column names (Strings)
   */
  public void addColumns(Vector c) { columns_ = c; }

  /**
   * Specify the VALUES part or SQL sub-query of the INSERT statement
   * @param e An SQL expression or a SELECT statement.
   * If it is a list of SQL expressions, e should be represented by ONE
   * SQL expression with operator = "," and operands = the expressions in
   * the list.
   * If it is a SELECT statement, e should be a MQuery object.
   */
  public void addValueSpec(MExp e) { valueSpec_ = e; }

  /**
   * Get the VALUES part of the INSERT statement
   * @return A vector of SQL Expressions (MExp objects);
   * If there's no VALUES but a subquery, returns null (use getQuery() method).
   */
  public Vector getValues() {
    if(! (valueSpec_ instanceof MExpression)) return null;
    return ((MExpression)valueSpec_).getOperands();
  }

  /**
   * Get the sub-query (ex. in INSERT INTO table1 SELECT * FROM table2;, the
   * sub-query is SELECT * FROM table2;)
   * @return A MQuery object (A SELECT statement), or null if there's no
   * sub-query (in that case, use the getValues() method to get the VALUES
   * part).
   */
  public MQuery getQuery() {
    if(! (valueSpec_ instanceof MQuery)) return null;
    return (MQuery)valueSpec_;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer("insert into " + table_);
    if(columns_ != null && columns_.size() > 0) {
      //buf.append(" " + columns_.toString());
      buf.append("(" + columns_.elementAt(0));
      for(int i=1; i<columns_.size(); i++) {
        buf.append("," + columns_.elementAt(i));
      }
      buf.append(")");
    }

    String vlist = valueSpec_.toString();
    buf.append(" ");
    if(getValues() != null)
      buf.append("values ");
    if(vlist.startsWith("(")) buf.append(vlist);
    else buf.append(" (" + vlist + ")");

    return buf.toString();
  }
};

