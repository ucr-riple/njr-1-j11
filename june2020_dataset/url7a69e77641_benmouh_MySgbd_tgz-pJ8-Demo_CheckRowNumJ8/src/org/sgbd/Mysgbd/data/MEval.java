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

package org.sgbd.Mysgbd.data;

import java.sql.SQLException;
import java.util.Vector;
import java.io.*;

import org.sgbd.Mysgbd.*;

/**
 * Evaluate SQL expressions
 */
public class MEval {

  /**
   * Evaluate a boolean expression to true or false (for example, SQL WHERE
   * clauses are boolean expressions)
   * @param tuple The tuple on which to evaluate the expression
   * @param exp The expression to evaluate
   * @return true if the expression evaluate to true for this tuple,
   * false if not.
   */
  public boolean eval(MTuple tuple, MExp exp) throws SQLException {

    if(tuple == null || exp == null)  {
      throw new SQLException("MEval.eval(): null argument or operator");
    }
    if(! (exp instanceof MExpression))
      throw new SQLException("MEval.eval(): only expressions are supported");

    MExpression pred = (MExpression)exp;
    String op = pred.getOperator();

    if(op.equals("AND")) {
      boolean and = true;
      for(int i = 0; i<pred.nbOperands(); i++) {
        and &= eval(tuple, pred.getOperand(i));
      }
      return and;
    } else if(op.equals("OR")) {
      boolean or = false;
      for(int i = 0; i<pred.nbOperands(); i++) {
        or |= eval(tuple, pred.getOperand(i));
      }
      return or;
    } else if(op.equals("NOT")) {
      return ! eval(tuple, pred.getOperand(0));

    } else if(op.equals("=")) {
      return evalCmp(tuple, pred.getOperands()) == 0;
    } else if(op.equals("!=")) {
      return evalCmp(tuple, pred.getOperands()) != 0;
    } else if(op.equals("<>")) {
      return evalCmp(tuple, pred.getOperands()) != 0;
    } else if(op.equals("#")) {
      throw new SQLException("MEval.eval(): Operator # not supported");
    } else if(op.equals(">")) {
      return evalCmp(tuple, pred.getOperands()) > 0;
    } else if(op.equals(">=")) {
      return evalCmp(tuple, pred.getOperands()) >= 0;
    } else if(op.equals("<")) {
      return evalCmp(tuple, pred.getOperands()) < 0;
    } else if(op.equals("<=")) {
      return evalCmp(tuple, pred.getOperands()) <= 0;

    } else if(op.equals("BETWEEN") || op.equals("NOT BETWEEN")) {

      // Between: borders included
      MExpression newexp = new MExpression("AND", 
        new MExpression(">=", pred.getOperand(0), pred.getOperand(1)),
        new MExpression("<=", pred.getOperand(0), pred.getOperand(2)));

      if(op.equals("NOT BETWEEN"))
        return ! eval(tuple, newexp);
      else
        return eval(tuple, newexp);

    } else if(op.equals("LIKE") || op.equals("NOT LIKE")) {
      boolean like = evalLike(tuple, pred.getOperands());
      return op.equals("LIKE") ? like : !like;

    } else if(op.equals("IN") || op.equals("NOT IN")) {

      MExpression newexp = new MExpression("OR");

      for(int i = 1; i < pred.nbOperands(); i++) {
        newexp.addOperand(new MExpression("=",
          pred.getOperand(0), pred.getOperand(i)));
      }

      if(op.equals("NOT IN"))
        return ! eval(tuple, newexp);
      else
        return eval(tuple, newexp);

    } else if(op.equals("IS NULL")) {

      if(pred.nbOperands() <= 0 || pred.getOperand(0) == null) return true;
      MExp x = pred.getOperand(0);
      if(x instanceof MConstant) {
        return (((MConstant)x).getType() == MConstant.NULL);
      } else {
        throw new SQLException("MEval.eval(): can't eval IS (NOT) NULL");
      }

    } else if(op.equals("IS NOT NULL")) {

      MExpression x = new MExpression("IS NULL");
      x.setOperands(pred.getOperands());
      return ! eval(tuple, x);

    } else {
      throw new SQLException("MEval.eval(): Unknown operator " + op);
    }

  }

  double evalCmp(MTuple tuple, Vector operands) throws SQLException {

    if(operands.size() < 2) {
      throw new SQLException(
        "MEval.evalCmp(): Trying to compare less than two values");
    }
    if(operands.size() > 2) {
      throw new SQLException(
        "MEval.evalCmp(): Trying to compare more than two values");
    }

    Object o1 = null, o2 = null, obj;

    o1 = evalExpValue(tuple, (MExp)operands.elementAt(0));
    o2 = evalExpValue(tuple, (MExp)operands.elementAt(1));

    if(o1 instanceof String || o2 instanceof String) {
      return(o1.equals(o2) ? 0 : -1);
    }

    if(o1 instanceof Number && o2 instanceof Number) {
      return ((Number)o1).doubleValue() - ((Number)o2).doubleValue();
    } else {
      throw new SQLException("MEval.evalCmp(): can't compare (" + o1.toString()
        + ") with (" + o2.toString() + ")");
    }
  }


