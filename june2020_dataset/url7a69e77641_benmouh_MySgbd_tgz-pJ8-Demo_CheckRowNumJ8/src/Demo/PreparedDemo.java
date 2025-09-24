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

package Demo;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.*;

import org.sgbd.Mysgbd.*;

/**
 * <pre>
 * </pre>
 */
public class PreparedDemo {

  public static void main(String args[]) {
    try {

      MqlParser p = null;

      if(args.length < 1) {
        System.out.println("Reading SQL from stdin (quit; or exit; to quit)");
        p = new MqlParser(System.in);
      } else {
        p = new MqlParser(new DataInputStream(new FileInputStream(args[0])));
      }

      // Read all SQL statements from input
      MStatement st;
      while((st = p.readStatement()) != null) {

        System.out.println(st.toString()); // Display the statement

        if(st instanceof MQuery) { // An SQL query: query the DB
          handleQuery((MQuery)st);
        } else if(st instanceof MInsert) { // An SQL insert
          handleInsert((MInsert)st);
        } else if(st instanceof MUpdate) {
          handleUpdate((MUpdate)st);
        } else if(st instanceof MDelete) {
          handleDelete((MDelete)st);
        }
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   */
  static void handleQuery(MQuery q) throws Exception {
    System.out.println("SELECT Statement:");

    Vector sel = q.getSelect(); // SELECT part of the query

    MExpression w = (MExpression)q.getWhere();
    if(w != null) {

      Hashtable meta = null;

      Vector from = q.getFrom();  // FROM part of the query

      // Column metadata: Hashtable (alias, table name)
      // Will be used by handleWhere() to resolve aliases into table names
      meta = new Hashtable();
      for(int i=0; i<from.size(); i++) {
        MFromItem fi = (MFromItem)from.elementAt(i);
        String alias = fi.getAlias();
        if(alias == null) alias = fi.getTable();
        meta.put(alias.toUpperCase(), fi.getTable());
      }

      handleWhere(w, meta);
    }

  }

  static void handleInsert(MInsert ins) throws Exception {
    System.out.println("INSERT Statement:");
    String tab = ins.getTable();
    Vector values = ins.getValues();
    if(values == null) {
      System.out.println("no VALUES(), probably a subquery ?");
    }
    int nval = values.size();
    Vector columns = ins.getColumns();
    if(columns == null) {
      System.out.println("no column names, assuming _col_1"
       + (nval > 1 ? " to _col_" + nval : ""));
      columns = new Vector(nval);
      for(int i=1; i<=nval; i++) {
        columns.addElement("_col_" + i);
      }
    }

    for (int i=0; i<nval; i++) {
      MExp v = (MExp)values.elementAt(i);
      if(isPreparedColumn(v)) {
        System.out.println("[" + tab + "," + columns.elementAt(i) + "]");
      }
    }
  }

  static void handleUpdate(MUpdate upd) throws Exception {
    System.out.println("UPDATE Statement:");

    String tab = upd.getTable();
    Hashtable set = upd.getSet();
    Enumeration k = set.keys();
    while(k.hasMoreElements()) {
      String col = (String)k.nextElement();
      MExp e = (MExp)set.get(col);
      if(isPreparedColumn(e)) {
        System.out.println("[" + tab + "," + col + "]");
      }
    }

    MExpression w = (MExpression)upd.getWhere();
    if(w != null) {
      Hashtable meta = new Hashtable(1);
      meta.put(tab, tab);
      handleWhere(w, meta);
    }

  }

  static void handleDelete(MDelete del) throws Exception {
    System.out.println("DELETE Statement:");

    String tab = del.getTable();

    MExpression w = (MExpression)del.getWhere();
    if(w != null) {
      Hashtable meta = new Hashtable(1);
      meta.put(tab, tab);
      handleWhere(w, meta);
    }

  }

  static void handleWhere(MExp e, Hashtable meta) throws Exception {

    //if(meta != null) System.out.println(meta);

    if(! (e instanceof MExpression)) return;
    MExpression w = (MExpression)e;

    Vector operands = w.getOperands();
    if(operands == null) return;

    // Look for prepared column ("?")
    String prepared = null;
    for(int i=0; i<operands.size(); i++) {
      if(isPreparedColumn((MExp)operands.elementAt(i))) {
        prepared = ((MConstant)operands.elementAt(0)).getValue();
        if(operands.size() != 2) {
          throw new Exception("ERROR in where clause ?? found:"
           + w.toString());
        }
        break;
      }
    }

    if(prepared != null) {  // prepared contains the (raw) column or alias name

      boolean noalias = false;

      // Parse raw column name to look for table name & columnalias name
      // Syntax: [[schema].table.]columnalias
      String tbl = null;

      int pos = prepared.lastIndexOf('.');
      if(pos > 0) {  // [schema.]table.columnalias

        tbl = prepared.substring(0, pos);
        prepared = prepared.substring(pos+1); // The real column name

        if((pos = tbl.lastIndexOf('.')) > 0) { // schema.table.columnalias
          tbl = tbl.substring(pos+1);
          noalias = true;
        }
      }

      // Now tbl is the table name or null, prepared is the column name
      // Note tbl may be an alias
      // (like in "select * from mytable t where t.mykey=1", the table name is
      // "mytable", not "t")

      if(! noalias) {
        // If tbl is an alias, resolve it
        if(tbl != null) tbl = (String)meta.get(tbl.toUpperCase());

      }

      if(tbl == null && meta.size() == 1) {
        Enumeration keys = meta.keys();
        tbl = (String)keys.nextElement();
      }

      // Now tbl is either the real table name, or null if unresolved
      System.out.println("[" + (tbl == null ? "unknown" : tbl) + ","
       + prepared + "]");

    } else {  // No prepared column, go further analyzing the expression

      for(int i=0; i<operands.size(); i++) {
        handleWhere(w.getOperand(i), meta); // WARNING - Recursive call...
      }
    }

  }

  static boolean isPreparedColumn(MExp v) {
    return
     (v instanceof MExpression && ((MExpression)v).getOperator().equals("?"));
  }

};

