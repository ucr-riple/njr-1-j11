package xscript.compiler.tree;

import java.util.List;

import xscript.compiler.XOperator;
import xscript.compiler.XTreePosition;
import xscript.compiler.XVarAccess;

public abstract class XTree{
	
	public static abstract class XTreeStatement extends XTree {
		
		public XTreeStatement(XTreePosition position) {
			super(position);
		}
		
	}
	
	public static abstract class XTreeLowExpr extends XTreeStatement {
		
		public XTreeLowExpr(XTreePosition position) {
			super(position);
		}
		
	}
	
	public static abstract class XTreeExpr extends XTreeLowExpr {
		
		public XTreeExpr(XTreePosition position) {
			super(position);
		}
		
	}
	
	public static class XTreeError extends XTreeExpr {

		public XTreeError(XTreePosition position) {
			super(position);
		}

		@Override
		public void accept(XVisitor v) {
			v.visitError(this);
		}
		
	}
	
	public static class XTreeScope extends XTreeStatement {

		public List<XTreeStatement> statements;
		
		public XTreeScope(XTreePosition position, List<XTreeStatement> statements) {
			super(position);
			this.statements = statements;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitScope(this);
		}
		
	}
	
	public static class XTreeClass extends XTreeExpr{

		public XVarAccess access;
		public XTreeIdent name;
		public XTreeExpr superClasses;
		public XTreeScope init;
		
		public XTreeClass(XTreePosition position, XVarAccess access, XTreeIdent name, XTreeExpr superClasses, XTreeScope init) {
			super(position);
			this.access = access;
			this.name = name;
			this.superClasses = superClasses;
			this.init = init;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitClass(this);
		}
		
	}
	
	public static class XTreeFunc extends XTreeExpr{

		public XVarAccess access;
		public XTreeIdent name;
		public List<XTreeIdent> params;
		public int listParam;
		public int kwParam;
		public List<XTreeExpr> defaults;
		public XTreeScope body;
		
		public XTreeFunc(XTreePosition position, XVarAccess access, XTreeIdent name, List<XTreeIdent> params, int listParam, int kwParam, List<XTreeExpr> defaults, XTreeScope body) {
			super(position);
			this.access = access;
			this.name = name;
			this.params = params;
			this.listParam = listParam;
			this.kwParam = kwParam;
			this.defaults = defaults;
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitFunc(this);
		}
		
	}
	
	public static class XTreeModule extends XTree{

		public String source;
		public XTreeScope body;
		
		public XTreeModule(XTreePosition position, XTreeScope body) {
			super(position);
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitModule(this);
		}
		
	}
	
	public static class XTreeImportEntry extends XTreeStatement{

		public XTreeIdent _import;
		public XTreeIdent as;
		
		public XTreeImportEntry(XTreePosition position, XTreeIdent _import, XTreeIdent as) {
			super(position);
			this._import = _import;
			this.as = as;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitImportEntry(this);
		}
		
	}
	
	public static class XTreeImport extends XTreeStatement{

		public List<XTreeImportEntry> imports;
		
		public XTreeImport(XTreePosition position, List<XTreeImportEntry> imports) {
			super(position);
			this.imports = imports;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitImport(this);
		}
		
	}
	
	public static class XTreeImportFrom extends XTreeStatement{

		public XTreeIdent from;
		public List<XTreeImportEntry> imports;
		
		public XTreeImportFrom(XTreePosition position, XTreeIdent from, List<XTreeImportEntry> imports) {
			super(position);
			this.from = from;
			this.imports = imports;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitImportFrom(this);
		}
		
	}
	
	public static class XTreeIf extends XTreeStatement{

		public XTreeExpr condition;
		public XTreeStatement onTrue;
		public XTreeStatement onFalse;
		
		public XTreeIf(XTreePosition position, XTreeExpr condition, XTreeStatement onTrue, XTreeStatement onFalse) {
			super(position);
			this.condition = condition;
			this.onTrue = onTrue;
			this.onFalse = onFalse;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitIf(this);
		}
		
	}
	
	public static class XTreeFor extends XTreeStatement{

