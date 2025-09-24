package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.util.ITextRegion;

public class ParameterDeclaration extends VariableSymbolDefinition
{
    public ParameterDeclaration(Identifier identifier, DataType type, IScope scope,ITextRegion region,boolean isImmutable)
    {
        super(scope,identifier,type,isImmutable,region);
    }
    
    @Override
    public String toString()
    {
        return "param "+getDataType()+" "+getName();
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
    
    @Override
    protected ParameterDeclaration createCopy()
    {
        return new ParameterDeclaration(  getName() , getDataType(), getScope(), getTextRegion() , isImmutable() );
    }       
    
    public FunctionSymbol getFunctionDeclaration() {
        return (FunctionSymbol) getParent();
    }
}