package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class StatementNode extends ASTNodeImpl
{
    public StatementNode()
    {
    }
    
    public StatementNode(ASTNode child)
    {
        addChild(child);
    }    
    
    @Override
    public String toString()
    {
        return "Statement";
    }

    public StatementNode(ITextRegion textRegion)
    {
        super(textRegion);
    }

    public StatementNode(Token tok)
    {
        super(tok);
    }

    @Override
    protected ASTNode createCopy()
    {
        return new StatementNode(getTextRegion());
    }

}
