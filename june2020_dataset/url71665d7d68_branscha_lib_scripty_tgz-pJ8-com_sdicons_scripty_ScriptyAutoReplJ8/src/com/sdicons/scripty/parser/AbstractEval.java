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

import java.util.Collection;

public abstract class AbstractEval
implements IEval
{
    private IContext context;

    public AbstractEval(IContext aContext)
    {
        context = aContext;
    }

    public IContext getContext()
    {
        return context;
    }

    public void setContext(IContext context)
    {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    protected static boolean boolEval(Object aObj)
    {
        if(aObj instanceof Boolean) return (Boolean) aObj;
        else if(aObj instanceof Collection) return ((Collection) aObj).size() > 0;
        else if(aObj instanceof Byte) return ((Byte) aObj).intValue() != 0;
        else if(aObj instanceof Short) return ((Short) aObj).intValue() != 0;
        else if(aObj instanceof Integer) return (Integer) aObj != 0;
        else if(aObj instanceof Long) return (Long) aObj != 0l;
        else if(aObj instanceof String)
        {
            String lNor = ((String) aObj).toLowerCase();
            return ("true".equals(lNor) || "yes".equals(lNor) || "t".equals(lNor) || "y".equals(lNor) || "on".equals(lNor));
        }
        else return aObj != null;
    }
}
