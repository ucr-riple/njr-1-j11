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

import java.io.* ;
import java.util.* ;
import org.sgbd.Mysgbd.parser.*;

/**
 * MQuery: an SQL SELECT statement
 */
public class MQuery implements MStatement, MExp {

  Vector select_;
  public boolean distinct_ = false;
  Vector from_;
  MExp where_ = null;
  MGroupBy groupby_ = null;
  MExpression setclause_ = null;
  Vector orderby_ = null;
  public boolean forupdate_ = false;

  /**
   * Create a new SELECT statement
   */
  public MQuery() {}

  /**
   * Insert the SELECT part of the statement
   * @param s A vector of MSelectItem objects
   */
  public void addSelect(Vector s) { select_ = s; }

  /**
   * Insert the FROM part of the statement
   * @param f a Vector of MFromItem objects
   */
  public void addFrom(Vector f) { from_ = f; }

  /**
   * Insert a WHERE clause
   * @param w An SQL Expression
   */
  public void addWhere(MExp w) { where_ = w; }

  /**
   * Insert a GROUP BY...HAVING clause
   * @param g A GROUP BY...HAVING clause
   */
  public void addGroupBy(MGroupBy g) { groupby_ = g; }

  /**
   * Insert a SET clause (generally UNION, INTERSECT or MINUS)
   * @param s An SQL Expression (generally UNION, INTERSECT or MINUS)
   */
  public void addSet(MExpression s) { setclause_ = s; }

  /**
   * Insert an ORDER BY clause
   * @param v A vector of MOrderBy objects
   */
  public void addOrderBy(Vector v) { orderby_ = v; }

  /**
   * Get the SELECT part of the statement
   * @return A vector of MSelectItem objects
   */
  public Vector getSelect() { return select_; }

  /**
   * Get the FROM part of the statement
   * @return A vector of MFromItem objects
   */
  public Vector getFrom() { return from_; }

  /**
   * Get the WHERE part of the statement
   * @return An SQL Expression or sub-query (MExpression or MQuery object)
   */
  public MExp getWhere() { return where_; }

  /**
   * Get the GROUP BY...HAVING part of the statement
   * @return A GROUP BY...HAVING clause
   */
  public MGroupBy getGroupBy() { return groupby_; }

  /**
   * Get the SET clause (generally UNION, INTERSECT or MINUS)
   * @return An SQL Expression (generally UNION, INTERSECT or MINUS) 
   */
  public MExpression getSet() { return setclause_; }

  /**
   * Get the ORDER BY clause
   * @param v A vector of MOrderBy objects
   */
  public Vector getOrderBy() { return orderby_; }

  /**
   * @return true if it is a SELECT DISTINCT query, false otherwise.
   */
  public boolean isDistinct() { return distinct_; }

  /**
   * @return true if it is a FOR UPDATE query, false otherwise.
   */
  public boolean isForUpdate() { return forupdate_; }


  public String toString() {
    StringBuffer buf = new StringBuffer("select ");
    if(distinct_) buf.append("distinct ");

    //buf.append(select_.toString());
    int i;
    buf.append(select_.elementAt(0).toString());
    for(i=1; i<select_.size(); i++) {
      buf.append(", " + select_.elementAt(i).toString());
    }

    //buf.append(" from " + from_.toString());
    buf.append(" from ");
    buf.append(from_.elementAt(0).toString());
    for(i=1; i<from_.size(); i++) {
      buf.append(", " + from_.elementAt(i).toString());
    }

    if(where_ != null) {
      buf.append(" where " + where_.toString());
    }
    if(groupby_ != null) {
      buf.append(" " + groupby_.toString());
    }
    if(setclause_ != null) {
      buf.append(" " + setclause_.toString());
    }
    if(orderby_ != null) {
      buf.append(" order by ");
      //buf.append(orderby_.toString());
      buf.append(orderby_.elementAt(0).toString());
      for(i=1; i<orderby_.size(); i++) {
        buf.append(", " + orderby_.elementAt(i).toString());
      }
    }
    if(forupdate_) buf.append(" for update");

    return buf.toString();
  }

};

