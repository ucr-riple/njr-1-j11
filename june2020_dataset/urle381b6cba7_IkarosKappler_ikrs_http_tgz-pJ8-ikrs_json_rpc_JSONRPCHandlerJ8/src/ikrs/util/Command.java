package ikrs.util;

import ikrs.typesystem.BasicType;

/**
 * This interface should be used to implement the inner representation of 
 * parsed commands.
 *
 * @author Ikaros Kappler
 * @date 2012-05-02
 * @version 1.0.0
 **/


public interface Command {

    /**
     * This is the basic command name, such as "ls", "wget" or "git".
     **/
    public String getName();


    /**
     * Get the number of params this command has.
     *
     * The command name itself does not belong to the param list.
     **/
    public int getParamCount();

    /**
     * Get the param at the given index.
     * The param indices range from 0 to getParamCount()-1.
     *
     * @param index the param index.
     **/
    public BasicType getParamAt( int index )
	throws IndexOutOfBoundsException;

    /**
     * Convert this command into a parseable string.
     * The string will be stored in the given string buffer.
     *
     * @param b The stringbuffer to append this command to.
     **/
    public StringBuffer toString( StringBuffer b );
    

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
    public int execute();



}