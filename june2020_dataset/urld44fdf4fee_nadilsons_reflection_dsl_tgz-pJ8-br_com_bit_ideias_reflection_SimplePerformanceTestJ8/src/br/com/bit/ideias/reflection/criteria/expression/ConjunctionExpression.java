package br.com.bit.ideias.reflection.criteria.expression;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Campos
 * @date 31/07/2009
 * 
 *       For a ConjunctionExpression to be evaluated as true, all its
 *       subexpressions should evaluate to true
 */
public class ConjunctionExpression extends BaseExpression implements ComplexExpression {
	protected final List<Expression> expressions = new ArrayList<Expression>();

	public ConjunctionExpression() {
		super(null, null);
	}

	public ComplexExpression add(final Expression expression) {
		expressions.add(expression);

		return this;
	}

	@Override
	public boolean accept(final Member member) {
		for (final Expression expression : expressions) {
			if (!expression.accept(member))
				return false;
		}

		return true;
	}
}
