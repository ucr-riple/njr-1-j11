package xscript.compiler;

import xscript.XOpcode;
import xscript.compiler.scopes.XScope;
import xscript.compiler.scopes.XScope.Result;
import xscript.compiler.scopes.XScope.Result.R;
import xscript.compiler.tree.XTree;
import xscript.compiler.tree.XTree.XTreeIdent;



public class XCompiledPartVar extends XCompiledPart {

	private XScope scope;
	private XTreeIdent ident;
	private XVarAccess access;
	private Result result;
	
	public XCompiledPartVar(XTree t, XScope scope, XTreeIdent ident, XVarAccess access) {
		super(t);
		this.scope = scope;
		this.ident = ident;
		this.access = access;
	}
	
	@Override
	public XCompiledPart asStatement(XTreeCompiler compiler, XCodeGen codeGen) {
		result = scope.get(ident, ident.name, access);
		switch(result.r){
		case DUPLICATED:
			compiler.addDiagnostic(ident, "duplicated.var", ident.name, result.var.t.position.position.line);
			break;
		case FOUND:
			break;
		case DECLARED:
			if(result.var.getClass()==XVar.class){
				codeGen.addInstruction(t, XOpcode.LOADN);
				codeGen.addInstruction(t, result.var);
			}
			break;
		case NOT_FOUND:
			compiler.addDiagnostic(ident, "var.not.found", ident.name);
		default:
			break;
		}
		return this;
	}

	@Override
	public int setup(XTreeCompiler compiler, XCodeGen codeGen) {
		result = scope.get(ident, ident.name, access);
		switch(result.r){
		case DUPLICATED:
			compiler.addDiagnostic(ident, "duplicated.var", ident.name, result.var.t.position.position.line);
			break;
		case FOUND:
			break;
		case DECLARED:
			if(result.var.getClass()==XVar.class){
				codeGen.addInstruction(t, XOpcode.LOADN);
				codeGen.addInstruction(t, result.var);
			}
			break;
		case NOT_FOUND:
			compiler.addDiagnostic(ident, "var.not.found", ident.name);
			return 0;
		default:
			break;
		}
		if(result.var instanceof XClassAttr){
			XVar var = ((XClassAttr)result.var).getAccess();
			getVar(t, var, codeGen);
			//codeGen.addInstruction(t, XOpcode.GETBOTTOM1, 0);
			return 1;
		}else if(result.var instanceof XClosureVar){
			XVar base = ((XClosureVar)result.var).base;
			if(base instanceof XClassAttr){
				if(base == ((XClosureVar)result.var).var){
					codeGen.addInstructionB(t, XOpcode.GETBOTTOM1, 0);
				}else{
					codeGen.addInstruction2(t, XOpcode.GET_CLOSURE, result.var);
				}
			}
			return 1;
		}
		return 0;
	}
	
	@Override
	public int setupAndGetThis(XTreeCompiler compiler, XCodeGen codeGen) {
		int s = setup(compiler, codeGen);
		if(s==0){
			codeGen.addInstruction(t, XOpcode.LOADN);
		}else{
			codeGen.addInstruction(t, XOpcode.DUP);
		}
		return s;
	}

	public static void getVar(XTree t, XVar var, XCodeGen codeGen){
		if(var instanceof XClassAttr){
			XVar v = ((XClassAttr)var).getAccess();
			getVar(t, v, codeGen);
			codeGen.addInstruction(t, XOpcode.GET_ATTR, var.name);
		}else if(var instanceof XGlobal){
			codeGen.addInstruction2(t, XOpcode.GET_GLOBAL, var);
		}else if(var instanceof XClosureVar){
			XVar base = ((XClosureVar)var).base;
			if(base instanceof XClassAttr){
				if(base == ((XClosureVar)var).var){
					codeGen.addInstructionB(t, XOpcode.GETBOTTOM1, 0);
				}else{
					codeGen.addInstruction2(t, XOpcode.GET_CLOSURE, var);
				}
				codeGen.addInstruction(t, XOpcode.GET_ATTR, base.name);
			}else{
				codeGen.addInstruction2(t, XOpcode.GET_CLOSURE, var);
			}
		}else{
			codeGen.addInstruction2(t, XOpcode.GETBOTTOM1, var);
		}
	}

