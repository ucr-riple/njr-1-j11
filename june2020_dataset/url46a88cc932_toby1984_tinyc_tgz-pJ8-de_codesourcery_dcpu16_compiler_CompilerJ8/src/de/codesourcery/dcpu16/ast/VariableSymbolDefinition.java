package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.util.ITextRegion;


public abstract class VariableSymbolDefinition extends ASTNodeImpl implements VariableSymbol
{
    private final IScope scope;
    private final Identifier name;
    private final DataType dataType;
    private final boolean isImmutable;
    
	protected VariableSymbolDefinition(IScope scope, Identifier name, DataType dataType,boolean isImmutable,ITextRegion tok) 
	{
		super(tok);
		this.scope = scope;
		this.name = name;
		this.dataType = dataType;
		this.isImmutable = isImmutable;
	}

    public final boolean isArrayDefinition() {
        return dataType.isArray();
    }
    
    public final boolean isGlobalVariable() {
        return getScope().getIdentifier().equals( IScope.GLOBAL_SCOPE );
    }    
    
	public final IScope getScope() {
		return scope;
	}

	public final Identifier getName() {
		return name;
	}

	public final DataType getDataType() {
		return dataType;
	}

	public final boolean isImmutable() {
		return isImmutable;
	}   	
}
