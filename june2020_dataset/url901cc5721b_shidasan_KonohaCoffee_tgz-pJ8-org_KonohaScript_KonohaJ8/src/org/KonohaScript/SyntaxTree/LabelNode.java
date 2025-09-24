package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LabelNode extends TypedNode {
	public String Label;

	/* Label: */
	public LabelNode(KClass TypeInfo, String Label) {
		super(TypeInfo);
		this.Label = Label;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLabel(this);
		return Visitor.ExitLabel(this);
	}
}