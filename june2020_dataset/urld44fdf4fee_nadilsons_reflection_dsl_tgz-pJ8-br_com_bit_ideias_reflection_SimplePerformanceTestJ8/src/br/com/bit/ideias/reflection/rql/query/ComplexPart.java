package br.com.bit.ideias.reflection.rql.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.bit.ideias.reflection.criteria.Restriction;
import br.com.bit.ideias.reflection.criteria.expression.ComplexExpression;
import br.com.bit.ideias.reflection.criteria.expression.ConjunctionExpression;
import br.com.bit.ideias.reflection.criteria.expression.Expression;
import br.com.bit.ideias.reflection.exceptions.SyntaxException;

/**
 * @author Leonardo Campos
 * @date 20/11/2009
 */
public class ComplexPart extends QueryPart {
    private List<QueryPart> parts = new ArrayList<QueryPart>();
    private List<QueryConnector> connectors = new ArrayList<QueryConnector>();
    
    public ComplexPart() {
    }
    
    public ComplexPart addPart(QueryPart part) {
        parts.add(part);
        return this;
    }
    
    public ComplexPart addConnector(QueryConnector connector) {
        connectors.add(connector);
        return this;
    }
    
    @Override
    public Expression parse() {
        if ((parts.size() - 1) != connectors.size())
            throw new SyntaxException("The number of connectors AND/OR is invalid");

        if(parts.size() == 1)
            return parts.get(0).parse();
        
        ConjunctionExpression expression = new ConjunctionExpression();
        ComplexExpression disjunction = null;

        int index = 0;
        Iterator<QueryConnector> iterator = connectors.iterator();
        while (iterator.hasNext()) {
            QueryConnector connector = iterator.next();

            if (connector == QueryConnector.OR) {
                if (disjunction == null) {
                    // transforms previous and next into an disjunction
                    QueryPart previous = parts.get(index);
                    QueryPart actual = parts.get(index + 1);

                    disjunction = Restriction.disjunction(previous.parse(), actual.parse());
                    expression.add(disjunction);
                    // now, add the disjunction to the complex expression
                } else {
                    // adds next to the existing disjunction
                    disjunction.add(parts.get(index + 1).parse());
                }
            } else {
                // Just null the disjunction variable and add the expression
                disjunction = null;
                expression.add(parts.get(index).parse());
            }

            index++;
        }
        
        if(disjunction == null) {
            //it means it ended with an AND conjunction
            expression.add(parts.get(index).parse());
        }
        
        return expression;
    }
}
