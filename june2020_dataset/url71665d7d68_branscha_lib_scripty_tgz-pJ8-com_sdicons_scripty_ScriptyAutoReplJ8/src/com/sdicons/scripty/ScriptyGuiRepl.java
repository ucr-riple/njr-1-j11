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

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ScriptyGuiRepl
extends ScriptyCapable    
{
    private JFrame frame;
    private String title = "Scripty";
    private ScriptyPanel scriptyPanel;

    public ScriptyGuiRepl()
    {
    }

    public ScriptyGuiRepl(ScriptyCapable aFacade)
    {
        super(aFacade);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
        if(frame != null) frame.setTitle(title);
    }

    private void buildGuiConsole()
    {
        frame = new JFrame(title);

        // Install the closing mechanism on the frame when the user
        // wants to close the frame by clicking the X button.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                for(Frame lAppFrm : JFrame.getFrames()) lAppFrm.dispose();
            }
        });

        // Calculate the middle of the screen.
        final Rectangle lScreenRect = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        // Using the 'golden ratio' 1.618 for the width/height ratio.
        final Rectangle lFrameRect = new Rectangle(Math.min(647, lScreenRect.width), Math.min(400, lScreenRect.height));
        // Set the window size and location.
        frame.setLocation((lScreenRect.width - lFrameRect.width) / 2,(lScreenRect.height - lFrameRect.height) / 2);
        frame.setSize(lFrameRect.width, lFrameRect.height);
        //
        scriptyPanel = new ScriptyPanel(this);
        frame.add(scriptyPanel);
    }

    public void startLoop()
    {
        buildGuiConsole();
        if(frame != null) frame.setVisible(true);
    }
}


