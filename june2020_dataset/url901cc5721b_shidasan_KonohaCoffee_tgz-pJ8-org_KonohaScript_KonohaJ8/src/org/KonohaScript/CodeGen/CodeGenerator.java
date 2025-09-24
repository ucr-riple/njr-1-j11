package org.KonohaScript.CodeGen;

import java.util.ArrayList;

import org.KonohaScript.SyntaxTree.TypedNode;

class CompiledMethod {
	public Object CompiledCode;

	Object Invoke(Object[] Args) {
		return null;
	}
}

class Local {
	int Index;
	String Name;

	public Local(int index, String name) {
		this.Index = index;
		this.Name = name;
	}
}

public abstract class CodeGenerator {
	ArrayList<Local> LocalVals;

	CodeGenerator() {
		this.LocalVals = new ArrayList<Local>();
	}

	CompiledMethod Compile(TypedNode Block) {
		return null;
	}

	Local AddLocalVarIfNotDefined(String Text) {
		for (Local l : this.LocalVals) {
			if (l.Name.compareTo(Text) == 0) {
				return l;
			}
		}
		Local local = new Local(this.LocalVals.size(), Text);
		this.LocalVals.add(local);
		return local;
	}

}