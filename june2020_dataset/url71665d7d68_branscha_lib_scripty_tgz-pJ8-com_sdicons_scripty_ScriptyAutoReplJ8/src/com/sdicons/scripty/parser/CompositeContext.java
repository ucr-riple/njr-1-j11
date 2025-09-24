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

import java.util.HashMap;
import java.util.Map;

public class CompositeContext
implements IContext
{
	IContext main, backing;

	public CompositeContext(IContext aChildCtx, IContext aParentCtx)
	{
		main = aChildCtx;
		backing = aParentCtx;
	}

	public Object getBinding(String aKey)
	{
		if(main.isBound(aKey)) return main.getBinding(aKey);
		else return backing.getBinding(aKey);
	}

    public void removeBinding(String aKey)
    {
        if(main.isBound(aKey)) main.removeBinding(aKey);
        else backing.removeBinding(aKey);
    }

    public void setBinding(String aKey, Object aValue)
    throws CommandException
    {
        if(main.isBound(aKey)) main.setBinding(aKey, aValue);
        else if (backing.isBound(aKey)) backing.setBinding(aKey, aValue);
        else throw new CommandException(String.format("There is no binding for '%s' in the context.", aKey==null?"null":aKey));
    }

	public boolean isBound(String aKey)
	{
		return main.isBound(aKey) || backing.isBound(aKey);
	}

    public IContext getRootContext()
    {
        if(backing != null) return backing.getRootContext();
        else if(main != null) return main.getRootContext();
        else return null;
    }

    public void defBinding(String aKey, Object aValue)
    {
        main.defBinding(aKey, aValue);
    }

    public Map<String, Object> dumpBindings()
    {
        Map<String, Object> lBackingDump = backing.dumpBindings();
        Map<String, Object> lMainDump = main.dumpBindings();
        Map<String, Object> lDump = new HashMap<String, Object>(lBackingDump);
        lDump.putAll(lMainDump);
        return lDump;
    }

    @Override
    public String toString()
    {
        final StringBuilder lBuilder = new StringBuilder();
        lBuilder.append(main.toString());
        lBuilder.append("----------\n");
        lBuilder.append(backing.toString());
        return lBuilder.toString();
    }
}
