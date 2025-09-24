package br.com.bit.ideias.reflection.core.extrator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.bit.ideias.reflection.exceptions.InvalidStateException;

/**
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class Extractor extends BaseExtractor {

	private final Class<?> targetClass;

	private ExtractorConstructor extractorConstructor;

	private ExtractorMethod extractorMethod;

	private ExtractorField extractorField;

	private Extractor(final Class<?> classe) {
		this.targetClass = classe;
	}

	private Extractor(final Object instance) {
		this(instance.getClass());
		this.extractorConstructor = new ExtractorConstructor(this, instance);
	}

	public static Extractor inObject(final Object instance) {
		return new Extractor(instance);
	}

	public static Extractor forClass(final Class<?> classe) {
		return new Extractor(classe);
	}

	public ExtractorConstructor constructor() {
		extractorConstructor = new ExtractorConstructor(this);
		return extractorConstructor;
	}

	public ExtractorMethod setMethod(final String methodName) {
		extractorMethod = new ExtractorMethod(this, methodName);
		return extractorMethod;
	}
	
	public ExtractorMethod setMethod(final Method method) {
		extractorMethod = new ExtractorMethod(this, method);
		return extractorMethod;
	}

	public ExtractorMethod method() {
		if (extractorMethod == null)
			throw new InvalidStateException("Metodo nao foi especificado");

		return extractorMethod;
	}

	public ExtractorField setField(final String fieldName) {
		extractorField = new ExtractorField(this, fieldName);
		return extractorField;
	}
	
	public ExtractorField setField(final Field field) {
		extractorField = new ExtractorField(this, field);
		return extractorField;
	}


	public ExtractorField field() {
		if (extractorField == null)
			throw new InvalidStateException("Field nao foi especificado");

		return extractorField;
	}

	public Object getTargetInstance() {
		if (extractorConstructor == null)
			return null;

		return extractorConstructor.getTargetInstance();
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * Verifies if there is an extractorConstructor and if this
	 * extractorConstructor has an target instance reference
	 */
	public boolean isEmpty() {
		return false;//extractorConstructor == null;// || extractorConstructor.getTargetInstance() == null;
	}

}
