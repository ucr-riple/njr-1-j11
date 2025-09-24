package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class ErrorNode extends TypedNode {
	String ErrorMessage;

	public ErrorNode(KClass TypeInfo, KToken KeyToken, String ErrorMessage) {
		super(TypeInfo);
		this.ErrorMessage = KeyToken.SetErrorMessage(ErrorMessage);
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterError(this);
		return Visitor.ExitError(this);
	}
}
