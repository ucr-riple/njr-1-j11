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


/**
 * WARNING: This code uses INTERNAL APIs
 * MqlJJParser should not be used directly by Zql users
 */
package Demo;
import java.io.*;

import org.sgbd.Mysgbd.*;
import org.sgbd.Mysgbd.parser.MysgbdParser;

public class WhereClause {

  public static void main(String args[]) {
    try {

    	MysgbdParser p = new MysgbdParser(
        new ByteArrayInputStream(args[0].getBytes()));

      MExp e = p.SQLExpression();
      System.out.println(e.toString());

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

};

