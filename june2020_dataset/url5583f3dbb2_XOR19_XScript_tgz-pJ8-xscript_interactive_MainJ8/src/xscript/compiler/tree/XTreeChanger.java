package xscript.compiler.tree;

import java.util.List;

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

public class XTreeChanger implements XVisitor {

	protected static final Object REMOVE = new Object();
	
	public <T extends XTree> T visitTree(T tree){
		if(tree!=null){
			tree.accept(this);
		}
		return tree;
	}
	
	public <T extends XTree> List<T> visitTree(List<T> tree){
		if(tree!=null){
			for(int i=0; i<tree.size(); i++){
				T t = (T)visitTree(tree.get(i));
				if(t==REMOVE){
					tree.remove(i);
					i--;
				}else{
					tree.set(i, t);
				}
			}
		}
		return tree;
	}
	
	@Override
	public void visitError(XTreeError error) {}

	@Override
	public void visitScope(XTreeScope scope) {
		scope.statements = visitTree(scope.statements);
	}

	@Override
	public void visitClass(XTreeClass _class) {
		_class.name = visitTree(_class.name);
		_class.superClasses = visitTree(_class.superClasses);
		_class.init = visitTree(_class.init);
	}

	@Override
	public void visitFunc(XTreeFunc func) {
		func.name = visitTree(func.name);
		func.params = visitTree(func.params);
		func.defaults = visitTree(func.defaults);
		func.body = visitTree(func.body);
	}

	@Override
	public void visitModule(XTreeModule module) {
		module.body = visitTree(module.body);
	}

	@Override
	public void visitImportEntry(XTreeImportEntry importEntry) {
		importEntry._import = visitTree(importEntry._import);
		importEntry.as = visitTree(importEntry.as);
	}
	
	@Override
	public void visitImport(XTreeImport _import) {
		_import.imports = visitTree(_import.imports);
	}
	
	@Override
	public void visitImportFrom(XTreeImportFrom importFrom) {
		importFrom.from = visitTree(importFrom.from);
		importFrom.imports = visitTree(importFrom.imports);
	}

	@Override
	public void visitIf(XTreeIf _if) {
		_if.condition = visitTree(_if.condition);
		_if.onTrue = visitTree(_if.onTrue);
		_if.onFalse = visitTree(_if.onFalse);
	}

	@Override
	public void visitFor(XTreeFor _for) {
		_for.init = visitTree(_for.init);
		_for.condition = visitTree(_for.condition);
		_for.inc = visitTree(_for.inc);
		_for.body = visitTree(_for.body);
	}

	@Override
	public void visitForeach(XTreeForeach foreach) {
		foreach.var = visitTree(foreach.var);
		foreach.in = visitTree(foreach.in);
		foreach.body = visitTree(foreach.body);
	}

	@Override
	public void visitWhile(XTreeWhile _while) {
		_while.condition = visitTree(_while.condition);
		_while.body = visitTree(_while.body);
	}

	@Override
	public void visitDo(XTreeDo _do) {
		_do.body = visitTree(_do.body);
		_do.condition = visitTree(_do.condition);
	}

	@Override
	public void visitSynchronized(XTreeSynchronized _synchronized) {
		_synchronized.sync = visitTree(_synchronized.sync);
		_synchronized.body = visitTree(_synchronized.body);
	}

	@Override
	public void visitBreak(XTreeBreak _break) {
		_break.lable = visitTree(_break.lable);
	}

	@Override
	public void visitContinue(XTreeContinue _continue) {
		_continue.lable = visitTree(_continue.lable);
	}

	@Override
	public void visitReturn(XTreeReturn _return) {
		_return._return = visitTree(_return._return);
	}

	@Override
	public void visitYield(XTreeYield yield) {
		yield._return = visitTree(yield._return);
	}

	@Override
	public void visitThis(XTreeThis _this) {}

	@Override
	public void visitLabel(XTreeLabel label) {
		label.label = visitTree(label.label);
		label.statement = visitTree(label.statement);
	}

	@Override
	public void visitSuper(XTreeSuper _super) {}

