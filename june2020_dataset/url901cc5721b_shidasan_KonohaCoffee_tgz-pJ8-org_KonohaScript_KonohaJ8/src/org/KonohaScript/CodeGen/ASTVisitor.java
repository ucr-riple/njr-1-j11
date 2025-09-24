package org.KonohaScript.CodeGen;

import org.KonohaScript.SyntaxTree.AndNode;
import org.KonohaScript.SyntaxTree.AssignNode;
import org.KonohaScript.SyntaxTree.BlockNode;
import org.KonohaScript.SyntaxTree.BoxNode;
import org.KonohaScript.SyntaxTree.ConstNode;
import org.KonohaScript.SyntaxTree.DoneNode;
import org.KonohaScript.SyntaxTree.ErrorNode;
import org.KonohaScript.SyntaxTree.FieldNode;
import org.KonohaScript.SyntaxTree.FunctionNode;
import org.KonohaScript.SyntaxTree.IfNode;
import org.KonohaScript.SyntaxTree.JumpNode;
import org.KonohaScript.SyntaxTree.LabelNode;
import org.KonohaScript.SyntaxTree.LetNode;
import org.KonohaScript.SyntaxTree.LocalNode;
import org.KonohaScript.SyntaxTree.LoopNode;
import org.KonohaScript.SyntaxTree.MethodCallNode;
import org.KonohaScript.SyntaxTree.NewNode;
import org.KonohaScript.SyntaxTree.NullNode;
import org.KonohaScript.SyntaxTree.OrNode;
import org.KonohaScript.SyntaxTree.ReturnNode;
import org.KonohaScript.SyntaxTree.SwitchNode;
import org.KonohaScript.SyntaxTree.ThrowNode;
import org.KonohaScript.SyntaxTree.TryNode;
import org.KonohaScript.SyntaxTree.TypedNode;

public interface ASTVisitor {
	boolean Visit(TypedNode Node);

	void EnterDone(DoneNode Node);

	boolean ExitDone(DoneNode Node);

	void EnterConst(ConstNode Node);

	boolean ExitConst(ConstNode Node);

	void EnterNew(NewNode Node);

	boolean ExitNew(NewNode Node);

	void EnterNull(NullNode Node);

	boolean ExitNull(NullNode Node);

	void EnterLocal(LocalNode Node);

	boolean ExitLocal(LocalNode Node);

	void EnterField(FieldNode Node);

	boolean ExitField(FieldNode Node);

	void EnterBox(BoxNode Node);

	boolean ExitBox(BoxNode Node);

	void EnterMethodCall(MethodCallNode Node);

	boolean ExitMethodCall(MethodCallNode Node);

	void EnterAnd(AndNode Node);

	boolean ExitAnd(AndNode Node);

	void EnterOr(OrNode Node);

	boolean ExitOr(OrNode Node);

	void EnterAssign(AssignNode Node);

	boolean ExitAssign(AssignNode Node);

	void EnterLet(LetNode Node);

	boolean ExitLet(LetNode Node);

	void EnterBlock(BlockNode Node);

	boolean ExitBlock(BlockNode Node);

	void EnterIf(IfNode Node);

	boolean ExitIf(IfNode Node);

	void EnterSwitch(SwitchNode Node);

	boolean ExitSwitch(SwitchNode Node);

	void EnterLoop(LoopNode Node);

	boolean ExitLoop(LoopNode Node);

	void EnterReturn(ReturnNode Node);

	boolean ExitReturn(ReturnNode Node);

	void EnterLabel(LabelNode Node);

	boolean ExitLabel(LabelNode Node);

	void EnterJump(JumpNode Node);

	boolean ExitJump(JumpNode Node);

	void EnterTry(TryNode Node);

	boolean ExitTry(TryNode Node);

	void EnterThrow(ThrowNode Node);

	boolean ExitThrow(ThrowNode Node);

	void EnterFunction(FunctionNode Node);

	boolean ExitFunction(FunctionNode Node);

	void EnterError(ErrorNode Node);

	boolean ExitError(ErrorNode Node);
}