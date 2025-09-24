package org.KonohaScript.SyntaxTree;

import java.util.ArrayList;

import org.KonohaScript.KClass;
import org.KonohaScript.CodeGen.ASTVisitor;

public class TryNode extends TypedNode {
	/*
	 * let HasException = TRY(TryBlock); in if HasException ==
	 * CatchedExceptions[0] then CatchBlock[0] if HasException ==
	 * CatchedExceptions[1] then CatchBlock[1] ... FinallyBlock end
	 */
	TypedNode TryBlock;
	ArrayList<TypedNode> TargetException;
	public ArrayList<TypedNode> CatchBlock;
	TypedNode FinallyBlock;

	public TryNode(KClass TypeInfo, TypedNode TryBlock, TypedNode FinallyBlock) {
		super(TypeInfo);
		this.TryBlock = TryBlock;
		this.FinallyBlock = FinallyBlock;
		this.CatchBlock = new ArrayList<TypedNode>();
		this.TargetException = new ArrayList<TypedNode>();
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterTry(this);
		Visitor.Visit(this.TryBlock);
		Visitor.Visit(FinallyBlock);
		return Visitor.ExitTry(this);
	}
}
