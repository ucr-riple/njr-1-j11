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

import java.util.LinkedList;
import java.util.List;

/**
 * A minimal parser. Only lists, strings (delimited or not) and pairs (to make named parameters possible).
 * It is not intended as a full programming language, it is intended as a command processor.
 * No numbers, floats, symbols or other built-in data structures. The command extensions have to provide these 
 * if needed. The pairs are a special feature, it is a syntactic convenience to allow parser support
 * for key/value pairs which are often used to pass command line options, it has nothing to do with conses.
 * <p>
 * Examples:
 * <pre>
 * abc           -> A string.
 * "abc def"     -> A string with embedded whitespace.
 * key=val       -> A pair.
 * ()            -> Empty list.
 * (a b c d)     -> List of four strings.
 * (a b (c (d))) -> Nested lists.
 * (a b)=(c d)   -> A pair where both key and value are lists.
 * a = b = c     -> pair(pair(a,b),c). So pairing works from left to right.
 * ; comments    -> Comments start with a ; and are skipped by the lexer.
 * </pre>
 *
 */
public class Parser
{
    private Token pushback = null;
    
    /**
     * Lexer method.
     * 
     * @param aBuffer
     * @return
     */
    private Token getNextToken(IParserInput aBuffer)
    {
        if(pushback != null)
        {
            Token lToken = pushback;
            pushback = null;
            return lToken;            
        }
        
        if(aBuffer.eof()) return new Token(Token.TokenType.eof, "Unexpected end of expression encountered.", aBuffer.getLineNr(), aBuffer.getColNr());
        else
        {
            // Keep track of the start of the token.
            final int lLine = aBuffer.getLineNr();
            final int lCol = aBuffer.getColNr(); 
            // We switch on the value of lChar to see what we have to do next.
            char lChar = aBuffer.consumeChar();   
            
            switch(lChar)
            {
                case '(':
                    return new Token(Token.TokenType.beginlist, "(", lLine, lCol);
                case ')':
                    return new Token(Token.TokenType.endlist, ")", lLine, lCol);
                case '\'':
                    return new Token(Token.TokenType.quote, "'", lLine, lCol);
                case '=':
                    return new Token(Token.TokenType.pair, "=", lLine, lCol);
                case ';':
                {
                    // Comments, skip until end of line.
                    char lPeek = aBuffer.peekChar();
                    while(!aBuffer.eof() && ('\n' != lPeek))
                    {
                        aBuffer.consumeChar();
                        lPeek = aBuffer.peekChar();
                    }
                    // Consume newline as well.
                    if('\n' == lPeek) aBuffer.consumeChar();
                    return new Token(Token.TokenType.whitespace, "<comment>", lLine, lCol);
                }   
                case '"':
                {
                    // String literal encountered.
                    // Support for '\\',  '\"', '\n' and '\t'.
                    // Note that the starting " is skipped, it is not added to the value.
                    final StringBuilder lValue = new StringBuilder(); 
                    char lPeek = aBuffer.peekChar();
                    while(!aBuffer.eof() && ('"' != lPeek))
                    {
                        if('\\' == lPeek)
                        {
                            // Consume the backslash.
                            aBuffer.consumeChar();
                            lPeek = aBuffer.peekChar();
                            switch(lPeek)
                            {
                                case 'n':
                                {
                                    // We found a newline.
                                    lValue.append("\n");
                                    // Consume the 'n' char.
                                    aBuffer.consumeChar();
                                    break;
                                } 
                                case 't':
                                {
                                    // We found a double quote.
                                    lValue.append("\t");
                                    // Consume the 't' char.
                                    aBuffer.consumeChar();
                                    break;
                                }
                                case '"':
                                {
                                    // We found a double quote.
                                    lValue.append("\"");
                                    // Consume the '"'.
                                    aBuffer.consumeChar();
                                    break;
                                }
                                case '\\':
                                {
                                    // Add a backslash to the value.                            
                                    lValue.append("\\");
                                    // Consume the second backslash.
                                    aBuffer.consumeChar();
                                    break;
                                }
                                default:
                                {
                                    // Unknown quoted character found ...
                                    // We add the complete sequence to the string!
                                    // This allows cleaner regexp expressions in our language, but can 
                                    // lead to errors if a mistake is made.
                                    // For the time being I choose the more relaxed checking, since
                                    // this better fits the style and purpose of our language.
                                    lValue.append("\\").append(aBuffer.consumeChar());
                                    // The other option is to be strict and reject these escape messages.
                                    // In this case this following code should be used in stead.
                                    //return new Token(Token.TokenType.error, String.format("Unexpected escaped character found in a string constant: '\\%s' is not defined.", lPeek), lLine, lCol);                            
                                }
                            }
                        }
                        else lValue.append(aBuffer.consumeChar());
                        
                        // Peek the next character.
                        lPeek = aBuffer.peekChar();
                    }
                    
                    // We examine the two finishing conditions.
                    // The string is complete OR the buffer ended unexpectedly.
                    if(aBuffer.eof()) 
                    {
                        // EOF encountered, open string ...
                        return new Token(Token.TokenType.eof, String.format("Unclosed string starting on line: %d col: %d with contents \"%s\".", lLine, lCol, lValue.toString()), lLine, lCol);
                    }
                    else 
                    {
                        // The string ended in a normal way.
                        // We skip the " delimiter without doing anything with it.
                        aBuffer.consumeChar();
                        return new Token(Token.TokenType.string, lValue.toString(), lLine, lCol);
                    }
                }
                default:
                {
                    if(Character.isWhitespace(lChar))
                    {                
                        // Whitespace encountered. In this mode we gobble all whitespace and put it in 
                        // a single token.
                        final StringBuilder lValue = new StringBuilder();
                        lValue.append(lChar);
                        while (!aBuffer.eof() && Character.isWhitespace(aBuffer.peekChar()))
                            lValue.append(aBuffer.consumeChar());
                        // We return the accumulated whitespace.
                        return new Token(Token.TokenType.whitespace, lValue.toString(), lLine, lCol);
                    } 
                    else 
                    {
                        // Normal identifier encountered. We turn this into a string as well.
                        // We have to test on set of characters that do not appear in any of the
                        // previous branches.
                        final StringBuilder lValue = new StringBuilder();
                        lValue.append(lChar);
                        char lPeek = aBuffer.peekChar();
                        while (!aBuffer.eof() && '(' != lPeek && ')' != lPeek && '"' != lPeek && !Character.isWhitespace(lPeek) && '=' != lPeek && ';' != lPeek)
                        {
                            lValue.append(aBuffer.consumeChar());
                            lPeek = aBuffer.peekChar();
                        }
                        return new Token(Token.TokenType.string, lValue.toString(), lLine, lCol);
                    }
                }
            }
        }        
    }
    
