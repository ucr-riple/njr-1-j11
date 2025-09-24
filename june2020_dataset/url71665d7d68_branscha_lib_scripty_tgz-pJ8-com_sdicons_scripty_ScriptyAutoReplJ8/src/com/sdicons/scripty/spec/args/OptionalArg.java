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

package com.sdicons.scripty.spec.args;

import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.parser.Pair;
import com.sdicons.scripty.spec.type.ITypeSpec;
import com.sdicons.scripty.spec.type.TypeSpecException;

/**
 * Optional args are positional (have a fixed location). An optional parameter
 * can be omitted, in which case the default value will be used.
 * 
 * It is assumed that fixed args come first, then optional ones and 
 * eventually named parameters.
 * 
 * An optional parameter cannot be a pair, because it could conflict
 * with the named parameters which are grouped at the end of the list.
 *
 */
public class OptionalArg 
implements IArgSpec
{
    private ITypeSpec spec;
    private String specName;
    private Object defaultVal;
    
    public OptionalArg(ITypeSpec aSpec, Object aVal)
    {
        spec = aSpec;
        defaultVal = aVal;                
        specName = "opt: " + spec.getSpecName();
    }
    
    public String getSpecName()
    {     
        return specName;                    
    }

    public Object guard(Object[] aArgs, int aPos, IContext aCtx) 
    throws ArgSpecException
    {
        try
        {
            if(aPos < 0 || aPos >= aArgs.length) return spec.guard(defaultVal, aCtx);
            else if(aArgs[aPos] instanceof Pair) return spec.guard(defaultVal, aCtx);
            else return spec.guard(aArgs[aPos], aCtx);
        }
        catch (TypeSpecException e)
        {
            throw new ArgSpecException(String.format("Optional argument at position %d: %s", aPos, e.getMessage()));
        }
    }
}
