package xscript.compiler.tree;

import xscript.compiler.tree.XTree.XTreeAssert;
import xscript.compiler.tree.XTree.XTreeBreak;
import xscript.compiler.tree.XTree.XTreeCall;
import xscript.compiler.tree.XTree.XTreeCase;
import xscript.compiler.tree.XTree.XTreeCatch;
import xscript.compiler.tree.XTree.XTreeClass;
import xscript.compiler.tree.XTree.XTreeConstant;
import xscript.compiler.tree.XTree.XTreeContinue;
import xscript.compiler.tree.XTree.XTreeDelete;
import xscript.compiler.tree.XTree.XTreeDo;
import xscript.compiler.tree.XTree.XTreeEmpty;
import xscript.compiler.tree.XTree.XTreeError;
import xscript.compiler.tree.XTree.XTreeFor;
import xscript.compiler.tree.XTree.XTreeForeach;
import xscript.compiler.tree.XTree.XTreeFunc;
import xscript.compiler.tree.XTree.XTreeGroup;
import xscript.compiler.tree.XTree.XTreeIdent;
import xscript.compiler.tree.XTree.XTreeIf;
import xscript.compiler.tree.XTree.XTreeImport;
import xscript.compiler.tree.XTree.XTreeImportEntry;
import xscript.compiler.tree.XTree.XTreeImportFrom;
import xscript.compiler.tree.XTree.XTreeIndex;
import xscript.compiler.tree.XTree.XTreeInstanceof;
import xscript.compiler.tree.XTree.XTreeIssubclass;
import xscript.compiler.tree.XTree.XTreeLabel;
import xscript.compiler.tree.XTree.XTreeMakeList;
import xscript.compiler.tree.XTree.XTreeMakeMap;
import xscript.compiler.tree.XTree.XTreeMakeTuple;
import xscript.compiler.tree.XTree.XTreeMapEntry;
import xscript.compiler.tree.XTree.XTreeModule;
import xscript.compiler.tree.XTree.XTreeOperator;
import xscript.compiler.tree.XTree.XTreeOperatorIf;
import xscript.compiler.tree.XTree.XTreeOperatorPrefixSuffix;
import xscript.compiler.tree.XTree.XTreeReturn;
import xscript.compiler.tree.XTree.XTreeScope;
import xscript.compiler.tree.XTree.XTreeSuper;
import xscript.compiler.tree.XTree.XTreeSwitch;
import xscript.compiler.tree.XTree.XTreeSynchronized;
import xscript.compiler.tree.XTree.XTreeThis;
import xscript.compiler.tree.XTree.XTreeThrow;
import xscript.compiler.tree.XTree.XTreeTry;
import xscript.compiler.tree.XTree.XTreeTypeof;
import xscript.compiler.tree.XTree.XTreeVarDecl;
import xscript.compiler.tree.XTree.XTreeVarDeclEntry;
import xscript.compiler.tree.XTree.XTreeWhile;
import xscript.compiler.tree.XTree.XTreeYield;


public interface XVisitor {

	public void visitError(XTreeError error);

	public void visitScope(XTreeScope scope);

	public void visitClass(XTreeClass _class);

	public void visitFunc(XTreeFunc func);

	public void visitModule(XTreeModule module);

	public void visitImportEntry(XTreeImportEntry importEntry);
	
	public void visitImport(XTreeImport _import);
	
	public void visitImportFrom(XTreeImportFrom importFrom);

	public void visitIf(XTreeIf _if);

	public void visitFor(XTreeFor _for);

	public void visitForeach(XTreeForeach foreach);

	public void visitWhile(XTreeWhile _while);

	public void visitDo(XTreeDo _do);

	public void visitSynchronized(XTreeSynchronized _synchronized);

	public void visitBreak(XTreeBreak _break);

	public void visitContinue(XTreeContinue _continue);

	public void visitReturn(XTreeReturn _return);

	public void visitYield(XTreeYield yield);

	public void visitThis(XTreeThis _this);
	
	public void visitLabel(XTreeLabel label);

	public void visitSuper(XTreeSuper _super);

	public void visitThrow(XTreeThrow _throw);

	public void visitAssert(XTreeAssert _assert);

	public void visitCatch(XTreeCatch _catch);

	public void visitTry(XTreeTry _try);

	public void visitGroup(XTreeGroup group);

	public void visitOperator(XTreeOperator operator);

	public void visitOperatorIf(XTreeOperatorIf operatorIf);

	public void visitOperatorPrefixSuffix(XTreeOperatorPrefixSuffix operatorPrefixSuffix);

	public void visitIdent(XTreeIdent ident);

	public void visitIndex(XTreeIndex index);

	public void visitInstanceof(XTreeInstanceof _instanceof);

	public void visitIssubclass(XTreeIssubclass issubclass);
	
	public void visitTypeof(XTreeTypeof typeof);
	
	public void visitSwitch(XTreeSwitch _switch);

	public void visitCase(XTreeCase _case);

	public void visitConstant(XTreeConstant constant);

	public void visitCall(XTreeCall call);

	public void visitMakeTuple(XTreeMakeTuple makeTuple);
	
	public void visitMakeList(XTreeMakeList makeList);

	public void visitMakeMap(XTreeMakeMap makeMap);

	public void visitMapEntry(XTreeMapEntry mapEntry);

	public void visitEmpty(XTreeEmpty empty);

	public void visitVarDecl(XTreeVarDecl varDecl);

	public void visitVarDeclEntry(XTreeVarDeclEntry varDeclEntry);
	
	public void visitDelete(XTreeDelete delete);

}
