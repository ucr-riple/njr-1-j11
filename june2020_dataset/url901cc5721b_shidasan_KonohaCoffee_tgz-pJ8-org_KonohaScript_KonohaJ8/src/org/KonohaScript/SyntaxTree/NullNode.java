package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class NullNode extends TypedNode {

	public NullNode(KClass TypeInfo) {
		super(TypeInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterNull(this);
		return Visitor.ExitNull(this);
	}
}
