package de.codesourcery.dcpu16.util;

import de.codesourcery.dcpu16.ast.ASTNode;
import de.codesourcery.dcpu16.ast.OperatorNode;
import de.codesourcery.dcpu16.ast.TermNode;
import de.codesourcery.dcpu16.compiler.Operator;

public class ExpressionUtils {

	public static TermNode negate(TermNode expression) 
	{
		if ( ! isOperator( expression ) ) {
			return expression;
		}

		TermNode result = internalNegate( expression );
		result.setParent(expression.getParent());
		return result;
	}
	
	private static boolean isOperator(ASTNode node) {
		return node instanceof OperatorNode;
	}
	
	private static TermNode internalNegate(TermNode child) 
	{
		if ( ! ( child instanceof OperatorNode) ) 
		{
			return (TermNode) child.createCopy(true);
		}
		
		final OperatorNode op = (OperatorNode ) child;
		switch( op.getOperator() ) 
		{
			case LOGICAL_NOT: // !(!a) = a
				return (TermNode) internalNegate( (TermNode) child.child(0) ).createCopy(true);
			case LOGICAL_AND: // !(a & b) = ( a | b )
				OperatorNode newChild = new OperatorNode(Operator.LOGICAL_OR, op.getTextRegion() );
				newChild.addChild( internalNegate( (TermNode) op.child(0) ).createCopy(true) );
				newChild.addChild( internalNegate( (TermNode) op.child(1) ).createCopy(true) );
				return newChild;
			case LOGICAL_OR: // !(a | b) = ( a & b )
				OperatorNode newChild2 = new OperatorNode(Operator.LOGICAL_AND, op.getTextRegion() );
				newChild2.addChild( internalNegate( (TermNode) op.child(0) ).createCopy(true) );
				newChild2.addChild( internalNegate( (TermNode) op.child(1) ).createCopy(true) );
				return newChild2;
			default:
		} 
		return child;
	}
}