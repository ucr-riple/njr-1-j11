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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.sdicons.scripty.parser.IContext;

public class CheckedListType
implements ITypeSpec
{
    private ITypeSpec spec;
    private int min;
    private int max;

    public CheckedListType(ITypeSpec aSpec, int aMin, int aMax)
    {
        spec = aSpec;
        min = aMin;
        max = aMax;
    }

    public CheckedListType(ITypeSpec aSpec)
    {
        spec = aSpec;
        min = -1;
        max = -1;
    }

    public String getSpecName()
    {
        StringBuilder lBuilder = new StringBuilder();
        lBuilder.append("ListOf " + spec.getSpecName());
        if(min >= 0) lBuilder.append(", min size: ").append(min);
        if(max >= 0) lBuilder.append(", max size: ").append(max);
        return lBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public Object guard(Object aArg, IContext aCtx)
    throws TypeSpecException
    {
        if(!(aArg instanceof List))
            throw new TypeSpecException(TypeUtil.msgExpectedOther(getSpecName(), aArg));
        final List lListArg = (List) aArg;

        if(min >= 0 && lListArg.size() < min)
            throw new TypeSpecException(String.format("Not enough elements in the list. There should be at least %d elements.", min));
        else if (max >= 0 && lListArg.size() > max)
            throw new TypeSpecException(String.format("Too many elements in the list. There should be at most %d elements.", max));

        try
        {
            // The list is modified in-place
            //
            final ListIterator lIter = lListArg.listIterator();
            while(lIter.hasNext())
            {
                Object lObj = lIter.next();
                lIter.remove();
                lIter.add(spec.guard(lObj, aCtx));
            }
            return lListArg;
        }
        catch (UnsupportedOperationException e)
        {
            // We could not modify the list in-place ...
            // We recover by creating a new one in this case.
            //
            final List lNewList = new ArrayList(lListArg.size());
            for(Object lArg: lListArg)
            {
                lNewList.add(spec.guard(lArg, aCtx));
            }
            return lNewList;
        }
    }
}
