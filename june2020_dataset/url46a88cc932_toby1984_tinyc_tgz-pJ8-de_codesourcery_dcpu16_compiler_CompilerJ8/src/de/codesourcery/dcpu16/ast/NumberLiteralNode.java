package de.codesourcery.dcpu16.ast;

import java.util.regex.Pattern;

import de.codesourcery.dcpu16.compiler.DataType;
import de.codesourcery.dcpu16.parser.Token;
import de.codesourcery.dcpu16.util.ITextRegion;

public class NumberLiteralNode extends TermNode
{
    private static final Pattern HEX_NUMBER_PATTERN = Pattern.compile("^0x[0-9a-f]+$");
    private static final Pattern BIN_NUMBER_PATTERN = Pattern.compile("^0b[0-9a-f]+$");
    private static final Pattern DEC_NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
    
    private long value;

    public NumberLiteralNode(long value, ITextRegion token)
    {
        super(token);
        this.value = value;
    }
    
    public NumberLiteralNode(long value, Token token)
    {
        this(value,token.getTextRegion());
    }

    public long getValue()
    {
        return value;
    }
    
    @Override
    public String toString()
    {
        return Long.toString( value );
    }

    @Override
    protected ASTNode createCopy()
    {
        return new NumberLiteralNode(value,getTextRegion());
    }

    @Override
    public TermNode reduce()
    {
        return this;
    }

    @Override
    public boolean isLiteralValue()
    {
        return true;
    }

    @Override
    public DataType internalGetDataType()
    {
        return DataType.INTEGER;
    }
    
    public static Long valueOf(TermNode condition)
    {
        TermNode current = condition;
        if ( condition != null ) {            
            while ( !(current instanceof NumberLiteralNode) && current.getChildCount() == 1 ) {
                current = (TermNode) current.child(0);
            }
        }
        if ( current instanceof NumberLiteralNode) {
            return ((NumberLiteralNode) current).getValue();
        }               
        return null;
    }      
    
    public static long parse(String s) 
    {
        if ( s == null || s.trim().length() == 0 ) {
            throw new IllegalArgumentException("Not a valid number "+s);
        }
        if ( isDecimalNumber( s ) ) {
            return Long.parseLong( s );
        }
        if ( isHexadecimalNumber( s ) ) {
            return Long.parseLong( s.substring(2), 16 );
        }
        if ( isBinaryNumber( s ) ) 
        {
            return Long.parseLong(s.substring(2), 2 );
        }
        throw new IllegalArgumentException("Not a valid number "+s);
    }
    
    public static boolean isValidNumberLiteral(String s) {
        if ( s == null || s.trim().length() == 0 ) {
            return false;
        }
        return isDecimalNumber( s ) || isHexadecimalNumber( s ) || isBinaryNumber( s );
    }
    
    private static boolean isHexadecimalNumber(String s) {
        return HEX_NUMBER_PATTERN.matcher( s ).matches();
    }
    private static boolean isBinaryNumber(String s) 
    {
        return BIN_NUMBER_PATTERN.matcher( s ).matches();
    }
    private static boolean isDecimalNumber(String s) {
        return DEC_NUMBER_PATTERN.matcher( s ).matches();
    }    
}
