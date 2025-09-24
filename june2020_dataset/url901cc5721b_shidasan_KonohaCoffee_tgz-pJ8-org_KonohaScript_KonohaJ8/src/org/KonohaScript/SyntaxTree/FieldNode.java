package org.KonohaScript.SyntaxTree;

import org.KonohaScript.KClass;
import org.KonohaScript.KToken;
import org.KonohaScript.CodeGen.ASTVisitor;

public class FieldNode extends TypedNode {
	/* frame[Index][Xindex] (or ($TermToken->text)[Xindex] */
	public KToken TermToken;
	int Index;
	public int Xindex;

	public FieldNode(KClass TypeInfo, KToken TermToken, int Index, int Xindex) {
		super(TypeInfo);
		this.TermToken = TermToken;
		this.Index = Index;
		this.Xindex = Xindex;
	}

	@Override
	public boolean Evaluate(ASTVisitor Visitor) {
		Visitor.EnterField(this);
		return Visitor.ExitField(this);
	}
}
