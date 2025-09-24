/*
 * Scripty Programming Language
 * Copyright (C) 2010-2012 Bruno Ranschaert, S.D.I.-Consulting BVBA
 * http://www.sdi-consulting.be
 * mailto://info@sdi-consulting.be
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.sdicons.scripty.parser;

import java.util.ArrayList;
import java.util.List;


/**
 * Internal data structure used by the Repl to represent user defined functions.
 */
public class Lambda 
{
    // The parameter names.
	private String[] params;
    // The function body.
	private Object expr;
    // Lexical context the context that was in effect
    // when the function was defined.
	private IContext lexicalCtx;
	
	public Lambda(String[] aParams, Object aExpr, IContext aLexicalCtx)
	{
		params = aParams;
		expr = aExpr;
		lexicalCtx = aLexicalCtx;
	}
	
	public IContext createContext(Object[] aArgs, int aFrom, int aTo)
	throws CommandException
	{
		if((aTo - aFrom) != params.length)
        {
            final List<String> lPretty = new ArrayList<String>(params.length);
            for(int i =0; i < params.length; i++) lPretty.add(params[i]);
			throw new CommandException(String.format("Wrong number of arguments. Expected %d named %s and received %d.", params.length, lPretty, (aTo - aFrom)));
        }
		IContext lCallContext = new BasicContext();
		for(int i = 0; i < params.length; i++) lCallContext.defBinding(params[i], aArgs[i + aFrom]);
		return new CompositeContext(lCallContext, lexicalCtx);
	}

	public Object getExpr() 
	{
		return expr;
	}
}
