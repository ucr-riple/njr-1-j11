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

public class XTreePrinter implements XVisitor {

	private String spaces = "";
	private String add = "";
	
	private void enter(){
		spaces += " ";
	}
	
	private void leave(){
		spaces = spaces.substring(0, spaces.length()-1);
	}
	
	private void println(String s){
		if(add==null){
			System.out.println(spaces+s);
		}else{
			System.out.println(add+s);
			add=null;	
		}
	}
	
	private void print(String s){
		if(add==null)
			add=spaces;
		add += s;
	}
	
	private void accept(String name, List<? extends XTree> tree){
		if(tree!=null && !tree.isEmpty()){
			print(name+"=[");
			enter();
			for(XTree t:tree){
				if(t!=null){
					println(t.getClass().getSimpleName()+"{");
					enter();
					t.accept(this);
					leave();
					print("}");
				}
			}
			leave();
			println("]");
		}
	}
	
	private void accept(String name, XTree tree){
		if(tree!=null){
			println(name+"="+tree.getClass().getSimpleName()+"{");
			enter();
			tree.accept(this);
			leave();
			println("}");
		}
	}
	
	@Override
	public void visitError(XTreeError error) {
		println("error");
	}

	@Override
	public void visitScope(XTreeScope scope) {
		accept("scope", scope.statements);
	}

	@Override
	public void visitClass(XTreeClass _class) {
		accept("name", _class.name);
		println("access="+_class.access);
		accept("superClasses", _class.superClasses);
		accept("init", _class.init);
	}

	@Override
	public void visitFunc(XTreeFunc func) {
		accept("name", func.name);
		println("access"+func.access);
		accept("params", func.params);
		accept("defaults", func.defaults);
		println("kwParam="+func.kwParam);
		println("listParam="+func.listParam);
		accept("body", func.body);
	}

	@Override
	public void visitModule(XTreeModule module) {
		accept("module", module.body);
	}

	@Override
	public void visitImportEntry(XTreeImportEntry importEntry) {
		accept("import", importEntry._import);
		accept("as", importEntry.as);
	}
	
	@Override
	public void visitImport(XTreeImport _import) {
		accept("imports", _import.imports);
	}

	@Override
	public void visitImportFrom(XTreeImportFrom importFrom) {
		accept("from", importFrom.from);
		accept("imports", importFrom.imports);
	}
	
	@Override
	public void visitIf(XTreeIf _if) {
		accept("condition", _if.condition);
		accept("onTrue", _if.onTrue);
		accept("onFalse", _if.onFalse);
	}

	@Override
	public void visitFor(XTreeFor _for) {
		accept("init", _for.init);
		accept("condition", _for.condition);
		accept("inc", _for.inc);
		accept("body", _for.body);
	}

	@Override
	public void visitForeach(XTreeForeach foreach) {
		accept("var", foreach.var);
		accept("in", foreach.in);
		accept("body", foreach.body);
	}

	@Override
	public void visitWhile(XTreeWhile _while) {
		accept("condition", _while.condition);
		accept("body", _while.body);
	}

	@Override
	public void visitDo(XTreeDo _do) {
		accept("body", _do.body);
		accept("condition", _do.condition);
	}

	@Override
	public void visitSynchronized(XTreeSynchronized _synchronized) {
		accept("sync", _synchronized.sync);
		accept("body", _synchronized.body);
	}

	@Override
	public void visitBreak(XTreeBreak _break) {
		accept("lable", _break.lable);
	}

	@Override
	public void visitContinue(XTreeContinue _continue) {
		accept("lable", _continue.lable);
	}

	@Override
	public void visitReturn(XTreeReturn _return) {
		accept("return", _return._return);
	}

	@Override
	public void visitYield(XTreeYield yield) {
		accept("yield", yield._return);
	}

	@Override
	public void visitThis(XTreeThis _this) {
		
	}

	@Override
	public void visitLabel(XTreeLabel label) {
		accept("lable", label.label);
		accept("statement", label.statement);
	}

