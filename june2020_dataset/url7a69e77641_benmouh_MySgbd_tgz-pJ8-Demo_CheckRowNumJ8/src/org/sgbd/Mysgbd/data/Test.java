package org.sgbd.Mysgbd.data;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;

import org.sgbd.Mysgbd.MExp;
import org.sgbd.Mysgbd.MqlParser;

public class Test {

	// test
	  public static void main(String args[]) {
	    try {
	    	
	      System.out.println("trying to access data file ...");	
	      BufferedReader db = new BufferedReader(new FileReader("test.db"));
	      String tpl = db.readLine();
	      MTuple t = new MTuple(tpl);

	      MqlParser parser = new MqlParser();
	      MEval evaluator = new MEval();

	      while((tpl = db.readLine()) != null) {
	        t.setRow(tpl);
	        BufferedReader sql = new BufferedReader(new FileReader("test.sql")); 
	        String query;
	        while((query = sql.readLine()) != null) {
	          parser.initParser(new ByteArrayInputStream(query.getBytes()));
	          MExp exp = parser.readExpression();
	          System.out.print(tpl + ", " + query + ", ");
	          System.out.println(evaluator.eval(t, exp));
	        }
	        sql.close();
	      }
	      db.close();
	    } catch(Exception e) {
	      e.printStackTrace();
	    }
	  }
}
