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

import javax.swing.*;
import java.awt.*;

@ScriptyLibrary(name="System", type=ScriptyLibraryType.STATIC)
public class ExitLibrary
{
    @ScriptyCommand
    @ScriptyStdArgList(optional = {@ScriptyArg(name="code", type="Integer", value = "0")})
    public static void exit(@ScriptyParam("code") Integer aCode)
    {
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final boolean lIsHeadless = ge.isHeadless();
        if(lIsHeadless)
        {
            // We have to close all frames. Closing our main frame is not enough,
            // since the help system for instance creates other frames that have to be disposed as well.
            for(Frame lAppFrm : JFrame.getFrames()) lAppFrm.dispose();
        }
        else 
        {
            System.exit(aCode);
        }
    }
}
