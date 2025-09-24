package ikrs.json.rpc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This is an abstract RPCMethodInvocationTarget implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-06-13
 * @version 1.0.0
 **/

public abstract class AbstractInvocationTarget
    implements RPCInvocationTarget {

    /**
     * This is a default implementation that allows ONLY this method
     * itself and all public methods to be invocated.
     *
     * @param method The method which shall be checked if invocation
     *               is allowed.
     * @return true if the method is allowed to be called, false 
     *              otherwise.
     **/
    public boolean checkMethodInvocation( Method method ) {

	if( method == null )
	    return false;    // result not specified

	// This method itself is definitely allowed to be called
	if( method.getName().equals("checkMethodInvocation") )
	    return true;
	
	
	// ... all _public_ methods should be accessible by default.
	return Modifier.isPublic( method.getModifiers() );
    }

}
