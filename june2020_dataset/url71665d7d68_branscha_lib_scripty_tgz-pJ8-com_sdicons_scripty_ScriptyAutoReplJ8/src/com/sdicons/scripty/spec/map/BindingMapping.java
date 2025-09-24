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

public class BindingMapping
implements IArgMapping
{
    private String binding;
    private boolean excIfNull;

    public BindingMapping(String aBinding, boolean aExcIfNull)
    {
        binding = aBinding;
        excIfNull = aExcIfNull;
    }

    public Object map(IEval aEval, IContext aContext, Object aArgs)
    throws ArgMappingException
    {
        if(aContext.isBound(binding)) return aContext.getBinding(binding);
        else if(excIfNull) throw new ArgMappingException("... no such binding ...");
        else return null;
    }

    public void setOffset(int aOffset)
    {
        // Nop.
    }
}
