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

public class InstanceType
implements ITypeSpec
{
    private Class requiredClass;
    private boolean allowsNull = false;
    private String typeName;
    
    public InstanceType(Class aClass, boolean nullAllowed)
    {
        this(aClass, aClass.getCanonicalName(), nullAllowed);
    }
    
    public InstanceType(Class aClass, String aTypeName, boolean nullAllowed)
    {
        requiredClass = aClass;       
        allowsNull = nullAllowed;
        typeName = aTypeName;
    }
    
    @SuppressWarnings("unchecked")
    public Object guard(Object aArg, IContext aCtx) 
    throws TypeSpecException
    {
        if(aArg == null)
        {
            if(allowsNull) return null;
            else throw new TypeSpecException(String.format("Null value. Expected type '%s' and null is not allowed.", typeName));
        }
        else
        {
            if(requiredClass.isAssignableFrom(aArg.getClass())) return aArg;
            else throw new TypeSpecException(TypeUtil.msgExpectedOther(getSpecName(), aArg));
        }        
    }
    
    public String getSpecName()
    {
        return typeName;
    }
}
