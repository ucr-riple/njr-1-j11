package xscript.compiler.tree;

import javax.tools.DiagnosticListener;

import xscript.compiler.XDiagnostic;
import xscript.compiler.XOperator;
import xscript.compiler.tree.XTree.XTreeConstant;
import xscript.compiler.tree.XTree.XTreeFor;
import xscript.compiler.tree.XTree.XTreeGroup;
import xscript.compiler.tree.XTree.XTreeIf;
import xscript.compiler.tree.XTree.XTreeOperator;
import xscript.compiler.tree.XTree.XTreeOperatorIf;
import xscript.compiler.tree.XTree.XTreeOperatorPrefixSuffix;
import xscript.compiler.tree.XTree.XTreeWhile;

public class XTreeMakeEasy extends XTreeChanger {

	private DiagnosticListener<String> diagnosticListener;
	
	private boolean doreplace;
	
	private XTree replace;
	
	public XTreeMakeEasy(DiagnosticListener<String> diagnosticListener){
		this.diagnosticListener = diagnosticListener;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends XTree> T visitTree(T tree){
		if(tree!=null){
			try{
				tree.accept(this);
			}catch(UnsupportedOperationException e){
			}catch(Exception e){
				addDiagnostic(tree, "makeeasy.tree.failure", e.getMessage());
			}
		}
		if(doreplace){
			tree = (T) replace;
			doreplace = false;
		}
		return tree;
	}
	
	@Override
	public void visitOperator(XTreeOperator operator) {
		super.visitOperator(operator);
		XTree t = operator.exprs.get(0);
		if(!(t instanceof XTreeConstant))
			return;
		XTreeConstant left = (XTreeConstant)t;
		while(!operator.operators.isEmpty()){
			XOperator o = operator.operators.get(0);
			t = operator.exprs.get(1);
			if(!(t instanceof XTreeConstant)){
				break;
			}
			XTreeConstant right = (XTreeConstant)t;
			try{
				right.value = o.calc(left.value, right.value);
			}catch(UnsupportedOperationException e){
				break;
			}catch(ClassCastException e){
				addDiagnostic(right, "invalid.data.types", e.getMessage());
				break;
			}
			left = right;
			operator.operators.remove(0);
			operator.exprs.remove(0);
		}
		if(operator.operators.isEmpty()){
			setReplace(left);
		}
	}

	@Override
	public void visitOperatorPrefixSuffix(XTreeOperatorPrefixSuffix xOperatorPrefixSuffix) {
		super.visitOperatorPrefixSuffix(xOperatorPrefixSuffix);
		if(xOperatorPrefixSuffix.expr instanceof XTreeConstant){
			Object value = ((XTreeConstant)xOperatorPrefixSuffix.expr).value;
			if(xOperatorPrefixSuffix.prefix!=null){
				//TODO also implement half resolve
				for(XOperator op: xOperatorPrefixSuffix.prefix){
					value = op.calc(value, null);
				}
			}
			if(xOperatorPrefixSuffix.suffix!=null){
				for(XOperator op: xOperatorPrefixSuffix.suffix){
					value = op.calc(value, null);
				}
			}
			replaceWith(xOperatorPrefixSuffix, value);
		}
	}
	
	@Override
	public void visitWhile(XTreeWhile xWhile) {
		super.visitWhile(xWhile);
		if(xWhile.condition instanceof XTreeConstant){
			Object value = ((XTreeConstant)xWhile.condition).value;
			if(!nonZero(value)){
				addDiagnostic(xWhile.body, "code.dead");
				setReplace(null);
			}
		}
	}

	@Override
	public void visitFor(XTreeFor xFor) {
		super.visitFor(xFor);
		if(xFor.condition instanceof XTreeConstant){
			Object value = ((XTreeConstant)xFor.condition).value;
			if(!nonZero(value)){
				addDiagnostic(xFor.body, "code.dead");
				setReplace(null);
			}
		}
	}

	@Override
	public void visitIf(XTreeIf xIf) {
		super.visitIf(xIf);
		if(xIf.condition instanceof XTreeConstant){
			Object value = ((XTreeConstant)xIf.condition).value;
			if(nonZero(value)){
				if(xIf.onFalse!=null)
					addDiagnostic(xIf.onFalse, "code.dead");
				setReplace(xIf.onTrue);
			}else{
				addDiagnostic(xIf.onTrue, "code.dead");
				setReplace(xIf.onFalse);
			}
		}
	}

	@Override
	public void visitGroup(XTreeGroup xGroup) {
		super.visitGroup(xGroup);
		if(xGroup.group instanceof XTreeConstant){
			replaceWith(xGroup, ((XTreeConstant)xGroup.group).value);
		}
	}

	@Override
	public void visitOperatorIf(XTreeOperatorIf operatorIf) {
		super.visitOperatorIf(operatorIf);
		if(operatorIf.condition instanceof XTreeConstant){
			Object value = ((XTreeConstant)operatorIf.condition).value;
			if(nonZero(value)){
				addDiagnostic(operatorIf.onFalse, "code.dead");
				setReplace(operatorIf.onTrue);
			}else{
				addDiagnostic(operatorIf.onTrue, "code.dead");
				setReplace(operatorIf.onFalse);
			}
		}
	}

	private boolean nonZero(Object obj){
		if(obj==null)
			return false;
		if(obj instanceof Boolean){
			return ((Boolean)obj).booleanValue();
		}
		if(obj instanceof Float || obj instanceof Double){
			return ((Number)obj).doubleValue()!=0.0;
		}
		if(obj instanceof Number){
			return ((Number)obj).longValue()!=0;
		}
		if(obj instanceof Character){
			return ((Character)obj).charValue()!=0;
		}
		return true;
	}
	
	private void replaceWith(XTree tree, Object value){
		setReplace(new XTreeConstant(tree.position, value));
	}
	
	private void setReplace(XTree replace){
		doreplace = true;
		this.replace = replace;
	}
	
	private void addDiagnostic(XTree t, String message, Object...args){
		diagnosticListener.report(new XDiagnostic(t.position.position, t.position.start, t.position.end, message, args));
	}
	
}
