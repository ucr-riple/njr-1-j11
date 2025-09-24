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
 * MTransactStmt: an SQL statement that concerns database transactions
 * (example: COMMIT, ROLLBACK, SET TRANSACTION)
 */
public class MTransactStmt implements MStatement {

  String statement_;
  String comment_ = null;
  public boolean readOnly_ = false;

  public MTransactStmt(String st) { statement_ = new String(st); }

  public void setComment(String c) { comment_ = new String(c); }
  public String getComment() { return comment_; }
  public boolean isReadOnly() { return readOnly_; }
};

