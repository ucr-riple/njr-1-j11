package xscript.compiler.scopes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xscript.compiler.XClassAttr;
import xscript.compiler.XClosureVar;

public class XBaseScope extends XScope {

	private final List<XClosureVar> closures;
	
	public XBaseScope(XScope parent, boolean localsAllowed){
		super(parent.lock(), localsAllowed);
		closures = new ArrayList<XClosureVar>();
	}
	
	XBaseScope(XBaseScope thiz){
		super(thiz);
		closures = thiz.closures;
	}
	
	XBaseScope(){
		closures = new ArrayList<XClosureVar>();
	}
	
	protected void addClosure(XClosureVar var){
		if(var.base instanceof XClassAttr && var.base==var.var)
			return;
		var.position = this.closures.size();
		this.closures.add(var);
	}

	public List<XClosureVar> getClosures() {
		return Collections.unmodifiableList(closures);
	}
	
	public XScope lock() {
		return new XBaseScope(this);
	}
	
}
