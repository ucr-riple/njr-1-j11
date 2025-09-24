package ar.fiuba.tecnicas.framework.JTest;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class PatternRecognizer {
    private Pattern pattern;
    private Matcher matcher;
    private String regularExpression;

    public PatternRecognizer(String expression)throws PatternSyntaxException{
        regularExpression = expression;
        pattern = Pattern.compile(expression);
    }

    public boolean validate(String name) {
        if(!regularExpression.equals("")) {
            matcher = pattern.matcher(name);
            return matcher.find();
        }
        else
            return true;
    }
}

