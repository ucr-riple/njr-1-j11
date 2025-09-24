package br.com.bit.ideias.reflection.criteria;

import java.lang.annotation.Annotation;

import br.com.bit.ideias.reflection.criteria.expression.ComplexExpression;
import br.com.bit.ideias.reflection.criteria.expression.DisjunctionExpression;
import br.com.bit.ideias.reflection.criteria.expression.Expression;
import br.com.bit.ideias.reflection.criteria.expression.SimpleExpression;
import br.com.bit.ideias.reflection.type.LikeType;
import br.com.bit.ideias.reflection.type.ModifierType;
import br.com.bit.ideias.reflection.type.SearchType;
import br.com.bit.ideias.reflection.type.TargetType;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @since 27/07/2009
 */
public class Restriction {

	private Restriction() {

	}

	// /////////////////////////////////////////////////////////////////////////
	// SimpleExpression ///////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
	public static SimpleExpression eq(final String value) {
		return new SimpleExpression(value, SearchType.EQ);
	}

	public static SimpleExpression ne(final String value) {
		return new SimpleExpression(value, SearchType.NE);
	}

	public static SimpleExpression like(final String value) {
		return new SimpleExpression(value, SearchType.LIKE_START);
	}

	public static SimpleExpression like(final String value, final LikeType likeType) {
		switch (likeType) {
		case START:
			return like(value);
		case END:
			return new SimpleExpression(value, SearchType.LIKE_END);
		case ANYWHERE:
			return regex(value);
		default:
			throw new RuntimeException("Not implemented");
		}
	}

	public static SimpleExpression regex(final String value) {
		return new SimpleExpression(value, SearchType.REGEX);
	}

	public static SimpleExpression in(final String... values) {
		final StringBuilder concat = new StringBuilder();
		for (final String value : values)
			concat.append(value).append(Expression.NAME_SEPARATOR);

		return new SimpleExpression(concat.toString(), SearchType.IN);
	}

	// /////////////////////////////////////////////////////////////////////////
	// ConfigExpression ////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
	public static SimpleExpression withModifiers(final ModifierType... modifierTypes) {
		final StringBuilder concat = new StringBuilder();
		for (final ModifierType modifierType : modifierTypes)
			concat.append(modifierType).append(Expression.NAME_SEPARATOR);

		return new SimpleExpression(concat.toString(), SearchType.WITH_MODIFIERS);
	}

	// /////////////////////////////////////////////////////////////////////////
	// ClassExpression /////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
	public static SimpleExpression targetType(final TargetType targetType) {
		return new SimpleExpression(targetType.name(), SearchType.TARGET_TYPE);
	}

	public static SimpleExpression annotatedWith(final Class<? extends Annotation> clazzAnnotation) {
		return new SimpleExpression(clazzAnnotation.getName(), SearchType.ANNOTATION);
	}
	
	public static SimpleExpression notAnnotatedWith(final Class<? extends Annotation> clazzAnnotation) {
		return new SimpleExpression(clazzAnnotation.getName(), SearchType.NOT_ANNOTATION);
	}

	public static SimpleExpression fieldClassEq(final Class<?> classType) {
		return new SimpleExpression(classType.getName(), SearchType.FIELD_CLASS_EQ);
	}

	public static SimpleExpression methodReturnClassEq(final Class<?> classType) {
		return new SimpleExpression(classType.getName(), SearchType.METHOD_RETURN_CLASS_EQ);
	}

	public static SimpleExpression methodWithParams(final Class<?>... classTypes) {
		final StringBuilder concat = new StringBuilder();
		for (final Class<?> value : classTypes)
			concat.append(value.getName()).append(Expression.NAME_SEPARATOR);

		return new SimpleExpression(concat.toString(), SearchType.WITH_PARAMS);
	}

	// /////////////////////////////////////////////////////////////////////////
	// ComplexExpression //////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
	public static ComplexExpression disjunction() {
		return new DisjunctionExpression();
	}

	public static ComplexExpression disjunction(final Expression... expressions) {
		final ComplexExpression disjunctionExpression = disjunction();
		if (expressions == null)
			return disjunctionExpression;

		for (final Expression expression : expressions) {
			disjunctionExpression.add(expression);
		}

		return disjunctionExpression;
	}

}
