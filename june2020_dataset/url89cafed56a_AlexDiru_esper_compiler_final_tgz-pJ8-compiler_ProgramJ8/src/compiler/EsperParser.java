// $ANTLR 3.4 C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g 2013-03-15 18:17:42

  package compiler;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class EsperParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASSIGN", "CHARACTER", "DECLARE", "DECREASING", "DIGITS", "DIV", "ELSE", "ELSEIF", "ENDFOR", "ENDIF", "ENDWHILE", "EQUALTO", "FOR", "GREATERTHAN", "GREATERTHANEQUAL", "IDENTIFIER", "IF", "INCREASING", "INFINITY", "LESSTHAN", "LESSTHANEQUAL", "MINUS", "MULT", "NEGATIVEINFINITY", "NULLITY", "PLUS", "PRINT", "SEMICOLON", "STRING", "VARINT", "VARSTRING", "VARTRANSREAL", "WHILE", "WHITESPACE"
    };

    public static final int EOF=-1;
    public static final int ASSIGN=4;
    public static final int CHARACTER=5;
    public static final int DECLARE=6;
    public static final int DECREASING=7;
    public static final int DIGITS=8;
    public static final int DIV=9;
    public static final int ELSE=10;
    public static final int ELSEIF=11;
    public static final int ENDFOR=12;
    public static final int ENDIF=13;
    public static final int ENDWHILE=14;
    public static final int EQUALTO=15;
    public static final int FOR=16;
    public static final int GREATERTHAN=17;
    public static final int GREATERTHANEQUAL=18;
    public static final int IDENTIFIER=19;
    public static final int IF=20;
    public static final int INCREASING=21;
    public static final int INFINITY=22;
    public static final int LESSTHAN=23;
    public static final int LESSTHANEQUAL=24;
    public static final int MINUS=25;
    public static final int MULT=26;
    public static final int NEGATIVEINFINITY=27;
    public static final int NULLITY=28;
    public static final int PLUS=29;
    public static final int PRINT=30;
    public static final int SEMICOLON=31;
    public static final int STRING=32;
    public static final int VARINT=33;
    public static final int VARSTRING=34;
    public static final int VARTRANSREAL=35;
    public static final int WHILE=36;
    public static final int WHITESPACE=37;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public EsperParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public EsperParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return EsperParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g"; }


    public static class program_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "program"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:20:1: program : statements ;
    public final EsperParser.program_return program() throws RecognitionException {
        EsperParser.program_return retval = new EsperParser.program_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        EsperParser.statements_return statements1 =null;



        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:20:9: ( statements )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:20:11: statements
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_statements_in_program55);
            statements1=statements();

            state._fsp--;

            adaptor.addChild(root_0, statements1.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "program"


    public static class statements_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statements"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:21:1: statements : ( statement )+ ;
    public final EsperParser.statements_return statements() throws RecognitionException {
        EsperParser.statements_return retval = new EsperParser.statements_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        EsperParser.statement_return statement2 =null;



        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:21:12: ( ( statement )+ )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:21:14: ( statement )+
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:21:14: ( statement )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ASSIGN||LA1_0==DECLARE||(LA1_0 >= DIGITS && LA1_0 <= DIV)||(LA1_0 >= EQUALTO && LA1_0 <= IF)||(LA1_0 >= INFINITY && LA1_0 <= PRINT)||LA1_0==STRING||LA1_0==WHILE) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:21:14: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statements63);
            	    statement2=statement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, statement2.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "statements"


    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statement"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:22:1: statement : ( ifthenelse | condition | expr | assign | declaration | print | forloop | whileloop );
    public final EsperParser.statement_return statement() throws RecognitionException {
        EsperParser.statement_return retval = new EsperParser.statement_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        EsperParser.ifthenelse_return ifthenelse3 =null;

        EsperParser.condition_return condition4 =null;

        EsperParser.expr_return expr5 =null;

        EsperParser.assign_return assign6 =null;

        EsperParser.declaration_return declaration7 =null;

        EsperParser.print_return print8 =null;

        EsperParser.forloop_return forloop9 =null;

        EsperParser.whileloop_return whileloop10 =null;



        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:22:11: ( ifthenelse | condition | expr | assign | declaration | print | forloop | whileloop )
            int alt2=8;
            switch ( input.LA(1) ) {
            case IF:
                {
                alt2=1;
                }
                break;
            case EQUALTO:
            case GREATERTHAN:
            case GREATERTHANEQUAL:
            case LESSTHAN:
            case LESSTHANEQUAL:
                {
                alt2=2;
                }
                break;
            case DIGITS:
            case DIV:
            case IDENTIFIER:
            case INFINITY:
            case MINUS:
            case MULT:
            case NEGATIVEINFINITY:
            case NULLITY:
            case PLUS:
            case STRING:
                {
                alt2=3;
                }
                break;
            case ASSIGN:
                {
                alt2=4;
                }
                break;
            case DECLARE:
                {
                alt2=5;
                }
                break;
            case PRINT:
                {
                alt2=6;
                }
                break;
            case FOR:
                {
                alt2=7;
                }
                break;
            case WHILE:
                {
                alt2=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:22:13: ifthenelse
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_ifthenelse_in_statement72);
                    ifthenelse3=ifthenelse();

                    state._fsp--;

                    adaptor.addChild(root_0, ifthenelse3.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:23:13: condition
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_condition_in_statement86);
                    condition4=condition();

                    state._fsp--;

                    adaptor.addChild(root_0, condition4.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:24:13: expr
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_expr_in_statement100);
                    expr5=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr5.getTree());

                    }
                    break;
                case 4 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:25:13: assign
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_assign_in_statement114);
                    assign6=assign();

                    state._fsp--;

                    adaptor.addChild(root_0, assign6.getTree());

                    }
                    break;
                case 5 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:26:13: declaration
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_declaration_in_statement129);
                    declaration7=declaration();

                    state._fsp--;

                    adaptor.addChild(root_0, declaration7.getTree());

                    }
                    break;
                case 6 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:27:13: print
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_print_in_statement144);
                    print8=print();

                    state._fsp--;

                    adaptor.addChild(root_0, print8.getTree());

                    }
                    break;
                case 7 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:28:13: forloop
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_forloop_in_statement158);
                    forloop9=forloop();

                    state._fsp--;

                    adaptor.addChild(root_0, forloop9.getTree());

                    }
                    break;
                case 8 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:29:13: whileloop
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_whileloop_in_statement172);
                    whileloop10=whileloop();

                    state._fsp--;

                    adaptor.addChild(root_0, whileloop10.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "statement"


    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:31:1: expr : ( ( PLUS | MINUS ) ^ expr expr | term | STRING );
    public final EsperParser.expr_return expr() throws RecognitionException {
        EsperParser.expr_return retval = new EsperParser.expr_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set11=null;
        Token STRING15=null;
        EsperParser.expr_return expr12 =null;

        EsperParser.expr_return expr13 =null;

        EsperParser.term_return term14 =null;


        Object set11_tree=null;
        Object STRING15_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:31:9: ( ( PLUS | MINUS ) ^ expr expr | term | STRING )
            int alt3=3;
            switch ( input.LA(1) ) {
            case MINUS:
            case PLUS:
                {
                alt3=1;
                }
                break;
            case DIGITS:
            case DIV:
            case IDENTIFIER:
            case INFINITY:
            case MULT:
            case NEGATIVEINFINITY:
            case NULLITY:
                {
                alt3=2;
                }
                break;
            case STRING:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }

            switch (alt3) {
                case 1 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:31:11: ( PLUS | MINUS ) ^ expr expr
                    {
                    root_0 = (Object)adaptor.nil();


                    set11=(Token)input.LT(1);

                    set11=(Token)input.LT(1);

                    if ( input.LA(1)==MINUS||input.LA(1)==PLUS ) {
                        input.consume();
                        root_0 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(set11)
                        , root_0);
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    pushFollow(FOLLOW_expr_in_expr200);
                    expr12=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr12.getTree());

                    pushFollow(FOLLOW_expr_in_expr202);
                    expr13=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr13.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:32:11: term
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_term_in_expr215);
                    term14=term();

                    state._fsp--;

                    adaptor.addChild(root_0, term14.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:33:11: STRING
                    {
                    root_0 = (Object)adaptor.nil();


                    STRING15=(Token)match(input,STRING,FOLLOW_STRING_in_expr228); 
                    STRING15_tree = 
                    (Object)adaptor.create(STRING15)
                    ;
                    adaptor.addChild(root_0, STRING15_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:35:1: term : ( ( MULT | DIV ) ^ expr expr | factor );
    public final EsperParser.term_return term() throws RecognitionException {
        EsperParser.term_return retval = new EsperParser.term_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set16=null;
        EsperParser.expr_return expr17 =null;

        EsperParser.expr_return expr18 =null;

        EsperParser.factor_return factor19 =null;


        Object set16_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:35:9: ( ( MULT | DIV ) ^ expr expr | factor )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==DIV||LA4_0==MULT) ) {
                alt4=1;
            }
            else if ( (LA4_0==DIGITS||LA4_0==IDENTIFIER||LA4_0==INFINITY||(LA4_0 >= NEGATIVEINFINITY && LA4_0 <= NULLITY)) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:35:11: ( MULT | DIV ) ^ expr expr
                    {
                    root_0 = (Object)adaptor.nil();


                    set16=(Token)input.LT(1);

                    set16=(Token)input.LT(1);

                    if ( input.LA(1)==DIV||input.LA(1)==MULT ) {
                        input.consume();
                        root_0 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(set16)
                        , root_0);
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    pushFollow(FOLLOW_expr_in_term254);
                    expr17=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr17.getTree());

                    pushFollow(FOLLOW_expr_in_term256);
                    expr18=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr18.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:36:11: factor
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_factor_in_term269);
                    factor19=factor();

                    state._fsp--;

                    adaptor.addChild(root_0, factor19.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "term"


    public static class factor_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "factor"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:38:1: factor : ( DIGITS | IDENTIFIER | NULLITY | INFINITY | NEGATIVEINFINITY );
    public final EsperParser.factor_return factor() throws RecognitionException {
        EsperParser.factor_return retval = new EsperParser.factor_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set20=null;

        Object set20_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:38:9: ( DIGITS | IDENTIFIER | NULLITY | INFINITY | NEGATIVEINFINITY )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:
            {
            root_0 = (Object)adaptor.nil();


            set20=(Token)input.LT(1);

            if ( input.LA(1)==DIGITS||input.LA(1)==IDENTIFIER||input.LA(1)==INFINITY||(input.LA(1) >= NEGATIVEINFINITY && input.LA(1) <= NULLITY) ) {
                input.consume();
                adaptor.addChild(root_0, 
                (Object)adaptor.create(set20)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "factor"


    public static class declaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "declaration"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:44:1: declaration : DECLARE ^ IDENTIFIER vartype ;
    public final EsperParser.declaration_return declaration() throws RecognitionException {
        EsperParser.declaration_return retval = new EsperParser.declaration_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token DECLARE21=null;
        Token IDENTIFIER22=null;
        EsperParser.vartype_return vartype23 =null;


        Object DECLARE21_tree=null;
        Object IDENTIFIER22_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:44:13: ( DECLARE ^ IDENTIFIER vartype )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:44:15: DECLARE ^ IDENTIFIER vartype
            {
            root_0 = (Object)adaptor.nil();


            DECLARE21=(Token)match(input,DECLARE,FOLLOW_DECLARE_in_declaration351); 
            DECLARE21_tree = 
            (Object)adaptor.create(DECLARE21)
            ;
            root_0 = (Object)adaptor.becomeRoot(DECLARE21_tree, root_0);


            IDENTIFIER22=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_declaration354); 
            IDENTIFIER22_tree = 
            (Object)adaptor.create(IDENTIFIER22)
            ;
            adaptor.addChild(root_0, IDENTIFIER22_tree);


            pushFollow(FOLLOW_vartype_in_declaration356);
            vartype23=vartype();

            state._fsp--;

            adaptor.addChild(root_0, vartype23.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "declaration"


    public static class assign_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assign"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:45:1: assign : ASSIGN ^ IDENTIFIER expr ;
    public final EsperParser.assign_return assign() throws RecognitionException {
        EsperParser.assign_return retval = new EsperParser.assign_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ASSIGN24=null;
        Token IDENTIFIER25=null;
        EsperParser.expr_return expr26 =null;


        Object ASSIGN24_tree=null;
        Object IDENTIFIER25_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:45:8: ( ASSIGN ^ IDENTIFIER expr )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:45:10: ASSIGN ^ IDENTIFIER expr
            {
            root_0 = (Object)adaptor.nil();


            ASSIGN24=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assign364); 
            ASSIGN24_tree = 
            (Object)adaptor.create(ASSIGN24)
            ;
            root_0 = (Object)adaptor.becomeRoot(ASSIGN24_tree, root_0);


            IDENTIFIER25=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assign367); 
            IDENTIFIER25_tree = 
            (Object)adaptor.create(IDENTIFIER25)
            ;
            adaptor.addChild(root_0, IDENTIFIER25_tree);


            pushFollow(FOLLOW_expr_in_assign369);
            expr26=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr26.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "assign"


    public static class ifthenelse_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "ifthenelse"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:1: ifthenelse : if_ ( elseif )* ( else_ )* ENDIF ;
    public final EsperParser.ifthenelse_return ifthenelse() throws RecognitionException {
        EsperParser.ifthenelse_return retval = new EsperParser.ifthenelse_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ENDIF30=null;
        EsperParser.if__return if_27 =null;

        EsperParser.elseif_return elseif28 =null;

        EsperParser.else__return else_29 =null;


        Object ENDIF30_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:12: ( if_ ( elseif )* ( else_ )* ENDIF )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:14: if_ ( elseif )* ( else_ )* ENDIF
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_if__in_ifthenelse378);
            if_27=if_();

            state._fsp--;

            adaptor.addChild(root_0, if_27.getTree());

            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:18: ( elseif )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ELSEIF) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:18: elseif
            	    {
            	    pushFollow(FOLLOW_elseif_in_ifthenelse380);
            	    elseif28=elseif();

            	    state._fsp--;

            	    adaptor.addChild(root_0, elseif28.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:26: ( else_ )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==ELSE) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:46:26: else_
            	    {
            	    pushFollow(FOLLOW_else__in_ifthenelse383);
            	    else_29=else_();

            	    state._fsp--;

            	    adaptor.addChild(root_0, else_29.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            ENDIF30=(Token)match(input,ENDIF,FOLLOW_ENDIF_in_ifthenelse386); 
            ENDIF30_tree = 
            (Object)adaptor.create(ENDIF30)
            ;
            adaptor.addChild(root_0, ENDIF30_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "ifthenelse"


    public static class if__return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "if_"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:47:1: if_ : IF ^ condition statements ;
    public final EsperParser.if__return if_() throws RecognitionException {
        EsperParser.if__return retval = new EsperParser.if__return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token IF31=null;
        EsperParser.condition_return condition32 =null;

        EsperParser.statements_return statements33 =null;


        Object IF31_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:47:5: ( IF ^ condition statements )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:47:7: IF ^ condition statements
            {
            root_0 = (Object)adaptor.nil();


            IF31=(Token)match(input,IF,FOLLOW_IF_in_if_394); 
            IF31_tree = 
            (Object)adaptor.create(IF31)
            ;
            root_0 = (Object)adaptor.becomeRoot(IF31_tree, root_0);


            pushFollow(FOLLOW_condition_in_if_397);
            condition32=condition();

            state._fsp--;

            adaptor.addChild(root_0, condition32.getTree());

            pushFollow(FOLLOW_statements_in_if_399);
            statements33=statements();

            state._fsp--;

            adaptor.addChild(root_0, statements33.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "if_"


    public static class elseif_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "elseif"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:48:1: elseif : ELSEIF ^ statements ;
    public final EsperParser.elseif_return elseif() throws RecognitionException {
        EsperParser.elseif_return retval = new EsperParser.elseif_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ELSEIF34=null;
        EsperParser.statements_return statements35 =null;


        Object ELSEIF34_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:48:8: ( ELSEIF ^ statements )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:48:10: ELSEIF ^ statements
            {
            root_0 = (Object)adaptor.nil();


            ELSEIF34=(Token)match(input,ELSEIF,FOLLOW_ELSEIF_in_elseif407); 
            ELSEIF34_tree = 
            (Object)adaptor.create(ELSEIF34)
            ;
            root_0 = (Object)adaptor.becomeRoot(ELSEIF34_tree, root_0);


            pushFollow(FOLLOW_statements_in_elseif410);
            statements35=statements();

            state._fsp--;

            adaptor.addChild(root_0, statements35.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "elseif"


    public static class else__return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "else_"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:49:1: else_ : ELSE ^ statements ;
    public final EsperParser.else__return else_() throws RecognitionException {
        EsperParser.else__return retval = new EsperParser.else__return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ELSE36=null;
        EsperParser.statements_return statements37 =null;


        Object ELSE36_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:49:7: ( ELSE ^ statements )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:49:9: ELSE ^ statements
            {
            root_0 = (Object)adaptor.nil();


            ELSE36=(Token)match(input,ELSE,FOLLOW_ELSE_in_else_418); 
            ELSE36_tree = 
            (Object)adaptor.create(ELSE36)
            ;
            root_0 = (Object)adaptor.becomeRoot(ELSE36_tree, root_0);


            pushFollow(FOLLOW_statements_in_else_421);
            statements37=statements();

            state._fsp--;

            adaptor.addChild(root_0, statements37.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "else_"


    public static class condition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "condition"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:50:1: condition : conditionaloperator ^ expr expr ;
    public final EsperParser.condition_return condition() throws RecognitionException {
        EsperParser.condition_return retval = new EsperParser.condition_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        EsperParser.conditionaloperator_return conditionaloperator38 =null;

        EsperParser.expr_return expr39 =null;

        EsperParser.expr_return expr40 =null;



        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:50:11: ( conditionaloperator ^ expr expr )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:50:13: conditionaloperator ^ expr expr
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_conditionaloperator_in_condition429);
            conditionaloperator38=conditionaloperator();

            state._fsp--;

            root_0 = (Object)adaptor.becomeRoot(conditionaloperator38.getTree(), root_0);

            pushFollow(FOLLOW_expr_in_condition432);
            expr39=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr39.getTree());

            pushFollow(FOLLOW_expr_in_condition434);
            expr40=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr40.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "condition"


    public static class conditionaloperator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "conditionaloperator"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:51:1: conditionaloperator : ( LESSTHAN | EQUALTO | GREATERTHAN | LESSTHANEQUAL | GREATERTHANEQUAL );
    public final EsperParser.conditionaloperator_return conditionaloperator() throws RecognitionException {
        EsperParser.conditionaloperator_return retval = new EsperParser.conditionaloperator_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set41=null;

        Object set41_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:51:21: ( LESSTHAN | EQUALTO | GREATERTHAN | LESSTHANEQUAL | GREATERTHANEQUAL )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:
            {
            root_0 = (Object)adaptor.nil();


            set41=(Token)input.LT(1);

            if ( input.LA(1)==EQUALTO||(input.LA(1) >= GREATERTHAN && input.LA(1) <= GREATERTHANEQUAL)||(input.LA(1) >= LESSTHAN && input.LA(1) <= LESSTHANEQUAL) ) {
                input.consume();
                adaptor.addChild(root_0, 
                (Object)adaptor.create(set41)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "conditionaloperator"


    public static class print_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "print"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:57:1: print : PRINT ^ expr ;
    public final EsperParser.print_return print() throws RecognitionException {
        EsperParser.print_return retval = new EsperParser.print_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token PRINT42=null;
        EsperParser.expr_return expr43 =null;


        Object PRINT42_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:57:7: ( PRINT ^ expr )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:57:9: PRINT ^ expr
            {
            root_0 = (Object)adaptor.nil();


            PRINT42=(Token)match(input,PRINT,FOLLOW_PRINT_in_print570); 
            PRINT42_tree = 
            (Object)adaptor.create(PRINT42)
            ;
            root_0 = (Object)adaptor.becomeRoot(PRINT42_tree, root_0);


            pushFollow(FOLLOW_expr_in_print573);
            expr43=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr43.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "print"


    public static class vartype_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "vartype"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:58:1: vartype : ( VARINT | VARSTRING | VARTRANSREAL );
    public final EsperParser.vartype_return vartype() throws RecognitionException {
        EsperParser.vartype_return retval = new EsperParser.vartype_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set44=null;

        Object set44_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:58:9: ( VARINT | VARSTRING | VARTRANSREAL )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:
            {
            root_0 = (Object)adaptor.nil();


            set44=(Token)input.LT(1);

            if ( (input.LA(1) >= VARINT && input.LA(1) <= VARTRANSREAL) ) {
                input.consume();
                adaptor.addChild(root_0, 
                (Object)adaptor.create(set44)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "vartype"


    public static class forloop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "forloop"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:62:1: forloop : for_ ENDFOR ;
    public final EsperParser.forloop_return forloop() throws RecognitionException {
        EsperParser.forloop_return retval = new EsperParser.forloop_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ENDFOR46=null;
        EsperParser.for__return for_45 =null;


        Object ENDFOR46_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:62:9: ( for_ ENDFOR )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:62:11: for_ ENDFOR
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_for__in_forloop621);
            for_45=for_();

            state._fsp--;

            adaptor.addChild(root_0, for_45.getTree());

            ENDFOR46=(Token)match(input,ENDFOR,FOLLOW_ENDFOR_in_forloop623); 
            ENDFOR46_tree = 
            (Object)adaptor.create(ENDFOR46)
            ;
            adaptor.addChild(root_0, ENDFOR46_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "forloop"


    public static class for__return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "for_"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:63:1: for_ : FOR ^ forgap statements ;
    public final EsperParser.for__return for_() throws RecognitionException {
        EsperParser.for__return retval = new EsperParser.for__return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token FOR47=null;
        EsperParser.forgap_return forgap48 =null;

        EsperParser.statements_return statements49 =null;


        Object FOR47_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:63:6: ( FOR ^ forgap statements )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:63:8: FOR ^ forgap statements
            {
            root_0 = (Object)adaptor.nil();


            FOR47=(Token)match(input,FOR,FOLLOW_FOR_in_for_631); 
            FOR47_tree = 
            (Object)adaptor.create(FOR47)
            ;
            root_0 = (Object)adaptor.becomeRoot(FOR47_tree, root_0);


            pushFollow(FOLLOW_forgap_in_for_634);
            forgap48=forgap();

            state._fsp--;

            adaptor.addChild(root_0, forgap48.getTree());

            pushFollow(FOLLOW_statements_in_for_636);
            statements49=statements();

            state._fsp--;

            adaptor.addChild(root_0, statements49.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "for_"


    public static class forgap_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "forgap"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:64:1: forgap : ( INCREASING | DECREASING ) ^ factor factor ;
    public final EsperParser.forgap_return forgap() throws RecognitionException {
        EsperParser.forgap_return retval = new EsperParser.forgap_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set50=null;
        EsperParser.factor_return factor51 =null;

        EsperParser.factor_return factor52 =null;


        Object set50_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:64:8: ( ( INCREASING | DECREASING ) ^ factor factor )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:64:10: ( INCREASING | DECREASING ) ^ factor factor
            {
            root_0 = (Object)adaptor.nil();


            set50=(Token)input.LT(1);

            set50=(Token)input.LT(1);

            if ( input.LA(1)==DECREASING||input.LA(1)==INCREASING ) {
                input.consume();
                root_0 = (Object)adaptor.becomeRoot(
                (Object)adaptor.create(set50)
                , root_0);
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            pushFollow(FOLLOW_factor_in_forgap654);
            factor51=factor();

            state._fsp--;

            adaptor.addChild(root_0, factor51.getTree());

            pushFollow(FOLLOW_factor_in_forgap656);
            factor52=factor();

            state._fsp--;

            adaptor.addChild(root_0, factor52.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "forgap"


    public static class whileloop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "whileloop"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:65:1: whileloop : while_ ENDWHILE ;
    public final EsperParser.whileloop_return whileloop() throws RecognitionException {
        EsperParser.whileloop_return retval = new EsperParser.whileloop_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ENDWHILE54=null;
        EsperParser.while__return while_53 =null;


        Object ENDWHILE54_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:65:11: ( while_ ENDWHILE )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:65:13: while_ ENDWHILE
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_while__in_whileloop663);
            while_53=while_();

            state._fsp--;

            adaptor.addChild(root_0, while_53.getTree());

            ENDWHILE54=(Token)match(input,ENDWHILE,FOLLOW_ENDWHILE_in_whileloop665); 
            ENDWHILE54_tree = 
            (Object)adaptor.create(ENDWHILE54)
            ;
            adaptor.addChild(root_0, ENDWHILE54_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "whileloop"


    public static class while__return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "while_"
    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:66:1: while_ : WHILE ^ condition statements ;
    public final EsperParser.while__return while_() throws RecognitionException {
        EsperParser.while__return retval = new EsperParser.while__return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token WHILE55=null;
        EsperParser.condition_return condition56 =null;

        EsperParser.statements_return statements57 =null;


        Object WHILE55_tree=null;

        try {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:66:8: ( WHILE ^ condition statements )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:66:10: WHILE ^ condition statements
            {
            root_0 = (Object)adaptor.nil();


            WHILE55=(Token)match(input,WHILE,FOLLOW_WHILE_in_while_672); 
            WHILE55_tree = 
            (Object)adaptor.create(WHILE55)
            ;
            root_0 = (Object)adaptor.becomeRoot(WHILE55_tree, root_0);


            pushFollow(FOLLOW_condition_in_while_675);
            condition56=condition();

            state._fsp--;

            adaptor.addChild(root_0, condition56.getTree());

            pushFollow(FOLLOW_statements_in_while_677);
            statements57=statements();

            state._fsp--;

            adaptor.addChild(root_0, statements57.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "while_"

    // Delegated rules


 

    public static final BitSet FOLLOW_statements_in_program55 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_statements63 = new BitSet(new long[]{0x000000117FDF8352L});
    public static final BitSet FOLLOW_ifthenelse_in_statement72 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condition_in_statement86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_statement100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assign_in_statement114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_statement129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_print_in_statement144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forloop_in_statement158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileloop_in_statement172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_expr193 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_expr200 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_expr202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_expr215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_expr228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_term247 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_term254 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_term256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factor_in_term269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECLARE_in_declaration351 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_declaration354 = new BitSet(new long[]{0x0000000E00000000L});
    public static final BitSet FOLLOW_vartype_in_declaration356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSIGN_in_assign364 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assign367 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_assign369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if__in_ifthenelse378 = new BitSet(new long[]{0x0000000000002C00L});
    public static final BitSet FOLLOW_elseif_in_ifthenelse380 = new BitSet(new long[]{0x0000000000002C00L});
    public static final BitSet FOLLOW_else__in_ifthenelse383 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_ENDIF_in_ifthenelse386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_394 = new BitSet(new long[]{0x0000000001868000L});
    public static final BitSet FOLLOW_condition_in_if_397 = new BitSet(new long[]{0x000000117FDF8350L});
    public static final BitSet FOLLOW_statements_in_if_399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSEIF_in_elseif407 = new BitSet(new long[]{0x000000117FDF8350L});
    public static final BitSet FOLLOW_statements_in_elseif410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_else_418 = new BitSet(new long[]{0x000000117FDF8350L});
    public static final BitSet FOLLOW_statements_in_else_421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionaloperator_in_condition429 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_condition432 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_condition434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRINT_in_print570 = new BitSet(new long[]{0x000000013E480300L});
    public static final BitSet FOLLOW_expr_in_print573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for__in_forloop621 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ENDFOR_in_forloop623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_for_631 = new BitSet(new long[]{0x0000000000200080L});
    public static final BitSet FOLLOW_forgap_in_for_634 = new BitSet(new long[]{0x000000117FDF8350L});
    public static final BitSet FOLLOW_statements_in_for_636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_forgap645 = new BitSet(new long[]{0x0000000018480100L});
    public static final BitSet FOLLOW_factor_in_forgap654 = new BitSet(new long[]{0x0000000018480100L});
    public static final BitSet FOLLOW_factor_in_forgap656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while__in_whileloop663 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ENDWHILE_in_whileloop665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_while_672 = new BitSet(new long[]{0x0000000001868000L});
    public static final BitSet FOLLOW_condition_in_while_675 = new BitSet(new long[]{0x000000117FDF8350L});
    public static final BitSet FOLLOW_statements_in_while_677 = new BitSet(new long[]{0x0000000000000002L});

}