package ikrs.util;

import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;

/**
 * This class can be used to read clean CR-LF (0xD, 0xA) line feeded text.
 * Other line breaks will be taken as 'normal' text.
 *
 * @author Ikaros Kappler
 * @date 2012-05-21
 * @version 1.0.0
 **/


public class CRLFLineReader {
    

    /**
     * Read the next CR-LF line from the input stream. 
     * Note that only CR-LF is accepted as an entity line break! Single CR
     * single LF will _not_ instruct the reader to stop!
     * 
     * Hint: If the read line is empty this method will return null!
     *
     *
     * @return The next read line or null if the line was empty.
     *
     * @param in The InputStream to read from.
     * @param EOFException if the end of the stream was reached without getting
     *                     the fianl CR-LF.
     * @param IOException If any other IO errors occur.
     *
     **/
    public static String readLine( InputStream in )
	throws EOFException,
	       IOException {

	/* input must not be null */
	if( in == null )
	    throw new NullPointerException( "Cannot read from null." );


	int CR = 0xD;  // 13 decimal
	int LF = 0xA;  // 10 decimal
	
	/* Try to read the next text line from the input */
	int currentByte, lastByte = 0x00;
	StringBuffer buffer = new StringBuffer();
	int i = 0;
	while( (currentByte = in.read()) != -1   // EOI reached?
	       && lastByte != CR 
	       && currentByte != LF ) {
	    	    

	    if( i > 0 )
		buffer.append( (char)lastByte );
	
	    /* Store for next iteration */
	    lastByte = currentByte;
	    i++;
	}


	if( currentByte == -1 ) {  
	    // Only one possibility:
	    // EOI was reached
	    throw new EOFException( "End of stream after " + buffer.length() + " additional bytes ('"+buffer.toString()+"')." );
	} else if(  i == 1 ) {
	    // Only one possibility:
	    //  ONLY CR-LF was read (otherwise the loop could not have terminated)
	    return null; // No real data (implies end-of-headers)

	} 

	
	// In all other cases: the loop terminated without EOI reached and no empty
	// line read -> CR-LF must have been reached AND there must be data
	// --> Convert to String and return
	
	return buffer.toString();

    }


}