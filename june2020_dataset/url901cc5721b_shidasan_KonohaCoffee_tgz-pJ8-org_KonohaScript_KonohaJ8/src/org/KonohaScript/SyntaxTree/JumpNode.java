package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class JumpNode extends TypedNode {
	public String Label;

	/* goto Label */
	public JumpNode(KClass TypeInfo, String Label) {
		super(TypeInfo);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterJump(this);
		return Visitor.ExitJump(this);
	}
}
