package de.codesourcery.dcpu16.parser;

import de.codesourcery.dcpu16.util.ITextRegion;
import de.codesourcery.dcpu16.util.TextRegion;

public class Token
{
    private final TokenType type;
    private final int offset;
    private final String value;
    
    public Token(TokenType type, int offset,String value)
    {
        this.offset = offset;
        this.type = type;
        this.value = value;
    }
    
    public ITextRegion getTextRegion() {
        return new TextRegion( offset , value.length() );
    }
    
    public int getOffset()
    {
        return offset;
    }

    public TokenType getType()
    {
        return type;
    }
    
    public boolean hasType(TokenType t) {
        return t.equals( type );
    }
    
    public String getValue()
    {
        return value;
    }
    
    @Override
    public String toString()
    {
        return type+" { >"+value+"< }";
    }
}