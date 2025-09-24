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

/**
 * MSelectItem: an item in the SELECT part of an SQL query.
 * (The SELECT part of a query is a Vector of MSelectItem).
 */
public class MSelectItem extends MAliasedName {

  MExp expression_ = null;
  String aggregate_ = null;

  /**
   * Create a new SELECT item
   */
  public MSelectItem() { super(); }

  /**
   * Create a new SELECT item, given its name (for column names and wildcards).
   * @param fullname A string that represents a column name or wildcard
   * (example: a.*).
   */
  public MSelectItem(String fullname) {
    super(fullname, MAliasedName.FORM_COLUMN);
    setAggregate(MUtils.getAggregateCall(fullname)); //PY.Gibello 21 Apr 2001
  }

  /**
   * @return An SQL Expression if this SELECT item is an expression,
   * a MConstant if it is a column name,  null if it is a wildcard
   */
  public MExp getExpression() {
    if(isExpression()) return expression_;
    else if(isWildcard()) return null;
    else {
      return new MConstant(getColumn(), MConstant.COLUMNNAME);
    }
  }

  /**
   * Initialize this SELECT item as an SQL expression (not a column name
   * nor wildcard)
   * Example: SELECT a+b FROM table1; (a+b is an expression)
   */
  public void setExpression(MExp e) {
    expression_ = e;
    strform_ = expression_.toString();
  }

  /**
   * @return true if this item is an SQL expression, false if not.
   * (Example: SELECT a+b, c FROM num; -> a+b is an expression, not c)
   */
  public boolean isExpression() { return (expression_ != null && expression_ instanceof MExpression); }

  /**
   * Initialize an aggregate function on this item
   * (generally SUM, AVG, MAX, MIN)
   * Example: SELECT AVG(age) FROM people; -> The aggregate function is AVG.
   * @param a The name of the aggregate function
   * (a String, like SUM, AVG, MAX, MIN)
   */
  public void setAggregate(String a) { aggregate_ = a; }

  /**
   * If this item is an aggregate function, return the function name.
   * @return The name of an aggregate function (generally SUM, AVG, MAX, MIN),
   * or null if there's no aggregate.
   * Example: SELECT name, AVG(age) FROM people; -> null for the "name" item,
   * and "AVG" for the "AVG(age)" item.
   */
  public String getAggregate() { return aggregate_; }

/** TBD
  public String toString() {
    String agg = getAggregate();
    if(agg == null) agg = "";
    return agg + super.toString();
  }
**/
};