    private  void pushBackToken(Token lToken)
    {
        pushback = lToken;
    }
    
    private Token getNextNonWhitespaceToken(IParserInput aBuffer)
    {
        Token lToken = getNextToken(aBuffer);
        while(lToken.isWhitespace()) lToken = getNextToken(aBuffer);        
        return lToken;
    }
    
    /**
     * Returns a token or a List<Object> ...
     * A token result always indicates an error, parsing failed.
     * 
     * @param aBuffer
     * @return
     */
    @SuppressWarnings("unchecked")
	private Object parseList(IParserInput aBuffer)
    {
        Token lToken = getNextNonWhitespaceToken(aBuffer);
        if(lToken.isErroneous()) return lToken;
        else if(lToken.isBeginList())
        {
            // Keep track of beginning of the list.
            final int lLine = lToken.getLine();
            final int lCol = lToken.getCol();
            // Create the result of the list parsing.
            final List<Object> lResultList = new LinkedList<Object>();
            lToken = getNextNonWhitespaceToken(aBuffer);
            
            // Read the contents of the list. Any expression can occur in a list.
            // There has to be a recursive call in here.
            while(!lToken.isErroneous() && !lToken.isEndList())
            {
                // Push back the token so that the expression parser
                // has access to the full expression.
                pushBackToken(lToken);
                final Object lExpr = parseExpression(aBuffer);
                if(lExpr instanceof String || lExpr instanceof List || lExpr instanceof Pair) lResultList.add(lExpr);
                else if(lExpr instanceof Token) 
                {
                    Token lErrorToken = (Token) lExpr;
                    if(lErrorToken.isError()) return lErrorToken;
                    else if(lErrorToken.isEof()) return new Token(Token.TokenType.eof, String.format("Syntax error in the list starting at line: %d, col: %d.\n%s", lLine, lCol, lErrorToken.getValue()), lLine, lCol);                    
                    else return new Token(Token.TokenType.error, String.format("Syntax error in the list starting at line: %d, col: %d.\n Received unexpected token with contents \"%s\".", lLine, lCol, lErrorToken.getValue()), lLine, lCol);
                }
                else 
                {
                    return new Token(Token.TokenType.error, String.format("Internal error, evaluation resulted in an unexpected value: %d, col: %d.", lLine, lCol), lLine, lCol);                    
                }
                
                // Process the next token ...
                lToken = getNextNonWhitespaceToken(aBuffer);
            }
            
            // Test on the two possible ending conditions of the loop.
            if(lToken.isError()) return lToken;
            else if(lToken.isEof()) return new Token(Token.TokenType.eof, String.format("Syntax error in the list starting at line: %d, col: %d.\n%s", lLine, lCol, lToken.getValue()), lLine, lCol);
            else return lResultList;                                    
        }
        else
        {
            return new Token(Token.TokenType.error, "Syntax error, expected '(' but encoutered: " + lToken.getValue(), lToken.getLine(), lToken.getCol());            
        }                           
    }
    
