package br.com.bit.ideias.reflection.core.extrator;

import java.lang.reflect.Field;

import br.com.bit.ideias.reflection.exceptions.FieldNotExistsException;
import br.com.bit.ideias.reflection.exceptions.FieldPrivateException;
import br.com.bit.ideias.reflection.exceptions.InvalidParameterException;
import br.com.bit.ideias.reflection.exceptions.MethodNotExistsException;
import br.com.bit.ideias.reflection.exceptions.StaticFieldNotExistsException;
import br.com.bit.ideias.reflection.exceptions.StaticMethodNotExistsException;

/**
 * @author Nadilson Oliveira da Silva
 * @date 19/02/2009
 * 
 */
public class ExtractorField {

	private final Extractor extractor;

	private Field field;

	private boolean directAccess;

	ExtractorField(final Extractor extractor, final String fieldName) {
		this.extractor = extractor;

		final Class<?> targetClass = extractor.getTargetClass();
		Class<?> actualClass = targetClass;

		do {
			try {
				this.field = actualClass.getDeclaredField(fieldName);
			} catch (final NoSuchFieldException e) {
				// Nothing to do 
			}
			
			if (this.field != null)
				break;

			actualClass = actualClass.getSuperclass();
		} while (actualClass != null);

		if (field == null)
			throw new FieldNotExistsException(String.format("Field %s not exists in %s", fieldName, targetClass.getName()));
	}

	ExtractorField(Extractor extractor, Field field) {
		this.extractor = extractor;
		this.field = field;
	}

	public Object invoke(final boolean accessPrivateMembers, final Object... params) {
		if (params.length > 1)
			throw new InvalidParameterException(String.format("Número excessivo de parametros [%s] para o metodo setter", params.length));

		try {
			if(directAccess)
				return invokeField(accessPrivateMembers, params);

			return invokeMethod(accessPrivateMembers, params);
		} catch (final IllegalAccessException e) {
			throw new FieldPrivateException(e);
		} catch (MethodNotExistsException e) {
			boolean isStaticInvoke = extractor.getTargetInstance() == null;
			
			if(isStaticInvoke)
				throw new StaticFieldNotExistsException();
			
			throw new FieldNotExistsException(e.getMessage());
		} catch (StaticMethodNotExistsException e) {
			throw new StaticFieldNotExistsException();
		}
	}

	public Field get(Class<?>... parametersTypes) {
		if (parametersTypes.length > 0)
			throw new InvalidParameterException("This method should not be called with parameters");

		return field;
	}

	public void directAccess() {
		this.directAccess = true;
	}

	public void directAccess(final boolean directAccess) {
		this.directAccess = directAccess;
	}

	// /////////////////////////////////////////////////////////////////////

	private Object invokeField(final boolean accessPrivateMembers, final Object... params) throws IllegalAccessException {
		Object retorno = null;
		final boolean getter = params.length == 0;
		field.setAccessible(accessPrivateMembers);
		final Object targetInstance = extractor.getTargetInstance();

		if (getter)
			retorno = field.get(targetInstance);
		else
			field.set(targetInstance, params[0]);

		return retorno;
	}

	private Object invokeMethod(final boolean accessPrivateMembers, final Object... params) {
		if(params.length > 1) throw new InvalidParameterException("Can't set a field with more than one parameter");
		
		final boolean getter = params.length == 0;
		final ExtractorMethod em = new ExtractorMethod(extractor, getMethodForField(field, getter));

		return em.invoke(accessPrivateMembers, params);
	}

	private String getMethodForField(final Field field, final boolean getter) {
		final StringBuilder retorno = new StringBuilder(getter ? "get" : "set");

		final char[] letras = field.getName().toCharArray();
		letras[0] = Character.toUpperCase(letras[0]);

		return retorno.append(letras).toString();
	}

}
