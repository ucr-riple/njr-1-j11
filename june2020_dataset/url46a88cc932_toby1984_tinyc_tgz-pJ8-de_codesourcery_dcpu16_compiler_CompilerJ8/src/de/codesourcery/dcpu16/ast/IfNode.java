package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class IfNode extends ASTNodeImpl
{
    public IfNode(ITextRegion tok)
    {
        super(tok);
    }
    
    public IfNode(Token tok)
    {
        super(tok);
    }
    
    public TermNode getCondition() {
        return (TermNode) child(0);
    }
    
    public Block getBody() {
        return (Block) child(1);
    }    

    public boolean hasElseBlock() {
        return getChildCount() == 3;
    }
    
    public Block getElseBlock() {
        return (Block) child(2);
    }
    
    @Override
    public String toString()
    {
        return "IF";
    }
    
    @Override
    protected IfNode createCopy()
    {
        return new IfNode(getTextRegion());
    }    
}