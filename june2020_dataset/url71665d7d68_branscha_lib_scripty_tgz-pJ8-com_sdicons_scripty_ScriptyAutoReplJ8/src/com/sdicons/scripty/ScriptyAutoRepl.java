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

import com.sdicons.scripty.cmdlib.MathLibrary;
import com.sdicons.scripty.cmdlib.PrintLibrary;

import java.awt.*;

public class ScriptyAutoRepl
extends ScriptyCapable
{
    private ScriptyGuiRepl guiRepl;
    private ScriptyTextRepl textRepl;
    private boolean forceTextMode = false;
    
    public ScriptyAutoRepl()
    {
    }

    public ScriptyAutoRepl(ScriptyCapable aFacade)
    {
        super(aFacade);
    }
    
    private void buildRepl()
    {
        final boolean lIsHeadless = GraphicsEnvironment.isHeadless();

        if(lIsHeadless || forceTextMode)
        {
            textRepl = new ScriptyTextRepl(this);
        }
        else
        {
            guiRepl = new ScriptyGuiRepl(this);
        }
    }

    public void setForceTextMode(boolean aForceText)
    {
        forceTextMode = aForceText;
    }
    
    public void startLoop()
    {
        buildRepl();

        if(guiRepl != null) guiRepl.startLoop();
        else if(textRepl != null) textRepl.startLoop();
        else throw new IllegalStateException("Could not create GUI nor TEXT REPL.");
    }
    
    public static void main(String[] aArgs)
    throws ExtensionException
    {
        ScriptyAutoRepl lRepl = new ScriptyAutoRepl();
//        lRepl.setForceTextMode(true);
        lRepl.addLibraryClasses(PrintLibrary.class, MathLibrary.class);
        lRepl.startLoop();
    }
}
