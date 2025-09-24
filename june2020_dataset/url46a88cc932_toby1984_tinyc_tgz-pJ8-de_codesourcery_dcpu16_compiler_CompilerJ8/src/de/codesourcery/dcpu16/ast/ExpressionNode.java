package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;


public class ExpressionNode extends TermNode
{
    public ExpressionNode() {
    }

    public boolean isAssignment() 
    {
        if ( getChildCount() >= 1 &&
                child(0) instanceof OperatorNode &&
                ((OperatorNode) child(0)).isAssignment() ) 
        {
            return true;
        }
        return false;
    }

    @Override
    protected ASTNode createCopy()
    {
        return new ExpressionNode();
    }

    @Override
    public TermNode reduce()
    {
        return hasChildren() ? ((TermNode) child(0)).reduce() : this;
    }

    @Override
    public boolean isLiteralValue()
    {
        return false;
    }

    @Override
    protected DataType internalGetDataType()
    {
        TermNode reduced = reduce();
        if ( reduced == null || reduced == this ) {
            return DataType.UNKNOWN;
        }
        return reduced.getDataType();
    }
}