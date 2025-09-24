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
 import java.util.*;
 
 import java.sql.*;
 import org.sgbd.Mysgbd.*;
 public class CheckRowNum
 {
 
   public CheckRowNum()
   {
   }
 
   public String fixSqlWithRowNum( String sSql) throws Exception
   {
      StringBufferInputStream si = new StringBufferInputStream( sSql );
     MqlParser par = new MqlParser(si);
       MStatement st = par.readStatement();
        MQuery z = ( MQuery ) st;
         MGroupBy MGroupBy = z.getGroupBy();
         Vector vSelect = z.getSelect();
         MExpression  vWhere  = (  MExpression)z.getWhere();
         System.out.println(vWhere + vWhere.getClass().getName());
         for ( int i =0; i < vWhere. nbOperands() ; i++)
         {
          System.out.println(vWhere.getOperand(i));
         }

 MExpression  obj = new MExpression(
                    "<",
                    new MConstant("ROWNUM", MConstant.COLUMNNAME),
                    new MConstant("900", MConstant.NUMBER));
 vWhere.addOperand((MExp)(obj));

         for ( int i =0; i < vWhere. nbOperands() ; i++)
         {
          System.out.println(vWhere.getOperand(i) );
         }
 
       return st.toString();
   }
   public static void main(String[] args)
   {
     CheckRowNum cr = new CheckRowNum();
 
     String sql="SELECT * from NPA where x =1 and y=4;";
     try
     {
    System.out.println( cr.fixSqlWithRowNum(sql));
    }
    catch ( Exception ex)
    {
      System.out.println(ex);
      ex.printStackTrace();
    }
 
   }
   private boolean invokedStandalone = false;
 }

