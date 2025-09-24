package Demo;
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


import java.io.*;
import java.util.*;
import org.sgbd.Mysgbd.*;

public class Mtest {

  public static void main(String args[]) {
    try {

      MqlParser p = new MqlParser();

      p.initParser(new ByteArrayInputStream(args[0].getBytes()));
      MStatement st = p.readStatement();
      System.out.println(st.toString()); // Display the statement

      MQuery q = (MQuery)st;
      Vector v = q.getSelect();
      for(int i=0; i<v.size(); i++) {
        MSelectItem it = (MSelectItem)v.elementAt(i);
        System.out.print("col=" + it.getColumn() + ",agg=" + it.getAggregate());
        String s = it.getSchema();
        if(s != null) System.out.print(",schema=" + s);
        s = it.getTable();
        if(s != null) System.out.print(",table=" + s);
        System.out.println();
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

};