  // -------------------------------------------------------------------------
  /**
   * evalLike
   * evaluates the LIKE operand
   * 
   * @param tuple the tuple to evaluate
   * @param operands the operands
   * @return true-> the expression matches
   * @throws SQLException 
   */
  private boolean evalLike(MTuple tuple, Vector operands) throws SQLException
  {
    if(operands.size() < 2) {
      throw new SQLException(
        "MEval.evalCmp(): Trying to compare less than two values");
    }
    if(operands.size() > 2) {
      throw new SQLException(
        "MEval.evalCmp(): Trying to compare more than two values");
    }

    Object o1 = evalExpValue(tuple, (MExp)operands.elementAt(0));
    Object o2 = evalExpValue(tuple, (MExp)operands.elementAt(1));

    if ( (o1 instanceof String) && (o2 instanceof String) ) {
      String s1 = (String)o1;
      String s2 = (String)o2;
      if ( s2.startsWith("%") ) {
        return s1.endsWith(s2.substring(1));
      } else if ( s2.endsWith("%") ) {
        return s1.startsWith(s2.substring(0,s2.length()-1));
      } else {
        return s1.equalsIgnoreCase(s2);
      }
    }
    else {
      throw new SQLException("MEval.evalLike(): LIKE can only compare strings");
    }

  }

  double evalNumericExp(MTuple tuple, MExpression exp)
  throws SQLException {

    if(tuple == null || exp == null || exp.getOperator() == null)  {
      throw new SQLException("MEval.eval(): null argument or operator");
    }

    String op = exp.getOperator();

    Object o1 = evalExpValue(tuple, (MExp)exp.getOperand(0));
    if(! (o1 instanceof Double))
      throw new SQLException("MEval.evalNumericExp(): expression not numeric");
    Double dobj = (Double)o1;

    if(op.equals("+")) {

      double val = dobj.doubleValue();
      for(int i = 1; i < exp.nbOperands(); i++) {
        Object obj = evalExpValue(tuple, (MExp)exp.getOperand(i));
        val += ((Number)obj).doubleValue();
      }
      return val;

    } else if(op.equals("-")) {

      double val = dobj.doubleValue();
      if(exp.nbOperands() == 1) return -val;
      for(int i = 1; i < exp.nbOperands(); i++) {
        Object obj = evalExpValue(tuple, (MExp)exp.getOperand(i));
        val -= ((Number)obj).doubleValue();
      }
      return val;

    } else if(op.equals("*")) {

      double val = dobj.doubleValue();
      for(int i = 1; i < exp.nbOperands(); i++) {
        Object obj = evalExpValue(tuple, (MExp)exp.getOperand(i));
        val *= ((Number)obj).doubleValue();
      }
      return val;

    } else if(op.equals("/")) {

      double val = dobj.doubleValue();
      for(int i = 1; i < exp.nbOperands(); i++) {
        Object obj = evalExpValue(tuple, (MExp)exp.getOperand(i));
        val /= ((Number)obj).doubleValue();
      }
      return val;

    } else if(op.equals("**")) {

      double val = dobj.doubleValue();
      for(int i = 1; i < exp.nbOperands(); i++) {
        Object obj = evalExpValue(tuple, (MExp)exp.getOperand(i));
        val = Math.pow(val, ((Number)obj).doubleValue());
      }
      return val;

    } else {
      throw new SQLException("MEval.evalNumericExp(): Unknown operator " + op);
    }
  }


  /**
   * Evaluate a numeric or string expression (example: a+1)
   * @param tuple The tuple on which to evaluate the expression
   * @param exp The expression to evaluate
   * @return The expression's value
   */
  public Object evalExpValue(MTuple tuple, MExp exp) throws SQLException {

    Object o2 = null;

    if(exp instanceof MConstant) {

      MConstant c = (MConstant)exp;

      switch(c.getType()) {

        case MConstant.COLUMNNAME:

          Object o1 = tuple.getAttValue(c.getValue());
          if(o1 == null)
            throw new SQLException("MEval.evalExpValue(): unknown column "
             + c.getValue());
          try {
            o2 = new Double(o1.toString());
          } catch(NumberFormatException e) {
            o2 = o1;
          }
          break;

        case MConstant.NUMBER:
          o2 = new Double(c.getValue());
          break;

        case MConstant.STRING:
        default:
          o2 = c.getValue();
          break;
      }
    } else if(exp instanceof MExpression) {
      o2 = new Double(evalNumericExp(tuple, (MExpression)exp));
    }
    return o2;
  }


  
};

