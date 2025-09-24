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

public class Buffer 
implements IParserInput
{
    // Essential data.
    private String sentence;
    private int pos;
    // Informative bookkeeping.
    // Can be useful for error messages.
    private int line;
    private int col;
    
    public Buffer(String aSentence)
    {
        sentence = aSentence;
        pos = 0;
        
        line = 1;
        col = 1;
    }
    
    /* (non-Javadoc)
     * @see com.kbc.fileview.parser.IBuffer#consumeChar()
     */
    public char consumeChar()
    {
        if(eof()) return 0;
        else
        {
            // Get the character.
            final char lChar = sentence.charAt(pos++); 
            // Keep track of line/column count.
            if(lChar == '\n')
            {
                line++;
                col=1;
            }
            else col++;
            return lChar;
        }
    }
    
    /* (non-Javadoc)
     * @see com.kbc.fileview.parser.IBuffer#peekChar()
     */
    public char peekChar()
    {
        if(eof()) return 0;
        else return sentence.charAt(pos);
    }
    
    /* (non-Javadoc)
     * @see com.kbc.fileview.parser.IBuffer#eof()
     */
    public boolean eof()
    {
        return (pos >= sentence.length());
    }

    /* (non-Javadoc)
     * @see com.kbc.fileview.parser.IBuffer#getCol()
     */
    public int getColNr()
    {
        return col;
    }

    /* (non-Javadoc)
     * @see com.kbc.fileview.parser.IBuffer#getLine()
     */
    public int getLineNr()
    {
        return line;
    }    
}
