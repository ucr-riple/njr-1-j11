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
 * MFromItem: an SQL FROM clause (example: the FROM part of a SELECT...FROM).
 */
public class MFromItem extends MAliasedName {

  /**
   * Create a new FROM clause.
   * See the MAliasedName constructor for more information.
   */
  public MFromItem() { super(); }

  /**
   * Create a new FROM clause on a given table.
   * See the MAliasedName constructor for more information.
   * @param fullname the table name.
   */
  public MFromItem(String fullname) {
    super(fullname, MAliasedName.FORM_TABLE);
  }

};

