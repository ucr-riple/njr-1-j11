package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class WhileLoopNode extends ASTNodeImpl
{
    public WhileLoopNode(ITextRegion tok)
    {
        super(tok);
    }
    
    public WhileLoopNode(Token tok)
    {
        super(tok);
    }

    @Override
    public String toString()
    {
        return "WHILE";
    }
    
    @Override
    protected ASTNode createCopy()
    {
        return new WhileLoopNode( getTextRegion() );
    }     
    
    public TermNode getCondition() {
        return (TermNode) child(0);
    }
    
    public boolean hasBody() {
        return getChildCount() > 1;
    }
    
    public Block getBody() {
        return (Block) child(1); 
    }
}