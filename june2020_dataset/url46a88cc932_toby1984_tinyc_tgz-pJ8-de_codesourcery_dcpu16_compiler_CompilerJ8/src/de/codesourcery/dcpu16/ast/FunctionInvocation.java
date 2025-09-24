package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class FunctionInvocation extends TermNode
{
    private final Identifier identifier;

    public FunctionInvocation(Identifier identifier,ITextRegion tok)
    {
        super(tok);
        this.identifier = identifier;
    }
    
    public FunctionInvocation(Identifier identifier,Token tok)
    {
        this(identifier,tok.getTextRegion());
    }
    
    public Identifier getFunctionName()
    {
        return identifier;
    }
    
    public int getArgumentCount() {
        return getChildCount();
    }
    
    @Override
    public String toString() {
    	return "invoke "+identifier+"()";
    }

    @Override
    protected ASTNode createCopy()
    {
        return new FunctionInvocation( identifier , getTextRegion() );
    }

	@Override
	public TermNode reduce() {
		return this;
	}

	@Override
	public boolean isLiteralValue() {
		return false;
	}

	@Override
	protected DataType internalGetDataType() 
	{
		ASTNode definitionSite = findScope().getDefinitionSite( identifier , true );
		if ( definitionSite instanceof FunctionDefinitionNode ) {
			return ((FunctionDefinitionNode) definitionSite).getReturnType();
		}
		if ( definitionSite instanceof FunctionDeclarationNode) {
			return ((FunctionDeclarationNode) definitionSite).getReturnType();
		}		
		throw new RuntimeException("Internal error, failed to find function "+identifier+" in scope "+findScope());
	}
}