	@Override
	public void visitSuper(XTreeSuper _super) {
		
	}

	@Override
	public void visitThrow(XTreeThrow _throw) {
		accept("throw", _throw._throw);
	}

	@Override
	public void visitAssert(XTreeAssert _assert) {
		accept("condition", _assert.condition);
		accept("message", _assert.message);
	}

	@Override
	public void visitCatch(XTreeCatch _catch) {
		accept("excType", _catch.excType);
		accept("varName", _catch.varName);
		accept("body", _catch.body);
	}

	@Override
	public void visitTry(XTreeTry _try) {
		accept("resources", _try.resources);
		accept("body", _try.body);
		accept("catches", _try.catches);
		accept("finally", _try._finally);
	}

	@Override
	public void visitGroup(XTreeGroup group) {
		accept("group", group.group);
	}

	@Override
	public void visitOperator(XTreeOperator operator) {
		accept("expr", operator.exprs.get(0));
		for(int i=0; i<operator.operators.size(); i++){
			println(operator.operators.get(i).toString());
			accept("expr", operator.exprs.get(i+1));
		}

	}

	@Override
	public void visitOperatorIf(XTreeOperatorIf operatorIf) {
		accept("condition", operatorIf.condition);
		accept("onTrue", operatorIf.onTrue);
		accept("onFalse", operatorIf.onFalse);
	}

	@Override
	public void visitOperatorPrefixSuffix(XTreeOperatorPrefixSuffix operatorPrefixSuffix) {
		//TODO
		accept("expr", operatorPrefixSuffix.expr);
		
	}

	@Override
	public void visitIdent(XTreeIdent ident) {
		println("ident="+ident.name);
	}

	@Override
	public void visitIndex(XTreeIndex index) {
		accept("array", index.array);
		accept("index", index.index);
	}

	@Override
	public void visitInstanceof(XTreeInstanceof _instanceof) {
		accept("statement", _instanceof.statement);
		accept("type", _instanceof.type);
	}

	@Override
	public void visitIssubclass(XTreeIssubclass issubclass) {
		accept("statement", issubclass.statement);
		accept("type", issubclass.type);
	}

	@Override
	public void visitTypeof(XTreeTypeof typeof) {
		accept("typeof", typeof.statement);
	}

	@Override
	public void visitSwitch(XTreeSwitch _switch) {
		accept("statement", _switch.statement);
		accept("cases", _switch.cases);
	}

	@Override
	public void visitCase(XTreeCase _case) {
		accept("key", _case.key);
		accept("block", _case.block);
	}

	@Override
	public void visitConstant(XTreeConstant constant) {
		println("const:"+constant.value);
	}

	@Override
	public void visitCall(XTreeCall call) {
		accept("method", call.method);
		accept("kws", call.kws);
		accept("params", call.params);
	}

	@Override
	public void visitMakeTuple(XTreeMakeTuple makeTuple) {
		accept("exprs", makeTuple.exprs);
	}

	@Override
	public void visitMakeList(XTreeMakeList makeList) {
		accept("exprs", makeList.exprs);
	}

	@Override
	public void visitMakeMap(XTreeMakeMap makeMap) {
		accept("entries", makeMap.entries);
	}

	@Override
	public void visitMapEntry(XTreeMapEntry mapEntry) {
		accept("key", mapEntry.key);
		accept("expr", mapEntry.expr);
	}

	@Override
	public void visitEmpty(XTreeEmpty empty) {
		
	}

	@Override
	public void visitVarDecl(XTreeVarDecl varAccess) {
		println("access:"+varAccess.access);
		accept("entries", varAccess.entries);
	}

	@Override
	public void visitVarDeclEntry(XTreeVarDeclEntry varDeclEntry) {
		accept("ident", varDeclEntry.ident);
		accept("default", varDeclEntry.def);
	}
	
	@Override
	public void visitDelete(XTreeDelete delete) {
		accept("expr", delete.expr);
	}

}
