package ikrs.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * The byte sequence tokenizer splits a given byte sequence (coming from an inputstream)
 * into a sequence of byte-streams, all seperated by a given separator byte sequence.
 *
 *
 * @authior Ikaros Kappler
 * @date 2012-10-08
 * @version 1.0.0
 **/

public class ByteSequenceTokenizer {

    /**
     * The underlying input stream.
     **/
    private InputStream in;

    /**
     * The separator sequence.
     **/
    private byte[] separator;

    /**
     * The latest detected token (null after initialization).
     **/
    private StopMarkInputStream currentToken;

    /**
     * This flag will be set when a tokenizer reaches the _real_ EOI (not the mark).
     **/
    private boolean eoiReached;

    public ByteSequenceTokenizer( InputStream in,
				  byte[] separator )
	throws NullPointerException {

	super();

	if( in == null )
	    throw new NullPointerException( "Cannot create a ByteSequenceTokenizer with a null-stream." );
	if( separator == null )
	    throw new NullPointerException( "Cannot create a ByteSequenceTokenizer with a null-separator." );

	this.in = in;
	this.separator = separator;
	
    }


    /**
     * Get the next token from the underlying input stream; the token itself is an InputStream, too.
     *
     * All tokens a separated by the given separator byte sequence.
     *
     * Note that if the last token was not fully consumed this method will dispose its data and 
     * invalidating the old token. Data cannot be read then any more using the old reference.
     *
     * If no more data is available from the underlying input stream this method returns null.
     *
     * @return The next InputStream token from the underlying input stream.
     **/
    public InputStream getNextToken() 
	throws IOException {

	
	if( this.currentToken != null ) {

	    this.eoiReached = currentToken.eoiReached();
	    this.consumeCurrentToken();

	}
	// Current token is definitely null now.


	// EOI reached implies: no more tokens available
	if( this.eoiReached )
	    return null;

	
	// Create a new token
	this.currentToken = new StopMarkInputStream( this.in, 
						     this.separator );


	return currentToken;
    }




    private long consumeCurrentToken() 
	throws IOException {

	if( this.currentToken == null )
	    return -1L;


	byte[] buff = new byte[256];
	int len;
	long totalLength = 0;
	if( !this.currentToken.isClosed() ) {

	    while( (len = this.currentToken.read(buff)) > 0 )
		totalLength += len;

	}


	this.currentToken.close();
	this.currentToken = null;

	return totalLength;
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
	    ByteSequenceTokenizer tokenizer = new ByteSequenceTokenizer( fin, 
									 stopMark.getBytes() );
	    
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
