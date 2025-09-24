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
 * MConstant: a representation of SQL constants
 */
public class MConstant implements MExp {

  
  public static final int UNKNOWN = -1;
  public static final int COLUMNNAME = 0;
  public static final int NULL = 1;
  public static final int NUMBER = 2;
  public static final int STRING = 3;

  int type_ = MConstant.UNKNOWN;
  String val_ = null;

  /**
   * Create a new constant, given its name and type.
   */
  public MConstant(String v, int typ) {
    val_ = new String(v);
    type_ = typ;
  }

  /*
   * @return the constant value
   */
  public String getValue() { return val_; }

  /*
   * @return the constant type
   */
  public int getType() { return type_; }

  public String toString() {
    if(type_ == STRING) return '\'' + val_ + '\'';
    else return val_;
  }
};

