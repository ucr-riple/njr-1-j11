package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.Operator;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public abstract class TermNode extends ASTNodeImpl
{
    protected DataType dataType;
    
    protected TermNode()
    {
        super();
    }

    protected TermNode(ITextRegion textRegion)
    {
        super(textRegion);
    }

    protected TermNode(Token tok)
    {
        super(tok);
    }
    
    /**
     * Tries to reduce this term node.
     * 
     * @return either NULL (indicating that reducing this term failed) , a reduced term or this node unaltered if the node cannot be reduced any further
     */
    public abstract TermNode reduce();
    
    public abstract boolean isLiteralValue();
    
    public final DataType getDataType() {
        if ( dataType != null ) {
            return dataType;
        }
        return internalGetDataType();
    }
    
    protected abstract DataType internalGetDataType();
    
    public void setDataType(DataType type) 
    {
        this.dataType = type;
    }
    
    public final boolean isLValue() {
        
        /*
         * Any of the following C expressions can be l-value expressions:
         * 
         * TODO: - A member-selection expression (–> or .)
         * TODO: - A const object (a nonmodifiable l-value)                         
         */
        
        // An identifier of integral, floating, pointer, structure, or union type
        if ( this instanceof VariableReferenceNode) {
            return true;
        }
        if ( this instanceof OperatorNode ) 
        {
            Operator op = ((OperatorNode) this).getOperator();
            if ( op == Operator.ARRAY_SUBSCRIPT) 
            {
                // A subscript ([ ]) expression that does not evaluate to an array
                TermNode target = (TermNode) child(0);
                return ! target.getDataType().isArray();
            } 
            if ( op == Operator.DEREFERENCE ) {
                // A unary-indirection (*) expression that does not refer to an array
                TermNode target = (TermNode) child(0);
                return ! target.getDataType().isArray();
            }
        }
        if ( this instanceof ExpressionNode) 
        {
            //  An l-value expression in parentheses
            return ((TermNode) child(0)).isLValue();
        }
        return false;
    }
}
