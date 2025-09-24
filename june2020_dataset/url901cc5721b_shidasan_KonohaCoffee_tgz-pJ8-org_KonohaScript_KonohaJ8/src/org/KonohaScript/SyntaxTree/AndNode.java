package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AndNode extends BinaryNode {
	public AndNode(KClass TypeInfo, TypedNode Left, TypedNode Right) {
		super(TypeInfo, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAnd(this);
		Visitor.Visit(this.Left);
		Visitor.Visit(this.Right);
		return Visitor.ExitAnd(this);
	}

}
