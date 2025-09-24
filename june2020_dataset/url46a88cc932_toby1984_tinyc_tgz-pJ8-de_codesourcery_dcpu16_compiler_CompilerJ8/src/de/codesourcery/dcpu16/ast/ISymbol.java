package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.IScope;
import de.codesourcery.dcpu16.compiler.Identifier;

public interface ISymbol {

	public IScope getScope();

	public Identifier getName();
	
	/**
	 * 
	 * @return {@link IScope#getUniqueIdentifier()} concatenated with {@link #getName()}
	 * @see IScope#getUniqueIdentifier()
	 */
	public Identifier getUniqueIdentifier();
}
