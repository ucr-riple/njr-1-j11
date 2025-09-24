package xscript;

import xscript.object.XRuntime;
import xscript.values.XValue;
import xscript.values.XValueClosure;

public class XClosure{
	
	private final XExec parent; 
	private XClosure next;
	XValue value;
	private int refs=1;
	
	XClosure(XExec parent, XClosure next){
		this.parent = parent;
		this.next = next;
	}
	
	public XValue getValue(){
		return value instanceof XValueClosure?((XValueClosure)value).value:value;
	}
	
	public void setValue(XValue value){
		if(this.value instanceof XValueClosure){
			((XValueClosure)this.value).value = value;
		}
		this.value = value;
	}
	
	public void decRefs(){
		if(--refs<=0){
			if(value instanceof XValueClosure){
				parent.removeClosure((XValueClosure)value);
			}
			XClosure c = parent.getOrReplaceFirstClosure(this);
			if(c==null)
				return;
			XClosure prev = c;
			while((c = c.next)!=null){
				if(c==this){
					prev.next = next;
					return;
				}
				prev = c;
			}
		}
	}
	public XClosure getNext() {
		return next;
	}

	public XClosure incRef() {
		refs++;
		return this;
	}

	public void setVisible(XRuntime rt) {
		value.setVisible(rt);
	}
}