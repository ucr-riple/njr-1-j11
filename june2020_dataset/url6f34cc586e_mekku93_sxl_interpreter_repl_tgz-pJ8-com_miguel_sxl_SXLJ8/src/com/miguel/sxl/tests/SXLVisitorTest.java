package com.miguel.sxl.tests;

import com.miguel.sxl.*;

public class SXLVisitorTest {

	public static void main(String args[]) {
		SXLParser parser = new SXLParser(System.in);
		try {
			SimpleNode parseTree = parser.Start();
			System.out.println("== Abstract Syntax Tree ==============================\n");
			System.out.println();
			parseTree.dump("");
			System.out.println("== Visiting Nodes ====================================\n");
			SXLVistor visitor = new SXLVistor();
			parseTree.jjtAccept(visitor, null);
			
			System.out.println("======================================================\n");
			System.out.println("Done!");
		} catch( Exception e ) {
			System.out.println("======================================================\n");
			System.out.println("An error occurred while parsing:");
			System.out.println();
			System.out.println( e.getMessage() );
		}
	}
	
}
