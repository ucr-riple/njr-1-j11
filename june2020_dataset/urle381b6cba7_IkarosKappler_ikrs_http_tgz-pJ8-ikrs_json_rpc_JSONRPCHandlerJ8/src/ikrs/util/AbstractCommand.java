package ikrs.util;


import java.util.ArrayList;

import ikrs.typesystem.BasicType;

/**
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/

public abstract class AbstractCommand
    implements Command {

    private String name;
    private ArrayList<BasicType> params;

    public AbstractCommand( String name,
			    BasicType[] params ) {

	this.name = name;
	this.setParams( params );
    }


    protected void setName( String name ) {
	this.name = name;
    }

    protected void setParams( BasicType[] params ) {

	if( params == null || params.length == 0 ) {

	    this.params = new ArrayList<BasicType>( 1 );

	} else {
	    
	    this.params = new ArrayList<BasicType>( params.length );
	    for( int i = 0; i < params.length; i++ )
		this.params.add( params[i] );

	}
    }
   
    //---BEGIN------------------------ Command implementation ---------------------
    /**
     * This is the basic command name, such as "ls", "wget" or "git".
     **/
    public String getName() {
	return this.name;
    }

    /**
     * Get the number of params this command has.
     *
     * The command name itself does not belong to the param list.
     **/
    public int getParamCount() {
	return this.params.size();
    }

    /**
     * Get the param at the given index.
     * The param indices range from 0 to getParamCount()-1.
     *
     * @param index the param index.
     **/
    public BasicType getParamAt( int index )
	throws IndexOutOfBoundsException {
	
	return this.params.get( index );
    }

    /**
     * Convert this command into a parseable string.
     * The string will be stored in the given string buffer.
     *
     * @param b The stringbuffer to append this command to.
     **/
    public StringBuffer toString( StringBuffer b ) {
	b.append( getClass().getName() ).append( "=[ " ).
	    append( "name=" ).append( this.name );

	if( this.params.size() != 0 ) {
	    for( int i = 0; i < this.params.size(); i++ ) {
		b.append( ", param[" ).append( i ).append( "]: " ).append( this.params.get(i) );
	    }
	    
	}
	
	b.append( " ]" );
	return b;
    }

    /**
     * This is the final execution method.
     *
     * Note that this method does _not_ throw any exceptions!
     * It up to a stored internal command handler to handle exceptions.
     *
     * @return a return code that indicates the execution result. It depends
     *         on the context what the exact meaning of the return code is,
     *         but usually the value 0 (zero) implies success.
     **/
    public abstract int execute();
    //---END-------------------------- Command implementation ---------------------

    public String toString() {
	return toString( new StringBuffer() ).toString();
    }

}