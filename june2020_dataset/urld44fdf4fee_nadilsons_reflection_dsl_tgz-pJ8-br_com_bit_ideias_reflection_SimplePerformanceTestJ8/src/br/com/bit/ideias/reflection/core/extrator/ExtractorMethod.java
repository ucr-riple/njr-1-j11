package br.com.bit.ideias.reflection.core.extrator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import br.com.bit.ideias.reflection.exceptions.MethodAccessException;
import br.com.bit.ideias.reflection.exceptions.MethodNotExistsException;
import br.com.bit.ideias.reflection.exceptions.MethodPrivateException;
import br.com.bit.ideias.reflection.exceptions.StaticMethodNotExistsException;
import br.com.bit.ideias.reflection.type.TargetType;
import br.com.bit.ideias.reflection.util.ReflectionHelper;

/**
 * @author Nadilson Oliveira da Silva
 * @date 19/02/2009
 * 
 */
public class ExtractorMethod extends BaseExtractor {

	private static final ReflectionHelper HELPER = ReflectionHelper.getInstance();

	private final Extractor extractor;

	private final String methodName;

	private final Method method;

	ExtractorMethod(final Extractor extractor, final String methodName) {
		this.extractor = extractor;
		this.methodName = methodName;
		this.method = null;
	}

	ExtractorMethod(final Extractor extractor, final Method method) {
		this.extractor = extractor;
		this.methodName = null;
		this.method = method;
	}

	public Object invoke(final boolean accessPrivateMembers, final Object... params) {
		try {
			return invoke(accessPrivateMembers, false, params);
		} catch (final MethodNotExistsException e) {
			return invoke(accessPrivateMembers, true, params);
		} catch (final StaticMethodNotExistsException e) {
			return invoke(accessPrivateMembers, true, params);
		}
	}

	public Method get(Class<?>... parametersTypes) {
		return getMethod(parametersTypes);
	}

	private Object invoke(final boolean accessPrivateMembers, final boolean primitiveParam, final Object... params) {
		final Object targetInstance = extractor.getTargetInstance();
		
		try {
			final Class<?>[] parametersTypes = getParametersTypes(primitiveParam, params);
			final Method method = getMethod(parametersTypes);
			method.setAccessible(accessPrivateMembers);
			return method.invoke(targetInstance, params);
		} catch (final IllegalAccessException e) {
			throw new MethodPrivateException(e);
		} catch (final InvocationTargetException e) {
			throw new MethodAccessException(e);
		}
	}

	private Method getMethod(final Class<?>[] parametersTypes) {
		final Class<?> targetClass = extractor.getTargetClass();
		try {
			return (this.method == null) ? findMethod(parametersTypes, targetClass) : this.method;
		} catch (final NoSuchMethodException e) {
			boolean isStaticInvoke = extractor.getTargetInstance() == null;
			if(isStaticInvoke) throw new StaticMethodNotExistsException();
			
			throw new MethodNotExistsException(e);
		}
	}

	private Method findMethod(final Class<?>[] parametersTypes, final Class<?> targetClass) throws NoSuchMethodException {
		List<Member> methods = HELPER.getMembers(targetClass, TargetType.METHOD);
		
		for (Member member : methods) {
			Method method = (Method) member;
			if (method.getName().equals(methodName) && HELPER.isSameParameters(method.getParameterTypes(), parametersTypes))
				return method;
		}

		throw new NoSuchMethodException(String.format("Method %s not exists in %s", methodName, targetClass.getName()));
	}
}
