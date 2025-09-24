package xscript;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class XInvocationHandler implements InvocationHandler {
	
	private final XScriptEngine scriptEngine;
	private final Object thiz;
	
	public XInvocationHandler(XScriptEngine scriptEngine, Object thiz) {
		this.scriptEngine = scriptEngine;
		this.thiz = thiz;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return scriptEngine.invokeMethod(thiz, method.getName(), args);
	}

}
