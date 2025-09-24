package xscript;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class XStaticInvocationHandler implements InvocationHandler {

	private final XScriptEngine scriptEngine;

	public XStaticInvocationHandler(XScriptEngine scriptEngine) {
		this.scriptEngine = scriptEngine;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String module = method.getDeclaringClass().getPackage().getName();
		return scriptEngine.invokeFunction(module + "." + method.getName(),
				args);
	}

}
