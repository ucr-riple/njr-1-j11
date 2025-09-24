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

package com.sdicons.scripty.spec.type;

import com.sdicons.scripty.parser.IContext;

public class IntegerRangeType implements ITypeSpec
{
    private int from;
    private int to;
    private IntegerType intSpec = new IntegerType();
    
    public IntegerRangeType(int aFrom, int aTo)
    {
        from = aFrom;
        to = aTo;
    }

    public String getSpecName()
    {
        return String.format("IntegerRange %d...%d", from, to);
    }

    public Object guard(Object aArg, IContext aCtx) 
    throws TypeSpecException
    {
        final Integer lInt = (Integer) intSpec.guard(aArg, aCtx);
        if(lInt < from || lInt > to)
            throw new TypeSpecException(String.format("Value out of range. Expected type '%s' and received an incompatible type '%s' value '%s'.", getSpecName(), aArg.getClass().getCanonicalName(), aArg.toString()));
        else return lInt;
    }
}
