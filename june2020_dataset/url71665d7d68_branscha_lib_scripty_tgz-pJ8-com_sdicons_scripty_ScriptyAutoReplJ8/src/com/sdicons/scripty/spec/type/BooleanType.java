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

public class BooleanType 
implements ITypeSpec<Boolean>
{
    public static final ITypeSpec<Boolean> BOOLEAN_TYPE = new BooleanType();
    
    public String getSpecName()
    {
        return "Boolean";
    }

    public Boolean guard(Object aArg, IContext aCtx) 
    throws TypeSpecException
    {
        if(aArg instanceof Boolean)
        {
            return (Boolean) aArg;            
        }        
        else if (aArg instanceof String)
        {
                final String lStr = ((String) aArg).trim().toLowerCase();    
                if("true".equals(lStr) || "on".equals(lStr) || "yes".equals(lStr) || "ok".equals(lStr)) return Boolean.TRUE;
                else if("false".equals(lStr) || "off".equals(lStr) || "no".equals(lStr) || "nok".equals(lStr)) return Boolean.FALSE;
                else throw new TypeSpecException(TypeUtil.msgBadRepr(getSpecName(), lStr));
        }
        else
        {
            throw new TypeSpecException(TypeUtil.msgExpectedOther(getSpecName(), aArg));
        }
    }
}