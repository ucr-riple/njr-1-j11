package ikrs.httpd.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a simple parser class that splits several header line formats.
 *
 * Example A:
 *  the header line (from the 'Accept-Charset' header)
 *    'ISO-8859-1,utf-8;q=0.7,*;q=0.3'
 *  would be split into this three-dimensional structure:
 *  { 
 *    { { 'ISO-8859-1' } },
 *
 *    { { 'utf-8' }, { 'q', '0.7' } },
 *
 *    { { '*' }, { 'q', '0.3' } }
 *  }
 *
 *
 * Example B:
 * the header line (from the 'Content-Type' header):
 *   'form-data; name="my_file"; filename="robots.txt"'
 * would be split into this structure:
 * {
 *   { { 'form-data' }, { 'name', 'my_file' }, { 'filename', 'robots.txt' } }
 * }
 *
 * Note that the quotes (double and single will be removed).
 * 
 *
 *
 * @author Ikaros Kappler
 * @date 2012-10-04
 * @modified 2013-03-22 Changed the structure from a two-dimensional to a 
 *                      three-dimensional array: '=' delimiter is now recognized.
 * @version 1.0.1
 **/


public class HeaderParams {

    /**
     * The header key.
     **/
    private String headerKey;
    
    /**
     * The original raw header value (unparsed).
     **/
    private String headerValue;

    /**
     * The parsed header value as a nested list.
     **/
    private List<List<List<String>>> params;


    /**
     * Creates a new HeaderParams instance from the given key/value pair.
     *
     * @param headerKey   The header's key (must not be null).
     * @param headerValue The header's value (must not be null).
     * @throws NullPointerException If the the passed key or value is null.
     **/
    public HeaderParams( String headerKey,
			 String headerValue ) 
	throws NullPointerException {


	if( headerKey == null )
	    throw new NullPointerException( "Cannot create HeaderParams with a null-key." );
	if( headerValue == null )
	    throw new NullPointerException( "Cannot create HeaderParams with a null-value." );


	this.headerKey   = headerKey;
	this.headerValue = headerValue;

	this.params      = new ArrayList<List<List<String>>>( 1 );

	HeaderParams.parse( headerValue,
			    this.params );
    }

    /**
     * This returns the main token, which is at position (0,0,0) in the
     * nested token structure.
     *
     * Example A:
     *   form-data; name="my_file"; filename="robots.txt"
     * here the main token at (0,0,0) is 'form-data'.
     *
     * Example B:
     *   ISO-8859-1,utf-8;q=0.7,*;q=0.3
     * here the main token is 'ISO-8859-1'.
     **/
    public String getMainToken() {	
	return this.getToken( 0, 0, 0 );
    }

    public int getLevelCount() {
	return this.params.size();
    }

    public int getSublevelCount( int levelA ) {

	if( levelA < 0 || levelA >= this.getLevelCount() )
	    return -1;

	List<List<String>> list = this.params.get( levelA );
	return list.size();
    }

    public int getSublevelCount( int levelA, int levelB ) {
	
	if( levelA < 0 || levelA >= this.getLevelCount() )
	    return -1;
	if( levelB < 0 || levelB >= this.getSublevelCount(levelA) )
	    return -1;

	List<List<String>> list = this.params.get( levelA );

	
	return list.get( levelB ).size();
    }

    public String getToken( int levelA,
			    int levelB ) {
	return this.getToken( levelA, 
			      levelB, 
			      0        // levelC
			      );
    }   
    
    public String getToken( String tokenA,
			    boolean caseSensitive,
			    int levelB,
			    int levelC ) {

	int levelA = locateMainToken( tokenA, caseSensitive );
	if( levelA == -1 )
	    return null;

	return getToken( levelA, levelB, levelC );
    }
    
    public String getToken( int levelA,
			    int levelB,
			    int levelC ) {

	if( levelA < 0 || levelA >= this.getLevelCount() || levelB < 0 || levelC < 0)
	    return null;
	
	List<List<String>> list_A = this.params.get( levelA );
	if( levelA >= list_A.size() )
	    return null;

	List<String> list_B = list_A.get( levelB );

	if( levelC >= list_B.size() )
	    return null;
	
	return list_B.get( levelC );
    }

    /**
     * This method is meant to retrieve the value of key-value-tuples inside the header params.
     * The value is a sub-sub-index 1 (key is at index 0).
     **/
    public String getTokenValue( String tokenA,
				 String tokenB,
				 boolean caseSensitive ) {
	return this.getTokenValue( tokenA, tokenB, caseSensitive, 1 );
    }

    public String getTokenValue( String tokenA,
				 String tokenB,
				 boolean caseSensitive,
				 int levelCIndex ) {
	
	int tokenIndexA = this.locateMainToken( tokenA, caseSensitive );
	if( tokenIndexA == -1 )
	    return null;  // main token not found

	int subTokenIndex = this.locateSubToken( tokenIndexA, tokenB, caseSensitive );
	if( subTokenIndex == -1 ) 
	    return null;  // sub token not found
	
	List<String> subsublist = this.params.get( tokenIndexA ).get( subTokenIndex );
	// tokenB is definitely at index 0 of the retrieved sub-sub-list.
	if( levelCIndex >= 0 && levelCIndex < subsublist.size() )
	    return subsublist.get(levelCIndex);
	else
	    return null;
	
    }

