package br.com.bit.ideias.reflection.rql.query;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import br.com.bit.ideias.reflection.criteria.Restriction;
import br.com.bit.ideias.reflection.criteria.expression.Expression;
import br.com.bit.ideias.reflection.exceptions.SyntaxException;
import br.com.bit.ideias.reflection.type.LikeType;
import br.com.bit.ideias.reflection.type.ModifierType;
import br.com.bit.ideias.reflection.type.TargetType;

/**
 * @author Leonardo Campos
 * @date 20/11/2009
 */
public enum QueryOperator {
    EQ {
        @Override
        public Expression getExpression(QueryClause clause, String value) {
            switch (clause) {
                case ANNOTATION:
                    return Restriction.annotatedWith(getAnnotationClassFromValue(value));
                case MODIFIER:
                    return Restriction.withModifiers(ModifierType.valueOf(value.toUpperCase()));
                case TARGET:
                    return Restriction.targetType(TargetType.valueOf(value.toUpperCase()));
                case FIELDCLASS:
                    return Restriction.fieldClassEq(getClassFromValue(value));
                case METHODRETURNCLASS:
                    return Restriction.methodReturnClassEq(getClassFromValue(value));
                default:
                    //If it's not anything else, than it is NAME CLAUSE
                    return Restriction.eq(value);
            }
        }
    },
    NE {
        @Override
        public Expression getExpression(QueryClause clause, String value) {
            if (clause == QueryClause.ANNOTATION)
                return Restriction.notAnnotatedWith(getAnnotationClassFromValue(value));

            //If not an Annotation, it is a NAME CLAUSE
            return Restriction.ne(value);
        }
    },
    LIKE {
        @Override
        public Expression getExpression(QueryClause clause, String value) {
            if (value.startsWith("/")) {
                // it is a Regex, so it should end with another "/"
                if (!value.endsWith("/"))
                    throw new SyntaxException("Unclosed regex expression");
                return Restriction.regex(removeEdges(value));
            }

            if (value.startsWith("%") && value.endsWith("%"))
                return Restriction.like(removeEdges(value), LikeType.ANYWHERE);

            if (value.startsWith("%"))
                return Restriction.like(value.substring(1), LikeType.END);

            if (value.endsWith("%"))
                return Restriction.like(value.substring(0, value.length() - 1), LikeType.START);

            return Restriction.eq(value);
        }
    },
    IN {
        @Override
        public Expression getExpression(QueryClause clause, String value) {
            String[] parts = ARRAY_SEPARATOR_PATTERN.split(value);
            for (int i = 0; i < parts.length; i++) {
                parts[i] = removeEdges(parts[i]);
            }

            return Restriction.in(parts);
        }
    },
    WITH {
        @Override
        public Expression getExpression(QueryClause clause, String value) {
            //It must be a METHOD CLAUSE, it is the only clause that accepts WITH
            String[] parts = ARRAY_SEPARATOR_PATTERN.split(value);
            Class<?>[] params = new Class<?>[parts.length];
            for (int i = 0; i < parts.length; i++) {
                params[i] = getClassFromValue(removeEdges(parts[i]));
            }

            return Restriction.methodWithParams(params);
        }
    };

    public static final Pattern ARRAY_SEPARATOR_PATTERN = Pattern.compile("[ ]{0,},[ ]{0,}");

    public abstract Expression getExpression(QueryClause clause, String value);

    protected Class<?> getClassFromValue(String value) {
        Class<?> klass = null;
        try {
            klass = (Class<?>) Class.forName(value);
        } catch (Throwable e) {
            throw new SyntaxException(String.format("Right hand must be a Class => %s", value));
        }
        return klass;
    }

    protected String removeEdges(String value) {
        return value.substring(1, value.length() - 1);
    }

    @SuppressWarnings("unchecked")
    protected Class<? extends Annotation> getAnnotationClassFromValue(String value) {
        Class<? extends Annotation> classFromValue = null;
        classFromValue = (Class<? extends Annotation>) getClassFromValue(value);
        if (!classFromValue.isAnnotation())
            throw new SyntaxException(String.format("Right hand must be an Annotation => %s", value));

        return classFromValue;
    }
}
