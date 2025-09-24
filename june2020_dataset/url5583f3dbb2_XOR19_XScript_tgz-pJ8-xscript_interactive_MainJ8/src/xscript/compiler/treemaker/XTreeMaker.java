package xscript.compiler.treemaker;

import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticListener;

import xscript.compiler.XDiagnostic;
import xscript.compiler.XOperator;
import xscript.compiler.XOperator.Type;
import xscript.compiler.XTreePosition;
import xscript.compiler.XVarAccess;
import xscript.compiler.parser.XToken;
import xscript.compiler.parser.XTokenKind;
import xscript.compiler.parser.XTokenizer;
import xscript.compiler.tree.XTree;
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
import xscript.compiler.tree.XTree.XTreeExpr;
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
import xscript.compiler.tree.XTree.XTreeLowExpr;
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
import xscript.compiler.tree.XTree.XTreeStatement;
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

public class XTreeMaker {
	
	private XTokenizer tokenizer;
	private DiagnosticListener<String> diagnosticListener;
	private List<XTreePosition> positions = new ArrayList<XTreePosition>();
	private boolean skip;
	
	public XTreeMaker(XTokenizer tokenizer, DiagnosticListener<String> diagnosticListener){
		this.tokenizer = tokenizer;
		this.diagnosticListener = diagnosticListener;
	}

	private void skip(){
		if(skip){
			skip = false;
			XToken token = tokenizer.getToken();
			while(token.kind==XTokenKind.KEYWORD){
				switch((XKeyword)token.data){
				case AND:
				case CASE:
				case CATCH:
				case COLON:
				case COMMA:
				case DEFAULT:
				case DIV:
				case DOT:
				case ELSE:
				case EQUAL:
				case FINALLY:
				case GREATER:
				case IDIV:
				case INSTANCEOF:
				case ISSUBCLASS:
				case MOD:
				case MUL:
				case OR:
				case QUESTIONMARK:
				case RBRAKET:
				case RINDEX:
				case RSCOPE:
				case SAND:
				case SBAND:
				case SBOR:
				case SMALLER:
				case SMOD:
				case SOR:
				case SPOW:
				case SXOR:
				case XOR:
				case AS:
					break;
				default:
					return;
				}
				token = tokenizer.next();
			}
		}
	}
	
	public XTreeModule makeModule() {
		tokenizer.next();
		startPosition();
		List<XTreeStatement> list = new ArrayList<XTreeStatement>();
		while(!tokenEof()){
			list.add(makeStatement());
			skip();
		}
		XTreePosition position = endPosition();
		XTreeScope scope = new XTreeScope(position.clone(), list);
		return new XTreeModule(position, scope);
	}
	
	public XTreeModule makeModuleInteractive() {
		tokenizer.next();
		startPosition();
		List<XTreeStatement> list = new ArrayList<XTreeStatement>();
		while(!tokenEof()){
			list.add(makeStatementInteractive());
			skip();
		}
		XTreePosition position = endPosition();
		XTreeScope scope = new XTreeScope(position.clone(), list);
		return new XTreeModule(position, scope);
	}
	
