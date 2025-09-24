package br.com.bit.ideias.reflection.type;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * @since 08/09/2009
 * @author Nadilson Oliveira da Silva
 */
public enum ModifierType {

	ABSTRACT(Modifier.ABSTRACT), FINAL(Modifier.FINAL), INTERFACE(Modifier.INTERFACE), NATIVE(Modifier.NATIVE), PRIVATE(Modifier.PRIVATE), PROTECTED(Modifier.PROTECTED), PUBLIC(Modifier.PUBLIC), STATIC(Modifier.STATIC), STRICT(Modifier.STRICT), SYNCHRONIZED(Modifier.SYNCHRONIZED), TRANSIENT(Modifier.TRANSIENT), VOLATILE(Modifier.VOLATILE);

	private final int modifier;

	private ModifierType(final int modifier) {
		this.modifier = modifier;
	}

	public boolean matches(final Member member) {
		return (member.getModifiers() & modifier) != 0;
	}
}
