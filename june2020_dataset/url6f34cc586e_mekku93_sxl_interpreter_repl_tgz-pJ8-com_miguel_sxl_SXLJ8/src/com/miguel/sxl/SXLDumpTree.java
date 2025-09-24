package com.miguel.sxl;

public class SXLDumpTree {

	public static void main(String args[]) {
		SXLParser parser = new SXLParser(System.in);
		try {
			SimpleNode parseTree = parser.Start();
			System.out.println("=====================================");
			parseTree.dump("");
		} catch( Exception e ) {
			System.out.println("=====================================");
			System.out.println("An error occurred while parsing:");
			System.out.println();
			System.out.println( e.getMessage() );
		}
	}
	
}
