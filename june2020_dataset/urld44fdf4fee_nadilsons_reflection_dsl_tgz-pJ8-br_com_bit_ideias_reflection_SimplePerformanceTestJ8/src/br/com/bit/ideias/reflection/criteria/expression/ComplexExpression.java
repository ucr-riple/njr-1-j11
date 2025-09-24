package br.com.bit.ideias.reflection.criteria.expression;

/**
 * @author Leonardo Campos
 * @date 31/07/2009
 * 
 * Expressions should work in a composite pattern, a ComplexExpression holds other expressions but
 * behaves as a single one.
 */
public interface ComplexExpression extends Expression {
    public ComplexExpression add(Expression expression);
}
