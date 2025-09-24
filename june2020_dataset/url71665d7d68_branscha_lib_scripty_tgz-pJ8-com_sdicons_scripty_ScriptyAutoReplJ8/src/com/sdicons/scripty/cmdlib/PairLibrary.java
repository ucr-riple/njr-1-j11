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

package com.sdicons.scripty.cmdlib;

import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.parser.Pair;
import com.sdicons.scripty.annot.*;

/**
 * A Pair is an immutable parser artifact. There are only test functions and getter functions.
 * <ul>
 *    <li><b><code>pair?</code></b> Test whether an object is of type pair.<code>(pair? obj)</code></li>
 *    <li><b><code>pair-left</code></b> Get the left part of a pair.<code>(pair-left p)</code></li>
 *    <li><b><code>pair-right</code></b> Get the right part of a pair.<code>(pair-right p)</code></li>
 * </ul>
 *
 */
@ScriptyLibrary(type = ScriptyLibraryType.STATIC)
public class PairLibrary
{
    @ScriptyCommand(name="pair?")
    public static boolean isPair(Object[] aArgs)
    throws CommandException
    {
        return (guardSingleObject(aArgs) instanceof Pair);
    }
    
    @ScriptyCommand(name="pair-left")
    public static Object pairLeft(Object[] aArgs)
    throws CommandException
    {
        return guardSinglePair(aArgs).getLeft();
    }
    
    @ScriptyCommand(name="pair-right")
    public static Object pairRight(Object[] aArgs)
    throws CommandException
    {
        return guardSinglePair(aArgs).getRight();
    }
    
    private static Pair guardSinglePair(Object[] aArgs)
    throws CommandException
    {
        if(aArgs.length != 2 || !(aArgs[1] instanceof Pair))
            throw new CommandException(String.format("Command '%s' expects a single argument of type pair.", aArgs[0]));
        return (Pair) aArgs[1];
    }

    private static Object guardSingleObject(Object[] aArgs)
    throws CommandException
    {
        if(aArgs.length != 2)
            throw new CommandException(String.format("Command '%s' expects a single argument.", aArgs[0]));
        return aArgs[1];
    }
}
