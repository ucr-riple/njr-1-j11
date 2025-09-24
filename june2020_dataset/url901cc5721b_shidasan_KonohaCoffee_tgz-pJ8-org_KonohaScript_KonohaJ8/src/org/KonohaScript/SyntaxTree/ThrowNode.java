package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ThrowNode extends UnaryNode {
	/* THROW ExceptionExpr */
	public ThrowNode(KClass TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterThrow(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitThrow(this);
	}
}
