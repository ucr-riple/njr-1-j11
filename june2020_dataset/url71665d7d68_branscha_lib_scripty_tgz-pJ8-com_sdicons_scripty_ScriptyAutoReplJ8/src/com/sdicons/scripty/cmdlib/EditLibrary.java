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

import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.parser.Parser;
import com.sdicons.scripty.parser.Token;
import com.sdicons.scripty.annot.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ScriptyLibrary(type=ScriptyLibraryType.STATIC)
@ScriptyNamedArgLists(std = {@ScriptyStdArgList(name="content + title", optional = {@ScriptyArg(name="content", type="String")}, named = {@ScriptyArg(name="title", type="String", optional = true, value = "Text Editor")})})
public class EditLibrary
{
    @ScriptyCommand(name="edit")
    @ScriptyRefArgList(ref = "content + title")
    public static String edit(@ScriptyParam("content") String aContent, @ScriptyParam("title") String aTitle)
    throws CommandException
    {
        // Not available in head less mode.
        if(GraphicsEnvironment.isHeadless())
            throw new CommandException("Java VM is running in headless mode.");

        JFrame lParent = null;
        final EditDialog lDiag = new EditDialog(lParent, aTitle, aContent);
        lDiag.setLocationRelativeTo(lParent);
        
        lDiag.setVisible(true);
        final String lEdited = lDiag.getContent();

        return lEdited;
    }

    @ScriptyCommand(name="edit-expr")
    @ScriptyRefArgList(ref = "content + title")
    public static Object editExpr(@ScriptyParam("content") String aContent, @ScriptyParam("title") String aTitle)
    throws CommandException
    {
        final String lEdited = edit(aContent, aTitle);
        if(lEdited != null)
        {
            Parser lParser = new Parser();
            Object lRes =  lParser.parseExpression(lEdited);
            if(lRes instanceof  Token)
            {
                Token lResTok = (Token) lRes;
                if(lResTok.isErroneous()) throw new CommandException(lResTok.getValue());
            }
            return lRes;
        }
        else return null;
    }
}

class EditDialog
extends JDialog
{
    private String content = null;
    private JTextArea txtArea;

    public EditDialog(Frame aParent, String lTitle, String aContent)
    {
        this(aParent, lTitle);
        txtArea.setText(aContent);
    }

    public EditDialog(Frame aParent, String lTitle)
    {
        super(aParent, lTitle);

        setModal(true);
        setSize(400, 300);

        Container lCont = getContentPane();
        lCont.setLayout(new GridBagLayout());
        txtArea = new JTextArea();

        final Font lFont = new Font("Monospaced", Font.PLAIN, 12);
        txtArea.setFont(lFont);

        GridBagConstraints lConstraints = new GridBagConstraints();
        lConstraints.gridx = 0;
        lConstraints.gridy = 0;
        lConstraints.gridwidth = 3;
        lConstraints.gridheight = 1;
        lConstraints.fill = GridBagConstraints.BOTH;
        lConstraints.insets = new Insets(5,5,5,5);
        lConstraints.weightx = 1.0;
        lConstraints.weighty = 1.0;
        lCont.add(new JScrollPane(txtArea), lConstraints);

        JButton lOkButton = new JButton("Ok");
        JButton lCancelButton = new JButton("Cancel");

        lConstraints = new GridBagConstraints();
        lConstraints.gridwidth = 2;
        lConstraints.gridheight = 1;
        lConstraints.gridx = 0;
        lConstraints.gridy = 1;
        lConstraints.insets = new Insets(0,5,5,5);
        lConstraints.weightx = 1.0;
        lConstraints.anchor = GridBagConstraints.EAST;
        lCont.add(lOkButton, lConstraints);

        lConstraints.gridwidth = 1;
        lConstraints.gridx = 2;
        lConstraints.gridy = 1;
        lConstraints.weightx = 0.0;
        lCont.add(lCancelButton, lConstraints);

        lCancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                content = null;
                EditDialog.this.setVisible(false);
            }
        });

        lOkButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                content = txtArea.getText();
                EditDialog.this.setVisible(false);
            }
        });
    }

    public String getContent()
    {
        return content;
    }
}
