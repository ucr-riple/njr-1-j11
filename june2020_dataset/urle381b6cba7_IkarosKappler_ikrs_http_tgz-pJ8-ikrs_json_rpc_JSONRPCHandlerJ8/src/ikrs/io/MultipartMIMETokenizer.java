package ikrs.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 *  The format of the multipart-body is:
 *  ------------------------------------
 *  boundary        := 0*69<bchars> bcharsnospace
 *  bchars          := bcharsnospace / " "
 *  bcharsnospace   := DIGIT / ALPHA / "'" / "(" / ")" /
 *                     "+" / "_" / "," / "-" / "." /
 *                     "/" / ":" / "=" / "?"
 *
 * --- with --------------------------
 *
 *  multipart-body  := [preamble CRLF]
 *                     dash-boundary transport-padding CRLF
 *                     body-part *encapsulation
 *                     close-delimiter transport-padding
 *                     [CRLF epilogue]
 *  dash-boundary   := "--" boundary
 *  encapsulation   := delimiter transport-padding
 *                     CRLF body-part
 *  delimiter       := CRLF dash-boundary
 *  close-delimiter := delimiter "--"
 *
 * --- Example: ----------------------
 *  --boundary
 *  1. body-part
 *  --boundary
 *  2. body-part
 *  --boundary
 *  3. body-part
 *  --boundary--
 *
 * (See http://stackoverflow.com/questions/4656287/what-rules-apply-to-mime-boundary)
 *
 * So to detect if the last boundary is reached we need to check if the next
 * body-party begins with 
 * '--' CRLF
 *
 * This can only be done using a pushback input stream.
 *
 *
 * @authior Ikaros Kappler
 * @date 2012-10-10
 * @version 1.0.0
 **/


public class MultipartMIMETokenizer 
    extends ByteSequenceTokenizer {


    public static final int CR = 0xD;  // 13 decimal
    public static final int LF = 0xA;  // 10 decimal

    /**
     * This field stores the next token's index; this is required because the first (and also
     * the last) token is not a real token as the multipart MIME body starts and ends with
     * the boundary sequence.
     **/
    private int tokenIndex;


    /**
     * Constructs a new multpiart-MIME tokenizer.
     *
     * @param in       The input stream to read from.
     * @param boundary The boundary from the HTTP headers, WITHOUT the leading or trailing '--' respective CRLF!
     **/
    public MultipartMIMETokenizer( InputStream in,
				   String boundary )
	throws NullPointerException {

	super( in, new String("--" + boundary).getBytes() );

	this.tokenIndex = 0;
    }


    /**
     * The 
     *
     * @override
     **/
    public InputStream getNextToken() 
	throws IOException {

	
	InputStream token = super.getNextToken();
	this.tokenIndex++;

	if( token == null )
	    return null;

	// Skip first token (multipart MIME body begins with a boundary sequence, so the first token IS EMPTY)
	if( this.tokenIndex == 1 ) 
	    return getNextToken();

	// Try to read 
	//  (i)  '--' CRLF   [the terminating boundary]
	//  or
	//  (ii) CRLF        [a normal inner boundary]
	byte[] buffer = new byte[4];
	
	// Try to read CRLF or '--'
	int length = token.read( buffer, 0, 2 );

	if( length < 2 ) {

	    // Not enough bytes available. EOI reached.
	    throw new IOException( "Cannot tokenize multipart MIME body. Unexpected EOI after partial boundary (CRLF missing)." );

	} 



	if( buffer[0] == MultipartMIMETokenizer.CR 
	    && buffer[1] == MultipartMIMETokenizer.LF ) {
	
	    // A normal inner part.
	    // Just return the original stream.
	    return token;
	
	} else if( buffer[0] == (byte)'-'
		   && buffer[1] == (byte)'-' ) {

	    // Try to read CRLF
	    length += token.read( buffer, 2, 2 );
	    if( length < 4 ) {

		throw new IOException( "Cannot tokenize multipart MIME body. Unexpected EOI after partial boundary ('--' read, CRLF missing)." );

	    } else if( buffer[2] == MultipartMIMETokenizer.CR 
		&& buffer[3] == MultipartMIMETokenizer.LF ) {

		// '--' read, then CRLF. 
		// This is the final boundary!
		return null; 

	    } else {

		throw new IOException( "Cannot tokenize multipart MIME body. Unexpected characters after end of expected boundary ('--' read): '" + new String(buffer,2,2) + "'." );

	    }

	} else {

	    throw new IOException( "Cannot tokenize multipart MIME body. Unexpected characters after end of expected boundary: '" + new String(buffer,0,4) + "'." );

	}
	
    }

    public String toString() {
	return "MultipartMIMETokenizer=[ " + super.toString() + "]";
    }


    public static void main( String[] argv  ) {

	try {

	    String filename;
	    String stopMark;
	    if( argv.length >= 1 ) filename = argv[0];
	    else                   filename = "document_root_alpha/example.POST_DATA.txt";

	    if( argv.length >= 2 ) stopMark = argv[1];
	    else                   stopMark = "----WebKitFormBoundaryeANQMKoBwsmwQrYZ";


	    System.out.println( "Using stopMark: " + stopMark  );
	    System.out.println( "Reading file until stopMark will be found ... ");
	    java.io.FileInputStream fin = new java.io.FileInputStream( new java.io.File(filename) );
	    MultipartMIMETokenizer tokenizer = new MultipartMIMETokenizer( fin, 
									   stopMark );
	    
	    InputStream token;
	    int i = 0;
	    while( (token = tokenizer.getNextToken()) != null ) {
		
		int b;
		System.out.println( "Reading bytes from token ["+i+"] ..." );
		int len = 0;
		while( (b = token.read()) != -1 ) {

		    System.out.print( (char)b );
		    len++;

		}


		System.out.println( "\n" + len +" bytes read from token [" + i+ "]." );
		System.out.println( "----------------------------------------------" );

		token.close();

		i++;
	    }

	} catch( IOException e ) {

	    e.printStackTrace();

	}

    }


}