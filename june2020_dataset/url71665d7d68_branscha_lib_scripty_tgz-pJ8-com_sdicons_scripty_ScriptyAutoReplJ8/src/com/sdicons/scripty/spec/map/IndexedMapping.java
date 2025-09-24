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

package com.sdicons.scripty.spec.map;

import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.parser.IEval;

import java.util.List;

public class IndexedMapping
implements IArgMapping
{
    private int offset;

    public IndexedMapping(int aOffset)
    {
        offset = aOffset;
    }

    public Object map(IEval aEval, IContext aContext, Object aArgs)
    throws ArgMappingException
    {
        if(aArgs == null)
        {
            // TODO generate ERROR
            throw new ArgMappingException("...");
            
        }
        
        if(offset < 0)
        {
            // TODO generate ERROR
            throw new ArgMappingException("...");
        }
        
        Class lArgsClass = aArgs.getClass();
        if(lArgsClass.isArray())
        {
            Object[] lArgs = (Object[]) aArgs;
            if(offset >= lArgs.length)
            {
                // TODO generate ERROR
                throw new ArgMappingException("...");
            }
            
            return lArgs[offset];
        }
        else if(List.class.isAssignableFrom(lArgsClass))
        {
            List lArgs = (List) aArgs;
            if(offset >= lArgs.size())
            {
                // TODO generate error
                throw new ArgMappingException("...");
            }
            return lArgs.get(offset);
        }
        
        // If we get here we could not interprete the argument object as an
        // indexed collection.
        throw new ArgMappingException("...");
    }

    public void setOffset(int aOffset)
    {
       offset = offset + aOffset;
    }
}
