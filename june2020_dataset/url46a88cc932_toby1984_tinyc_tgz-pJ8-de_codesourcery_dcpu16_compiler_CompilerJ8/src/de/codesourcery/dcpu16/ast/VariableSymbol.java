package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;


public interface VariableSymbol extends ASTNode , ISymbol 
{
    public boolean isArrayDefinition();
    
    public boolean isGlobalVariable();
    
	public DataType getDataType();
	
    public VariableSymbol getDefinitionSite();
}
