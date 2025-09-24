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

/**
 * There are 3 types of parameters in a variable argument list.
 * 1. Fixed (always required) arguments, each argument has its own type.
 * 2. Variable length. These all have the same type. These cannot be pairs.
 *    It is the same type that can occur min to max times. A repetition of the same type.
 *    Eg. a list of 1 or more integers.
 * 3. Named (optional or required). These are pairs at the end of the command line.
 *    The named parameters can have a default value.
 *
 * Warning: The fixed args will be put first in the argument list.
 *          After that, the (!) named args will be put in the list.
 *          Finally, the variable part.
 */
public class VarArgList
implements IArgList
{
    private FixedArg req[];
    private VarArg var;
    private NamedArg[] named;
    int min, max;

    public VarArgList(FixedArg aReq[], VarArg aVar, int aMin, int aMax, NamedArg[] aNamed)
    {
        req = aReq;
        var = aVar;
        named = aNamed;
        min = aMin;
        max = aMax;
    }

    /**
     * The args are expected to have the form ("CMD", arg-1, arg-2, ... arg-n).
     * The result is in this order:
     *  - First the fixed.
     *  - Then !!! the named.
     *  - Lastly the varargs.
     */
    public Object[] guard(Object[] aArgs, IContext aCtx)
    throws ArgSpecException
    {
        // We look for all pairs at the end of the argument list. We will only
        // consider these trailing pairs.
        int lStartNamed = aArgs.length - 1;
        while(lStartNamed > 0 && (aArgs[lStartNamed] instanceof Pair)) lStartNamed --;
        int lNrVar = lStartNamed - req.length ;
        if(lNrVar < 0) lNrVar = 0;

        if(min >= 0 && lNrVar < min)
            throw new ArgSpecException(String.format("Too few arguments of type '%s'. Expected at least %d and received %d.", var.getSpecName(), min, lNrVar));
        if(max >= 0 && lNrVar > max)
            throw new ArgSpecException(String.format("Too many arguments of type '%s'. Expected at most %d and received %d.", var.getSpecName(), max, lNrVar));

        // Create a new argument list where we will accumulate the
        // converted results.
        Object[] lNewArgs = new Object[1 + req.length + lNrVar + named.length];
        // Copy the command name to the new argument list, the structure will remain the same.
        lNewArgs[0] = aArgs[0];

        // Check the fixed.
        if(aArgs.length - 1 < req.length)
            throw new ArgSpecException(String.format("Too few arguments. Expected at least %d arguments but received %d.", req.length, aArgs.length - 1));

        // We skip the command name.
        int lArgIdx = 1;
        for(int i = 0; i < req.length; i++)
        {
            lNewArgs[lArgIdx] = req[i].guard(aArgs, lArgIdx++, aCtx);
        }

        // Check the named args.
        // We look for all pairs at the end of the argument list. We will only
        // consider these trailing pairs.
        while(lStartNamed > 0 && (aArgs[lStartNamed] instanceof Pair)) lStartNamed --;
        // Now we can resolve the named arguments within this range.
        for(IArgSpec lSpec: named)
        {
            lNewArgs[lArgIdx++] = lSpec.guard(aArgs, lStartNamed, aCtx);
        }
        // Finally we go looking for spurious named parameters that were not specified ...
        for(int i = lStartNamed; i < aArgs.length; i++)
        {
            if(aArgs[i] instanceof Pair)
            {
                final Pair lPair = (Pair) aArgs[i];
                if(!(lPair.getLeft() instanceof String))
                    throw new ArgSpecException(String.format("Found an badly formed named argument, where the name is not a string but an instance of type '%s'.", lPair.getLeft()==null?"null":lPair.getLeft().getClass().getCanonicalName()));
                String lPairName = (String) lPair.getLeft();
                boolean found = false;
                for(int j = 0; j < named.length; j++)
                {
                    if(named[j].getName().equals(lPairName))
                    {
                        found = true;
                        break;
                    }
                }
                if(!found)
                    throw new ArgSpecException(String.format("Found an unexpected named argument '%s'.", lPairName));
            }
        }

        // Check the var ones.
        // Start looking after the fixed args,
        // Provide the expected index.
        // Skip the cmd in the beginning of the arguments.
        final int lStartVar = 1 + req.length;
        for(int i = 0; i < lNrVar; i++)
        {
            lNewArgs[lArgIdx++] = var.guard(aArgs, lStartVar + i, aCtx);
        }
        return lNewArgs;
    }
}