		public XTreeLowExpr init;
		public XTreeExpr condition;
		public XTreeExpr inc;
		public XTreeStatement body;
		
		public XTreeFor(XTreePosition position, XTreeLowExpr init, XTreeExpr condition, XTreeExpr inc, XTreeStatement body) {
			super(position);
			this.init = init;
			this.condition = condition;
			this.inc = inc;
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitFor(this);
		}
		
	}
	
	public static class XTreeForeach extends XTreeStatement{

		public XTreeLowExpr var;
		public XTreeExpr in;
		public XTreeStatement body;
		
		public XTreeForeach(XTreePosition position, XTreeLowExpr var, XTreeExpr in, XTreeStatement body) {
			super(position);
			this.var = var;
			this.in = in;
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitForeach(this);
		}
		
	}
	
	public static class XTreeWhile extends XTreeStatement{

		public XTreeExpr condition;
		public XTreeStatement body;
		
		public XTreeWhile(XTreePosition position, XTreeExpr condition, XTreeStatement body) {
			super(position);
			this.condition = condition;
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitWhile(this);
		}
		
	}
	
	public static class XTreeDo extends XTreeStatement{

		public XTreeExpr condition;
		public XTreeStatement body;
		
		public XTreeDo(XTreePosition position, XTreeExpr condition, XTreeStatement body) {
			super(position);
			this.condition = condition;
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitDo(this);
		}
		
	}
	
	public static class XTreeSynchronized extends XTreeStatement{

		public XTreeExpr sync;
		public XTreeScope body;
		
		public XTreeSynchronized(XTreePosition position, XTreeExpr sync, XTreeScope body) {
			super(position);
			this.sync = sync;
			this.body = body;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitSynchronized(this);
		}
		
	}
	
	public static class XTreeBreak extends XTreeStatement{
		
		public XTreeIdent lable;
		
		public XTreeBreak(XTreePosition position, XTreeIdent lable) {
			super(position);
			this.lable = lable;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitBreak(this);
		}
		
	}
	
	public static class XTreeContinue extends XTreeStatement{
		
		public XTreeIdent lable;
		
		public XTreeContinue(XTreePosition position, XTreeIdent lable) {
			super(position);
			this.lable = lable;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitContinue(this);
		}
		
	}
	
	public static class XTreeReturn extends XTreeStatement{
		
		public XTreeExpr _return;
		
		public XTreeReturn(XTreePosition position, XTreeExpr _return) {
			super(position);
			this._return = _return;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitReturn(this);
		}
		
	}
	
	public static class XTreeYield extends XTreeExpr{
		
		public XTreeExpr _return;
		
		public XTreeYield(XTreePosition position, XTreeExpr _return) {
			super(position);
			this._return = _return;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitYield(this);
		}
		
	}
	
	public static class XTreeThis extends XTreeExpr{
		
		public XTreeThis(XTreePosition position) {
			super(position);
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitThis(this);
		}
		
	}
	
	public static class XTreeLabel extends XTreeStatement{

		public XTreeIdent label;
		public XTreeStatement statement;
		
		public XTreeLabel(XTreePosition position, XTreeIdent label, XTreeStatement statement) {
			super(position);
			this.label = label;
			this.statement = statement;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitLabel(this);
		}
		
	}
	
	public static class XTreeSuper extends XTreeExpr{
		
		public XTreeSuper(XTreePosition position) {
			super(position);
		}

		@Override
		public void accept(XVisitor v) {
			v.visitSuper(this);
		}
		
	}
	
	public static class XTreeThrow extends XTreeStatement{
		
		public XTreeExpr _throw;
		
		public XTreeThrow(XTreePosition position, XTreeExpr _throw) {
			super(position);
			this._throw = _throw;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitThrow(this);
		}
		
	}
	
	public static class XTreeAssert extends XTreeStatement{
		
		public XTreeExpr condition;
		public XTreeExpr message;
		
		public XTreeAssert(XTreePosition position, XTreeExpr condition, XTreeExpr message) {
			super(position);
			this.condition = condition;
			this.message = message;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitAssert(this);
		}
		
	}
	
