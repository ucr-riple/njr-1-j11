package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LocalNode extends TypedNode {
	/* frame[$Index] (or TermToken->text) */
	int ClassicStackIndex;

	public LocalNode(KClass TypeInfo, KToken SourceToken, int Index) {
		super(TypeInfo, SourceToken);
		this.ClassicStackIndex = Index;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLocal(this);
		return Visitor.ExitLocal(this);
	}
}
