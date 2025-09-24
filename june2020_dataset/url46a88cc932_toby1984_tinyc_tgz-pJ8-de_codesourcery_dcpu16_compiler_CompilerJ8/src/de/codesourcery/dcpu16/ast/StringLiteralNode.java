package de.codesourcery.dcpu16.ast;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.util.ITextRegion;

public class StringLiteralNode extends TermNode
{
	private boolean isSingleCharacter;
    private String value;
    
    public StringLiteralNode(String value,boolean isSingleCharacter,ITextRegion tok)
    {
        super(tok);
        this.value = value;
        this.isSingleCharacter = isSingleCharacter;
    }
    
    @Override
    public String toString() {
    	return "StringLiteral >"+value+"<";
    }
    
    public String getValue()
    {
        return value;
    }
    
    @Override
    protected StringLiteralNode createCopy()
    {
        return new StringLiteralNode(  value , isSingleCharacter, getTextRegion() );
    }
    
    public boolean isSingleCharacter() {
        return isSingleCharacter;
    }
    
    @Override
    public TermNode reduce()
    {
        return this;
    }
    
    public static String escapeQuotes(String s) 
    {
        StringBuffer result = new StringBuffer();
        for ( char c : s.toCharArray() ) {
            if ( c == '\'' || c == '\"' ) {
                result.append("\\");
            } 
            result.append(c);
        }
        return result.toString();
    }

    @Override
    public boolean isLiteralValue()
    {
        return true;
    }

    @Override
    protected DataType internalGetDataType()
    {
    	if ( isSingleCharacter ) {
    		return DataType.CHAR;
    	}
        return DataType.getDataType( DataType.CHAR.getIdentifier() , false , 1 );
    }
}