package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class AssignNode extends TypedNode {
	public KToken TermToken;
	int Index;
	TypedNode Right;

	/* frame[Index] = Right */
	public AssignNode(KClass TypeInfo, KToken TermToken, int Index,
			TypedNode Right) {
		super(TypeInfo);
		this.TermToken = TermToken;
		this.Index = Index;
		this.Right = Right;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterAssign(this);
		return Visitor.ExitAssign(this);
	}

}