	@Override
	public XCompiledPart get(XTreeCompiler compiler, XCodeGen codeGen) {
		if(result.r==R.NOT_FOUND){
			codeGen.addInstruction(t, XOpcode.LOADN);
		}else{
			if(result.var instanceof XClassAttr){
				codeGen.addInstruction(t, XOpcode.GET_ATTR, result.var.name);
			}else if(result.var instanceof XGlobal){
				codeGen.addInstruction2(t, XOpcode.GET_GLOBAL, result.var);
			}else if(result.var instanceof XClosureVar){
				XVar base = ((XClosureVar)result.var).base;
				if(base instanceof XClassAttr){
					codeGen.addInstruction(t, XOpcode.GET_ATTR, base.name);
				}else{
					codeGen.addInstruction2(t, XOpcode.GET_CLOSURE, result.var);
				}
			}else{
				codeGen.addInstruction2(t, XOpcode.GETBOTTOM1, result.var);
			}
		}
		return this;
	}

	@Override
	public XCompiledPart set(XTreeCompiler compiler, XCodeGen codeGen) {
		if(result.r!=R.NOT_FOUND){
			if(result.var instanceof XClassAttr){
				codeGen.addInstruction(t, XOpcode.SET_ATTR, result.var.name);
			}else if(result.var instanceof XGlobal){
				codeGen.addInstruction(t, XOpcode.DUP);
				codeGen.addInstruction2(t, XOpcode.SET_GLOBAL, result.var);
			}else if(result.var instanceof XClosureVar){
				XVar base = ((XClosureVar)result.var).base;
				if(base instanceof XClassAttr){
					codeGen.addInstruction(t, XOpcode.SET_ATTR, base.name);
				}else{
					codeGen.addInstruction(t, XOpcode.DUP);
					codeGen.addInstruction2(t, XOpcode.SET_CLOSURE, result.var);
				}
			}else{
				codeGen.addInstruction(t, XOpcode.DUP);
				codeGen.addInstruction2(t, XOpcode.SETBOTTOM1, result.var);
			}
		}
		return this;
	}

	@Override
	public XCompiledPart del(XTreeCompiler compiler, XCodeGen codeGen) {
		if(result.r!=R.NOT_FOUND){
			if(result.var instanceof XClassAttr){
				codeGen.addInstruction(t, XOpcode.DEL_ATTR, result.var.name);
			}else if(result.var instanceof XGlobal){
				codeGen.addInstruction2(t, XOpcode.DEL_GLOBAL, result.var);
			}else if(result.var instanceof XClassAttr){
				XVar base = ((XClosureVar)result.var).base;
				if(base instanceof XClassAttr){
					codeGen.addInstruction(t, XOpcode.DEL_ATTR, base.name);
				}else{
					super.del(compiler, codeGen);
				}
			}else{
				super.del(compiler, codeGen);
			}
		}
		return this;
	}

	@Override
	public boolean isConstValue() {
		// TODO Auto-generated method stub
		return super.isConstValue();
	}

	@Override
	public Object getConst() {
		// TODO Auto-generated method stub
		return super.getConst();
	}

	@Override
	public boolean isFalse() {
		// TODO Auto-generated method stub
		return super.isFalse();
	}

	public boolean setup2(XTreeCompiler compiler, XCodeGen code) {
		result = scope.get(ident, ident.name, access);
		switch(result.r){
		case DUPLICATED:
			compiler.addDiagnostic(ident, "duplicated.var", ident.name, result.var.t.position.position.line);
			break;
		case FOUND:
			break;
		case DECLARED:
			if(result.var.getClass()==XVar.class)
				code.addInstruction(t, result.var);
			break;
		case NOT_FOUND:
			compiler.addDiagnostic(ident, "var.not.found", ident.name);
			return false;
		default:
			break;
		}
		if(result.var instanceof XClassAttr){
			return true;
		}else if(result.var instanceof XClosureVar){
			return true;
		}
		return false;
	}

}
