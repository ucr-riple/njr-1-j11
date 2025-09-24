package de.codesourcery.dcpu16.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringInterpolator
{
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile( "\\$\\{([0-9]+)\\}" );
    
    public static String interpolate(String expression,Object... arguments) 
    {
        final int argCount = arguments == null ? 0 : arguments.length;
        String result = expression;
        do 
        {
            Matcher m = PLACEHOLDER_PATTERN.matcher( result );
            if ( ! m.find() ) {
                break;
            }
            final int index = Integer.parseInt( m.group(1) );
            if ( index >= argCount || index < 0 ) {
                throw new RuntimeException("Expression '"+expression+"' contains invalid placeholder index "+index+" , argument count: "+argCount);
            }
            final String toReplace = Pattern.quote("${"+index+"}");
            if (arguments[ index ] == null ) {
                throw new RuntimeException("Variable ${"+index+"} in expression "+expression+" is NULL ?");
            }
            result = result.replaceAll( toReplace , arguments[ index ].toString() );
        } while ( true );
        return result;
    } 
}