	@Override
	public void visitThrow(XTreeThrow _throw) {
		_throw._throw = visitTree(_throw._throw);
	}

	@Override
	public void visitAssert(XTreeAssert _assert) {
		_assert.condition = visitTree(_assert.condition);
		_assert.message = visitTree(_assert.message);
	}

	@Override
	public void visitCatch(XTreeCatch _catch) {
		_catch.excType = visitTree(_catch.excType);
		_catch.varName = visitTree(_catch.varName);
		_catch.body = visitTree(_catch.body);
	}

	@Override
	public void visitTry(XTreeTry _try) {
		_try.resources = visitTree(_try.resources);
		_try.body = visitTree(_try.body);
		_try.catches = visitTree(_try.catches);
		_try._finally = visitTree(_try._finally);
	}

	@Override
	public void visitGroup(XTreeGroup group) {
		group.group = visitTree(group.group);
	}

	@Override
	public void visitOperator(XTreeOperator operator) {
		operator.exprs = visitTree(operator.exprs);
	}

	@Override
	public void visitOperatorIf(XTreeOperatorIf operatorIf) {
		operatorIf.condition = visitTree(operatorIf.condition);
		operatorIf.onTrue = visitTree(operatorIf.onTrue);
		operatorIf.onFalse = visitTree(operatorIf.onFalse);
	}

	@Override
	public void visitOperatorPrefixSuffix(XTreeOperatorPrefixSuffix operatorPrefixSuffix) {
		operatorPrefixSuffix.expr = visitTree(operatorPrefixSuffix.expr);
	}

	@Override
	public void visitIdent(XTreeIdent ident) {}

	@Override
	public void visitIndex(XTreeIndex index) {
		index.array = visitTree(index.array);
		index.index = visitTree(index.index);
	}

	@Override
	public void visitInstanceof(XTreeInstanceof _instanceof) {
		_instanceof.statement = visitTree(_instanceof.statement);
		_instanceof.type = visitTree(_instanceof.type);
	}

	@Override
	public void visitIssubclass(XTreeIssubclass issubclass) {
		issubclass.statement = visitTree(issubclass.statement);
		issubclass.type = visitTree(issubclass.type);
	}

	@Override
	public void visitTypeof(XTreeTypeof typeof) {
		typeof.statement = visitTree(typeof.statement);
	}

	@Override
	public void visitSwitch(XTreeSwitch _switch) {
		_switch.statement = visitTree(_switch.statement);
		_switch.cases = visitTree(_switch.cases);
	}

	@Override
	public void visitCase(XTreeCase _case) {
		_case.key = visitTree(_case.key);
		_case.block = visitTree(_case.block);
	}

	@Override
	public void visitConstant(XTreeConstant constant) {}

	@Override
	public void visitCall(XTreeCall call) {
		call.method = visitTree(call.method);
		call.params = visitTree(call.params);
		call.kws = visitTree(call.kws);
	}

	@Override
	public void visitMakeTuple(XTreeMakeTuple makeTuple) {
		makeTuple.exprs = visitTree(makeTuple.exprs);
	}

	@Override
	public void visitMakeList(XTreeMakeList makeList) {
		makeList.exprs = visitTree(makeList.exprs);
	}

	@Override
	public void visitMakeMap(XTreeMakeMap makeMap) {
		makeMap.entries = visitTree(makeMap.entries);
	}

	@Override
	public void visitMapEntry(XTreeMapEntry mapEntry) {
		mapEntry.key = visitTree(mapEntry.key);
		mapEntry.expr = visitTree(mapEntry.expr);
	}

	@Override
	public void visitEmpty(XTreeEmpty empty) {}

	@Override
	public void visitVarDecl(XTreeVarDecl varAccess) {
		varAccess.entries = visitTree(varAccess.entries);
	}

	@Override
	public void visitVarDeclEntry(XTreeVarDeclEntry varDeclEntry) {
		varDeclEntry.ident = visitTree(varDeclEntry.ident);
		varDeclEntry.def = visitTree(varDeclEntry.def);
	}
	
	@Override
	public void visitDelete(XTreeDelete delete) {
		delete.expr = visitTree(delete.expr);
	}

}
