package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class SwitchNode extends TypedNode {
	public SwitchNode(KClass TypeInfo) {
		super(TypeInfo);
	}

	/*
	 * switch CondExpr { Label[0]: Blocks[0]; Label[1]: Blocks[2]; ... }
	 */
	TypedNode CondExpr;
	public ArrayList<String> Labels;
	public ArrayList<TypedNode> Blocks;

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterSwitch(this);
		Visitor.Visit(this.CondExpr);
		for (TypedNode Node : this.Blocks) {
			Visitor.Visit(Node);
		}
		return Visitor.ExitSwitch(this);
	}
}
