package de.codesourcery.dcpu16.util;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.ast.OperatorNode;
import de.codesourcery.dcpu16.ast.StringLiteralNode;
import de.codesourcery.dcpu16.ast.TermNode;
import de.codesourcery.dcpu16.ast.VariableReferenceNode;
import de.codesourcery.dcpu16.parser.ASTUtils;
import de.codesourcery.dcpu16.parser.ASTUtils.IAdvancedVisitor;
import de.codesourcery.dcpu16.parser.ASTUtils.IIterationContext;

public abstract class ExpressionPrinter
{
    public static String printDebug(TermNode expression) {
        return new ExpressionPrinter() {

            @Override
            protected String getLabelForVariable(VariableReferenceNode ref)
            {
                return ref.getName().getStringValue();
            }
        }.print( expression );
    }
    
    public String print(TermNode expression) 
    {
        final StringBuffer buffer= new StringBuffer();
        final IAdvancedVisitor visitor = new IAdvancedVisitor() {
            
            @Override
            public void visit(ASTNode n, int depth, IIterationContext context)
            {
                if ( n instanceof VariableReferenceNode) {
                    buffer.append( getLabelForVariable( (VariableReferenceNode) n ) );
                } 
                else if ( n instanceof NumberLiteralNode ) 
                {
                    buffer.append( Long.toString( ((NumberLiteralNode) n).getValue() ) );
                } 
                else if ( n instanceof OperatorNode ) 
                {
                    OperatorNode node = (OperatorNode) n;
                    final TermNode child0 = node.getOperator().getOperandCount() > 0 ? (TermNode) node.child(0) : (TermNode)null ;
                    final TermNode child1 = node.getOperator().getOperandCount() > 1 ? (TermNode) node.child(1) : (TermNode)null ;
                    
                    if ( ! node.getOperator().isLeftAssociative() ) {
                        buffer.append( node.getOperator().getLiteral()+" "+print( child0) );
                    } else {
                        buffer.append( print( child0)+" "+node.getOperator().getLiteral()+" "+print( child1) );                        
                    }
                    context.dontGoDeeper();
                } else if ( n instanceof StringLiteralNode) {
                    buffer.append("\""+((StringLiteralNode) n).getValue()+"\"");
                } else {
                    throw new RuntimeException("Internal error, don't know how to print: "+n);
                }
            }
        };
        ASTUtils.visitInOrder( expression , visitor );
        return buffer.toString();
    }
    
    protected abstract String getLabelForVariable(VariableReferenceNode ref);    
}