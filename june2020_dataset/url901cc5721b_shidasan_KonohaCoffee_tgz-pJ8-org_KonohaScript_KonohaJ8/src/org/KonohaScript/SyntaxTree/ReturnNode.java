package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ReturnNode extends UnaryNode {

	public ReturnNode(KClass TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterReturn(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitReturn(this);
	}
}