package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class BoxNode extends UnaryNode {
	public BoxNode(KClass TypeInfo, TypedNode Expr) {
		super(TypeInfo, Expr);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterBox(this);
		Visitor.Visit(this.Expr);
		return Visitor.ExitBox(this);
	}
}
