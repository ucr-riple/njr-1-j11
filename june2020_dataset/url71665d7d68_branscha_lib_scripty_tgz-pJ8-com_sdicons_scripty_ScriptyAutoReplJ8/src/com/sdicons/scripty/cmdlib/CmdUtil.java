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

public class CmdUtil
{    
    /**
     * Concatenate error messages from nested exceptions to get an error message that contains
     * as much information as possible.
     * 
     * @param e An exception.
     * @return An exception message that is the concatenation of all embedded exception messages.
     */
    public static String concatExceptionMessages(Throwable e)
    {
        final StringBuilder lBuilder = new StringBuilder();
        if(e.getMessage() != null) lBuilder.append(e.getMessage());
        if(e.getCause() != null) lBuilder.append("\n");
        Throwable t = e;
        while (t.getCause() != null)
        {
            t = t.getCause();
            if(t.getMessage() != null)
                lBuilder.append(t.getMessage());
            if(t.getCause() != null) lBuilder.append("\n");
        }
        return lBuilder.toString();
    }
}
