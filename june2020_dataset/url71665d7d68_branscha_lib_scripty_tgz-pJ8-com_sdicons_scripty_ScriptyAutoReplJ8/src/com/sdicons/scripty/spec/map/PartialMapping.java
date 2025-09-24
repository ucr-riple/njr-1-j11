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

import java.util.Arrays;

public class PartialMapping
implements IArgMapping
{
    private int from;
    private int length;

    public PartialMapping(int aFrom, int aLength)
    {
        from = aFrom;
        length = aLength;
    }

    public Object map(IEval aEval, IContext aContext, Object aArgs)
    throws ArgMappingException
    {
        // TODO TODO
        // Add code for lists/collections

        Object[] lArgs = (Object[]) aArgs;
        int lLength = 0;
        if(length < 0)
        {
            lLength = lArgs.length - from;
            if(lLength <= 0) lLength = 0;
        }
        
        return Arrays.copyOfRange(lArgs, from, from + lLength);
    }

    public void setOffset(int aOffset)
    {
       from = from + aOffset;
    }
}
