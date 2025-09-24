package br.com.bit.ideias.reflection.rql.query;

import br.com.bit.ideias.reflection.criteria.expression.Expression;

/**
 * @author Leonardo Campos
 * @date 20/11/2009
 */
public abstract class QueryPart {
    protected String rql;
    public abstract Expression parse();
}
