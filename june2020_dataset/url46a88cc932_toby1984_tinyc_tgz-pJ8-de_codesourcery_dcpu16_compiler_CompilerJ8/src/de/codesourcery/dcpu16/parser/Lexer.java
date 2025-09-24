package de.codesourcery.dcpu16.parser;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import de.codesourcery.dcpu16.ast.NumberLiteralNode;
import de.codesourcery.dcpu16.compiler.Identifier;
import de.codesourcery.dcpu16.compiler.Operator;
import de.codesourcery.dcpu16.exceptions.ParseException;
import de.codesourcery.dcpu16.util.IInputStreamProvider;

public class Lexer
{
    private final IInputStreamProvider input;
    private Scanner scanner;
    
    private final List<Token> tokens = new ArrayList<>();
    private boolean atEOF = false;
    
    private boolean ignoreWhitespace = true;
    
    // the token returned by the last read() call , 
    // required to be able to reset the scanner
    // when switching from "ignore whitespace" -> "do not ignore whitespace"
    private Token previousToken = null;
    
    // transient
    private final StringBuilder buffer = new StringBuilder();
    private final Stack<State> stack = new Stack<>();
    
    protected final class State 
    {
        private final List<Token> tokens = new ArrayList<>(Lexer.this.tokens);
        private final boolean atEOF = Lexer.this.atEOF;
        private final int currentOffset = scanner.getOffset();
        private final boolean ignoreWhitespace = Lexer.this.ignoreWhitespace;
        private final Token previousToken = Lexer.this.previousToken;
        
        public void apply() throws IOException 
        {
            Lexer.this.tokens.clear();
            Lexer.this.tokens.addAll(tokens);
            Lexer.this.atEOF = atEOF;
            Lexer.this.ignoreWhitespace = ignoreWhitespace;
            Lexer.this.previousToken = previousToken;
            
            final InputStream newIn = createInputStream( currentOffset );
            Lexer.this.scanner = new Scanner( newIn, currentOffset);
        }
    }
    
    private InputStream createInputStream(int offset) throws EOFException, IOException {
        return advanceInputStream( input.createInputStream() , offset );
    }
    
    private static InputStream advanceInputStream(InputStream newIn, int bytesToSkip) throws EOFException, IOException 
    {
        int toAdvance = bytesToSkip;
        while ( toAdvance > 0 ) 
        {
            if ( newIn.read() == -1 ) {
                throw new EOFException();
            }
            toAdvance--;
        }
        return newIn;
    }
    
    public String toString() {
    	return tokens.toString();
    }
    
    public Lexer(IInputStreamProvider input,boolean ignoreWhitespace) throws IOException 
    {
        this.input = input;
        this.scanner = new Scanner(input.createInputStream());
        this.ignoreWhitespace = ignoreWhitespace;
        parse();
    }
    
    public void mark() {
        stack.push( new State() );
    }
    
    public void dropMark()
    {
        if ( stack.isEmpty() ) {
            throw new IllegalStateException("Stack empty?");
        }
        stack.pop();
    }     
    
    public void reset() throws IOException 
    {
        if ( stack.isEmpty() ) {
            throw new IllegalStateException("Stack empty?");
        }
        stack.pop().apply();
    }    
    
    public boolean eof() {
        return tokens.isEmpty() && atEOF;
    }
    
    public Token read(TokenType t) throws IOException
    {
        if ( ! peek(t) ) {
            throw new ParseException("Expected "+t+" but got "+peek(),peek());
        }
        return read();
    }
    
    public int currentOffset() throws IOException 
    {
        if ( tokens.isEmpty() ) {
            parse();
        }
        return tokens.get(0).getOffset();
    }
    
    public Token read() throws IOException
    {
        if ( tokens.isEmpty() ) {
            parse();
        }
        Token result= tokens.remove(0);
        if ( ! atEOF ) {
            parse();
        }
        previousToken = result;
        return result;
    }
    
    
    public boolean peek(TokenType t) throws IOException
    {
        return peek().hasType(t);
    }
    
