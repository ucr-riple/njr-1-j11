package com.miguel.sxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Symbol Table, as a hash map of string => hash map.
 * Each entry in the hash map represents a scope, with a string key, and a hash map value;
 * 
 * Thus, the Symbol Table is essentially:
 * {
 * 		"scope"	:	{ .. },
 * 		"another_scope" : { ... },
 * 		...
 * }
 * 
 * Each scope is another hash map, containing entries for each variable in that scope.
 * Thus, a scope is essentially:
 * {
 * 		"variable" : [SXLValue],
 * 		"another_var: [SXLValue],
 * 		...
 * }
 * 
 * @author Miguel Muscat
 */
public class SXLSymbolTable extends SXLScope {

	public static final String MAIN_SCOPE = "#sxlmain";
	
	private SXLScope main;
	private SXLScope currentScope;
	
	public SXLSymbolTable() {
		super(null);
		try {
			this.main = this.createScope(MAIN_SCOPE);
		} catch (Exception e) {
			// Should not throw exception, since at this point, no scopes should exist
			e.printStackTrace();
		}
		this.currentScope = this.main;
	}
	
	public SXLScope getCurrentScope() {
		return this.currentScope;
	}
	
	public void enterScope(String scopeName) throws Exception {
		SXLScope scope = this.currentScope.getScope(scopeName);
		this.currentScope = scope;
	}
	
	public void enterScope(SXLScope scope) {
		this.currentScope = scope;
	}
	
	
	
	@Override
	public void dump(String prefix) {
		System.out.println(prefix + "System Table:");
		super.dump(prefix + "\t");
		System.out.println("Current scope:");
		this.currentScope.dump(prefix + "\t");
	}
	
}
