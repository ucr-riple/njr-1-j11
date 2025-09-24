package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class DoLoopNode extends ASTNodeImpl
{
    public DoLoopNode(ITextRegion tok)
    {
        super(tok);
    }
    
    public DoLoopNode(Token tok)
    {
        super(tok);
    }

    @Override
    public String toString()
    {
        return "DO";
    }
    
    @Override
    protected ASTNode createCopy()
    {
        return new WhileLoopNode( getTextRegion() );
    }     
    
    public TermNode getCondition() {
        return (TermNode) child(1);
    }
    
    public Block getBody() {
        return (Block) child(0); 
    }	
}