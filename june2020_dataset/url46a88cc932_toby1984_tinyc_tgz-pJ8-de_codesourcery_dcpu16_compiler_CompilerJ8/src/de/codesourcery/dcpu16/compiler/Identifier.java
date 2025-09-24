package de.codesourcery.dcpu16.compiler;

import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.parser.TokenType;

public class Identifier
{
    private static final String VALID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz_$";
    
    private String value;

    public Identifier(Token t) {
        if ( t == null || ! t.hasType(TokenType.IDENTIFIER ) || ! isValidIdentifier( t.getValue() ) ) 
        {
            throw new IllegalArgumentException("Not a valid identifier: "+t);
        }
        this.value = t.getValue();
    }
    
    public Identifier(String value)
    {
        if ( ! isValidIdentifier( value ) ) {
            throw new IllegalArgumentException("Not a valid identifier: "+value);
        }
        this.value = value;
    }
    
    @Override
    public String toString()
    {
        return value;
    }
    
    public String getStringValue() {
    	return value; 
    }    
    
    public static boolean isValidIdentifier(String s)
    {
        if ( s == null || s.trim().length() == 0 ) {
            return false;
        }
        if ( Character.isDigit( s.charAt( 0 ) ) ) {
            return false;
        }
        
        final String tmp = s.toLowerCase();
        for ( int i = 0 ; i < s.length() ; i++ ) 
        {
            if ( ! VALID_CHARS.contains( Character.toString( tmp.charAt(i ) ) ) )
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
    	return value.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( obj instanceof Identifier) 
        {
            return this.value.equals( ((Identifier) obj).value );
        }
        return false;
    }
}
