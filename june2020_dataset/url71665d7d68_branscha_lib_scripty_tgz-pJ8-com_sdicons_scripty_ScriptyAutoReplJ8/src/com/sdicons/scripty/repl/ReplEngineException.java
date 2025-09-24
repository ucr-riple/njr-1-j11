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

package com.sdicons.scripty.repl;

public class ReplEngineException
extends Exception
{
    private int line = 1;
    private int column = 1;
    
    public ReplEngineException()
    {
        super();
    }

    public ReplEngineException(int aLine, int aColumn, String message)
    {        
        super(message);
        line = aLine;
        column = aColumn;
    }

    public ReplEngineException(int aLine, int aColumn, String message, Throwable cause)
    {
        super(message, cause);
        line = aLine;
        column = aColumn;
    }

    public ReplEngineException(int aLine, int aColumn, Throwable cause)
    {        
        super(cause);
        line = aLine;
        column = aColumn;
    }

    @Override
    public String getMessage()
    {
        return String.format("ERROR: On line %d column %d.\n%s", line, column, super.getMessage());
    }
}
