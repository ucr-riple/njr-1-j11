package ikrs.util;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * As there is a small issue mapping file extensions in different styles; if you store
 * a file extension ".txt" to this map (key) looking up ".TXT" or "txt" won't retrieve
 * the mapped value if using a regular String orientated map.
 *
 * This map uses a case-insensitive key comparator (so ".txt" and ".TXT" are the same)
 * and handles the preceding dot "." (so ".txt" and "txt" are the same).
 *
 * Both settings are optional.
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @version 1.0.0
 *
 **/

public class FileExtensionKeyMap<V>
    extends TreeMap<String,V> {

    /**
     * If the 'isStrict' flag is set to true, ".txt" and "txt" are NOT the same!
     **/
    private boolean isStrict;


    /**
     * If the 'isCaseSensitive' flag is set, ".txt" and ".TXT" are NOT the same!
     **/
    private boolean isCaseSensitive;



    public FileExtensionKeyMap() {
	this( false, false );
    }

    public FileExtensionKeyMap( boolean isStrict,
				boolean isCaseSensitive ) {
	super( FileExtensionKeyMap.determineKeyComparator(isCaseSensitive) );

	this.isStrict        = isStrict;
	this.isCaseSensitive = isCaseSensitive;
	
    }


    /**
     * This method overrides TreeMap.get(String) to adapt the file extension mapping.
     **/
    public V get( Object fileExtension ) {
	try {
	    return get( (String)fileExtension );
	} catch( ClassCastException e ) {
	    return null;
	}
    }

    /**
     * This method tries to resolve multiple meanings of the file extension.
     **/
    protected V get( String fileExtension ) {

	//System.out.println( "Looking up '" + fileExtension +"' ..." );
	
	// First step: try to find the mapping as it is.
	// Note: the keySet's comparator handles the key's case.
	V value = super.get( fileExtension );

	if( fileExtension == null )
	    return value; // The key cannot be modified in any way (allow null keys).


	if( !this.isStrict && value == null ) {

	    // Not found.
	    // IF there is a mapping there are two possible cases the simple lookup failed:
	    if( !fileExtension.startsWith(".") ) {
		
		// There might be a mapping with the leading dot (but the key hasn't one).
		//System.out.println( "Looking up '." + fileExtension +"' ..." );
		value = super.get( "." + fileExtension );

	    } else if( fileExtension.length() > 1 ) {

		// The key consists of more than the dot "."; Remove it and search again.
		//System.out.println( "Looking up '" + fileExtension.substring(1,fileExtension.length()) +"' ..." );
		value = super.get( fileExtension.substring(1,fileExtension.length()) );

	    } else {

		// The file extension consists of the dot "." only.
		// There is nothing more we can do (key not found).
		//System.out.println( "Cannot modify key." );

		// NOOP
	
	    }

	}

	return value;

    }


    private static Comparator<String> determineKeyComparator( boolean isCaseSensitive ) {

	if( isCaseSensitive ) 
	    return null;  // This will cause the TreeMap constructor to use natural order
	else
	    return CaseInsensitiveComparator.sharedInstance;

    }



    /**
     * For testing only.
     **/
    public static void main( String[] argv ) {

	boolean strict = false;
	boolean caseSensitive = false;
	System.out.println( "Creating new map (strict=" + strict + ", caseSensitive=" + caseSensitive +") ..." );
	java.util.Map<String,String> map = new FileExtensionKeyMap<String>( strict,   
									    caseSensitive
									    );

	System.out.println( "Adding some test mappings ..." );
	map.put( ".txt", "text/plain" );
	map.put( "DAT",  "application/octet-stream" );
	System.out.println( "map=" + map );


	FileExtensionKeyMap.testLookup( map, ".TXT" );
	FileExtensionKeyMap.testLookup( map, "TXT" );
	FileExtensionKeyMap.testLookup( map, ".txt" );
	FileExtensionKeyMap.testLookup( map, "txt" );

	FileExtensionKeyMap.testLookup( map, ".DAT" );
	FileExtensionKeyMap.testLookup( map, "DAT" );
	FileExtensionKeyMap.testLookup( map, ".dat" );
	FileExtensionKeyMap.testLookup( map, "dat" );
	

    }

    private static void testLookup( java.util.Map<String,String> map, String key ) {
	System.out.println( "Making test lookup for '"+key+"' ..." );
	String value = map.get( key );
	System.out.println( "value=" + value );
    }

}