    /**
     * Minimal parser, it can parse lists and strings (delimited and undelimited).
     * No numbers, floats, ...
     * Some notworthy functionality:
     *   - The parser understands quotes '. It will replace backticked expressions '<expr> with (quote <expr>).
     *     This is just syntactic sugar for convenience.
     * 
     * @param aBuffer
     * @return
     */
    public Object parseExpression(IParserInput aBuffer)
    {
        final Token lToken = getNextNonWhitespaceToken(aBuffer);
        if(lToken.isErroneous()) 
        {
            return lToken;
        }
        else if(lToken.isBeginList() || lToken.isString()) 
        {
            Object lResultExpr;            
            if(lToken.isBeginList())
            {
                // List found.
                // Push it back, so that the list parser can parse the complete list.
                pushBackToken(lToken);
                // Pass control to the list production.
                lResultExpr = parseList(aBuffer);
            }
            else
            {
                // String found.
                lResultExpr = lToken.getValue();                
            }
            
            // If something bad happened with the result of the parsing, we
            // don't attempt to parse a pair.
            if(lResultExpr instanceof Token) return lResultExpr;
            
            // Now we check if the expression is part of a pairing or not.
            // Take a look at the next token.
            final Token lPeek = getNextNonWhitespaceToken(aBuffer);
            if(lPeek.isPair())
            {
                // Yes, we found a pair.
                final Object lValueExpr = parseExpression(aBuffer);
                // If something bad happened with the value part of the pair,
                // we quit with the error token.
                if(lValueExpr instanceof Token) return lValueExpr;
                // If we got here, we got ourselves a nice pair.
                return new Pair(lResultExpr, lValueExpr);                
            }
            else
            {
                // No, not part of pairing at all.
                pushBackToken(lPeek);
                return lResultExpr;
            }                        
        }     
        else if (lToken.isQuote())
        {
            // Take a look at the next token.
            final Token lPeek = getNextToken(aBuffer);
            pushBackToken(lPeek);
            
            // If it is whitespace, we return the backtick as
            // a string. If it is followed by something else
            // we embed the next thing with (quote ...).
            if(!lPeek.isWhitespace())
            {            
                // Get the next expression.
                final Object lExpr = parseExpression(aBuffer);
                // If we get a token, it must be an error.
                if(lExpr instanceof Token) return lExpr;
                // Otherwise we embed it.
                final List<Object> lResult = new LinkedList<Object>();
                lResult.add("quote");
                lResult.add(lExpr);
                return lResult;
            }
            else
            {
                return lToken.getValue();
            }
        }
        else 
        {
            // Unexpected token found.
            return new Token(Token.TokenType.error, String.format("Syntax error, expected a string- or listtoken, but found a token of type: '%s', value: '%s'.", lToken.getType(), lToken.getValue()), lToken.getLine(), lToken.getCol());            
        }        
    }
    
    public Object parseExpression(String aExpression)
    {
        reset();
        return parseExpression(new Buffer(aExpression));
    }
    
    public void reset()
    {
        pushback = null;
    }
}
