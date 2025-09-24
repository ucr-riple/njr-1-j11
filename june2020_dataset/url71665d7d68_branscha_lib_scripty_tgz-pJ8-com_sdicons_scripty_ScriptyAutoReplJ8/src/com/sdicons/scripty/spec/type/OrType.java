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

public class OrType 
implements ITypeSpec
{
    private ITypeSpec[] types;
    private String name;
    
    public OrType(ITypeSpec[] aTypes)
    {
        types = aTypes;
        StringBuilder lBuf = new StringBuilder();
        for(int i = 0; i < types.length; i++) 
        {
            lBuf.append(types[i].getSpecName());
            if(i < types.length - 1) lBuf.append(" or ");
        }
        name = lBuf.toString();
    }
    
    public String getSpecName()
    {
        return name;
    }

    public Object guard(Object aArg, IContext aCtx) 
    throws TypeSpecException
    {
        for(int i = 0; i < types.length; i++)
        {
            try
            {
                return types[i].guard(aArg, aCtx);
            }
            catch(TypeSpecException e)
            {
                // Ignore, try the next one!   
                // Only one has to succeed.
            }
        }
        
        // If we arrive here, then none of the typespecs was true.
        throw new TypeSpecException(TypeUtil.msgExpectedOther(getSpecName(), aArg));
    }
}
