package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.util.ITextRegion;

public class ArrayInitializer extends ASTNodeImpl {

	public ArrayInitializer(ITextRegion region) {
		super(region);
	}
	
	@Override
	protected ASTNode createCopy() {
		return new ArrayInitializer( getTextRegion() );
	}
	
	@Override
	public String toString() {
		return "ArrayInitializer";
	}

}
