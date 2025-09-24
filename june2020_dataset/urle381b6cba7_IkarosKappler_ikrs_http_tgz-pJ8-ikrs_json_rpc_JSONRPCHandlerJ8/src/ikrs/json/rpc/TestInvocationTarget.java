package ikrs.json.rpc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import ikrs.json.JSONArray;
import ikrs.json.JSONObject;

/**
 * This is a simple RPCMethodInvocationTarget example implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-06-13
 * @modified 2013-07-16 Ikaros Kappler (added printJSONArray() and printJSONObject() methods).
 * @version 1.0.1
 **/

public class TestInvocationTarget
    extends AbstractInvocationTarget {

    /**
     * This is a default implementation that allows ONLY
     *
     * @param method The method which shall be checked if invocation
     *               is allowed.
     * @return true if the method is allowed to be called, false 
     *              otherwise.
     **/
    public boolean checkMethodInvocation( Method method ) {

	if( method == null )
	    return false;    // result not specified

	// Method must be public
	if( ! Modifier.isPublic(method.getModifiers()) )
	    return false;

	return 
	    method.getName().equals("doAnything") || 
	    method.getName().equals("doSomething") ||
	    method.getName().equals("printJSONArray") ||
	    method.getName().equals("printJSONObject");
    }


    public void doAnything( Integer i, String s, Boolean b ) {
	System.out.println( "DOING ANYTHING" );
	System.out.println( " integer param: " + i );
	System.out.println( " string param:  " + s );
	System.out.println( " boolean param: " + b );
    }
    
    public void doSomething() {
	System.out.println( "DO SOMETHING" );
    }

    public void printJSONArray( JSONArray arr ) {
	System.out.println( "PRINTING JSON Array: " + arr.toJSONString() );
    }

    public void printJSONObject( JSONObject obj ) {
	System.out.println( "PRINTING JSON OBJECT: " + obj.toJSONString() );
    }

}
