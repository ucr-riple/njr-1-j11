package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.util.ITextRegion;

public class InlineAssemblyNode extends ASTNodeImpl {

	private final String code;
	
	public InlineAssemblyNode(String code,ITextRegion region) {
		super(region);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return "inline assembly";
	}
	
	@Override
	protected ASTNode createCopy() 
	{
		return new InlineAssemblyNode(this.code,getTextRegion());
	}
}