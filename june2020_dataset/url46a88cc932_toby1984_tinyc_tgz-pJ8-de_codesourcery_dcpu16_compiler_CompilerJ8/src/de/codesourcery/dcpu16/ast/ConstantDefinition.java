package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.util.ITextRegion;

public class ConstantDefinition extends ASTNodeImpl
{
    private final Identifier identifier;
    private final DataType type;
    private final IScope scope;
    
    public ConstantDefinition(Identifier identifier, DataType type, IScope scope,ITextRegion region)
    {
        super(region);
        this.identifier = identifier;
        this.type = type;
        this.scope = scope;
    }
    
    public Identifier getParameterName() {
        return identifier;
    }
    
    public IScope getScope()
    {
        return scope;
    }
    
    public DataType getDataType() {
        return type;
    }
    
    @Override
    public String toString()
    {
        return "param "+type+" "+identifier;
    }
    
    @Override
    protected ConstantDefinition createCopy()
    {
        return new ConstantDefinition(  identifier , type , scope , getTextRegion() );
    }       
}