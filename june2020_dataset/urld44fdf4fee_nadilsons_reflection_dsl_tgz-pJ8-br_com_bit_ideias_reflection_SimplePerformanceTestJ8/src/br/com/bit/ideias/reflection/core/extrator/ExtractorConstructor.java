package br.com.bit.ideias.reflection.core.extrator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.com.bit.ideias.reflection.exceptions.ConstructorNotExistsException;
import br.com.bit.ideias.reflection.exceptions.ObjectCreateException;

/**
 * @author Nadilson Oliveira da Silva
 * @date 19/02/2009
 * 
 */
public class ExtractorConstructor extends BaseExtractor {

	private final Extractor extractor;

	private Object targetInstance;

	ExtractorConstructor(final Extractor extractor, final Object instance) {
	    this(extractor);
		this.targetInstance = instance;
	}

	ExtractorConstructor(final Extractor extractor) {
		this.extractor = extractor;
	}

	public Object newInstance(final Object... params) {
		return constructorStandard(params);
	}

	public Object getTargetInstance() {
		return targetInstance;
	}

	// /////////////////////////////////////////////////////////////////////////

	private Object constructorStandard(final Object... params) {
		final Class<?> targetClass = extractor.getTargetClass();
		try {
			final Constructor<?> constructor = targetClass.getConstructor(getParametersTypes(params));
			targetInstance = constructor.newInstance(params);
			return targetInstance;
		} catch (final NoSuchMethodException e) {
			throw new ConstructorNotExistsException(e);
		} catch (final InstantiationException e) {
			throw new ObjectCreateException(e);
		} catch (final IllegalAccessException e) {
			throw new ConstructorNotExistsException(e);
		} catch (final InvocationTargetException e) {
			throw new ObjectCreateException(e);
		}
	}
}
