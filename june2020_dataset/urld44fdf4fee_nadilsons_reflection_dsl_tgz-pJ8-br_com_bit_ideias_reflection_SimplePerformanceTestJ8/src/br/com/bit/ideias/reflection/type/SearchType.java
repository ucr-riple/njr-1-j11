package br.com.bit.ideias.reflection.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import br.com.bit.ideias.reflection.criteria.expression.Expression;
import br.com.bit.ideias.reflection.exceptions.ClassNotExistsException;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @since 28/07/2009
 */
public enum SearchType {

	EQ {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			return member.getName().equals(expression.getValue());
		}
	},
	NE {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			return !SearchType.EQ.matches(member, expression);
		}
	},
	LIKE_START {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			return member.getName().startsWith(expression.getValue());
		}
	},
	LIKE_END {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			return member.getName().endsWith(expression.getValue());
		}
	},
	REGEX {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final String regex = String.format(".*%s.*", expression.getValue());
			return Pattern.matches(regex, member.getName());
		}
	},
	IN {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final String[] values = expression.getValue().split(Expression.NAME_SEPARATOR);
			for (final String value : values)
				if (member.getName().equals(value))
					return true;

			return false;
		}
	},
	ANNOTATION {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final Class<? extends Annotation> annotationClass = getAnnotatedClass(expression);
			final AnnotatedElement annotatedElement = (AnnotatedElement) member;

			return annotatedElement.isAnnotationPresent(annotationClass);
		}

		@SuppressWarnings("unchecked")
		private Class<? extends Annotation> getAnnotatedClass(final Expression expression) {
			Class<? extends Annotation> classe = null;
			try {
				classe = (Class<? extends Annotation>) Class.forName(expression.getValue());
			} catch (final ClassNotFoundException e) {
				throw new ClassNotExistsException(e);
			}
			return classe;
		}
	}, 
	NOT_ANNOTATION {
		@Override
		public boolean matches(Member member, Expression expression) {
			return !ANNOTATION.matches(member, expression);
		}
	},	
	WITH_MODIFIERS {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final String[] e = expression.getValue().split(Expression.NAME_SEPARATOR);
			for (final String typeString : e) {
				final ModifierType type = ModifierType.valueOf(typeString);
				if (type.matches(member))
					return true;
			}
			return false;
		}
	},
	TARGET_TYPE {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final TargetType targetType = TargetType.valueOf(expression.getValue());
			return targetType.isValidMember(member);
		}
	},
	FIELD_CLASS_EQ {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final Class<?> classe = extractClass(expression.getValue());
			return Field.class.equals(member.getClass()) && ((Field) member).getType().equals(classe);
		}
	},
	METHOD_RETURN_CLASS_EQ {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			final Class<?> classe = extractClass(expression.getValue());
			return Method.class.equals(member.getClass()) && ((Method) member).getReturnType().equals(classe);
		}
	},
	WITH_PARAMS {
		@Override
		public boolean matches(final Member member, final Expression expression) {
			if (!Method.class.equals(member.getClass()))
				return false;

			final String[] values = expression.getValue().split(Expression.NAME_SEPARATOR);
			final Class<?>[] parameterTypes = ((Method) member).getParameterTypes();

			if (values.length != parameterTypes.length)
				return false;

			for (int i = 0; i < parameterTypes.length; i++) {
				if (!parameterTypes[i].equals(extractClass(values[i])))
					return false;
			}

			return true;
		}
	};

	public abstract boolean matches(Member member, Expression expression);

	private static Class<?> extractClass(final String className) {
		try {
			return Class.forName(className);
		} catch (final ClassNotFoundException e) {
			throw new ClassNotExistsException(e);
		}
	}

}
