package de.codesourcery.dcpu16.exceptions;

import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;
import de.codesourcery.dcpu16.util.TextRegion;

public class ParseException extends RuntimeException
{
    private final ITextRegion region;
   
    public ParseException(String message,Token r)
    {
        this(message,r.getTextRegion());
    }
    
    public ParseException(String message,ITextRegion r)
    {
        super(message+" (at offset: "+r+")");
        this.region = new TextRegion(r);
    }
    
    public ParseException(String message,int offset)
    {
        super(message);
        this.region = new TextRegion(offset,0);        
    }
    
    public int getOffset()
    {
        return region.getStartingOffset();
    }
    
    public ITextRegion getRegion()
    {
        return region;
    }
}
