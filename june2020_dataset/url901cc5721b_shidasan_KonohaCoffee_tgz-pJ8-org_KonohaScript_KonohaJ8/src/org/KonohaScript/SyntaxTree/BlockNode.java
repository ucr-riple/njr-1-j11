package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class BlockNode extends TypedNode {
	public ArrayList<TypedNode> ExprList;

	/* [Expr1, Expr2, ... ] */
	public BlockNode(KClass TypeInfo) {
		super(TypeInfo);
		this.ExprList = new ArrayList<TypedNode>();
	}

	public BlockNode(KClass TypeInfo, TypedNode Node1) {
		super(TypeInfo);
		init();
		this.ExprList.add(Node1);
	}

	public BlockNode(KClass TypeInfo, TypedNode Node1, TypedNode Node2) {
		super(TypeInfo);
		init();
		this.ExprList.add(Node1);
		this.ExprList.add(Node2);
	}

	public BlockNode(KClass TypeInfo, TypedNode Node1, TypedNode Node2,
			TypedNode Node3) {
		super(TypeInfo);
		init();
		this.ExprList.add(Node1);
		this.ExprList.add(Node2);
		this.ExprList.add(Node3);
	}

	void init() {
		this.ExprList = new ArrayList<TypedNode>();
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterBlock(this);
		for (TypedNode Node : this.ExprList) {
			if (Visitor.Visit(Node) == false) {
				break;
			}
		}
		return Visitor.ExitBlock(this);
	}
}
