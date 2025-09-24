package br.com.bit.ideias.reflection.rql.query;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Leonardo Campos
 * @date 20/11/2009
 */
public enum QueryClause {
    NAME {
        @Override
        public Set<QueryOperator> doGetAccepted() {
            return createSet(QueryOperator.EQ,QueryOperator.NE,QueryOperator.LIKE,QueryOperator.IN);
        }
    },ANNOTATION {
        @Override
        public Set<QueryOperator> doGetAccepted() {
            return createSet(QueryOperator.EQ,QueryOperator.NE);
        }
    }, MODIFIER {
    }, FIELDCLASS {
    }, METHODRETURNCLASS{
    }, METHOD {
        @Override
        public Set<QueryOperator> doGetAccepted() {
            return createSet(QueryOperator.WITH);
        }
    }, TARGET {
    };
    
    public boolean acceptsOperator(QueryOperator operator) {
        return doGetAccepted().contains(operator);
    }

    public Set<QueryOperator> doGetAccepted() {
        return createSet(QueryOperator.EQ);
    }
    
    protected Set<QueryOperator> createSet(QueryOperator...operators) {
        Set<QueryOperator> accepted = new HashSet<QueryOperator>();
        for (QueryOperator operator : operators) {
            accepted.add(operator);
        }
        
        return accepted;
    }
}
