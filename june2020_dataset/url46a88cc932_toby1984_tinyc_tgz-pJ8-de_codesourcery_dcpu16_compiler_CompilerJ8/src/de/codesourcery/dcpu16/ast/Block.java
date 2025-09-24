package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.IScope;

public class Block extends ASTNodeImpl implements IScopeDefinition
{
	private IScope scope;
	
	public Block() {
	}
	
	private Block(IScope scope) {
		this.scope = scope;
	}
	
    @Override
    public String toString()
    {
        return "BLOCK";
    }

    public boolean isEmpty() {
        return hasNoChildren();
    }
    
    @Override
    protected ASTNode createCopy()
    {
        return new Block( this.scope );
    }

    @Override
    public IScope getScope()
    {
        return scope;
    }

    public void setScope(IScope scope) {
		this.scope = scope;
	}
    
    public boolean isNotEmpty()
    {
        return ! isEmpty();
    }
}
