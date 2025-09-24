package de.codesourcery.dcpu16.parser;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class Scanner
{
    private final InputStream in;
    
    private char current;
    private boolean atEOF = false;
    
    private int offset=-1;
    
    public Scanner(String s) throws IOException {
        this( new ByteArrayInputStream( s.getBytes() ) );
    }
    
    public Scanner(InputStream in,int offset) throws IOException
    {
        this.in = in;
        this.offset = offset-1;
        readChar();
    }
    
    public Scanner(InputStream in) throws IOException
    {
        this(in,0);
    }

    @Override
    public String toString() 
    {
    	if ( atEOF ) {
    		return "Scanner ( EOF )";
    	}
    	if ( offset == -1 ) {
    		return "Scanner ( before start of input )";
    	}
    	return "Scanner ( char = >"+current+"< , offset = "+offset+" )";
    }
    
    public int getOffset() {
        return offset;
    }

    private void readChar() throws IOException
    {
        if ( atEOF ) {
            throw new EOFException();
        }
        final int result = in.read();
        offset++;
        
        if ( result == -1 ) {
            atEOF = true;
        } else {
            this.current = (char) result;
        }
    }
    
    public boolean eof() {
        return atEOF;
    }
    
    public char peek() throws IOException 
    {
        if ( atEOF ) {
            throw new EOFException();
        }
        return current;
    }
    
    public char read() throws IOException 
    {
        if ( atEOF ) {
            throw new EOFException();
        }
        final char result = current;
        readChar();
        return result;
    }
}