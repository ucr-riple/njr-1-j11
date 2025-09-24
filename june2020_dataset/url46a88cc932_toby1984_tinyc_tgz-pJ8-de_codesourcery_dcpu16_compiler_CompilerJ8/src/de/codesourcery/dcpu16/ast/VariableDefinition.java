package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.util.ITextRegion;

public class VariableDefinition extends VariableSymbolDefinition
{
    private final int elementCount;

    public VariableDefinition(IScope scope,Identifier name,DataType dataType,int elementCount , ITextRegion tok,boolean isImmutable)
    {
        super(scope,name,dataType,isImmutable,tok);
        this.elementCount = elementCount;
    }
    
    public int getElementCount() {
		return elementCount;
	}
    
    @Override
    public Identifier getUniqueIdentifier()
    {
        return new Identifier( getScope().getUniqueIdentifier().getStringValue()+"_"+getName().getStringValue() );
    }
    
    @Override
    public VariableSymbol getDefinitionSite()
    {
        return this;
    }    
    
    public boolean hasArrayInitializer() {
    	return hasChildren() && child(0) instanceof ArrayInitializer;
    }
    
    public ArrayInitializer getArrayInitializer() {
    	return (ArrayInitializer) child(0);
    }
    
    @Override
    public String toString()
    {
        return getDataType()+" "+getName()+" = ";
    }
    
    public boolean hasInitializer() {
        return hasChildren();
    }
    
    public TermNode getInitializer() {
        return (TermNode) child(0);
    }
    
    @Override
    protected VariableDefinition createCopy()
    {
        return new VariableDefinition( getScope(), getName() ,getDataType(),elementCount ,getTextRegion() , isImmutable());
    }    
}
