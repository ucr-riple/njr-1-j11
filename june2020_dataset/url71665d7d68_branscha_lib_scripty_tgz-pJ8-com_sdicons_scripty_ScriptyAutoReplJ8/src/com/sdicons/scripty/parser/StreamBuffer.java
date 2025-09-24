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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class StreamBuffer
implements IParserInput
{
    private Reader reader;
    private char buff[] = new char[1024];
    private int buffPos = 0;
    private int buffLim = 0;
    private boolean eof = false;
    private int line = 1;
    private int col = 1;
    
    public StreamBuffer(InputStream aIn)
    {
        reader = new InputStreamReader(aIn);        
    }
    
    public StreamBuffer(Reader aReader)
    {
        reader = aReader;
    }

    public char consumeChar()
    {
        fillBuf();
        if(eof) return 0;
        char lChar = buff[buffPos++];
        // Keep track of line/column count.
        if(lChar == '\n')
        {
            line++;
            col=1;
        }
        else col++;
        return lChar;
    }

    public boolean eof()
    {
        fillBuf();
        return eof;
    }

    public int getColNr()
    {
        return col;
    }

    public int getLineNr()
    {
        return line;
    }

    public char peekChar()
    {
        fillBuf();
        if(eof) return 0;
        else return buff[buffPos];
    }
    
    private void fillBuf()
    {
        try
        {
            if(!eof && buffPos >= buffLim)
            {
                buffLim = reader.read(buff);
                if(buffLim == -1)
                {
                    eof = true;
                    buffPos = buffLim = 0;                    
                }
                else 
                {
                    buffPos = 0;
                }
            }
        }
        catch (IOException e)
        {
            buffLim = buffPos = 0;
        }        
    }   
}
