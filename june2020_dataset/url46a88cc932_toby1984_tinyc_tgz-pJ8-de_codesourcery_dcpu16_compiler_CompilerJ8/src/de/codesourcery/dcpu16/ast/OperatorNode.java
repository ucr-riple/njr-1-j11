package de.codesourcery.dcpu16.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.Operator;
import de.codesourcery.dcpu16.parser.ASTUtils;
import de.codesourcery.dcpu16.parser.ASTUtils.IAdvancedVisitor;
import de.codesourcery.dcpu16.parser.ASTUtils.IIterationContext;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class OperatorNode extends TermNode
{ 
    private Operator operator;

    public OperatorNode(Operator operator, ITextRegion token)
    {
        super(token);
        this.operator = operator;
    }
    
    public OperatorNode(Operator operator, Token token)
    {
        this(operator,token.getTextRegion());
    }
    
    public Operator getOperator()
    {
        return operator;
    }

    public boolean hasAllOperands()
    {
        return getChildCount() == operator.getOperandCount();
    }
    
    @Override
    public String toString()
    {
        return operator.getLiteral()+" ("+operator.name()+")";
    }
    
    public boolean isAssignment() {
    	return operator == Operator.ASSIGNMENT;
    }
    
    public boolean isComparison() {
        return operator.isComparisonOperator();
    }
    
    @Override
    protected OperatorNode createCopy()
    {
        return new OperatorNode(  operator , getTextRegion() );
    }

    @Override
    public TermNode reduce()
    {
        final Operator op = getOperator();
        TermNode result0 = ((TermNode) child(0)).reduce();
        
        final TermNode result;
        if ( op.getOperandCount() == 1 ) 
        {
            if ( op == Operator.DEREFERENCE || op == Operator.ADDRESS_OF ) { 
                return this;
            } 
            if ( result0 != null && result0 != child(0) ) 
            {
                TermNode reduced = (TermNode) op.reduce( result0 );
                if ( reduced != null ) {
                    result = new OperatorNode(op,getTextRegion());
                    result.addChild(result0);
                } else {
                    result = null;
                }
            } else {
                result = null;
            }
        } 
        else 
        {
            TermNode result1 = ((TermNode) child(1)).reduce();
            result = (TermNode) op.reduce( result0 , result1 );
        }        
        return result == null ? this : result;
    }

    @Override
    public boolean isLiteralValue()
    {
        return false;
    }

    @Override
    protected DataType internalGetDataType()
    {
        final Operator op = getOperator();
        final DataType type1 = ((TermNode) child(0)).getDataType();
        
        if ( op.getOperandCount() == 1 ) 
        {
        	return op.inferType( type1 , DataType.UNKNOWN );
        } 
        final DataType type2 = ((TermNode) child(1)).getDataType();
        return op.inferType( type1,type2 );
    }    
    
    public boolean isAssignmentTo(VariableDefinition r) 
    {
        return isAssignmentTo( r.getName() , r.getScope() );
    }    
    
    public boolean isAssignmentTo(VariableReferenceNode r) 
    {
        return isAssignmentTo( r.getName() , r.getScope() );
    }
    
    public boolean isAssignmentTo(Identifier identifier,IScope scope) 
    {
        final ASTNode expectedDefinitionSite = scope.getDefinitionSite( identifier , true );
        
        final AtomicBoolean result = new AtomicBoolean(false);
        final IAdvancedVisitor visitor = new IAdvancedVisitor() {
            
            @Override
            public void visit(ASTNode n, int depth, IIterationContext context)
            {
                if ( n instanceof OperatorNode) {
                    if ( ((OperatorNode) n).getOperator() == Operator.ASSIGNMENT) 
                    {
                        TermNode child = (TermNode) n.child(0);
                        if ( child instanceof VariableReferenceNode) 
                        {
                            // check if both refer to the same variable
                            VariableReferenceNode ref = (VariableReferenceNode) child;
                            ASTNode actualDefinitionSite = ref.findScope().getDefinitionSite( ref.getName() , true );
                            
                            if ( actualDefinitionSite == expectedDefinitionSite ) 
                            {
                                result.set(true);
                                context.stop();
                            }
                        }
                    }
                }
            }
        };
        ASTUtils.visitInOrder( this , visitor );
        return result.get();
    }    
}