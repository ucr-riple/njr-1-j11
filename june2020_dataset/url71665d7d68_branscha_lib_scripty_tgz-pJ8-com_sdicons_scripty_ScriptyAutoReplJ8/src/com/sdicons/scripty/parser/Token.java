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

public class Token
{
    // Difference between eof versus error.
    // We need this for the REPL. If a command is not complete, the user can continue 
    // the command on a fresh line. If there is an error, the message will be printed and a new
    // command will be started.
    public enum TokenType {beginlist, endlist, string, pair, whitespace, quote, error, eof};
    
    private String value;
    private TokenType type;
    
    private int line;
    private int col;
    
    public Token(TokenType aType, String aValue, int aLine, int aCol)
    {
        type = aType;
        value = aValue;
        line = aLine;
        col = aCol;
    }

    public TokenType getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }  
    
    public boolean isWhitespace()
    {
        return type == TokenType.whitespace;
    }
    
    public boolean isErroneous()
    {
        return isError() || isEof();
    }
    
    public boolean isError()
    {
        return type == TokenType.error;
    }
    
    public boolean isEof()
    {
        return type == TokenType.eof;
    }
    
    public boolean isBeginList()
    {
        return type == TokenType.beginlist;
    }
    
    public boolean isEndList()
    {
        return type == TokenType.endlist;
    }
    
    public boolean isString()
    {
        return type == TokenType.string;
    }
    
    public boolean isQuote()
    {
        return type == TokenType.quote;
    }
    
    public boolean isPair()
    {
        return type == TokenType.pair;
    }

    public int getCol()
    {
        return col;
    }

    public int getLine()
    {
        return line;
    }
}
