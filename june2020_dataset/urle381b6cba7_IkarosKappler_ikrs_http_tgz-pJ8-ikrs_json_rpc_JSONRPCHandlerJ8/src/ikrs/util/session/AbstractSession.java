package ikrs.util.session;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import ikrs.util.Environment;
import ikrs.util.EnvironmentDelegation;
import ikrs.util.EnvironmentFactory;

/**
 * A simple session interface.
 *
 * Basically a session is nothing more than a named environment that is bound to a specific
 * system user (remote or local). 
 *
 * Each session is identified by its unique session ID.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/


public abstract class AbstractSession<K,V,U>
    extends EnvironmentDelegation<K,V>
    implements Session<K,V,U> {

    
    /**
     * The session's ID (must not be null and must never change).
     **/
    public UUID sessionID;

    /**
     * The session's user ID (must not be null and must never change).
     **/
    private U userID;

    /**
     * The date/time of the session creation.
     **/
    private Date creationTime;

    /**
     * The date/time of the last write access.
     **/
    private Date lastModified;

    /**
     * The date/time of the last read OR write access.
     **/
    private Date lastAccessTime;


    /**
     * Creates a new AbstractSession.
     *
     * @param userID The session's user ID.
     * @param environment A factory to create the base environment to use.
     *
     * @throws NullPointerException If the userID or the environment factory are null.
     **/
    public AbstractSession( U userID,
			    EnvironmentFactory<K,V> environmentFactory ) 
	throws NullPointerException {

	this( userID,
	      environmentFactory.create() 
	      );
    }

    /**
     * Creates a new AbstractSession.
     *
     * @param userID The session's user ID.
     * @param environment The base environment to use.
     *
     * @throws NullPointerException If the userID or the environment factory are null.
     **/
    public AbstractSession( U userID,
			    Environment<K,V> environment ) 
	throws NullPointerException {

	super( environment );

	if( userID == null )
	    throw new NullPointerException( "Cannot create a new session with a null-userID." );

	this.sessionID      = UUID.randomUUID();
	this.userID         = userID;


	long curTime        = System.currentTimeMillis();
	this.creationTime   = new Date( curTime );
	this.lastModified   = new Date( curTime );
	this.lastAccessTime = new Date( curTime );
    }



    /**
     * Get the session's unique ID.
     *
     * The returned ID must never be null and it must never change.
     * 
     * @return The session's ID.
     **/
    public UUID getSessionID() {
	return this.sessionID;
    }

    
    /**
     * Get the session's user ID.
     *
     * The user ID must not be null and must not change.
     *
     * @return The session's user ID.
     **/
    public U getUserID() {
	return this.userID;
    }
	

    /**
     * This method return the date/time of the session's creation.
     *
     * @return The date/time of the session's creation.
     **/
    public Date getCreationTime() {
	return this.creationTime;
    }


    /**
     * This method returns the date/time of the last write access to this session.
     *
     * @return The date/time of the last write access to this session.
     **/
    public Date getLastModified() {
	return this.lastModified;
    }


    /**
     * This method returns the date/time of the last read OR write access to this session.
     *
     * @return The date/time of the last read OR write access to this session.
     **/
    public Date getLastAccessTime() {
	return this.lastAccessTime;
    }

    //--- BEGIN -------------- Override the delegation's environment methods to keep track of access times! ------------
    /**
     * This method indicates if the environment allows multiple names
     * for child environments.
     *
     * If this method returns false the method createChild MUST NOT create
     * a new child if there already exists a child with that name.
     **/
    public boolean allowsMultipleChildNames() {
	this.updateAccessTimes( false );
	return super.allowsMultipleChildNames();
    }

    /**
     * Get the environments parent. If there is no parent the method will
     * return null.
     * 
     * Only the root environment has not parent.     * 
     **/
    public Environment<K,V> getParent() {
	this.updateAccessTimes( false );
	return super.getParent();
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
	this.updateAccessTimes( false );
	return super.getChild( name );
    }

    /**
     * This method simply returns a list containing _all_ children.
     **/
    public List<Environment<K,V>> getAllChildren() {
	this.updateAccessTimes( false );
	return super.getAllChildren();
    }

    /**
     * Get *all* children with the given name.
     *
     * @param name The child's name (if the child search is case sensitive
     *             depends on the actual implementation).
     * @see DefaultEnvironment
     **/
    public List<Environment<K,V>> getChildren( String name ) {
	this.updateAccessTimes( false );
	return super.getChildren( name );
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
	this.updateAccessTimes( true );
	return super.createChild( name );
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
	this.updateAccessTimes( true );
	return super.removeChild( name );
    }

    /**
     * This method returns the number of all children.
     * 
     **/
    public int getChildCount() {
	this.updateAccessTimes( false );
	return super.getChildCount();
    }
    //--- END ---------------- Override the delegation's Environment methods to keep track of access times! ------------
    

    //--- BEGIN -------------- Override the delegation's Map method to keep track of access times! ---------------------
    public void clear() {
	this.updateAccessTimes( true );
	super.clear();
    }

    public boolean containsKey( Object key ) {
	this.updateAccessTimes( false );
	return super.containsKey( key );
    }

    public boolean containsValue( Object value ) {
	this.updateAccessTimes( false );
	return super.containsValue( value );
    }

    public Set<Map.Entry<K,V>> entrySet()  {
	this.updateAccessTimes( false );
	return super.entrySet();
    }

    public boolean equals(Object o) {
	this.updateAccessTimes( false );
	return super.equals( o );
    }

    public V get(Object key) {
	this.updateAccessTimes( false );
	return super.get( key );
    }

    public int hashCode() {
	this.updateAccessTimes( false );
	return super.hashCode();
    } 
         
    public boolean isEmpty() {
	this.updateAccessTimes( false );
	return super.isEmpty();
    } 
          
    public Set<K> keySet() {
	this.updateAccessTimes( false );
	return super.keySet();
    } 
          
    public V put(K key, V value) {
	this.updateAccessTimes( true );
	return super.put( key, value );
    } 
          
    public void putAll(Map<? extends K,? extends V> m) {
	this.updateAccessTimes( true );
	super.putAll( m );
    } 
         
    public V remove(Object key) {
	this.updateAccessTimes( true );
	return super.remove( key );
    } 
          
    public int size() {
	this.updateAccessTimes( false );
	return super.size();
    } 
         
    public Collection<V> values() {
	this.updateAccessTimes( false );
	return super.values();
    } 
    //--- END ---------------- Override the delegation's Map method to keep track of access times! ---------------------

    private void updateAccessTimes( boolean isWriteAccess ) {
	long curTime = System.currentTimeMillis();

	if( isWriteAccess )
	    this.lastModified.setTime( curTime );

	this.lastAccessTime.setTime( curTime );
    }

    public String toString() {
	return toString( new StringBuffer() ).toString();
    }

    public StringBuffer toString( StringBuffer b ) {
	b.append( "{ creationTime=" ).append( this.creationTime ).
	    append( ", lastModified=" ).append( this.lastModified ).
	    append( ", lastAccessTime=" ).append( this.lastAccessTime ).
	    append( ", set=" );

	b.append( super.toString() );

	b.append( " }" );

	return b;
    }
    
}