	public static class XTreeCatch extends XTreeStatement{
		
		public List<XTreeExpr> excType;
		public XTreeIdent varName;
		public XTreeScope body;
		
		public XTreeCatch(XTreePosition position, List<XTreeExpr> excType, XTreeIdent varName, XTreeScope body) {
			super(position);
			this.excType = excType;
			this.varName = varName;
			this.body = body;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitCatch(this);
		}
		
	}
	
	public static class XTreeTry extends XTreeStatement{
		
		public XTreeScope resources;
		public XTreeScope body;
		public List<XTreeCatch> catches;
		public XTreeScope _finally;
		
		public XTreeTry(XTreePosition position, XTreeScope resources, XTreeScope body, List<XTreeCatch> catches, XTreeScope _finally) {
			super(position);
			this.resources = resources;
			this.body = body;
			this.catches = catches;
			this._finally = _finally;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitTry(this);
		}
		
	}
	
	public static class XTreeGroup extends XTreeExpr{
		
		public XTreeExpr group;
		
		public XTreeGroup(XTreePosition position, XTreeExpr group) {
			super(position);
			this.group = group;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitGroup(this);
		}
		
	}
	
	public static class XTreeOperator extends XTreeExpr{
		
		public List<XTreeExpr> exprs;
		public List<XOperator> operators;
		
		public XTreeOperator(XTreePosition position, List<XTreeExpr> exprs, List<XOperator> operators){
			super(position);
			this.exprs = exprs;
			this.operators = operators;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitOperator(this);
		}
		
	}
	
	public static class XTreeOperatorIf extends XTreeExpr{
		
		public XTreeExpr condition;
		public XTreeExpr onTrue;
		public XTreeExpr onFalse;
		
		public XTreeOperatorIf(XTreePosition position, XTreeExpr condition, XTreeExpr onTrue, XTreeExpr onFalse) {
			super(position);
			this.condition = condition;
			this.onTrue = onTrue;
			this.onFalse = onFalse;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitOperatorIf(this);
		}
		
	}
	
	public static class XTreeOperatorPrefixSuffix extends XTreeExpr{

		public List<XOperator> prefix;
		
		public XTreeExpr expr;
		
		public List<XOperator> suffix;
		
		public XTreeOperatorPrefixSuffix(XTreePosition position, List<XOperator> prefix, XTreeExpr expr, List<XOperator> suffix) {
			super(position);
			this.prefix = prefix;
			this.expr = expr;
			this.suffix = suffix;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitOperatorPrefixSuffix(this);
		}
		
	}
	
	public static class XTreeIdent extends XTreeExpr{

		public String name;
		
		public XTreeIdent(XTreePosition position, String name) {
			super(position);
			this.name = name;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitIdent(this);
		}
		
	}
	
	public static class XTreeIndex extends XTreeExpr{
		
		public XTreeExpr array;
		public XTreeExpr index;
		
		public XTreeIndex(XTreePosition position, XTreeExpr array, XTreeExpr index) {
			super(position);
			this.array = array;
			this.index = index;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitIndex(this);
		}
		
	}
	
	public static class XTreeInstanceof extends XTreeExpr{

		public XTreeExpr statement;
		public XTreeExpr type;
		
		public XTreeInstanceof(XTreePosition position, XTreeExpr statement, XTreeExpr type) {
			super(position);
			this.statement = statement;
			this.type = type;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitInstanceof(this);
		}
		
	}
	
	public static class XTreeIssubclass extends XTreeExpr{

		public XTreeExpr statement;
		public XTreeExpr type;
		
		public XTreeIssubclass(XTreePosition position, XTreeExpr statement, XTreeExpr type) {
			super(position);
			this.statement = statement;
			this.type = type;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitIssubclass(this);
		}
		
	}
	
	public static class XTreeTypeof extends XTreeExpr{

		public XTreeExpr statement;
		
		public XTreeTypeof(XTreePosition position, XTreeExpr statement) {
			super(position);
			this.statement = statement;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitTypeof(this);
		}
		
	}
	