    public Token peek() throws IOException
    {
        if ( tokens.isEmpty() ) {
            parse();
        }
        return tokens.get(0);
    }    
    
    private boolean checkEOF() throws EOFException 
    {
    	boolean result = false;
        if ( scanner.eof() ) 
        {
        	result = true;
            if ( atEOF ) {
                throw new EOFException();
            }
            atEOF = true;
        }
        return result;
    }
    
    private void parse() throws IOException
    {
        if ( checkEOF() ) 
        {
            return;
        }
        
        buffer.setLength(0);
        
        if ( ignoreWhitespace ) 
        {
        	while ( ! checkEOF() && isWhitespace( scanner.peek() ) ) 
        	{
        		scanner.read();
        	}
        } 
        else 
        {
        	int offset = scanner.getOffset();
        	while ( ! checkEOF() && isWhitespace( scanner.peek() ) ) 
        	{
        		char c = scanner.read();
        		if ( c == '\n' ) 
        		{
        			if ( buffer.length() != 0 ) {
        				tokens.add( new Token(TokenType.WHITESPACE,offset,buffer.toString() ) );
        			}
        			tokens.add( new Token(TokenType.EOL,scanner.getOffset()-1, "\n" ) );
        			return;
        		} 
        		
        		if ( c == '\r' ) 
        		{
        		    if ( checkEOF() || scanner.peek() != '\n' ) 
        		    {
           				tokens.add( new Token(TokenType.WHITESPACE,offset,buffer.toString()+"\r" ) );
            			return;
        		    }
        		    
        		    if ( buffer.length() != 0 ) {
        		    	tokens.add( new Token(TokenType.WHITESPACE,offset,buffer.toString() ) );
        		    }
        		    scanner.read(); // skip \n
        			tokens.add( new Token(TokenType.EOL,scanner.getOffset()-2, "\r\n" ) );        		    
        		    return;
        		} 
       			buffer.append( c );
        	}
        	
		    if ( buffer.length() != 0 ) {
		    	tokens.add( new Token(TokenType.WHITESPACE,offset,buffer.toString() ) );
		    }
		    buffer.setLength(0);
        }
        
        if ( atEOF ) {
        	return;
        }
        
        int bufferStartOffset = scanner.getOffset();
        while ( ! checkEOF() ) 
        {
            int offset = scanner.getOffset();
            char c = scanner.peek();
            
            if ( isWhitespace( c ) ) 
            {
                break;
            }
            scanner.read();
            
            if ( Operator.isOperatorPrefix( ""+c ) ) 
            {
                String prefix = ""+c;
                while ( ! checkEOF() && Operator.isOperatorPrefix( prefix + scanner.peek() ) ) {
                    prefix += scanner.read();
                }
                if ( Operator.isValidOperator( prefix ) ) {
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.OPERATOR, offset, prefix ) );
                    return;
                }
                buffer.append( prefix );
                continue;
            }
            
