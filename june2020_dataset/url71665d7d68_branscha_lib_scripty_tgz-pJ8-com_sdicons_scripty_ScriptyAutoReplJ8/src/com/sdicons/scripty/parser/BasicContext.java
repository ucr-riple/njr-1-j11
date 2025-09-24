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
import java.util.Set;
import java.util.TreeSet;

public class BasicContext
implements IContext
{
	private Map<String, Object> context = new HashMap<String, Object>();

	public Object getBinding(String aKey)
	{
		return context.get(aKey);
	}

	public void setBinding(String aKey, Object aValue)
	throws CommandException
	{
	    if(isBound(aKey)) context.put(aKey, aValue);
	    else throw new CommandException(String.format("There is no binding for '%s' in the context.", aKey==null?"null":aKey));
	}

	public boolean isBound(String aKey)
	{
		return context.containsKey(aKey);
	}

    public void removeBinding(String aKey)
    {
        context.remove(aKey);
    }

    public IContext getRootContext()
    {
        return this;
    }

    public void defBinding(String aKey, Object aValue)
    {
        context.put(aKey, aValue);
    }

    public Map<String, Object> dumpBindings()
    {
        // Create a new map.
        return new HashMap<String, Object>(context);
    }

    @Override
    public String toString()
    {
        final StringBuilder lBuilder = new StringBuilder();
        Map<String, Object> lDump = dumpBindings();
        Set<String> lKeyset = new TreeSet<String>(lDump.keySet());

        for(String lKey : lKeyset)
        {
            Object lVal = lDump.get(lKey);
            lBuilder.append(lKey);
            lBuilder.append(" = ");
            lBuilder.append(lVal == null?"null":lVal.toString());
            lBuilder.append("\n");
        }
        return lBuilder.toString();
    }
}