	public XTreeLowExpr makeExprOrDecl(){
		XToken token = tokenizer.getToken();
		if(token.kind==XTokenKind.KEYWORD){
			startPosition();
			switch((XKeyword)token.data){
			case CLASS:
				return makeClassPosStarted(true);
			case FUNC:
				return makeFuncPosStarted(true);
			case GLOBAL:{
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.CLASS)){
					return makeClassPosStarted(true, XVarAccess.GLOBAL);
				}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.FUNC)){
					return makeFuncPosStarted(true, XVarAccess.GLOBAL);
				}
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.GLOBAL);
			}case LOCAL:{
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.CLASS)){
					return makeClassPosStarted(true, XVarAccess.LOCAL);
				}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.FUNC)){
					return makeFuncPosStarted(true, XVarAccess.LOCAL);
				}
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.LOCAL);
			}case NONLOCAL:{
				tokenizer.next();
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.NONLOCAL);
			}
			default:
				break;
			}
			endPosition();
		}
		return makeExpr();
	}
	
	public XTreeStatement makeStatement() {
		XToken token = tokenizer.getToken();
		if(token.kind==XTokenKind.KEYWORD){
			startPosition();
			XTree t = null;
			XTree t2 = null;
			XTree t3 = null;
			XTree t4 = null;
			switch((XKeyword)token.data){
			case ASSERT:
				tokenizer.next();
				t = makeExpr();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
					tokenizer.next();
					t2 = makeExpr();
				}
				expect(XKeyword.SEMICOLON);
				return new XTreeAssert(endPosition(), (XTreeExpr)t, (XTreeExpr)t2);
			case BREAK:
				if(tokenizer.next().kind==XTokenKind.IDENT){
					t = makeIdent();
				}
				expect(XKeyword.SEMICOLON);
				return new XTreeBreak(endPosition(), (XTreeIdent)t);
			case CLASS:
				return makeClassPosStarted(true);
			case CONTINUE:
				if(tokenizer.next().kind==XTokenKind.IDENT){
					t = makeIdent();
				}
				expect(XKeyword.SEMICOLON);
				return new XTreeContinue(endPosition(), (XTreeIdent)t);
			case DELETE:
				tokenizer.next();
				t = makeExpr();
				expect(XKeyword.SEMICOLON);
				return new XTreeDelete(endPosition(), (XTreeExpr)t);
			case DO:
				tokenizer.next();
				t = makeStatement();
				expect(XKeyword.WHILE);
				expect(XKeyword.LBRAKET);
				t2 = makeExpr();
				expect(XKeyword.RBRAKET);
				expect(XKeyword.SEMICOLON);
				return new XTreeDo(endPosition(), (XTreeExpr)t2, (XTreeStatement)t);
			case FOR:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExprOrDecl();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON) && (t instanceof XTreeIdent || (t instanceof XTreeVarDecl && ((XTreeVarDecl)t).entries.size()==1))){
					tokenizer.next();
					t2 = makeExpr();
					expect(XKeyword.RBRAKET);
					t3 = makeStatement();
					return new XTreeForeach(endPosition(), (XTreeExpr)t, (XTreeExpr)t2, (XTreeStatement)t3);
				}
				expect(XKeyword.SEMICOLON);
				t2 = makeExpr();
				expect(XKeyword.SEMICOLON);
				t3 = makeExpr();
				expect(XKeyword.RBRAKET);
				t4 = makeStatement();
				return new XTreeFor(endPosition(), (XTreeLowExpr)t, (XTreeExpr)t2, (XTreeExpr)t3, (XTreeStatement)t4);
			case FOREACH:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeVarDelc();
				expect(XKeyword.COLON);
				t2 = makeExpr();
				expect(XKeyword.RBRAKET);
				t3 = makeStatement();
				return new XTreeForeach(endPosition(), (XTreeLowExpr)t, (XTreeExpr)t2, (XTreeStatement)t3);
			case FROM:{
				tokenizer.next();
				t = makeIdentEx();
				expect(XKeyword.IMPORT);
				List<XTreeImportEntry> imports = makeImports(true);
				expect(XKeyword.SEMICOLON);
				return new XTreeImportFrom(endPosition(), (XTreeIdent) t, imports);
			}case FUNC:
				return makeFuncPosStarted(true);
			case GLOBAL:{
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.CLASS)){
					return makeClassPosStarted(true, XVarAccess.GLOBAL);
				}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.FUNC)){
					return makeFuncPosStarted(true, XVarAccess.GLOBAL);
				}
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				expect(XKeyword.SEMICOLON);
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.GLOBAL);
			}case IF:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				t2 = makeStatement();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.ELSE)){
					tokenizer.next();
					t3 = makeStatement();
				}
				return new XTreeIf(endPosition(), (XTreeExpr)t, (XTreeStatement)t2, (XTreeStatement)t3);
			case IMPORT:
			{
				tokenizer.next();
				List<XTreeImportEntry> imports = makeImports(false);
				expect(XKeyword.SEMICOLON);
				return new XTreeImport(endPosition(), imports);
			}
			case LOCAL:{
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.CLASS)){
					return makeClassPosStarted(true, XVarAccess.LOCAL);
				}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.FUNC)){
					return makeFuncPosStarted(true, XVarAccess.LOCAL);
				}
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				expect(XKeyword.SEMICOLON);
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.LOCAL);
			}case NONLOCAL:{
				tokenizer.next();
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				expect(XKeyword.SEMICOLON);
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.NONLOCAL);
			}case RETURN:
				tokenizer.next();
				if(!tokenOk(XTokenKind.KEYWORD, XKeyword.SEMICOLON))
					t = makeExpr();
				expect(XKeyword.SEMICOLON);
				return new XTreeReturn(endPosition(), (XTreeExpr)t);
			case SEMICOLON:
				tokenizer.next();
				return new XTreeEmpty(endPosition());
			case SYNCHRONIZED:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				t2 = makeScope();
				return new XTreeSynchronized(endPosition(), (XTreeExpr)t, (XTreeScope)t2);
			case SWITCH:
				return makeSwitchPosStarted();
			case THROW:
				tokenizer.next();
				t = makeExpr();
				expect(XKeyword.SEMICOLON);
				return new XTreeThrow(endPosition(), (XTreeExpr)t);
			case TRY:
				return makeTryPosStarted();
			case WHILE:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				t2 = makeStatement();
				return new XTreeWhile(endPosition(), (XTreeExpr)t, (XTreeStatement)t2);
			case LSCOPE:
				return makeScope();
			default:
				break;
			}
			endPosition();
		}
		XTreeStatement statement = makeExpr();
		if(statement instanceof XTreeIdent && tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
			statement = new XTreeLabel(statement.position.clone(), (XTreeIdent)statement, makeStatement());
		}else{
			skip = !expect(XKeyword.SEMICOLON);
		}
		return statement;
	}
	
	public XTreeStatement makeStatementInteractive() {
		XToken token = tokenizer.getToken();
		if(token.kind==XTokenKind.KEYWORD){
			startPosition();
			XTree t = null;
			XTree t2 = null;
			XTree t3 = null;
			XTree t4 = null;
			switch((XKeyword)token.data){
			case ASSERT:
				tokenizer.next();
				t = makeExpr();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
					tokenizer.next();
					t2 = makeExpr();
				}
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeAssert(endPosition(), (XTreeExpr)t, (XTreeExpr)t2);
			case BREAK:
				if(tokenizer.next().kind==XTokenKind.IDENT){
					t = makeIdent();
				}
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeBreak(endPosition(), (XTreeIdent)t);
			case CONTINUE:
				if(tokenizer.next().kind==XTokenKind.IDENT){
					t = makeIdent();
				}
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeContinue(endPosition(), (XTreeIdent)t);
			case DELETE:
				tokenizer.next();
				t = makeExpr();
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeDelete(endPosition(), (XTreeExpr)t);
			case DO:
				tokenizer.next();
				t = makeStatement();
				expect(XKeyword.WHILE);
				expect(XKeyword.LBRAKET);
				t2 = makeExpr();
				expect(XKeyword.RBRAKET);
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeDo(endPosition(), (XTreeExpr)t2, (XTreeStatement)t);
			case FOR:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExprOrDecl();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON) && (t instanceof XTreeIdent || (t instanceof XTreeVarDecl && ((XTreeVarDecl)t).entries.size()==1))){
					tokenizer.next();
					t2 = makeExpr();
					expect(XKeyword.RBRAKET);
					t3 = makeStatement();
					return new XTreeForeach(endPosition(), (XTreeExpr)t, (XTreeExpr)t2, (XTreeStatement)t3);
				}
				expect(XKeyword.SEMICOLON);
				t2 = makeExpr();
				expect(XKeyword.SEMICOLON);
				t3 = makeExpr();
				expect(XKeyword.RBRAKET);
				t4 = makeStatement();
				return new XTreeFor(endPosition(), (XTreeLowExpr)t, (XTreeExpr)t2, (XTreeExpr)t3, (XTreeStatement)t4);
			case FOREACH:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeVarDelc();
				expect(XKeyword.COLON);
				t2 = makeExpr();
				expect(XKeyword.RBRAKET);
				t3 = makeStatement();
				return new XTreeForeach(endPosition(), (XTreeLowExpr)t, (XTreeExpr)t2, (XTreeStatement)t3);
			case FROM:{
				tokenizer.next();
				t = makeIdentEx();
				expect(XKeyword.IMPORT);
				List<XTreeImportEntry> imports = makeImports(true);
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeImportFrom(endPosition(), (XTreeIdent) t, imports);
			}
			case GLOBAL:{
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.CLASS)){
					return makeClassPosStarted(true, XVarAccess.GLOBAL);
				}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.FUNC)){
					return makeFuncPosStarted(true, XVarAccess.GLOBAL);
				}
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.GLOBAL);
			}case IF:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				t2 = makeStatement();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.ELSE)){
					tokenizer.next();
					t3 = makeStatement();
				}
				return new XTreeIf(endPosition(), (XTreeExpr)t, (XTreeStatement)t2, (XTreeStatement)t3);
			case IMPORT:
			{
				tokenizer.next();
				List<XTreeImportEntry> imports = makeImports(false);
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeImport(endPosition(), imports);
			}
			case LOCAL:{
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.CLASS)){
					return makeClassPosStarted(true, XVarAccess.LOCAL);
				}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.FUNC)){
					return makeFuncPosStarted(true, XVarAccess.LOCAL);
				}
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.LOCAL);
			}case NONLOCAL:{
				tokenizer.next();
				List<XTreeVarDeclEntry> entries = makeDeclEntries();
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeVarDecl(endPosition(), entries, XVarAccess.NONLOCAL);
			}case RETURN:
				tokenizer.next();
				if(!tokenOk(XTokenKind.KEYWORD, XKeyword.SEMICOLON))
					t = makeExpr();
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeReturn(endPosition(), (XTreeExpr)t);
			case SEMICOLON:
				tokenizer.next();
				return new XTreeEmpty(endPosition());
			case SYNCHRONIZED:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				t2 = makeScope();
				return new XTreeSynchronized(endPosition(), (XTreeExpr)t, (XTreeScope)t2);
			case SWITCH:
				return makeSwitchPosStarted();
			case THROW:
				tokenizer.next();
				t = makeExpr();
				expectOrEOF(XKeyword.SEMICOLON);
				return new XTreeThrow(endPosition(), (XTreeExpr)t);
			case TRY:
				return makeTryPosStarted();
			case WHILE:
				tokenizer.next();
				expect(XKeyword.LBRAKET);
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				t2 = makeStatement();
				return new XTreeWhile(endPosition(), (XTreeExpr)t, (XTreeStatement)t2);
			case LSCOPE:
				return makeScope();
			default:
				break;
			}
			endPosition();
		}
		XTreeStatement statement = makeExpr();
		if(statement instanceof XTreeIdent && tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
			statement = new XTreeLabel(statement.position.clone(), (XTreeIdent)statement, makeStatementInteractive());
		}else if(tokenEof()){
			return new XTreeReturn(statement.position.clone(), (XTreeExpr)statement);
		}else{
			skip = !expect(XKeyword.SEMICOLON);
		}
		return statement;
	}
	
	private XTreeLowExpr makeVarDelc() {
		if(tokenOk(XTokenKind.KEYWORD, XKeyword.LOCAL)){
			startPosition();
			tokenizer.next();
			List<XTreeVarDeclEntry> entry = new ArrayList<XTree.XTreeVarDeclEntry>(1);
			startPosition();
			XTreeIdent ident = makeIdent();
			entry.add(new XTreeVarDeclEntry(endPosition(), ident, null));
			return new XTreeVarDecl(endPosition(), entry, XVarAccess.LOCAL);
		}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.GLOBAL)){
			startPosition();
			tokenizer.next();
			List<XTreeVarDeclEntry> entry = new ArrayList<XTree.XTreeVarDeclEntry>(1);
			startPosition();
			XTreeIdent ident = makeIdent();
			entry.add(new XTreeVarDeclEntry(endPosition(), ident, null));
			return new XTreeVarDecl(endPosition(), entry, XVarAccess.GLOBAL);
		}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.NONLOCAL)){
			startPosition();
			tokenizer.next();
			List<XTreeVarDeclEntry> entry = new ArrayList<XTree.XTreeVarDeclEntry>(1);
			startPosition();
			XTreeIdent ident = makeIdent();
			entry.add(new XTreeVarDeclEntry(endPosition(), ident, null));
			return new XTreeVarDecl(endPosition(), entry, XVarAccess.NONLOCAL);
		}else{
			return makeIdent();
		}
	}

	private List<XTreeVarDeclEntry> makeDeclEntries() {
		List<XTreeVarDeclEntry> list = new ArrayList<XTree.XTreeVarDeclEntry>();
		startPosition();
		XTreeIdent ident = makeIdent();
		XTreeExpr def = null;
		if(tokenOk(XTokenKind.KEYWORD, XKeyword.EQUAL)){
			tokenizer.next();
			def = makeExpr(true);
		}
		list.add(new XTreeVarDeclEntry(endPosition(), ident, def));
		while(tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA)){
			ident = makeIdent();
			def = null;
			if(tokenOk(XTokenKind.KEYWORD, XKeyword.EQUAL)){
				tokenizer.next();
				def = makeExpr(true);
			}
			list.add(new XTreeVarDeclEntry(endPosition(), ident, def));
		}
		return list;
	}

	private List<XTreeImportEntry> makeImports(boolean from) {
		List<XTreeImportEntry> l = new ArrayList<XTreeImportEntry>();
		if(from && tokenOk(XTokenKind.KEYWORD, XKeyword.MUL)){
			XToken token2 = tokenizer.getToken();
			XTreeIdent ident = new XTreeIdent(new XTreePosition(token2.pos, token2.pos.pos, token2.end), "*");
			l.add(new XTreeImportEntry(new XTreePosition(token2.pos, token2.pos.pos, token2.end), ident, null));
		}else{
			l.add(makeImportEntry(from));
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA)){
				tokenizer.next();
				l.add(makeImportEntry(from));
			}
		}
		return l;
	}

	private XTreeImportEntry makeImportEntry(boolean from){
		startPosition();
		XTreeIdent as;
		XTreeIdent ident = from?makeIdent():makeIdentEx();
		if(tokenOk(XTokenKind.KEYWORD, XKeyword.AS)){
			tokenizer.next();
			as = makeIdent();
		}else{
			as = null;
		}
		return new XTreeImportEntry(endPosition(), ident, as);
	}
	
	private XTreeExpr makeExpr(){
		return makeExpr(false);
	}
	
	private XTreeExpr makeExpr(boolean inList){
		XTreeExpr expr = makeExprPrefixSuffix(inList);
		XTreeExpr between = null;
		while(isOperator()){
			startPosition();
			between = null;
			XOperator o = readOperator(Type.INFIX, inList);
			if(o==XOperator.IF){
				between = makeExprPrefixSuffix(inList);
				expect(XKeyword.COLON);
			}
			if(o==XOperator.NONE){
				break;
			}
			XTreeExpr expr2  = makeExprPrefixSuffix(inList);
			expr = mergeStatements(endPosition(), expr, o, expr2, between);
		}
		return expr;
	}
	
	private XTreeExpr mergeStatements(XTreePosition position, XTreeExpr left, XOperator o, XTreeExpr right, XTreeExpr between){
		if(left instanceof XTreeOperator){
			XTreeOperator oLeft = (XTreeOperator) left;
			if(oLeft.operators.get(0).priority<o.priority){
				oLeft.exprs.set(oLeft.exprs.size()-1,  mergeStatements(position, oLeft.exprs.get(oLeft.exprs.size()-1), o, right, between));
				return oLeft;
			}else{
				if(o==XOperator.IF)
					return new XTreeOperatorIf(position, left, between, right);
				if(oLeft.operators.get(0).priority==o.priority){
					oLeft.operators.add(o);
					oLeft.exprs.add(right);
					return oLeft;
				}else{
					List<XTreeExpr> exprs = new ArrayList<XTreeExpr>();
					exprs.add(left);
					exprs.add(right);
					List<XOperator> operators = new ArrayList<XOperator>();
					operators.add(o);
					return new XTreeOperator(position, exprs, operators);
				}
			}
		}else if(left instanceof XTreeOperatorIf){
			XTreeOperatorIf oLeft = (XTreeOperatorIf) left;
			if(XOperator.IF.priority<o.priority){
				oLeft.onFalse = mergeStatements(position, oLeft.onFalse, o, right, between);
				return oLeft;
			}else{
				if(o==XOperator.IF)
					return new XTreeOperatorIf(position, left, between, right);
				List<XTreeExpr> exprs = new ArrayList<XTreeExpr>();
				exprs.add(left);
				exprs.add(right);
				List<XOperator> operators = new ArrayList<XOperator>();
				operators.add(o);
				return new XTreeOperator(position, exprs, operators);
			}
		}else{
			if(o==XOperator.IF)
				return new XTreeOperatorIf(position, left, between, right);
			List<XTreeExpr> exprs = new ArrayList<XTreeExpr>();
			exprs.add(left);
			exprs.add(right);
			List<XOperator> operators = new ArrayList<XOperator>();
			operators.add(o);
			return new XTreeOperator(position, exprs, operators);
		}
	}
	
	private XTreeExpr makeExprPrefixSuffix(boolean inList){
		startPosition();
		startPosition();
		XOperator operator;
		List<XOperator> prefix = new ArrayList<XOperator>();
		while(isOperator()){
			operator = readOperator(Type.PREFIX, true);
			if(operator==XOperator.NONE)
				break;
			prefix.add(0, operator);
		}
		if(prefix.isEmpty())
			prefix = null;
		XTreeExpr expr = makeExpr2(inList);
		List<XOperator> suffix = new ArrayList<XOperator>();
		while(isOperator()){
			operator = readOperator(Type.SUFFIX, true);
			if(operator==XOperator.NONE)
				break;
			suffix.add(operator);
		}
		if(suffix.isEmpty())
			suffix = null;
		if(suffix == null){
			endPosition();
		}else{
			expr = new XTreeOperatorPrefixSuffix(endPosition(), null, expr, suffix);
		}
		XToken token = tokenizer.getToken();
		while(token.kind==XTokenKind.KEYWORD && (token.data==XKeyword.LBRAKET || token.data==XKeyword.LINDEX || token.data==XKeyword.DOT || token.data==XKeyword.INSTANCEOF || token.data==XKeyword.ISSUBCLASS)){
			startPosition();
			startPosition();
			if(token.data==XKeyword.LBRAKET){
				expr = makeCallPosStarted(expr);
			}else if(token.data==XKeyword.LINDEX){
				tokenizer.next();
				XTreeExpr index = makeExpr();
				expect(XKeyword.RINDEX);
				expr = new XTreeIndex(endPosition(), expr, index);
			}else if(token.data==XKeyword.DOT){
				tokenizer.next();
				XTreeIdent ident = makeIdent();
				List<XTreeExpr> exprs = new ArrayList<XTreeExpr>();
				exprs.add(expr);
				exprs.add(ident);
				List<XOperator> operators = new ArrayList<XOperator>();
				operators.add(XOperator.ELEMENT);
				expr = new XTreeOperator(endPosition(), exprs, operators);
			}else if(token.data==XKeyword.INSTANCEOF){
				tokenizer.next();
				XTreeExpr type = makeExprPrefixSuffix(inList);
				expr = new XTreeInstanceof(endPosition(), expr, type);
			}else if(token.data==XKeyword.ISSUBCLASS){
				tokenizer.next();
				XTreeExpr type = makeExprPrefixSuffix(inList);
				expr = new XTreeIssubclass(endPosition(), expr, type);
			}
			suffix = new ArrayList<XOperator>();
			while(isOperator()){
				operator = readOperator(Type.SUFFIX, true);
				if(operator==XOperator.NONE)
					break;
				suffix.add(operator);
			}
			if(suffix.isEmpty())
				suffix = null;
			if(suffix == null){
				endPosition();
			}else{
				expr = new XTreeOperatorPrefixSuffix(endPosition(), null, expr, suffix);
			}
			token = tokenizer.getToken();
		}
		if(prefix == null){
			endPosition();
		}else if(expr instanceof XTreeOperatorPrefixSuffix){
			expr.position.end = endPosition().end;
			((XTreeOperatorPrefixSuffix) expr).prefix = prefix;
		}else{
			expr = new XTreeOperatorPrefixSuffix(endPosition(), prefix, expr, null);
		}
		return expr;
	}
	
	private XTreeExpr makeExpr2(boolean inList){
		startPosition();
		XToken token = tokenizer.getToken();
		switch(token.kind){
		case FLOAT:
		case INT:
		case STRING:
			tokenizer.next();
			return new XTreeConstant(endPosition(), token.data);
		case IDENT:
		{
			XTreeIdent ident = makeIdent();
			if(tokenOk(XTokenKind.KEYWORD, XKeyword.SUB)){
				tokenizer.mark();
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.GREATER) && tokenizer.getToken().spaces==0){
					tokenizer.apply();
					tokenizer.next();
					XTreeScope body;
					if(tokenOk(XTokenKind.KEYWORD, XKeyword.LSCOPE)){
						body = makeScope();
					}else{
						if(tokenOk(XTokenKind.KEYWORD, XKeyword.RETURN))
							tokenizer.next();
						List<XTreeStatement> statements = new ArrayList<XTreeStatement>();
						startPosition();
						startPosition();
						XTreeExpr expr = makeExpr(true);
						statements.add(new XTreeReturn(endPosition(), expr));
						body = new XTreeScope(endPosition(), statements);
					}
					List<XTreeIdent> params = new ArrayList<XTreeIdent>();
					params.add(ident);
					List<XTreeExpr> defaults = new ArrayList<XTreeExpr>() ;
					defaults.add(null);
					return new XTreeFunc(endPosition(), XVarAccess.NONE, null, params, -1, -1, defaults, body);
				}
				tokenizer.reset();
			}
			endPosition();
			return ident;
		}
		case KEYWORD:
			XTreeExpr t;
			switch((XKeyword)token.data){
			case CLASS:
				return makeClassPosStarted(false);
			case FALSE:
				tokenizer.next();
				return new XTreeConstant(endPosition(), false);
			case FUNC:
				return makeFuncPosStarted(false);
			case LBRAKET:
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.RBRAKET)){
					tokenizer.next();
					if(tokenOk(XTokenKind.KEYWORD, XKeyword.SUB)){
						tokenizer.mark();
						tokenizer.next();
						if(tokenOk(XTokenKind.KEYWORD, XKeyword.GREATER) && tokenizer.getToken().spaces==0){
							tokenizer.next();
							tokenizer.apply();
							XTreeScope body;
							if(tokenOk(XTokenKind.KEYWORD, XKeyword.LSCOPE)){
								body = makeScope();
							}else{
								if(tokenOk(XTokenKind.KEYWORD, XKeyword.RETURN))
									tokenizer.next();
								List<XTreeStatement> statements = new ArrayList<XTreeStatement>();
								startPosition();
								startPosition();
								XTreeExpr expr = makeExpr(true);
								statements.add(new XTreeReturn(endPosition(), expr));
								body = new XTreeScope(endPosition(), statements);
							}
							return new XTreeFunc(endPosition(), XVarAccess.NONE, null, new ArrayList<XTreeIdent>(), -1, -1, new ArrayList<XTreeExpr>(), body);
						}
						tokenizer.reset();
					}
					return new XTreeMakeTuple(endPosition(), new ArrayList<XTreeExpr>());
				}
				t = makeExpr();
				expect(XKeyword.RBRAKET);
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.SUB)){
					tokenizer.mark();
					tokenizer.next();
					if(tokenOk(XTokenKind.KEYWORD, XKeyword.GREATER) && tokenizer.getToken().spaces==0){
						tokenizer.next();
						tokenizer.apply();
						XTreeScope body;
						if(tokenOk(XTokenKind.KEYWORD, XKeyword.LSCOPE)){
							body = makeScope();
						}else{
							if(tokenOk(XTokenKind.KEYWORD, XKeyword.RETURN))
								tokenizer.next();
							List<XTreeStatement> statements = new ArrayList<XTreeStatement>();
							startPosition();
							startPosition();
							XTreeExpr expr = makeExpr(true);
							statements.add(new XTreeReturn(endPosition(), expr));
							body = new XTreeScope(endPosition(), statements);
						}
						List<XTreeIdent> params = new ArrayList<XTreeIdent>();
						List<XTreeExpr> defaults = new ArrayList<XTreeExpr>() ;
						int[] ret = makeFuncParams(t, params, defaults);
						return new XTreeFunc(endPosition(), XVarAccess.NONE, null, params, ret[0], ret[1], defaults, body);
					}
					tokenizer.reset();
				}
				return new XTreeGroup(endPosition(), t);
			case LINDEX:
				return makeMakeListPosStarted();
			case LSCOPE:
				return makeMakeMapPosStarted();
			case NULL:
				tokenizer.next();
				return new XTreeConstant(endPosition(), null);
			case SUPER:
				tokenizer.next();
				return new XTreeSuper(endPosition());
			case THIS:
				tokenizer.next();
				return new XTreeThis(endPosition());
			case TRUE:
				tokenizer.next();
				return new XTreeConstant(endPosition(), true);
			case TYPEOF:
				tokenizer.next();
				t = makeExpr2(inList);
				return new XTreeTypeof(endPosition(), t);
			case YIELD:
				tokenizer.next();
				if(!tokenOk(XTokenKind.KEYWORD, XKeyword.SEMICOLON) && !tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA))
					t = makeExpr();
				else
					t=null;
				return new XTreeYield(endPosition(), t);
			default:
				addDiagnostic("value.unexpected.keyword", token.data);
				break;
			}
			break;
		default:
			addDiagnostic("value.unexpected.kind", token.kind);
			break;
		}
		return new XTreeConstant(endPosition(), null);
	}
	
	private int[] makeFuncParams(XTreeExpr expr, List<XTreeIdent> params, List<XTreeExpr> defaults){
		int listParam = -1;
		int kwParam = -1;
		List<XTreeExpr> list = makeList(expr);
		for(XTreeExpr param:list){
			XTreeExpr ident = param;
			if(param instanceof XTreeOperator){
				XTreeOperator o = (XTreeOperator)param;
				if(o.operators.get(0)==XOperator.LET){
					ident = o.exprs.remove(0);
					o.operators.remove(0);
					if(o.operators.isEmpty()){
						param = o.exprs.get(0);
					}else{
						o.position.position = o.exprs.get(0).position.position.clone();
						o.position.start = o.exprs.get(0).position.start;
					}
				}
			}else{
				param = null;
			}
			if(ident instanceof XTreeOperatorPrefixSuffix){
				XTreeOperatorPrefixSuffix o = (XTreeOperatorPrefixSuffix)ident;
				if(o.prefix.get(0)==XOperator.UNPACKMAP){
					if(kwParam!=-1)
						addDiagnostic(o, "func.only.one.kw");
					else
						kwParam = params.size();
				}else if(o.prefix.get(0)==XOperator.UNPACKLIST){
					if(kwParam!=-1)
						addDiagnostic(o, "func.list.after.kw");
					else if(listParam!=-1)
						addDiagnostic(o, "func.only.one.list");
					else
						listParam = params.size();
				}
				if(o.prefix.size()==1 && o.suffix.isEmpty()){
					ident = o.expr;
				}
			}
			if(ident instanceof XTreeIdent){
				params.add((XTreeIdent)ident);
			}else{
				addDiagnostic("expect.ident", ident);
				params.add(new XTreeIdent(ident.position.clone(), "!error!"));
			}
			defaults.add(param);
		}
		return new int[]{listParam, kwParam};
	}
	
	private List<XTreeExpr> makeList(XTreeExpr expr){
		List<XTreeExpr> list;
		if(expr instanceof XTreeOperator){
			XTreeOperator o = (XTreeOperator)expr;
			if(o.operators.get(0)==XOperator.COMMA){
				return o.exprs;
			}else if(o.operators.get(0).priority>XOperator.COMMA.priority){
				list = new ArrayList<XTreeExpr>();
				list.add(expr);
			}else{
				list = new ArrayList<XTreeExpr>();
				List<XTreeExpr> l = makeList(o.exprs.get(0));
				XTreeExpr last = l.remove(l.size()-1);
				list.addAll(l);
				List<XTreeExpr> opexpr = new ArrayList<XTreeExpr>();
				List<XOperator> ops = new ArrayList<XOperator>();
				opexpr.add(last);
				for(int i=0; i<o.operators.size(); i++){
					XOperator operator = o.operators.get(i);
					ops.add(operator);
					l = makeList(o.exprs.get(i+1));
					last = l.remove(l.size()-1);
					if(!l.isEmpty()){
						XTreeExpr first = l.remove(0);
						opexpr.add(first);
						list.add(new XTreeOperator(new XTreePosition(opexpr.get(0).position.position.clone(), opexpr.get(0).position.start, opexpr.get(opexpr.size()-1).position.end), opexpr, ops));
						opexpr = new ArrayList<XTreeExpr>();
						ops = new ArrayList<XOperator>();
						list.addAll(l);
					}
					opexpr.add(last);
				}
				if(opexpr.size()>1){
					list.add(new XTreeOperator(new XTreePosition(opexpr.get(0).position.position.clone(), opexpr.get(0).position.start, opexpr.get(opexpr.size()-1).position.end), opexpr, ops));
				}else{
					list.add(opexpr.get(0));
				}
			}
		}else{
			list = new ArrayList<XTreeExpr>();
			list.add(expr);
		}
		return list;
	}
	
	private XTreeScope makeScope(){
		startPosition();
		expect(XKeyword.LSCOPE);
		List<XTreeStatement> list = new ArrayList<XTreeStatement>();
		while(!tokenOk(XTokenKind.KEYWORD, XKeyword.RSCOPE) && !tokenEof()){
			list.add(makeStatement());
			skip();
		}
		expect(XKeyword.RSCOPE);
		return new XTreeScope(endPosition(), list);
	}
	
	private XTreeIdent makeIdent() {
		XToken token = tokenizer.getToken();
		if(token.kind==XTokenKind.IDENT){
			tokenizer.next();
			return new XTreeIdent(new XTreePosition(token.pos, token.pos.pos, token.end), (String)token.data);
		}
		addDiagnostic("expect.ident", token);
		return new XTreeIdent(new XTreePosition(token.pos, token.pos.pos, token.end), "!error!");
	}
	
	private XTreeIdent makeIdentEx() {
		XToken token = tokenizer.getToken();
		if(token.kind!=XTokenKind.IDENT){
			addDiagnostic("expect.ident", token);
			return new XTreeIdent(new XTreePosition(token.pos, token.pos.pos, token.end), "!error!");
		}
		startPosition();
		String name = (String)token.data;
		while(tokenizer.next().data==XKeyword.DOT){
			token = tokenizer.next();
			if(token.kind!=XTokenKind.IDENT){
				addDiagnostic("expect.ident", token);
				return new XTreeIdent(endPosition(), name+".!error!");
			}
			name += "."+(String)token.data;
		}
		return new XTreeIdent(endPosition(), name);
	}
	
	private XTreeClass makeClassPosStarted(boolean needName){
		return makeClassPosStarted(needName, XVarAccess.NONE);
	}
	
	//type 0=none, 1=local, 2=nonlocal, 3=global
	private XTreeClass makeClassPosStarted(boolean needName, XVarAccess access){
		expect(XKeyword.CLASS);
		XTreeIdent name;
		if(needName || tokenizer.getToken().kind==XTokenKind.IDENT){
			name = makeIdent();
		}else{
			name = null;
		}
		XTreeExpr superClasses;
		if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
			tokenizer.next();
			superClasses = makeExpr();
		}else{
			superClasses = null;
		}
		XTreeScope init = makeScope();
		return new XTreeClass(endPosition(), access, name, superClasses, init);
	}
	
	private XTreeFunc makeFuncPosStarted(boolean needName){
		return makeFuncPosStarted(needName, XVarAccess.NONE);
	}
	
	private XTreeFunc makeFuncPosStarted(boolean needName, XVarAccess access){
		expect(XKeyword.FUNC);
		XTreeIdent name;
		if(needName || tokenizer.getToken().kind==XTokenKind.IDENT){
			name = makeIdent();
		}else{
			name = null;
		}
		List<XTreeIdent> params = new ArrayList<XTreeIdent>();
		int listParam = -1;
		int kwParam = -1;
		List<XTreeExpr> defaults = new ArrayList<XTreeExpr>();
		expect(XKeyword.LBRAKET);
		
		if(tokenizer.getToken().kind==XTokenKind.IDENT || tokenOk(XTokenKind.KEYWORD, XKeyword.MUL)){
			
			if(tokenOk(XTokenKind.KEYWORD, XKeyword.MUL)){
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.MUL)){
					tokenizer.next();
					kwParam = 0;
				}else{
					listParam = 0;
				}
			}
			
			params.add(makeIdent());
			
			if(tokenOk(XTokenKind.KEYWORD, XKeyword.EQUAL) || tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
				tokenizer.next();
				defaults.add(makeExpr(true));
			}else{
				defaults.add(null);
			}
			
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA)){
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.MUL)){
					tokenizer.next();
					if(tokenOk(XTokenKind.KEYWORD, XKeyword.MUL)){
						tokenizer.next();
						if(kwParam!=-1)
							addDiagnostic("func.only.one.kw");
						else
							kwParam = params.size();
					}else{
						if(kwParam!=-1)
							addDiagnostic("func.list.after.kw");
						else if(listParam!=-1)
							addDiagnostic("func.only.one.list");
						else
							listParam = params.size();
					}
				}
				
				params.add(makeIdent());
				
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.EQUAL) || tokenOk(XTokenKind.KEYWORD, XKeyword.COLON)){
					tokenizer.next();
					defaults.add(makeExpr(true));
				}else{
					defaults.add(null);
				}
				
			}
			
		}
		
		expect(XKeyword.RBRAKET);
		XTreeScope body = makeScope();
		return new XTreeFunc(endPosition(), access, name, params, listParam, kwParam, defaults, body);
	}
	
	private XTreeSwitch makeSwitchPosStarted(){
		expect(XKeyword.SWITCH);
		expect(XKeyword.LBRAKET);
		XTreeExpr expr = makeExpr();
		expect(XKeyword.RBRAKET);
		expect(XKeyword.LSCOPE);
		List<XTreeCase> cases  = new ArrayList<XTreeCase>();
		List<XTreeStatement> block = null;
		XTreeExpr key = null;
		startPosition();
		if(!tokenOk(XTokenKind.KEYWORD, XKeyword.RSCOPE)&&!tokenOk(XTokenKind.KEYWORD, XKeyword.DEFAULT)&&!tokenOk(XTokenKind.KEYWORD, XKeyword.CASE)){
			expect(XKeyword.CASE);
		}
		while(!tokenOk(XTokenKind.KEYWORD, XKeyword.RSCOPE) && !tokenEof()){
			if(tokenOk(XTokenKind.KEYWORD, XKeyword.CASE)){
				if(block==null){
					endPosition();
				}else if(block!=null){
					cases.add(new XTreeCase(endPosition(), key, block));
				}
				startPosition();
				tokenizer.next();
				block = new ArrayList<XTreeStatement>();
				key = makeExpr();
				expect(XKeyword.COLON);
			}else if(tokenOk(XTokenKind.KEYWORD, XKeyword.DEFAULT)){
				if(block==null){
					endPosition();
				}else if(block!=null){
					cases.add(new XTreeCase(endPosition(), key, block));
				}
				key = null;
				startPosition();
				tokenizer.next();
				block = new ArrayList<XTreeStatement>();
				expect(XKeyword.COLON);
			}else{
				XTreeStatement s = makeStatement();
				if(block!=null){
					block.add(s);
				}
				skip();
			}
		}
		if(block==null){
			endPosition();
		}else{
			cases.add(new XTreeCase(endPosition(), key, block));
		}
		expect(XKeyword.RSCOPE);
		return new XTreeSwitch(endPosition(), expr, cases);
	}
	
	private XTreeTry makeTryPosStarted(){
		expect(XKeyword.TRY);
		XTreeScope resources;
		if(tokenOk(XTokenKind.KEYWORD, XKeyword.LBRAKET)){
			startPosition();
			tokenizer.next();
			List<XTreeStatement> list = new ArrayList<XTreeStatement>();
			list.add(makeExpr());
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.SEMICOLON)){
				tokenizer.next();
				list.add(makeExpr());
			}
			expect(XKeyword.RBRAKET);
			resources = new XTreeScope(endPosition(), list);
		}else{
			resources = null;
		}
		XTreeScope body = makeScope();
		List<XTreeCatch> catches = new ArrayList<XTreeCatch>();
		startPosition();
		while(tokenOk(XTokenKind.KEYWORD, XKeyword.CATCH)){
			tokenizer.next();
			expect(XKeyword.LBRAKET);
			List<XTreeExpr> excType = new ArrayList<XTreeExpr>();
			excType.add(makeExprPrefixSuffix(false));
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.OR)){
				tokenizer.next();
				excType.add(makeExprPrefixSuffix(false));
			}
			XTreeIdent varName = null;
			if(!tokenOk(XTokenKind.KEYWORD, XKeyword.RBRAKET)){
				varName = makeIdent();
			}
			expect(XKeyword.RBRAKET);
			XTreeScope b = makeScope();
			catches.add(new XTreeCatch(endPosition(), excType, varName, b));
			startPosition();
		}
		endPosition();
		XTreeScope _finally;
		if(tokenOk(XTokenKind.KEYWORD, XKeyword.FINALLY)){
			tokenizer.next();
			_finally = makeScope();
		}else{
			_finally = null;
		}
		return new XTreeTry(endPosition(), resources, body, catches, _finally);
	}
	
	private XTreeMakeList makeMakeListPosStarted(){
		expect(XKeyword.LINDEX);
		List<XTreeExpr> exprs = new ArrayList<XTreeExpr>();
		if(!tokenOk(XTokenKind.KEYWORD, XKeyword.RINDEX) && !tokenEof()){
			exprs.add(makeExpr(true));
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA)){
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.RINDEX)){
					break;
				}
				exprs.add(makeExpr(true));
			}
		}
		expect(XKeyword.RINDEX);
		return new XTreeMakeList(endPosition(), exprs);
	}
	
	private XTreeMakeMap makeMakeMapPosStarted(){
		expect(XKeyword.LSCOPE);
		List<XTreeMapEntry> entries = new ArrayList<XTreeMapEntry>();
		if(!tokenOk(XTokenKind.KEYWORD, XKeyword.RSCOPE) && !tokenEof()){
			entries.add(makeMapEntry());
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA)){
				tokenizer.next();
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.RSCOPE)){
					break;
				}
				entries.add(makeMapEntry());
			}
		}
		expect(XKeyword.RSCOPE);
		return new XTreeMakeMap(endPosition(), entries);
	}
	
	private XTreeMapEntry makeMapEntry(){
		startPosition();
		XTreeExpr key = makeExpr();
		expect(XKeyword.COLON);
		XTreeExpr expr = makeExpr(true);
		return new XTreeMapEntry(endPosition(), key, expr);
	}
	
	private XTreeCall makeCallPosStarted(XTreeExpr method){
		expect(XKeyword.LBRAKET);
		List<XTreeExpr> params = new ArrayList<XTreeExpr>();
		List<XTreeIdent> kws = new ArrayList<XTreeIdent>();
		if(!tokenOk(XTokenKind.KEYWORD, XKeyword.RBRAKET) && !tokenEof()){
			XTreeExpr e = makeExpr(true);
			if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON) && e instanceof XTreeIdent){
				tokenizer.next();
				kws.add((XTreeIdent)e);
				e = makeExpr(true);
			}else{
				kws.add(null);
			}
			params.add(e);
			while(tokenOk(XTokenKind.KEYWORD, XKeyword.COMMA)){
				tokenizer.next();
				e = makeExpr(true);
				if(tokenOk(XTokenKind.KEYWORD, XKeyword.COLON) && e instanceof XTreeIdent){
					tokenizer.next();
					kws.add((XTreeIdent)e);
					e = makeExpr(true);
				}else{
					kws.add(null);
				}
				params.add(e);
			}
		}
		expect(XKeyword.RBRAKET);
		return new XTreeCall(endPosition(), method, params, kws);
	}
	
	public boolean isOperator(){
		XToken token = tokenizer.getToken();
		if(token.kind!=XTokenKind.KEYWORD)
			return false;
		XKeyword k = (XKeyword)token.data;
		return k==XKeyword.ADD || k==XKeyword.SUB || k==XKeyword.MUL
				|| k==XKeyword.DIV || k==XKeyword.IDIV || k==XKeyword.MOD || k==XKeyword.NOT
				|| k==XKeyword.INVERSE || k==XKeyword.AND || k==XKeyword.OR
				|| k==XKeyword.XOR || k==XKeyword.EQUAL || k==XKeyword.GREATER
				|| k==XKeyword.SMALLER || k==XKeyword.COMMA
				|| k==XKeyword.DOT || k==XKeyword.COLON || k==XKeyword.QUESTIONMARK
				|| k==XKeyword.SAND || k==XKeyword.SOR || k==XKeyword.SXOR
				|| k==XKeyword.SBAND || k==XKeyword.SBOR
				|| k==XKeyword.SMOD || k==XKeyword.SNOT || k==XKeyword.SBNOT || k==XKeyword.SPOW;
	}
	
	public XOperator readOperator(Type type, boolean noCommaOperators){
		XToken token = tokenizer.getToken();
		if(!isOperator()){
			return XOperator.NONE;
		}
		XOperator best = XOperator.NONE;
		switch((XKeyword)token.data){
		case SAND:
			best = XOperator.AND;
			break;
		case SOR:
			best = XOperator.OR;
			break;
		case SXOR:
			best = XOperator.XOR;
			break;
		case SBAND:
			best = XOperator.BAND;
			break;
		case SBOR:
			best = XOperator.BOR;
			break;
		case SMOD:
			best = XOperator.MOD;
			break;
		case SNOT:
			best = XOperator.NOT;
			break;
		case SBNOT:
			best = XOperator.BNOT;
			break;
		case SPOW:
			best = XOperator.POW;
			break;
		default:
			best = XOperator.NONE;
			break;
		}
		if(best!=XOperator.NONE){
			if(type!=null && best.type!=type){
				return XOperator.NONE;
			}
			tokenizer.next();
			return best;
		}
		XOperator[] all = XOperator.values();
		String s = ((XKeyword)token.data).name;
		best = XOperator.NONE;
		boolean hasNext;
		tokenizer.mark();
		do{
			token = tokenizer.next();
			hasNext = false;
			for(XOperator o:all){
				if(o.type!=null && (type == null || o.type==type)){
					if(o.op.equals(s)){
						best=o;
					}else if(o.op.startsWith(s)){
						hasNext = true;
					}
				}
			}
			if(hasNext && isOperator() && token.spaces==0){
				s += ((XKeyword)token.data).name;
			}else{
				hasNext = false;
			}
		}while(hasNext);
		tokenizer.reset();
		if(best==XOperator.COMMA && noCommaOperators)
			return XOperator.NONE;
		if(best!=XOperator.NONE){
			for(int i=0; i<best.op.length(); i++){
				tokenizer.next();
			}
		}
		return best;
	}
	
	private boolean expect(XKeyword keyword){
		if(tokenOk(XTokenKind.KEYWORD, keyword)){
			tokenizer.next();
			return true;
		}else{
			addDiagnostic("expect.keyword", keyword, tokenizer.getToken());
			return false;
		}
	}
	
	private boolean expectOrEOF(XKeyword keyword){
		if(tokenOk(XTokenKind.KEYWORD, keyword)){
			tokenizer.next();
			return true;
		}else if(tokenEof()){
			return true;
		}else{
			addDiagnostic("expect.keyword", keyword, tokenizer.getToken());
			return false;
		}
	}
	
	private void startPosition(){
		XToken token = tokenizer.getToken();
		XTreePosition position = new XTreePosition(token.pos.clone(), token.pos.pos, token.end);
		positions.add(position);
	}
	
	/*private void viewPosition(){
		positions.get(positions.size()-1).position = tokenizer.getToken().pos.clone();
	}*/
	
	private XTreePosition endPosition(){
		XTreePosition position = positions.remove(positions.size()-1);
		position.end = tokenizer.getToken().end;
		return position;
	}
	
	private boolean tokenOk(XTokenKind kind, Object data){
		XToken token = tokenizer.getToken();
		return token.kind==kind && token.data==data || (token.data!=null && token.data.equals(data));
	}
	
	private boolean tokenEof(){
		return tokenizer.getToken().kind==XTokenKind.EOF;
	}
	
	private void addDiagnostic(String message, Object...args){
		XToken token = tokenizer.getToken();
		diagnosticListener.report(new XDiagnostic(token.pos, token.pos.pos, token.end, message, args));
	}
	
	private void addDiagnostic(XTree t, String message, Object...args){
		diagnosticListener.report(new XDiagnostic(t.position.position, t.position.start, t.position.end, message, args));
	}
}
