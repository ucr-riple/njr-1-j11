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

import com.sdicons.scripty.parser.ICommand;
import com.sdicons.scripty.parser.IContext;
import com.sdicons.scripty.repl.ReplEngine;

public abstract class ScriptyCapable
implements IExtensions, IContextHolder
{
    private ReplEngine replEngine;

    public ScriptyCapable()
    {
        replEngine = new ReplEngine();
    }

    public ScriptyCapable(ScriptyCapable aFacade)
    {
        replEngine = aFacade.getReplEngine();
    }
    
    protected ReplEngine getReplEngine()
    {
        return replEngine;
    }

    protected void setReplEngine(ReplEngine aEngine)
    {
        replEngine = aEngine;
    }
    
    public IContext getContext()
    {
        return getReplEngine().getContext();
    }
    
    public void setContext(IContext aContext)
    {
        getReplEngine().setContext(aContext);
    }

    public void addCommand(String aName, ICommand aCommand)
            throws ExtensionException
    {
        getReplEngine().addCommand(aName, aCommand);
    }

    public void addMacro(String aName, ICommand aMacro)
    throws ExtensionException
    {
        getReplEngine().addMacro(aName, aMacro);
    }

    public void addLibraryClasses(Class... aLibraryClasses)
            throws ExtensionException
    {
        getReplEngine().addLibraryClasses(aLibraryClasses);
    }

    public void addLibraryInstances(Object... aLibraryInstances)
            throws ExtensionException
    {
        getReplEngine().addLibraryInstances(aLibraryInstances);
    }
}
