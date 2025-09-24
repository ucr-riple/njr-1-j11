package br.com.bit.ideias.reflection.criteria;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import br.com.bit.ideias.reflection.core.Introspector;
import br.com.bit.ideias.reflection.criteria.expression.ConjunctionExpression;
import br.com.bit.ideias.reflection.criteria.expression.Expression;
import br.com.bit.ideias.reflection.exceptions.NoResultException;
import br.com.bit.ideias.reflection.exceptions.TooManyResultException;
import br.com.bit.ideias.reflection.type.TargetType;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @since 28/07/2009
 */
public class CriterionImpl implements Criterion {
	private final ConjunctionExpression expressionHolder = new ConjunctionExpression();
	
	private final Introspector introspector;

	public CriterionImpl(final Introspector introspector) {
		this.introspector = introspector;
	}

	public Criterion add(final Expression expression) {
		expressionHolder.add(expression);
		return this;
	}
	
	public Introspector getIntrospector() {
        return introspector;
    }

    @SuppressWarnings("unchecked")
	public <T extends Member> T uniqueResult() {
		final List result = list();
		if (result.isEmpty())
			throw new NoResultException();

		if (result.size() > 1)
			throw new TooManyResultException();

		return (T) result.get(0);
	}

    @SuppressWarnings("unchecked")
    public <T extends Member> List<T> list() {
		return (List<T>) executeSearch(obtainAllMembers());
	}

	private List<Member> executeSearch(final List<? extends Member> members) {
		final List<Member> filtred = new ArrayList<Member>();
		for (final Member member : members) {
			if (expressionHolder.accept(member))
				filtred.add(member);
		}
		return filtred;
	}

	/**
	 * Obtain all members from class and superclasses;<br/>
	 */
	private List<? extends Member> obtainAllMembers() {
		Class<?> classe = introspector.getTargetClass();
        List<Member> members = TargetType.ANY.obtainMembersInClass(classe);
        
        //We don't want to get constructors from superclass, that's why we get
        //them before looping.
        members.addAll((List<Member>)TargetType.CONSTRUCTOR.obtainMembersInClass(classe));
        
		while (classe.getSuperclass() != null) {
			classe = classe.getSuperclass();
			members.addAll((List<Member>) TargetType.ANY.obtainMembersInClass(classe));
		}

		return members;
	}
}