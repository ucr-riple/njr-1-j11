package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class LoopNode extends TypedNode {
	public LoopNode(KClass TypeInfo) {
		super(TypeInfo);
	}

	/* while CondExpr then { LoopBlock; IterationExpr } */
	TypedNode CondExpr;
	TypedNode LoopBody;
	TypedNode IterationExpr;

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterLoop(this);
		Visitor.Visit(this.CondExpr);
		Visitor.Visit(this.LoopBody);
		Visitor.Visit(this.IterationExpr);
		return Visitor.ExitLoop(this);
	}
}
