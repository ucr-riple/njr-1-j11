package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class IfNode extends TypedNode {
	public TypedNode CondExpr;
	public TypedNode ThenNode;
	public TypedNode ElseNode;

	/* If CondExpr then ThenBlock else ElseBlock */
	public IfNode(KClass TypeInfo, TypedNode CondExpr, TypedNode ThenBlock, TypedNode ElseNode) {
		super(TypeInfo);
		this.CondExpr = CondExpr;
		this.ThenNode = ThenBlock;
		this.ElseNode = ElseNode;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterIf(this);
		Visitor.Visit(this.CondExpr);
		Visitor.Visit(this.ThenNode);
		Visitor.Visit(this.ElseNode);
		return Visitor.ExitIf(this);
	}
}