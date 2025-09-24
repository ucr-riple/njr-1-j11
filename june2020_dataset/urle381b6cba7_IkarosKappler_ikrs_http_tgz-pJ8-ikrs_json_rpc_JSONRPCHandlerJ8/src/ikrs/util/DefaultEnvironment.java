package ikrs.util;

/**
 * This is the default Environmen implementation.
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/ 

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DefaultEnvironment<K,V> 
    extends AbstractMapDelegation<K,V>
    implements Environment<K,V> {

    /* This flag will never change; it indicates wether this Environment may use multiple child names */
    private boolean
	allowsMultipleChildNames;

    /* When the environment uses multiple child names the child count calculation might become too complex */
    private int
	childCount;

    /* The MapFactory that will be used to create child maps */
    private MapFactory<K,V> 
	mapFactory;

    /* The comparator for the map's key set */
    private Comparator<K>
	keyComparator;
    
    /* The parent environment (if exists) */
    private Environment<K,V>
	parentEnvironment;

    /* The child environments - if the set is empty this environment is a leaf */
    private Map<String,List<Environment<K,V>>> 
	children;

    /**
     * Create a new Environment having allowsMultipleChildNames() set to true.
     *
     * The environment will use a TreeMapFactory.
     *
     * @param mapFactory The map factory that will be used to create
     *                   new internal child maps.
     **/
    public DefaultEnvironment()  
	throws NullPointerException {

	this( new TreeMapFactory<K,V>() );
    }

    /**
     * Create a new Environment with the given MapFactory and having allowsMultipleChildNames() set to true.
     * @param mapFactory The map factory that will be used to create
     *                   new internal child maps.
     **/
    public DefaultEnvironment( MapFactory<K,V> mapFactory )  
	throws NullPointerException {

	super( mapFactory );

	this.mapFactory = mapFactory;	
	this.children = new TreeMap<String,List<Environment<K,V>>>( CaseInsensitiveComparator.sharedInstance );
	
	this.allowsMultipleChildNames = true;
    }

    /**
     * Create a new Environment with the given MapFactory.
     * @param mapFactory The map factory that will be used to create
     *                   new internal child maps.
     **/
    public DefaultEnvironment( MapFactory<K,V> mapFactory, 
			       boolean allowsMultipleChildNames )  
	throws NullPointerException {

	super( mapFactory );

	this.mapFactory = mapFactory;	
	this.children = new TreeMap<String,List<Environment<K,V>>>( CaseInsensitiveComparator.sharedInstance );
	this.allowsMultipleChildNames = allowsMultipleChildNames;
    }

    /**
     * Create a new Environment with the given MapFactory.
     * @param mapFactory The map factory that will be used to create
     *                   new internal child maps.
     * @param keyComparator The key comparator for this map.
     **/
    public DefaultEnvironment( MapFactory<K,V> mapFactory,
			       Comparator<K> keyComparator, 
			       boolean allowsMultipleChildNames )  
	throws NullPointerException {

	super( mapFactory ); //keyComparator );

	this.mapFactory = mapFactory;
	this.keyComparator = keyComparator;
	this.children = new TreeMap<String,List<Environment<K,V>>>( CaseInsensitiveComparator.sharedInstance );
	this.allowsMultipleChildNames = allowsMultipleChildNames;
    }

    /**
     * Create a new Environment with the given MapFactory.
     * @param mapFactory The map factory that will be used to create
     *                   new internal child maps.
     * @param keyComparator The key comparator for this map.
     * @param parentEnvironment The parent environment (if exists).
     **/
    public DefaultEnvironment( MapFactory<K,V> mapFactory,
			       Comparator<K> keyComparator,
			       Environment<K,V> parentEnvironment,
			       boolean allowsMultipleChildNames ) 
	throws NullPointerException {

	super( mapFactory ); //keyComparator );

	this.mapFactory = mapFactory;
	this.keyComparator = keyComparator;
	this.children = new TreeMap<String,List<Environment<K,V>>>( CaseInsensitiveComparator.sharedInstance );
	this.parentEnvironment = parentEnvironment;	
	this.allowsMultipleChildNames = allowsMultipleChildNames;
    }


    //---BEGIN---------------- Environment --------------------
    /**
     * This method indicates if the environment allows multiple names
     * for child environments.
     *
     * If this method returns false the method createChild MUST NOT create
     * a new child if there already exists a child with that name.
     **/
    public boolean allowsMultipleChildNames() {
	return this.allowsMultipleChildNames;
    }

    /**
     * Get the environments parent. If there is no parent the method will
     * return null.
     * 
     * Only the root environment has not parent.     * 
     **/
    public Environment<K,V> getParent() {
	return this.parentEnvironment;
    }
  
    /**
     * Get the child environment with the given name.
     * 
     * If no such child exists the method returns null.
     * 
     * @param name The child's name (if the child search is case sensitive
     *             depends on the actual implementation).
     * @see DefaultEnvironment
     **/
    public Environment<K,V> getChild( String name ) {
	List<Environment<K,V>> tmpList = this.children.get( name );
	if( tmpList == null )
	    return null;
	else if( tmpList.size() == 0 )
	    return null;
	else
	    return tmpList.get( 0 );
    }

    /**
     * Locate a child in the environment tree structure. If the environment has
     * multiple children with the same name (if allowed; see allowsMultipleChildNames())
     * the method returns the child at the most left path (first child in subset).
     *
     * If the path is empty the method returns this environment itself.
     *
     * If a path element cannot be found in the child subsets the method returns null.
     *
     * @param path The path that determines the desired child environment.
     * @return The child environment that is located at the given path or null if the path
     *         is invalid in the environment tree.
     **/
    public Environment<K,V> locateChild( Path<String> path ) {
	
	if( path == null )
	    throw new NullPointerException( "Cannot locate children by null-paths." );
	
	if( path.getLength() == 0 )
	    return this;

	Environment<K,V> child = this.getChild( path.getFirstElement() );

	if( child == null || path.getLength() == 1 ) {

	    // No such child found or end-of-path reached
	    return child;

	}

	// Child found and path has more elements.
	return child.locateChild( path.getTrailingPath() );
    }

    /**
     * This method simply returns a list containing _all_ children.
     **/
    public List<Environment<K,V>> getAllChildren() {
	List<Environment<K,V>> list = new ArrayList<Environment<K,V>>( this.childCount );
	
	Iterator<String> keyIter = this.children.keySet().iterator();
	while( keyIter.hasNext() ) {

	    List<Environment<K,V>> tmpList = this.children.get( keyIter.next() );
	    
	    for( int i = 0; i < tmpList.size(); i++ ) {
		
		list.add( tmpList.get(i) );

	    }
	    
	}

	return list;
    }

    /**
     * Get *all* children with the given name.
     *
     * @param name The child's name (if the child search is case sensitive
     *             depends on the actual implementation).
     * @see DefaultEnvironment
     **/
    public List<Environment<K,V>> getChildren( String name ) {

	List<Environment<K,V>> tmpList = this.children.get( name );
	
	if( tmpList == null || tmpList.size() == 0 )
	    return new ArrayList<Environment<K,V>>();

	// else
	List<Environment<K,V>> list = new ArrayList<Environment<K,V>>( tmpList.size() );
	
	for( int i = 0; i < tmpList.size(); i++ ) {
	    
	    list.add( tmpList.get(i) );
	    
	}

	return list;
    }

    /**
     * This method creates a new child environment and returns it.
     *
     * Note A: if allowsMultipleChildNames() returns true, this method
     *         always creates a new child.
     * Note B: if allowsMultipleChildNames() returns false, and if a child 
     *         with the given name already exists there will be
     *         no modifications to the environment. In this case the method
     *         return the old child.
     *
     * @param name The child's name.
     **/
    public Environment<K,V> createChild( String name ) {
	
	//System.out.println( "Creating new child: "+name );

	// First: locate the container
	List<Environment<K,V>> tmpList = this.children.get( name );
	
	// Create new container if none exists
	if( tmpList == null ) {

	    tmpList = new ArrayList<Environment<K,V>>( 1 );
	    this.children.put( name, tmpList );

	} else if( !allowsMultipleChildNames() && tmpList.size() == 1 ) {

	    // Child already exists and NO multiple names allowed
	    return tmpList.get(0);

	}
	

	// Child count was not 1 so far
	// --> create new child
	Environment<K,V> child = new DefaultEnvironment<K,V>( this.mapFactory,
							      this.keyComparator,
							      this,    // parentEnvironment
							      this.allowsMultipleChildNames
							      );
	
	// Add to internal list
	tmpList.add( child );
	this.childCount++;

	return child;
    }

     /**
     * This method removes the child with the given name and returns
     * its old value.
     *
     * If no such child can be found the method returns null.     
     *
     * @param name The child's name.
     **/
    public Environment<K,V> removeChild( String name ) {
	
	// First: locate the container
	List<Environment<K,V>> tmpList = this.children.get( name );
	
	// Container exists?
	if( tmpList == null ) {

	    return null;

	} else if( tmpList.size() != 0 ) {

	    // Container exists and has elements
	    // --> remove first
	    Environment<K,V> env = tmpList.remove( 0 );
	    this.childCount--;
	    
	    // Also remove container (keep the child-set as small as possible)?
	    if( tmpList.size() == 0 )
		this.children.remove( name );
	    
	    return env;

	} else {
	    
	    this.children.remove( name );
	    // Ooops ... for some strange reason there was an empty container
	    return null;

	}
    }

    /**
     * This method simply removes all children from this environment.
     * It is not a deep routine, so the inner data of child-environments themselves 
     * will not be affected.
     *
     **/
    public void removeAllChildren() {
	Iterator<Map.Entry<String,List<Environment<K,V>>>> iter = this.children.entrySet().iterator();

	while( iter.hasNext() ) {

	    Map.Entry<String,List<Environment<K,V>>> entry = iter.next();
	    
	    // Simply remove
	    iter.remove();

	    // Warning: the child's parent reference is still set!!!

	}
	
    }

    /**
     * This method returns the number of all children.
     * 
     **/
    public int getChildCount() {
	return this.childCount;
    }
    //---END------------------ Environment --------------------

    public String toString() {
	StringBuffer b = new StringBuffer();

	return toString( b ).toString();
    }

    public StringBuffer toString( StringBuffer b ) {
	return toString( b, 0 );
    }

    protected StringBuffer toString( StringBuffer b,
				     int indent ) {

	super.toString( b );

	makeIndent( b, indent, ' ' ).append( "\n" );

	if( this.getChildCount() != 0 ) {
	    makeIndent( b, indent, ' ' ).append( "Children:\n" );
	    
	    Iterator<String> keyIter = this.children.keySet().iterator();
	    while( keyIter.hasNext() ) {
		
		String name = keyIter.next();
		List<Environment<K,V>> tmpList = this.children.get( name );
		
		
		for( int i = 0; i < tmpList.size(); i++ ) {
		    
		    //list.add( tmpList.get(i) );
		    makeIndent( b, indent, ' ' ).append( name ).append( ": " );
		    Environment<K,V> childEnv = tmpList.get(i);
		    // This environment SHOULD be the same type as its parent
		    try {
			((DefaultEnvironment)childEnv).toString( b, indent+6 );
		    } catch( ClassCastException e ) {
			b.append( tmpList.get(i) );
		    }
		    
		}
		b.append( "" );
		
	    }
	}

	return b;
    }

}