    private int locateSubToken( int mainTokenIndex,
				String tokenB,
				boolean caseSensitive ) {
	
	List<List<String>> sublist = this.params.get( mainTokenIndex );
	for( int i = 0; i < sublist.size(); i++ ) {

	    List<String> subsublist = sublist.get(i);
	    //System.out.println( "subsublist["+mainTokenIndex+"]["+i+"]=" + subsublist );
	    if( subsublist.size() != 0
		&& (caseSensitive && subsublist.get(0).equals(tokenB) 
		    ||
		    !caseSensitive && subsublist.get(0).equalsIgnoreCase(tokenB))
		) {

		return i;
	    }

	}
	
	return -1;
    }

    private int locateMainToken( String token,
				 boolean caseSensitive ) {

	for( int a = 0; a < this.params.size(); a++ ) {

	    List<List<String>> listB = this.params.get(a);
	    // First token equals the desired token?
	    if( listB.size() != 0
		&& listB.get(0).size() != 0 
		&& ((caseSensitive && listB.get(0).get(0).equals(token))
		    || (!caseSensitive && listB.get(0).get(0).equalsIgnoreCase(token)) 
		    )
		) {
		
		return a;
	    }
	}

	return -1;
    }
				 

    public static void parse( String value,
			      List<List<List<String>>> destination ) 
	throws NullPointerException {

	if( value == null )
	    throw new NullPointerException( "Cannot parse HeaderParams from a null-value." );

	// The value might be 
	//  - a single token
	//  - or a list of tokens, separated by ','
	//  - or a 2-level nested list of tokens; first level separated by ',' and the second level separated by ';'
	//  - or a 3-level nested list of tokens; first separator is ',', second is ';', third is '='

	String[] tokensA = value.split( "," );
	

	// This has the best runtime i've ever seen ;)
	for( int a = 0; a < tokensA.length; a++ ) {

	    if( (tokensA[a] = tokensA[a].trim()).length() == 0 )
		continue;

	    String[] tokensB = tokensA[a].split( ";" );
	    List<List<String>> sublist = new ArrayList<List<String>>( tokensB.length );
	    for( int b = 0; b < tokensB.length; b++ ) {

		String[] tokensC = tokensB[b].split("=");
		List<String> sublist_C = new ArrayList<String>(tokensC.length);
		for( int c = 0; c < tokensC.length; c++ ) {
		 
		    // In some cases (e.g. 'Content-Type' the inner tokens may be quoted.
		    // Trim quotes?
		    if( tokensC[c].length() >= 2  
			&& (tokensC[c].startsWith("\"") && tokensC[c].endsWith("\"")
			    ||
			    tokensC[c].startsWith("'") && tokensC[c].endsWith("'")) ) {
		
			sublist_C.add( tokensC[c].substring(1,tokensC[c].length()-1).trim() );
		    } else {
			sublist_C.add( tokensC[c].trim() );
		    }

		}

		sublist.add( sublist_C ); //tokensB[b].trim() );

	    }

	    destination.add( sublist );
	}


    }

    
    public String toString() {
	return this.params.toString();
    }


    /**
     * This is just for testing.
     **/
    public static void main( String[] argv ) {

	// Test A:
	String strA = "ISO-8859-1,utf-8;q=0.7,*;q=0.3";
	HeaderParams prmsA = new HeaderParams( "Accept-Charset",
					       strA );
	System.out.println( prmsA.params );


	// Test B:
	String strB = "form-data; name=\"my_file\"; filename=\"robots.txt\"";
	HeaderParams prmsB = new HeaderParams( "Content-Disposition",
					       strB );
	System.out.println( prmsB.params );

	System.out.println( "Try to get filename ..." );
	String filename = prmsB.getTokenValue( "form-data",    // tokenB
					       "filename",     // tokenC
					       false,          // caseSensitive?
					       1               // levelC-index
					       );
	System.out.println( "filename=" + filename );


	// Test C:
	String strC = "multipart/form-data; boundary=----WebKitFormBoundaryeANQMKoBwsmwQrYZ";
	HeaderParams prmsC = new HeaderParams( "Content-Type",
					       strC );
	System.out.println( prmsC.params );

	System.out.println( "Try to get boundary ..." );
	String boundary = prmsC.getTokenValue( "multipart/form-data",  // tokenB
					       "boundary",             // tokenC
					       false,                  // caseSensitive?
					       1                       // levelC-index
					       );
	System.out.println( "boundary=" + boundary );

	int mti_C0 = prmsC.locateMainToken( "multipart/form-data", false );
	System.out.println( "mainTokenIndex('multipart/form-data') is: " + mti_C0 );

	int mti_C1 = prmsC.locateMainToken( "boundary", false );
	System.out.println( "mainTokenIndex('boundary') is: " + mti_C1 );

	int sti_C2  = prmsC.locateSubToken( mti_C0, "boundary", false );
	System.out.println( "subTokenIndex('multipart/form-data','boundary') is: " + sti_C2 );
	
	
    }

}