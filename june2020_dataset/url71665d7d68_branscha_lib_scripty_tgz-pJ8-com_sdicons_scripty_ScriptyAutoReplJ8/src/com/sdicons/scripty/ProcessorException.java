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

package com.sdicons.scripty;

public class ProcessorException
extends Exception
{
    public ProcessorException()
    {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ProcessorException(String message)
    {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ProcessorException(String message, Throwable cause)
    {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ProcessorException(Throwable cause)
    {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
