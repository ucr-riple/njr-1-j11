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
 * MLockTable: an SQL LOCK TABLE statement
 */
public class MLockTable implements MStatement {

  public boolean nowait_ = false;
  String lockMode_ = null;
  Vector tables_ = null;

  public MLockTable() {}

  public void addTables(Vector v) { tables_ = v; }
  public Vector getTables() { return tables_; } 
  public void setLockMode(String lc) { lockMode_ = new String(lc); }
  public String getLockMode() { return lockMode_; }
  public boolean isNowait() { return nowait_; }
};

