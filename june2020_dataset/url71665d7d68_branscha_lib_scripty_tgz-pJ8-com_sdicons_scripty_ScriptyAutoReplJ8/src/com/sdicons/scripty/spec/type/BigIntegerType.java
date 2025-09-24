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

import java.math.BigInteger;

import com.sdicons.scripty.parser.IContext;

public class BigIntegerType 
implements ITypeSpec<BigInteger>
{
    public String getSpecName()
    {        
        return "BigInteger";
    }

    public BigInteger guard(Object aArg, IContext aCtx) 
    throws TypeSpecException
    {
        if(aArg instanceof BigInteger)
        {
            return (BigInteger) aArg;            
        }
        else if (aArg instanceof String || aArg instanceof Number)
        {
            try
            {      
                return new BigInteger(aArg.toString());
            }
            catch (NumberFormatException e)
            {                
                throw new TypeSpecException(TypeUtil.msgBadRepr(getSpecName(), (String) aArg));
            }            
        }
        else
        {
            throw new TypeSpecException(TypeUtil.msgExpectedOther(getSpecName(), aArg));
        }
    }
}
