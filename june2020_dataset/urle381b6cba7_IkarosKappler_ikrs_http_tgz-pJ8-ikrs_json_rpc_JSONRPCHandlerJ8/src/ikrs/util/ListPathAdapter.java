package ikrs.util;

import java.util.List;

/**
 * The ListPathAdapter class is a generalized java.util.List based path implementation.
 *
 * Note that this implementation operates on the passed list in-place! Concurrent modifications 
 * to the list may cause unexpected bevahior.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-12-11
 * @version 1.0.0
 **/


public class ListPathAdapter<T>
    implements Path<T> {

    /**
     * The internal path list (must not be null).
     **/
    private List<T> pathList;

    /**
     * The front offset is the number of items to be skipped from
     * the beginning of the path; those elements are hidden.
     **/
    private int frontOffset;


    /**
     * Create a new ListPathAdaper with the given path list (and frontOffset 0).
     *
     * @param pathList The underlying path list (must not be null).
     * @throws NullPointerException If pathList is null.
     **/
    public ListPathAdapter( List<T> pathList )
	throws NullPointerException {
	
	this( pathList, 0 );
    }


    /**
     * Create a new ListPathAdaper with the given path list and frontOffset.
     *
     * @param pathList    The underlying path list (must not be null).
     * @param frontOffset The front offset to use. The front offset must be in bounds:
     *                    0 <= frontOffset <= pathList.size().
     * @throws NullPointerException If pathList is null.
     * @throws IllegalArgumentException If frontOffset is out of bounds: frontOffset < 0 or frontOffset > pathList.size().
     **/
    public ListPathAdapter( List<T> pathList,
			    int frontOffset )
	throws NullPointerException {

	super();

	if( pathList == null )
	    throw new NullPointerException( "Cannot create ListPathAdapters with a null-list." );
	
	if( frontOffset < 0 || frontOffset > pathList.size() )
	    throw new IllegalArgumentException( "Cannot create ListPathAdapter: frontOffset out of bounds ("+frontOffset+")." );

	this.pathList    = pathList;
	this.frontOffset = frontOffset;
    }

     

    /**
     * Get the length of this path.
     * The length of a path is the exact number of items. An empty path has the size 0.
     *
     * @return The length of this path which is the number of path elements.
     **/
    public int getLength() {
	return ( this.pathList.size() - this.frontOffset );
    }

    /**
     * Get the first element of this path.
     * If the path is empty the method must return null.
     *
     * @return The first path element or null if the path is empty.
     **/
    public T getFirstElement() {
	if( this.getLength() <= 0 )
	    return null;
	else
	    return this.pathList.get( this.frontOffset );
    }

    /**
     * Get the trailing path from this path. The trailing path is this path without
     * the first element.
     * Retrieving the trailing path is equivalent to going down one level inside the 
     * tree structure.
     *
     * If this path is empty the returned trailing path is null.
     *
     * @return The trailing path or null if this path is empty.
     **/
    public Path<T> getTrailingPath() {
	if( this.getLength() <= 0 ) {

	    return null;

	} else {

	    return new ListPathAdapter<T>( this.pathList,
					   this.frontOffset + 1 );
	}
    }


    /**
     * @override Object.toString()
     **/
    public String toString() {
	StringBuffer b = new StringBuffer();
	b.append( getClass().getName() ).append( "=[" );
	Path<T> path = this;
	int i = 0;
	while( path != null && path.getLength() > 0 ) {

	    if( i > 0 )
		b.append( "," );

	    b.append( " " ).append( path.getFirstElement() );

	    path = path.getTrailingPath();
	    i++;
	}
	b.append( " ]" );

	return b.toString();
    }

    
    /**
     * This is for testing only.
     **/
    public static void main( String[] argv ) {

	List<String> list = new java.util.ArrayList<String>( Math.max( argv.length, 1 ) );
	for( int i = 0; i < argv.length; i++ )
	    list.add( argv[i] );
	
	Path<String> path = new ListPathAdapter<String>( list );
	
	System.out.println( "Path created from input arguments: " + path.toString() );

	System.out.println( "Consuming path ... " );
	while( path != null ) {

	    System.out.println( path.getFirstElement() );
	    path = path.getTrailingPath();

	}

    }

}
