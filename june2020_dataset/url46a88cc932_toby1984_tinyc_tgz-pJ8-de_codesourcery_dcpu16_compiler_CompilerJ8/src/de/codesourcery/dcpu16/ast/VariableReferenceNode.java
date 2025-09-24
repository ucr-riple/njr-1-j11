package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.util.ITextRegion;

public class VariableReferenceNode extends TermNode implements VariableSymbol
{
    public final Identifier identifier;
    public final IScope scope;

    public VariableReferenceNode(Identifier identifier,IScope scope,ITextRegion region)
    {
        super(region);
        if ( scope == null ) {
			throw new IllegalArgumentException("scope must not be null");
		}
        this.scope = scope;
        this.identifier = identifier;
    }
    
    public IScope getScope()
    {
        return scope;
    }
    
    public Identifier getName()
    {
        return identifier;
    }
    
    @Override
    public String toString()
    {
        return identifier.toString();
    }
    
    @Override
    protected VariableReferenceNode createCopy()
    {
        return new VariableReferenceNode( this.identifier , this.scope  , getTextRegion() );
    }

    @Override
    public TermNode reduce()
    {
        return this;
    }

    @Override
    public boolean isLiteralValue()
    {
        return false;
    }
    
    @Override
    public Identifier getUniqueIdentifier()
    {
        return new Identifier( getDefinitionSite().getScope().getUniqueIdentifier().getStringValue()+"_"+getName().getStringValue() );
    }
    
    public VariableSymbol getDefinitionSite() 
    {
    	IScope scope = findScope();
    	if ( scope == null ) {
    		throw new RuntimeException("Internal error, "+this+" ("+this.getClass().getSimpleName()+") has no scope ??");
    	}
        ASTNode definitionSite = scope.getDefinitionSite( identifier , true );
        if ( definitionSite == null ) {
            throw new RuntimeException("Failed to find definition site of "+this+" in scope "+getScope());
        }
        
        if ( definitionSite instanceof ParameterDeclaration || definitionSite instanceof VariableDefinition) 
        {
            return (VariableSymbol) definitionSite;
        }
        throw new RuntimeException("Internal error , unhandled definition site "+definitionSite+" for variable reference "+this);
    }

    @Override
    protected DataType internalGetDataType()
    {
        ASTNode definitionSite = findScope().getDefinitionSite( identifier , true );
        if ( definitionSite == null ) {
            return DataType.UNKNOWN;
        }
        if ( definitionSite instanceof ParameterDeclaration ) 
        {
            return ((ParameterDeclaration) definitionSite).getDataType();
        }
        return ((VariableDefinition) definitionSite).getDataType();
    }

	@Override
	public boolean isArrayDefinition() {
		return getDataType().isArray();
	}

	@Override
	public boolean isGlobalVariable() {
		return findScope().getIdentifier().equals( IScope.GLOBAL_SCOPE );
	}      	
}
