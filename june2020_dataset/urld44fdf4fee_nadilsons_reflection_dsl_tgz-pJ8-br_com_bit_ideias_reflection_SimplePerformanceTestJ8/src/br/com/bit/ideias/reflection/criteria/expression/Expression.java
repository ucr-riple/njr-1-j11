package br.com.bit.ideias.reflection.criteria.expression;

import java.lang.reflect.Member;

import br.com.bit.ideias.reflection.type.SearchType;

/**
 * @author Leonardo Campos
 * @date 31/07/2009
 */
public interface Expression {

	public static final String NAME_SEPARATOR = ",";

	public boolean accept(Member member);

	public String getValue();

	public SearchType getSearchType();
}
