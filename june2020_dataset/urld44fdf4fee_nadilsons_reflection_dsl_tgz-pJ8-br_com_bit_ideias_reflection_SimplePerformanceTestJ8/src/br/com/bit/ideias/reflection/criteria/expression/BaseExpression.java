package br.com.bit.ideias.reflection.criteria.expression;

import java.lang.reflect.Member;

import br.com.bit.ideias.reflection.type.SearchType;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @since 28/07/2009
 */
public abstract class BaseExpression implements Expression {

	protected String value;

	protected SearchType searchType;

	public BaseExpression(final String value, final SearchType searchType) {
		this.value = value;
		this.searchType = searchType;
	}

	public String getValue() {
		return value;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public boolean accept(final Member member) {
		return searchType.matches(member, this);
	}
}
