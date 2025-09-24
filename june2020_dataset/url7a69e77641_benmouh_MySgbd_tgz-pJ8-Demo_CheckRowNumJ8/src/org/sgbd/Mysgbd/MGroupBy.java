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
 * MGroupBy: an SQL GROUP BY...HAVING clause
 */
public class MGroupBy implements java.io.Serializable {

  Vector groupby_;
  MExp having_ = null;

  /**
   * Create a GROUP BY given a set of Expressions
   * @param exps A vector of SQL Expressions (MExp objects).
   */
  public MGroupBy(Vector exps) { groupby_ = exps; }

  /**
   * Initiallize the HAVING part of the GROUP BY
   * @param e An SQL Expression (the HAVING clause)
   */
  public void setHaving(MExp e) { having_ = e; }

  /**
   * Get the GROUP BY expressions
   * @return A vector of SQL Expressions (MExp objects)
   */
  public Vector getGroupBy() { return groupby_; }

  /**
   * Get the HAVING clause
   * @return An SQL expression
   */
  public MExp getHaving() { return having_; }

  public String toString() {
    StringBuffer buf = new StringBuffer("group by ");

    //buf.append(groupby_.toString());
    buf.append(groupby_.elementAt(0).toString());
    for(int i=1; i<groupby_.size(); i++) {
      buf.append(", " + groupby_.elementAt(i).toString());
    }
    if(having_ != null) {
      buf.append(" having " + having_.toString());
    }
    return buf.toString();
  }
};

