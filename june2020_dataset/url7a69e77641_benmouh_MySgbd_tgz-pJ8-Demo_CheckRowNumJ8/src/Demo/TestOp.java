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
import java.io.*;

import org.sgbd.Mysgbd.*;
import org.sgbd.Mysgbd.parser.ParseException;

public class TestOp {

  public static void main(String args[]) {
    try {

      MqlParser p = new MqlParser();

      p.initParser(new ByteArrayInputStream(args[0].getBytes()));
      MQuery st = (MQuery) p.readStatement();
      System.out.println(st.toString()); // Display the statement

        MExpression where = (MExpression) st.getWhere();
        MExpression prjNums = new MExpression("OR");
        for (int i = 1; i < 4; i++) {
            prjNums.addOperand(
                new MExpression(
                    "=",
                    new MConstant("ID", MConstant.COLUMNNAME),
                    new MConstant("" + i, MConstant.NUMBER)));
        }
 
        if (where != null) {
            //where.addOperand(new MExpression("AND", prjNums));
            MExpression w = new MExpression("AND");
            w.addOperand(where);
            w.addOperand(prjNums);
            where = w;
        } else {
            where = prjNums;
        }
        st.addWhere(where);
      System.out.println(st.toString()); // Display the statement

    } catch(ParseException e) {
      System.err.println("PARSE EXCEPTION:");
      e.printStackTrace(System.err);
    } catch(Error e) {
      System.err.println("ERROR");
    } catch(Exception e) {
      System.err.println("CLASS" + e.getClass());
      e.printStackTrace(System.err);
    }
  }

};
