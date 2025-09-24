package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;

public abstract class UnaryNode extends TypedNode {
	TypedNode Expr;

	UnaryNode(KClass TypeInfo, TypedNode Expr) {
		super(TypeInfo);
		this.Expr = Expr;
	}
}