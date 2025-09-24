package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class DoneNode extends TypedNode {
	public DoneNode(KClass TypeInfo) {
		super(TypeInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterDone(this);
		return Visitor.ExitDone(this);
	}
}