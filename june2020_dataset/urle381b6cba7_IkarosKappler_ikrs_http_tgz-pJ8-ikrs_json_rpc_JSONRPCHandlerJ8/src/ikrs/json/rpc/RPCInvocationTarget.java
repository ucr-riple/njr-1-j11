package ikrs.json.rpc;

import java.lang.reflect.Method;


/**
 * The RPCInvocationTarget interface must be implemented by classes
 * that should be accessible by the RPCHandler.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-06-13
 * @version 1.0.0
 **/

public interface RPCInvocationTarget {

    /**
     * This methods determines if the passed method is allowed to
     * be called by the RPC handler.
     *
     * The handler will only pass methods that are definitely
     * part of this object respective the implementing class.
     *
     * If the passed method is not part of this object the returned
     * value is not defined (may be true or false, as you want).
     *
     * @param method The method which shall be checked if invocation
     *               is allowed.
     * @return true if the method is allowed to be called, false 
     *              otherwise.
     **/
    public boolean checkMethodInvocation( Method method );

}
