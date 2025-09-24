package br.com.bit.ideias.reflection.rql.query;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.bit.ideias.reflection.core.Introspector;
import br.com.bit.ideias.reflection.criteria.Criterion;
import br.com.bit.ideias.reflection.criteria.expression.Expression;
import br.com.bit.ideias.reflection.exceptions.SyntaxException;

/**
 * @author Leonardo Campos
 * @date 16/11/2009
 */
public class Rql {
    private static final Pattern MAIN_PATTERN = Pattern.compile("from[ ]+[a-zA-Z]{1}[a-zA-Z0-9.]+([ ]+where[ ]+.+)?");
    private static final Pattern METHOD_WITH_PATTERN = Pattern.compile("(\\b|.*)method[ ]+with[ ]+");
    private static final Pattern NAME_IN_PATTERN  = Pattern.compile("(\\b|.*[ ]+)name[ ]+in[ ]+");
    private static final Pattern OPERATORS_PATTERN = Pattern.compile("([ ]+|\\b)([aA][nN][dD]|[oO][rR])([ ]+|\\b)");
    private static final Pattern CLASS_PATTERN = Pattern.compile("[^ ]*");
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("^(name|annotation|modifier|fieldclass|methodreturnclass|method|target)[ ]+(eq|in|like|ne|with|=|!=)[ ]+('[^\\']*'|\\([^\\)]*\\))$");
    private static Rql instance = new Rql();

    protected static Rql getInstance() {
        return instance;
    }

    protected Criterion parse(String rql) {
        if(isEmpty(rql)) throw new SyntaxException("Empty query");
        
        rql = rql.trim();
        
        validateOverPattern(rql);
        
        rql = removeFrom(rql);
        
        String className = retrieveClassName(rql);
        rql = rql.substring(className.length()).trim();
        
        Criterion retorno = Introspector.createCriterion(className);
        
        //is there anything else to parse? 
        //if not, return criterion as is
        if(isEmpty(rql)) return retorno;
        
        rql = removeWhere(rql);
        
        //ok, now we have to deal with the restrictions
        validateRestrictions(rql);
        QueryPart part = parseRestrictions(rql, new AtomicInteger());
        retorno.add(part.parse());
        
        return retorno;
    }

    private String removeWhere(String rql) {
        return rql.substring(5).trim();
    }

    private String retrieveClassName(String rql) {
        Matcher matcher = CLASS_PATTERN.matcher(rql);
        if(!matcher.find()) throw new SyntaxException("Classe inválida");
        String className = matcher.group();
        return className;
    }

    private String removeFrom(String rql) {
        return rql.substring(4).trim();
    }

    private void validateOverPattern(String rql) {
        String toTest = rql.toLowerCase();
        
        if(!MAIN_PATTERN.matcher(toTest).matches()) throw new SyntaxException(String.format("Query (%s) must follow the pattern: FROM [class_name] followed by optional WHERE clause", rql));
    }