            switch( c ) 
            {
                case ';':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.SEMICOLON, offset, ";" ) );
                    return;                    
                case ',':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.COMMA, offset, "," ) );
                    return;                   
                case '(':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.PARENS_OPEN, offset,"(" ) );
                    return;                
                case ')':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.PARENS_CLOSE , offset,")" ) );
                    return;   
                case '*':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.STAR, offset,"*" ) );
                    return;   
                case '&':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.AMPERSAND, offset,"&" ) );
                    return;       
                case '\\':
                	parseBuffer(bufferStartOffset);
                	tokens.add( new Token(TokenType.ESCAPE_CHARACTER, offset,"\\" ) );
                	return;                   
                case '[':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.ANGLE_BRACKETS_OPEN, offset,"[" ) );
                    return;                
                case ']':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.ANGLE_BRACKETS_CLOSE ,offset, "]" ) );
                    return;
                case '{':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.BRACE_OPEN, offset,"{" ) );
                    return;   
                case '}':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.BRACE_CLOSE ,offset, "}" ) );
                    return;                        
                case '\"':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.DOUBLE_QUOTE, offset , "\"" ) );
                    return;     
                case '\'':
                    parseBuffer(bufferStartOffset);
                    tokens.add( new Token(TokenType.SINGLE_QUOTE, offset , "\'" ) );
                    return;                      
            }
            buffer.append( c );
        }
        parseBuffer(bufferStartOffset);
    }
    
    private void parseBuffer(int offset) 
    {
        final String s = buffer.toString();
        if ( s.length() == 0 ) {
            return;
        }
        
        if ( "if".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.IF, offset,s ) );            
            return;
        }
        
        if ( "else".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.ELSE, offset,s ) );            
            return;
        }    
        
        if ( "while".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.WHILE, offset,s ) );            
            return;
        }
        
        if ( "do".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.DO, offset,s ) );            
            return;
        }      
        
        if ( "for".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.FOR, offset,s ) );            
            return;
        }          
        
        if ( "true".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.TRUE, offset,s ) );            
            return;
        }  
        
        if ( "false".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.FALSE, offset,s ) );            
            return;
        }         
        
        if ( "return".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.RETURN, offset,s ) );            
            return;
        }
        
        if ( "const".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.CONST, offset,s ) );            
            return;
        }           
        
        if ( "extern".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.EXTERN, offset,s ) );            
            return;
        }          
        
        if ( "asm".equalsIgnoreCase( s ) ) {
            tokens.add( new Token(TokenType.ASM , offset,s ) );            
            return;
        }
        
        if ( isIdentifier( s ) ) {
            tokens.add( new Token(TokenType.IDENTIFIER , offset,s ) );               
            return;
        }
        if ( NumberLiteralNode.isValidNumberLiteral( s ) ) {
            tokens.add( new Token(TokenType.NUMBER_LITERAL , offset,s ) );   
            return;
        }
        tokens.add( new Token(TokenType.UNKNOWN , offset,s ) );   
    }
    
    private static boolean isIdentifier(String s) 
    {
        return Identifier.isValidIdentifier(s);
    }    
    
    private boolean isWhitespace(char c) {
        return Character.isWhitespace( c );
    }

    public void setIgnoreWhitespace(boolean ignoreWhitespaceNow) throws EOFException, IOException 
    {
    	if ( isIgnoreWhitespace() && ! ignoreWhitespaceNow && previousToken != null ) 
    	{
    		// switching "skip whitespace" => "do not skip whitespace"
    		
    		// - reset to current token position
    		/*
        private final List<Token> tokens = new ArrayList<>(Lexer.this.tokens);
        private final boolean atEOF = Lexer.this.atEOF;
        private final int currentOffset = scanner.getOffset();
        private final boolean ignoreWhitespace = Lexer.this.ignoreWhitespace;    		 
    		 */
    		this.tokens.clear();
    		this.atEOF = false;
    		this.scanner= new Scanner( createInputStream( previousToken.getOffset() ) );
    		// skip previous token
    		read();
    	} 
    	else if ( ! isIgnoreWhitespace() && ignoreWhitespaceNow) 
    	{
    		// switching "do not skip whitespace" => "skip whitespace"
    		// remove all whitespace tokens from the token queue that might've
    		// been added by read-ahead
    		for ( Iterator<Token> it = tokens.iterator() ; it.hasNext() ; ) 
    		{
    			final Token tok = it.next();
    			if ( tok.hasType( TokenType.WHITESPACE ) || tok.hasType(TokenType.EOL ) ) {
    				it.remove();
    			}
    		}
    	}
		this.ignoreWhitespace = ignoreWhitespaceNow;
	}
    
    public boolean isIgnoreWhitespace() {
		return ignoreWhitespace;
	}
}
