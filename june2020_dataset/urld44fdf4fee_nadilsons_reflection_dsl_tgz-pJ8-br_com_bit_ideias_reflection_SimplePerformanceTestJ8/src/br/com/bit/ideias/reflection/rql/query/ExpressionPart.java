package br.com.bit.ideias.reflection.rql.query;

import br.com.bit.ideias.reflection.criteria.expression.Expression;

/**
 * @author Leonardo Campos
 * @date 20/11/2009
 */
public class ExpressionPart extends QueryPart {
    public ExpressionPart(String rql) {
        this.rql = rql;
    }
    
    @Override
    public Expression parse() {
        return Rql.parseSimple(rql);
    }
}
