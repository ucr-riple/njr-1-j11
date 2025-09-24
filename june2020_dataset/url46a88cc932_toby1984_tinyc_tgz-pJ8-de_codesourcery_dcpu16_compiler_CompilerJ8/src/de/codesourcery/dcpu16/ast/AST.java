package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Scope;

public class AST extends ASTNodeImpl implements IScopeDefinition
{
    private final IScope globalScope;
    
    public AST()
    {
        super();
        globalScope = new Scope( IScope.GLOBAL_SCOPE , this );
    }
    
    public AST(IScope scope)
    {
        this.globalScope = scope;
    }

    public IScope getGlobalScope()
    {
        return globalScope;
    }

    @Override
    public String toString()
    {
        return "AST";
    }

    @Override
    protected ASTNode createCopy()
    {
        return new AST( globalScope );
    }

    @Override
    public IScope getScope()
    {
        return globalScope;
    }
}