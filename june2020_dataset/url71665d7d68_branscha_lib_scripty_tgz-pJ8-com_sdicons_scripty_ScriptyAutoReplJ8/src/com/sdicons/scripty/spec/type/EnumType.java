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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumType
implements ITypeSpec
{
    private List<String> values = new ArrayList<String>();
    
    public EnumType(List<String> aValues)
    {
        values.addAll(aValues);
    }
    
    public EnumType(String ... aValues)
    {
        values.addAll(Arrays.asList(aValues));
    }
    
    public Object guard(Object aArg, IContext aCtx)
    throws TypeSpecException
    {
       if(aArg == null || !values.contains(aArg.toString()))
       {
           throw new TypeSpecException(TypeUtil.msgBadRepr(getSpecName(), (String) aArg));
       }
       return aArg.toString();
    }

    public String getSpecName()
    {
        return String.format("Enum %s", values.toString());
    }
}
