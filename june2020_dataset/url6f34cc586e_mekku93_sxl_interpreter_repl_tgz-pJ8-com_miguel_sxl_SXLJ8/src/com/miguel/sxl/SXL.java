package com.miguel.sxl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Last done: function declaration
 * SXLValue interface may not be complete. Needs to handle unary operations.
 * 
 * @author Miguel Muscat
 */
public class SXL {

	public static SXL runtime = null;
	
	public Scanner scanner;
	public SXLParser parser;
	public SXLVistor visitor;
	public SXLSymbolTable symtable;
	
	public SXL(InputStream input) {
		parser = new SXLParser(input);
		visitor = new SXLVistor();
		symtable = new SXLSymbolTable();
		scanner = new Scanner(System.in).useDelimiter(Pattern.compile("[\\r\\n;]+"));
	}
	
	public void exit(int code) {
		scanner.close();
		System.exit(code);
	}
	
	public static void main(String[] args) {
		boolean dumpTree = false;
		
		// Use System.in as input
		InputStream input = System.in;
		
		// Loop through args
		for( String s : args ) {
			// Check for -dumptree option
			if ( s.equals("-dumptree") ) {
				dumpTree = true;
			} else {
				// Check for file name
				try {
					input = new FileInputStream( new File( s ) );
				} catch(FileNotFoundException e) {
					System.err.println(e.getMessage());
					return;
				}
			}
		}
		
		
		try {
			SXL.runtime = new SXL(input);
			SimpleNode parseTree = SXL.runtime.parser.Start();
			if ( dumpTree ) parseTree.dump("");
			parseTree.jjtAccept(SXL.runtime.visitor, null);
		} catch( Exception e ) {
			System.err.println("Error: " + e.getMessage() );
		} finally {
			SXL.runtime.exit(0);			
		}
	}
	
}