	public static class XTreeSwitch extends XTreeStatement{

		public XTreeExpr statement;
		
		public List<XTreeCase> cases;
		
		public XTreeSwitch(XTreePosition position, XTreeExpr statement, List<XTreeCase> cases) {
			super(position);
			this.statement = statement;
			this.cases = cases;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitSwitch(this);
		}
		
	}
	
	public static class XTreeCase extends XTree{

		public XTreeExpr key;
		
		public List<XTreeStatement> block;
		
		public XTreeCase(XTreePosition position, XTreeExpr key, List<XTreeStatement> block) {
			super(position);
			this.key = key;
			this.block = block;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitCase(this);
		}
		
	}
	
	public static class XTreeConstant extends XTreeExpr{

		public Object value;
		
		public XTreeConstant(XTreePosition position, Object value) {
			super(position);
			this.value = value;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitConstant(this);
		}
		
	}
	
	public static class XTreeCall extends XTreeExpr{
		
		public XTreeExpr method;
		public List<XTreeExpr> params;
		public List<XTreeIdent> kws;
		
		public XTreeCall(XTreePosition position, XTreeExpr method, List<XTreeExpr> params, List<XTreeIdent> kws) {
			super(position);
			this.method = method;
			this.params = params;
			this.kws = kws;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitCall(this);
		}
		
	}
	
	public static class XTreeMakeTuple extends XTreeExpr{
		
		public List<XTreeExpr> exprs;
		
		public XTreeMakeTuple(XTreePosition position, List<XTreeExpr> exprs) {
			super(position);
			this.exprs = exprs;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitMakeTuple(this);
		}
		
	}
	
	public static class XTreeMakeList extends XTreeExpr{
		
		public List<XTreeExpr> exprs;
		
		public XTreeMakeList(XTreePosition position, List<XTreeExpr> exprs) {
			super(position);
			this.exprs = exprs;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitMakeList(this);
		}
		
	}
	
	public static class XTreeMakeMap extends XTreeExpr{
		
		public List<XTreeMapEntry> entries;
		
		public XTreeMakeMap(XTreePosition position, List<XTreeMapEntry> entries) {
			super(position);
			this.entries = entries;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitMakeMap(this);
		}
		
	}
	
	public static class XTreeMapEntry extends XTree{
		
		public XTreeExpr key;
		public XTreeExpr expr;
		
		public XTreeMapEntry(XTreePosition position, XTreeExpr key, XTreeExpr expr) {
			super(position);
			this.key = key;
			this.expr = expr;
		}

		@Override
		public void accept(XVisitor v) {
			v.visitMapEntry(this);
		}
		
	}
	
	public static class XTreeEmpty extends XTreeStatement{
		
		public XTreeEmpty(XTreePosition position) {
			super(position);
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitEmpty(this);
		}
		
	}
	
	public static class XTreeVarDecl extends XTreeLowExpr {
		
		public List<XTreeVarDeclEntry> entries;
		public XVarAccess access;
		
		public XTreeVarDecl(XTreePosition position, List<XTreeVarDeclEntry> entries, XVarAccess access) {
			super(position);
			this.entries = entries;
			this.access = access;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitVarDecl(this);
		}
		
	}
	
	public static class XTreeVarDeclEntry extends XTree {
		
		public XTreeIdent ident;
		public XTreeExpr def;
		
		public XTreeVarDeclEntry(XTreePosition position, XTreeIdent ident, XTreeExpr def) {
			super(position);
			this.ident = ident;
			this.def = def;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitVarDeclEntry(this);
		}
		
	}
	
	public static class XTreeDelete extends XTreeStatement{
		
		public XTreeExpr expr;
		
		public XTreeDelete(XTreePosition position, XTreeExpr expr) {
			super(position);
			this.expr = expr;
		}
		
		@Override
		public void accept(XVisitor v) {
			v.visitDelete(this);
		}
		
	}
	
	public XTreePosition position;
	
	public XTree(XTreePosition position){
		this.position = position;
	}
	
	public abstract void accept(XVisitor v);
	
}
