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

import com.sdicons.scripty.repl.ReplEngineException;

import java.io.InputStream;
import java.io.Reader;

public class ScriptyStreamProcessor
extends ScriptyCapable
{
    public ScriptyStreamProcessor()
    {
        // Initialize with a new ReplEngine.
    }
    
    public ScriptyStreamProcessor(ScriptyCapable aScriptyFacade)
    {
        // Initialize with an existing ReplEngine.
        super.setReplEngine(aScriptyFacade.getReplEngine());
    }
    
    public Object process(String aExpression)
    throws ProcessorException
    {
        try
        {
            return getReplEngine().startNonInteractive(aExpression);
        }
        catch (ReplEngineException e)
        {
            throw new ProcessorException(e.getMessage(), e);
        }
    }
    
    public Object process(InputStream aStream)
    throws ProcessorException
    {
        try
        {
            return getReplEngine().startNonInteractive(aStream);
        } 
        catch (ReplEngineException e)
        {
            throw new ProcessorException(e.getMessage(), e);
        }
    }
    
    public Object process(Reader aReader)
    throws ProcessorException
    {
        try
        {
            return getReplEngine().startNonInteractive(aReader);
        } 
        catch (ReplEngineException e)
        {
            throw new ProcessorException(e.getMessage(), e);
        }
    }
}
