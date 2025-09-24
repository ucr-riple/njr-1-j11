package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class OrNode extends BinaryNode {

	public OrNode(KClass TypeInfo, TypedNode Left, TypedNode Right) {
		super(TypeInfo, Left, Right);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterOr(this);
		Visitor.Visit(this.Left);
		Visitor.Visit(this.Right);
		return Visitor.ExitOr(this);
	}
}