    private boolean isEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }
    
    private QueryPart parseRestrictions(String rql, AtomicInteger pos) {
        ComplexPart part = new ComplexPart(); 

        rql = rql.trim();

        boolean isString = false; //if true, we are dealing with a string
        boolean isMethodWithParenthesis = false; //if true, it is not a sub expression
        char chr = '-'; //just any character
        StringBuilder builder = new StringBuilder(); // this hold the level's expressions
        String lowerRQL = null;
        
        int i = pos.get();
        while(i < rql.length()) {
            chr = rql.charAt(i);
            i = pos.incrementAndGet();
            
            switch(chr) {
                case '(':
                    if(isString) {
                        builder.append(chr);
                        continue;
                    }
                    
                    lowerRQL = builder.toString().toLowerCase();
                    isMethodWithParenthesis = isMethodWithParameter(lowerRQL);
                    
                    if(isMethodWithParenthesis) {
                        builder.append(chr);
                        i = pos.get();
                        continue;
                    }
                    
                    lowerRQL = lowerRQL.trim();
                    
                    if(lowerRQL.length() == 0) {
                        part.addPart(parseRestrictions(rql, pos));
                        i = pos.get();
                        continue;
                    }
                    
                    parsePartial(part, rql, pos, builder.toString().trim());
                    part.addPart(parseRestrictions(rql, pos));
                    i = pos.get();
                break;
                case ')':
                    if(isString) {
                        builder.append(chr);
                        i = pos.get();
                        continue;
                    }
                    
                    if(isMethodWithParenthesis) {
                        builder.append(chr);
                        isMethodWithParenthesis = false;
                        i = pos.get();
                        continue;
                    }
                    parsePartial(part, rql, pos, builder.toString());
                    return part;
                case '\'':
                    isString = !isString;
                    builder.append(chr);
                break;
                default:
                    builder.append(chr);
            }
        }
        
        parsePartial(part, rql, pos, builder.toString().trim());
        
        return part;
    }

    private boolean isMethodWithParameter(String lowerRQL) {
        return METHOD_WITH_PATTERN.matcher(lowerRQL).matches() || NAME_IN_PATTERN.matcher(lowerRQL).matches();
    }
    
    private void parsePartial(ComplexPart complexPart, String originalRql, AtomicInteger pos, String partial) {      
        Matcher matcher = OPERATORS_PATTERN.matcher(partial);
        List<QueryConnector> connectors = new ArrayList<QueryConnector>();
        while(matcher.find()) {
            connectors.add(QueryConnector.valueOf(matcher.group().trim().toUpperCase()));
        }
            
        String[] parts = OPERATORS_PATTERN.split(partial);
        
        for (String part : parts) {
            if(part.trim().length() == 0)
                continue;
            complexPart.addPart(new ExpressionPart(part));
        }
        
        for (QueryConnector connector : connectors) {
            complexPart.addConnector(connector);
        }
    }

    protected static Expression parseSimple(String rql) {
        String originalRql = rql;
        rql = rql.trim();
        String lowerRql = rql.toLowerCase();
        if(!EXPRESSION_PATTERN.matcher(lowerRql).matches())
            throw new SyntaxException(String.format("There is an error on this part of your rql => %s", rql));
        
        //now we get what it is demmanded
        int pos = rql.indexOf(' ');
        String clauseName = rql.substring(0, pos).toUpperCase().trim();
        
        QueryClause clause = QueryClause.valueOf(clauseName);
        
        rql = rql.substring(pos).trim();
        pos = rql.indexOf(' ');
        String op = rql.substring(0, pos).toUpperCase().trim();
        
        QueryOperator operator = transformToQueryOperator(op);
        
        if(!clause.acceptsOperator(operator))
            throw new SyntaxException(String.format("%s does not work with %s => %s", clauseName, op, rql));
        
        String rightHand = rql.substring(op.length()).trim();
        
        if(rightHand.startsWith("'") || rightHand.startsWith("(")) {
            return operator.getExpression(clause, removeEdges(rightHand));
        } else {
            throw new SyntaxException(String.format("There is an error on the right hand operand of this query => %s", originalRql));
        }
    }

    private static String removeEdges(String text) {
        return text.substring(1, text.length() - 1);
    }

    private static QueryOperator transformToQueryOperator(String op) {
        QueryOperator operator = null;
        if("=".equals(op))
            operator = QueryOperator.EQ;
        else if("!=".equals(op))
            operator = QueryOperator.NE;
        else
            operator = QueryOperator.valueOf(op);
        return operator;
    }

    private void validateRestrictions(String rql) {
        char chr = '-';
        Stack<Integer> stack = new Stack<Integer>();
        Integer integer = Integer.valueOf(0);
        boolean isString = false;
        
        for (int i = 0; i < rql.length(); i++) {
            chr = rql.charAt(i);
            
            switch(chr) {
                case '(':
                    if(!isString) stack.add(integer);
                break;
                case ')':
                    try {
                        if(!isString) stack.pop();
                    } catch (EmptyStackException e) {
                        throw new SyntaxException("Erro nas restrições");
                    }
                break;
                case '\'':
                    isString = !isString;
                break;
            }
        }
        
        if(isString) throw new SyntaxException("String não fechada");
        if(!stack.isEmpty()) throw new SyntaxException("Erro nas restrições");
    }
}
