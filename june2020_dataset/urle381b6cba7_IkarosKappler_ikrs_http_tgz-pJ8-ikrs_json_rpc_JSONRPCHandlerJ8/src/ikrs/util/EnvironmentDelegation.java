package ikrs.util;

import java.util.Map;
import java.util.List;

/**
 * The EnvironmentDelegation class is a wrapper for anonymous environments.
 *
 * The passed environment will become the underlying core and method calls to the
 * delegation's environment-methods will be directly forwarded to the core.
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/


public class EnvironmentDelegation<K,V>
    extends MapDelegation<K,V>
    implements Environment<K,V> {

    /**
     * The actual core environment.
     *
     * Warning: EnvironmentDelegation.coreEnvironment and MapDelegation.coreMap are the
     *          same instances in this case! :)
     **/
    private Environment<K,V> coreEnvironment;



    /**
     * Creates a new EnvironmentDelegation with the given core environment.
     *
     * @param core The core environment (must not be null).
     *
     * @throws NullPointerException if core is null.
     **/
    public EnvironmentDelegation( Environment<K,V> core ) 
	throws NullPointerException {

	super( core );

	if( core == null )
	    throw new NullPointerException( "Cannot create environment delegations with a null core." );
	
	this.coreEnvironment = core;
    }

    /**
     * This method indicates if the environment allows multiple names
     * for child environments.
     *
     * If this method returns false the method createChild MUST NOT create
     * a new child if there already exists a child with that name.
     **/
    public boolean allowsMultipleChildNames() {
	return this.coreEnvironment.allowsMultipleChildNames();
    }

    /**
     * Get the environments parent. If there is no parent the method will
     * return null.
     * 
     * Only the root environment has not parent.     * 
     **/
    public Environment<K,V> getParent() {
	return this.coreEnvironment.getParent();
    }
  
    /**
     * Get the child environment with the given name; there might be different
     * children with the same name!
     * The method will return the first match then.
     * 
     * If no such child exists the method returns null.
     * 
     * @param name The child's name (if the child search is case sensitive
     *             depends on the actual implementation).
     * @see DefaultEnvironment
     **/
    public Environment<K,V> getChild( String name ) {
	return this.coreEnvironment.getChild( name );
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
	return this.coreEnvironment.locateChild( path );
    }


    /**
     * This method simply returns a list containing _all_ children.
     **/
    public List<Environment<K,V>> getAllChildren() {
	return this.coreEnvironment.getAllChildren();
    }

    /**
     * Get *all* children with the given name.
     *
     * @param name The child's name (if the child search is case sensitive
     *             depends on the actual implementation).
     * @see DefaultEnvironment
     **/
    public List<Environment<K,V>> getChildren( String name ) {
	return this.coreEnvironment.getChildren( name );
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
	return this.coreEnvironment.createChild( name );
    }

    /**
     * This method removes the child with the given name and returns
     * its old value.
     *
     * If no such child can be found the method returns null.     *
     *
     * @param name The child's name.
     **/
    public Environment<K,V> removeChild( String name ) {
	return this.coreEnvironment.removeChild( name );
    }

    /**
     * This method simply removes all children from this environment.
     * It is not a deep routine, so the inner data of child-environments themselves 
     * will not be affected.
     *
     **/
    public void removeAllChildren() {
	this.coreEnvironment.removeAllChildren();
    }

    /**
     * This method returns the number of all children.
     * 
     **/
    public int getChildCount() {
	return this.coreEnvironment.getChildCount();
    }

}