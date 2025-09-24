package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.MethodSignature;
import de.codesourcery.dcpu16.util.ITextRegion;

public abstract class FunctionSymbol extends ASTNodeImpl implements IScopeDefinition , ISymbol {

	private MethodSignature signature;
	private IScope scope;

	protected FunctionSymbol(ITextRegion region) 
	{
		super( region );
	}

	protected final void setSignature(MethodSignature signature) {
		if ( this.signature != null ) {
			throw new IllegalStateException("Signature already set on "+this);
		}		
		this.signature = signature;
	}	

	public final void setScope(IScope scope) 
	{
		if ( this.scope != null ) {
			throw new IllegalStateException("Scope already set on "+this);
		}
		this.scope = scope;
	}

    public final boolean isVoidFunction() {
        return getReturnType().equals( DataType.VOID );
    }
    
	public final MethodSignature getSignature()
	{
		if ( this.signature == null ) {
			throw new IllegalStateException("Signature not set on "+this);
		}			
		return signature;
	}    
	
    public final boolean matches(FunctionSymbol other) 
    {
        return getSignature().equals( other.getSignature() );
    }    	
    
    public final int getParameterCount() {
    	return signature.getParameterCount();
    }    

	public String getSignatureAsString() 
	{
		return getSignature().toString();
	}    

    public final DataType getReturnType()
    {
        return signature.getReturnType();
    }
    
    public final Identifier getName()
    {
        return getSignature().getName();
    }
    
	public final IScope getScope()
	{
		if ( this.scope == null ) {
			throw new IllegalStateException("Scope not set on "+this);
		}
		return scope;
	}	

    public int getParameterIndex(Identifier name)
    {
        return getSignature().getParameterIndex( name );
    }	
}
