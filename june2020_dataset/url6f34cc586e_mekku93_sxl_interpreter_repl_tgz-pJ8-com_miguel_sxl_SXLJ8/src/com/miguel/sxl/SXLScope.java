package com.miguel.sxl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Represents a single symbol table scope.
 * 
 * @author Miguel Muscat
 */
public class SXLScope {

	private SXLScope parent;
	private Map<String, SXLValue> symbols;
	private Map<String, SXLScope> scopes;
	
	/**
	 * Constructs a new SXLScope
	 * 
	 * @param parent The parent SXLScope
	 */
	public SXLScope(SXLScope parent) {
		this.parent = parent;
		this.symbols = new HashMap<String, SXLValue>(0);
		this.scopes = new HashMap<String, SXLScope>(0);
	}
	
	
	/**
	 * Returns the parent scope
	 */
	public SXLScope getParent() {
		return this.parent;
	}
	
	
	/**
	 * Symbol lookup. Recurses to parent if symbol not found.
	 * 
	 * @param symName The identifier of the symbol
	 */
	public SXLValue getSymbol(String symName) throws SymbolNotFoundException {
		// Look for symbol in this scope 
		if ( this.symbols.containsKey(symName) ) {
			return this.symbols.get(symName);
		}
		// If not found: Look for symbol in parent
		else if ( this.parent != null ) {
			return this.parent.getSymbol(symName);
		}
		// If has no parent, return null
		else throw new SymbolNotFoundException(symName);
	}
	
	
	
	/**
	 * Symbol lookup. Recurses to parent if symbol not found.
	 * 
	 * @param symName The identifier of the symbol
	 * @throws SymbolNotFoundException If the symbol was not found
	 * @throws DuplicateSymbolException if the symbol already exists
	 */
	public void setSymbol(String symName, SXLValue value) throws SymbolNotFoundException {
		// Look for symbol in this scope 
		if ( this.symbols.containsKey(symName) ) {
			this.symbols.put(symName, value);
		}
		// If not found: Look for symbol in parent
		else if ( this.parent != null ) {
			this.parent.setSymbol(symName, value);
		}
		// If has no parent, return null
		else throw new SymbolNotFoundException(symName);
	}
	
	
	
	
	/**
	 * Checks if the symbol exists. Recurses into the parent scope.
	 * 
	 * @param symName The name of the symbol
	 * @return True if the symbol exists, false otherwise.
	 */
	public boolean symbolExists(String symName) {
		// Look for symbol in this scope 
		if ( this.symbols.containsKey(symName) ) {
			return true;
		}
		// If not found: Look for symbol in parent
		else if ( this.parent != null ) {
			return this.parent.symbolExists(symName);
		}
		// If has no parent, return null
		else return false;
	}
	
	
	
	/**
	 * Puts a symbol in the scope.
	 * 
	 * @param symName The name of symbol
	 * @param value The SXLValue of the symbol
	 * @throws Exception Throws an exception if the symbol already exists
	 */
	public void putSymbol(String symName, SXLValue value) throws DuplicateSymbolException { 
		// Check if symbol exists 
		if ( !this.symbolExists(symName) ) {
			this.symbols.put(symName, value);
		}
		// If exists, throw exception
		else throw new DuplicateSymbolException(symName); 
	}
	
	
	
	/**
	 * Child scope lookup.
	 * 
	 * @param scopeName The name of the scope
	 * @return The SXLScope found
	 * @throws Exception Throws an exception if the scope with the give name does not exist
	 */
	public SXLScope getScope(String scopeName) throws Exception {
		// Look for scope in this scope 
		if ( this.scopes.containsKey(scopeName) ) {
			return this.scopes.get(scopeName);
		}
		// If not found throw exception
		else throw new Exception(scopeName + " does not exist!");
	}
	
	
	/**
	 * Deletes a child scope.
	 * 
	 * @param scopeName The name of the scope
	 * @throws Exception Throws an exception if the scope with the give name does not exist
	 */
	public void deleteScope(String scopeName) throws Exception {
		// Look for scope in this scope 
		if ( this.scopes.containsKey(scopeName) ) {
			this.scopes.remove(scopeName);
		}
		// If not found throw exception
		else throw new Exception(scopeName + " does not exist!");
	}
	
	
	/**
	 * Creates a child scope
	 * 
	 * @param scopeName The name of the scope
	 * @throws Exception Throws an exception if a scope with the give name already exists
	 */
	public SXLScope createScope(String scopeName) throws Exception {
		// Look for scope in this scope 
		if ( !this.scopes.containsKey(scopeName) ) {
			SXLScope newScope = new SXLScope(this);
			this.scopes.put(scopeName, newScope);
			return newScope;
		}
		// If exists throw exception
		else throw new Exception(scopeName + " is already defined!");
	}
	
	
	
	
	public void dump(String prefix) {
		System.out.println(prefix + "Scopes : {");
		
		for( Entry<String, SXLScope> entry : this.scopes.entrySet() ) {
			System.out.println(prefix + "\t'" + entry.getKey() + "' : {");
			entry.getValue().dump(prefix + "\t\t");
			System.out.println(prefix + "\t}");
		}
		System.out.println(prefix + "}" );
		System.out.println(prefix + "Symbols : {");
		for( Entry<String, SXLValue> entry : this.symbols.entrySet() ) {
			System.out.println(prefix + "\t'" + entry.getKey() + "' : " + entry.getValue().getType());
		}
		System.out.println(prefix + "}");
		System.out.println();
	}
}
