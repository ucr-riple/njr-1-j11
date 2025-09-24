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
import java.util.Vector;
import java.io.*;
import org.sgbd.Mysgbd.*;

public class MergeQueries {

  public static void main(String args[]) {
    try {

      String q1 = "select a.id, a.descr from acce.producto a;";
      String q2 = "select b.id, b.price from info.ventas b;";

      MqlParser p = new MqlParser();

      // Parse query 1
      p.initParser(new ByteArrayInputStream(q1.getBytes()));
      MQuery st1 = (MQuery)p.readStatement();
      System.out.println(st1.toString()); // Display the query

      // Parse query 2
      p.initParser(new ByteArrayInputStream(q2.getBytes()));
      MQuery st2 = (MQuery)p.readStatement();
      System.out.println(st2.toString()); // Display the query

      // Extract "select" and "from" parts of query 1
      Vector cols = st1.getSelect();
      Vector from = st1.getFrom();

      // Extract "select" and "from" parts of query 2
      Vector cols2 = st2.getSelect();
      Vector f2 = st2.getFrom();

      // Append the 2 "select" parts
      for(int i=0; i<cols2.size(); i++) cols.addElement(cols2.elementAt(i));

      // Append the 2 "from" parts
      for(int i=0; i<f2.size(); i++) from.addElement(f2.elementAt(i));

      // Build the new query (adding a new "where" clause)
      MQuery q = new MQuery();
      q.addSelect(cols);
      q.addFrom(from);
      MExpression where = new MExpression("=",
       new MConstant("a.id", MConstant.COLUMNNAME),
       new MConstant("b.id", MConstant.COLUMNNAME));
      q.addWhere(where);

      // Display the new query
      System.out.println(q);

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

};

