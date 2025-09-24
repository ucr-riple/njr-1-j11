package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class NewNode extends TypedNode {

	public NewNode(KClass TypeInfo) {
		super(TypeInfo);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterNew(this);
		return Visitor.ExitNew(this);
	}
}