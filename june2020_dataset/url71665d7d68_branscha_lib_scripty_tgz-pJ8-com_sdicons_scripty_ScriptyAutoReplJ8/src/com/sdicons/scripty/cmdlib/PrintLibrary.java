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

import com.sdicons.scripty.annot.*;

import java.io.PrintWriter;

@ScriptyLibrary(name="System", type=ScriptyLibraryType.STATIC)
public class PrintLibrary
{
    @ScriptyCommand
    public static String print(Object[] aArgs,
                               @ScriptyBindingParam("*output") PrintWriter aWriter)
    {
        final String lResult = buildString(aArgs);
        if (aWriter != null) aWriter.print(lResult);
        return lResult;
    }

    @ScriptyCommand
    public static String println(Object[] aArgs,
                               @ScriptyBindingParam("*output") PrintWriter aWriter)
    {
        final String lResult = buildString(aArgs);
        if (aWriter != null) aWriter.println(lResult);
        return lResult;
    }
    
    private static String buildString(Object[] aArgs)
    {
        final StringBuilder lBuilder = new StringBuilder();
        for (int i = 1; i < aArgs.length; i++)
        {
            final Object lObj = aArgs[i];
            if (lObj != null)
            {
                lBuilder.append(lObj.toString());
                if(i < aArgs.length - 1) lBuilder.append(" ");
            }
        }
        return lBuilder.toString();
    }
}
