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

package com.sdicons.scripty.parser;

public class CommandException 
extends Exception
{
    private static final long serialVersionUID = -4301173601968482098L;

    // The stack will be filled in by the Eval2 implementation.
    // The Eval implementation does not manage a stack of its own, it will only
    // produce a message without actual stack.
    
    private Eval2.EvalStack stack;

    // Normal constructors without stack.
    // Used by the Eval implementation.
    
    public CommandException()
    {
        super();
    }

    public CommandException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CommandException(String message)
    {
        super(message);
    }

    public CommandException(Throwable cause)
    {
        super(cause);
    }

    // Stack constructors.
    // These are used by the Eval2 implementation.

    public CommandException(Eval2.EvalStack aStack)
    {
        super();
        this.stack = aStack;
    }

    public CommandException(String message, Throwable cause, Eval2.EvalStack aStack)
    {
        super(message, cause);
        this.stack = aStack;
    }

    public CommandException(String message, Eval2.EvalStack aStack)
    {
        super(message);
        this.stack = aStack;
    }

    public CommandException(Throwable cause, Eval2.EvalStack aStack)
    {
        super(cause);
        this.stack = aStack;
    }

    public Eval2.EvalStack getStack()
    {
        return stack;
    }

    public void setStack(Eval2.EvalStack stack)
    {
        this.stack = stack;
    }

    @Override
    public String getMessage()
    {
        // This method accomodates both Eval and Eval2 implementations.
        // If it is an exception from Eval, normally only the message will be available.
        // If it is a message from Eval2, we will include our own stacktrace.

        final String lMsg = super.getMessage();
        if(stack != null) return lMsg + "\n" + stack.toString();
        else return lMsg;
    }
}
