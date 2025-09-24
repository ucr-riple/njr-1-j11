package expression;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class DirkExpParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INDEX", "MEMBERACCESS", "CALL", "VARIABLE", "BRACKETEXP", "OR", "AND", "EQUALS", "NOTEQUALS", "LT", "LTEQ", "GT", "GTEQ", "PLUS", "MINUS", "MUL", "DIV", "MOD", "POWER", "NOT", "LPAREN", "RPAREN", "INTEGER_LITERAL", "DECIMAL_LITERAL", "DATETIME_LITERAL", "STRING_LITERAL", "IDENTIFIER", "COMMA", "LBRACKET", "RBRACKET", "LBRACE", "RBRACE", "LETTER", "Digit", "Exponent", "WS"
    };
    public static final int LT=13;
    public static final int RBRACE=35;
    public static final int LETTER=36;
    public static final int MOD=21;
    public static final int LBRACE=34;
    public static final int LTEQ=14;
    public static final int Exponent=38;
    public static final int POWER=22;
    public static final int NOTEQUALS=12;
    public static final int EQUALS=11;
    public static final int DATETIME_LITERAL=28;
    public static final int NOT=23;
    public static final int GTEQ=16;
    public static final int MINUS=18;
    public static final int Digit=37;
    public static final int AND=10;
    public static final int EOF=-1;
    public static final int MUL=19;
    public static final int LPAREN=24;
    public static final int LBRACKET=32;
    public static final int INDEX=4;
    public static final int RPAREN=25;
    public static final int WS=39;
    public static final int STRING_LITERAL=29;
    public static final int VARIABLE=7;
    public static final int COMMA=31;
    public static final int BRACKETEXP=8;
    public static final int IDENTIFIER=30;
    public static final int INTEGER_LITERAL=26;
    public static final int OR=9;
    public static final int GT=15;
    public static final int PLUS=17;
    public static final int MEMBERACCESS=5;
    public static final int CALL=6;
    public static final int RBRACKET=33;
    public static final int DIV=20;
    public static final int DECIMAL_LITERAL=27;

    // delegates
    // delegators


        public DirkExpParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public DirkExpParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    @Override
	public String[] getTokenNames() { return DirkExpParser.tokenNames; }
    @Override
	public String getGrammarFileName() { return "F:\\antlr\\BoncExp.g"; }


    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // F:\\antlr\\BoncExp.g:18:1: expr : logicalOrExpr ;
    public final DirkExpParser.expr_return expr() throws RecognitionException {
        DirkExpParser.expr_return retval = new DirkExpParser.expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        DirkExpParser.logicalOrExpr_return logicalOrExpr1 = null;



        try {
            // F:\\antlr\\BoncExp.g:19:5: ( logicalOrExpr )
            // F:\\antlr\\BoncExp.g:19:9: logicalOrExpr
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_logicalOrExpr_in_expr97);
            logicalOrExpr1=logicalOrExpr();

            state._fsp--;

            adaptor.addChild(root_0, logicalOrExpr1.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class logicalOrExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "logicalOrExpr"
    // F:\\antlr\\BoncExp.g:22:1: logicalOrExpr : logicalAndExpr ( OR logicalAndExpr )* ;
    public final DirkExpParser.logicalOrExpr_return logicalOrExpr() throws RecognitionException {
        DirkExpParser.logicalOrExpr_return retval = new DirkExpParser.logicalOrExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token OR3=null;
        DirkExpParser.logicalAndExpr_return logicalAndExpr2 = null;

        DirkExpParser.logicalAndExpr_return logicalAndExpr4 = null;


        CommonTree OR3_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:23:5: ( logicalAndExpr ( OR logicalAndExpr )* )
            // F:\\antlr\\BoncExp.g:23:9: logicalAndExpr ( OR logicalAndExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_logicalAndExpr_in_logicalOrExpr118);
            logicalAndExpr2=logicalAndExpr();

            state._fsp--;

            adaptor.addChild(root_0, logicalAndExpr2.getTree());
            // F:\\antlr\\BoncExp.g:23:24: ( OR logicalAndExpr )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:23:25: OR logicalAndExpr
            	    {
            	    OR3=(Token)match(input,OR,FOLLOW_OR_in_logicalOrExpr121); 
            	    OR3_tree = (CommonTree)adaptor.create(OR3);
            	    adaptor.addChild(root_0, OR3_tree);

            	    pushFollow(FOLLOW_logicalAndExpr_in_logicalOrExpr123);
            	    logicalAndExpr4=logicalAndExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, logicalAndExpr4.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "logicalOrExpr"

    public static class logicalAndExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "logicalAndExpr"
    // F:\\antlr\\BoncExp.g:26:1: logicalAndExpr : equalityExpr ( AND equalityExpr )* ;
    public final DirkExpParser.logicalAndExpr_return logicalAndExpr() throws RecognitionException {
        DirkExpParser.logicalAndExpr_return retval = new DirkExpParser.logicalAndExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token AND6=null;
        DirkExpParser.equalityExpr_return equalityExpr5 = null;

        DirkExpParser.equalityExpr_return equalityExpr7 = null;


        CommonTree AND6_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:27:5: ( equalityExpr ( AND equalityExpr )* )
            // F:\\antlr\\BoncExp.g:27:9: equalityExpr ( AND equalityExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_equalityExpr_in_logicalAndExpr170);
            equalityExpr5=equalityExpr();

            state._fsp--;

            adaptor.addChild(root_0, equalityExpr5.getTree());
            // F:\\antlr\\BoncExp.g:27:22: ( AND equalityExpr )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==AND) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:27:23: AND equalityExpr
            	    {
            	    AND6=(Token)match(input,AND,FOLLOW_AND_in_logicalAndExpr173); 
            	    AND6_tree = (CommonTree)adaptor.create(AND6);
            	    adaptor.addChild(root_0, AND6_tree);

            	    pushFollow(FOLLOW_equalityExpr_in_logicalAndExpr175);
            	    equalityExpr7=equalityExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, equalityExpr7.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "logicalAndExpr"

    public static class equalityExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpr"
    // F:\\antlr\\BoncExp.g:30:1: equalityExpr : relationalExpr ( ( EQUALS | NOTEQUALS ) relationalExpr )* ;
    public final DirkExpParser.equalityExpr_return equalityExpr() throws RecognitionException {
        DirkExpParser.equalityExpr_return retval = new DirkExpParser.equalityExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set9=null;
        DirkExpParser.relationalExpr_return relationalExpr8 = null;

        DirkExpParser.relationalExpr_return relationalExpr10 = null;


        CommonTree set9_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:31:5: ( relationalExpr ( ( EQUALS | NOTEQUALS ) relationalExpr )* )
            // F:\\antlr\\BoncExp.g:31:9: relationalExpr ( ( EQUALS | NOTEQUALS ) relationalExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_relationalExpr_in_equalityExpr227);
            relationalExpr8=relationalExpr();

            state._fsp--;

            adaptor.addChild(root_0, relationalExpr8.getTree());
            // F:\\antlr\\BoncExp.g:31:24: ( ( EQUALS | NOTEQUALS ) relationalExpr )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=EQUALS && LA3_0<=NOTEQUALS)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:31:25: ( EQUALS | NOTEQUALS ) relationalExpr
            	    {
            	    set9=(Token)input.LT(1);
            	    set9=(Token)input.LT(1);
            	    if ( (input.LA(1)>=EQUALS && input.LA(1)<=NOTEQUALS) ) {
            	        input.consume();
            	        root_0 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(set9), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpr_in_equalityExpr237);
            	    relationalExpr10=relationalExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, relationalExpr10.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "equalityExpr"

    public static class relationalExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpr"
    // F:\\antlr\\BoncExp.g:34:1: relationalExpr : additiveExpr ( ( LT | LTEQ | GT | GTEQ ) additiveExpr )? ;
    public final DirkExpParser.relationalExpr_return relationalExpr() throws RecognitionException {
        DirkExpParser.relationalExpr_return retval = new DirkExpParser.relationalExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set12=null;
        DirkExpParser.additiveExpr_return additiveExpr11 = null;

        DirkExpParser.additiveExpr_return additiveExpr13 = null;


        CommonTree set12_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:35:5: ( additiveExpr ( ( LT | LTEQ | GT | GTEQ ) additiveExpr )? )
            // F:\\antlr\\BoncExp.g:35:9: additiveExpr ( ( LT | LTEQ | GT | GTEQ ) additiveExpr )?
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_additiveExpr_in_relationalExpr263);
            additiveExpr11=additiveExpr();

            state._fsp--;

            adaptor.addChild(root_0, additiveExpr11.getTree());
            // F:\\antlr\\BoncExp.g:35:22: ( ( LT | LTEQ | GT | GTEQ ) additiveExpr )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=LT && LA4_0<=GTEQ)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:35:23: ( LT | LTEQ | GT | GTEQ ) additiveExpr
                    {
                    set12=(Token)input.LT(1);
                    set12=(Token)input.LT(1);
                    if ( (input.LA(1)>=LT && input.LA(1)<=GTEQ) ) {
                        input.consume();
                        root_0 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(set12), root_0);
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_additiveExpr_in_relationalExpr277);
                    additiveExpr13=additiveExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, additiveExpr13.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationalExpr"

    public static class additiveExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpr"
    // F:\\antlr\\BoncExp.g:38:1: additiveExpr : multiplyExpr ( ( PLUS | MINUS ) multiplyExpr )* ;
    public final DirkExpParser.additiveExpr_return additiveExpr() throws RecognitionException {
        DirkExpParser.additiveExpr_return retval = new DirkExpParser.additiveExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set15=null;
        DirkExpParser.multiplyExpr_return multiplyExpr14 = null;

        DirkExpParser.multiplyExpr_return multiplyExpr16 = null;


        CommonTree set15_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:39:5: ( multiplyExpr ( ( PLUS | MINUS ) multiplyExpr )* )
            // F:\\antlr\\BoncExp.g:39:9: multiplyExpr ( ( PLUS | MINUS ) multiplyExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_multiplyExpr_in_additiveExpr302);
            multiplyExpr14=multiplyExpr();

            state._fsp--;

            adaptor.addChild(root_0, multiplyExpr14.getTree());
            // F:\\antlr\\BoncExp.g:39:22: ( ( PLUS | MINUS ) multiplyExpr )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=PLUS && LA5_0<=MINUS)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:39:23: ( PLUS | MINUS ) multiplyExpr
            	    {
            	    set15=(Token)input.LT(1);
            	    set15=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        root_0 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(set15), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplyExpr_in_additiveExpr312);
            	    multiplyExpr16=multiplyExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multiplyExpr16.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "additiveExpr"

    public static class multiplyExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplyExpr"
    // F:\\antlr\\BoncExp.g:42:1: multiplyExpr : powExpr ( ( MUL | DIV | MOD ) powExpr )* ;
    public final DirkExpParser.multiplyExpr_return multiplyExpr() throws RecognitionException {
        DirkExpParser.multiplyExpr_return retval = new DirkExpParser.multiplyExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set18=null;
        DirkExpParser.powExpr_return powExpr17 = null;

        DirkExpParser.powExpr_return powExpr19 = null;


        CommonTree set18_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:43:5: ( powExpr ( ( MUL | DIV | MOD ) powExpr )* )
            // F:\\antlr\\BoncExp.g:43:9: powExpr ( ( MUL | DIV | MOD ) powExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_powExpr_in_multiplyExpr334);
            powExpr17=powExpr();

            state._fsp--;

            adaptor.addChild(root_0, powExpr17.getTree());
            // F:\\antlr\\BoncExp.g:43:17: ( ( MUL | DIV | MOD ) powExpr )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>=MUL && LA6_0<=MOD)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:43:18: ( MUL | DIV | MOD ) powExpr
            	    {
            	    set18=(Token)input.LT(1);
            	    set18=(Token)input.LT(1);
            	    if ( (input.LA(1)>=MUL && input.LA(1)<=MOD) ) {
            	        input.consume();
            	        root_0 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(set18), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_powExpr_in_multiplyExpr349);
            	    powExpr19=powExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, powExpr19.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multiplyExpr"

    public static class powExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "powExpr"
    // F:\\antlr\\BoncExp.g:46:1: powExpr : unaryExpr ( POWER unaryExpr )? ;
    public final DirkExpParser.powExpr_return powExpr() throws RecognitionException {
        DirkExpParser.powExpr_return retval = new DirkExpParser.powExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token POWER21=null;
        DirkExpParser.unaryExpr_return unaryExpr20 = null;

        DirkExpParser.unaryExpr_return unaryExpr22 = null;


        CommonTree POWER21_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:47:5: ( unaryExpr ( POWER unaryExpr )? )
            // F:\\antlr\\BoncExp.g:47:9: unaryExpr ( POWER unaryExpr )?
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_unaryExpr_in_powExpr375);
            unaryExpr20=unaryExpr();

            state._fsp--;

            adaptor.addChild(root_0, unaryExpr20.getTree());
            // F:\\antlr\\BoncExp.g:47:19: ( POWER unaryExpr )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==POWER) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:47:20: POWER unaryExpr
                    {
                    POWER21=(Token)match(input,POWER,FOLLOW_POWER_in_powExpr378); 
                    POWER21_tree = (CommonTree)adaptor.create(POWER21);
                    adaptor.addChild(root_0, POWER21_tree);

                    pushFollow(FOLLOW_unaryExpr_in_powExpr380);
                    unaryExpr22=unaryExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, unaryExpr22.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "powExpr"

    public static class unaryExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpr"
    // F:\\antlr\\BoncExp.g:50:1: unaryExpr : ( ( PLUS | MINUS | NOT ) unaryExpr | memberExpr );
    public final DirkExpParser.unaryExpr_return unaryExpr() throws RecognitionException {
        DirkExpParser.unaryExpr_return retval = new DirkExpParser.unaryExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set23=null;
        DirkExpParser.unaryExpr_return unaryExpr24 = null;

        DirkExpParser.memberExpr_return memberExpr25 = null;


        CommonTree set23_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:51:5: ( ( PLUS | MINUS | NOT ) unaryExpr | memberExpr )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=PLUS && LA8_0<=MINUS)||LA8_0==NOT) ) {
                alt8=1;
            }
            else if ( (LA8_0==LPAREN||(LA8_0>=INTEGER_LITERAL && LA8_0<=IDENTIFIER)||LA8_0==LBRACKET) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:51:9: ( PLUS | MINUS | NOT ) unaryExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    set23=(Token)input.LT(1);
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==NOT ) {
                        input.consume();
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(set23));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpr_in_unaryExpr418);
                    unaryExpr24=unaryExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, unaryExpr24.getTree());

                    }
                    break;
                case 2 :
                    // F:\\antlr\\BoncExp.g:52:9: memberExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_memberExpr_in_unaryExpr432);
                    memberExpr25=memberExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, memberExpr25.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaryExpr"

    public static class memberExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "memberExpr"
    // F:\\antlr\\BoncExp.g:55:1: memberExpr : basicExpr ;
    public final DirkExpParser.memberExpr_return memberExpr() throws RecognitionException {
        DirkExpParser.memberExpr_return retval = new DirkExpParser.memberExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        DirkExpParser.basicExpr_return basicExpr26 = null;



        try {
            // F:\\antlr\\BoncExp.g:56:5: ( basicExpr )
            // F:\\antlr\\BoncExp.g:56:9: basicExpr
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_basicExpr_in_memberExpr460);
            basicExpr26=basicExpr();

            state._fsp--;

            adaptor.addChild(root_0, basicExpr26.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "memberExpr"

    public static class basicExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "basicExpr"
    // F:\\antlr\\BoncExp.g:59:1: basicExpr : ( parenExpr | literal | memberFunctionExpr );
    public final DirkExpParser.basicExpr_return basicExpr() throws RecognitionException {
        DirkExpParser.basicExpr_return retval = new DirkExpParser.basicExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        DirkExpParser.parenExpr_return parenExpr27 = null;

        DirkExpParser.literal_return literal28 = null;

        DirkExpParser.memberFunctionExpr_return memberFunctionExpr29 = null;



        try {
            // F:\\antlr\\BoncExp.g:60:5: ( parenExpr | literal | memberFunctionExpr )
            int alt9=3;
            switch ( input.LA(1) ) {
            case LPAREN:
                {
                alt9=1;
                }
                break;
            case INTEGER_LITERAL:
            case DECIMAL_LITERAL:
            case DATETIME_LITERAL:
            case STRING_LITERAL:
            case LBRACKET:
                {
                alt9=2;
                }
                break;
            case IDENTIFIER:
                {
                alt9=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:60:10: parenExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_parenExpr_in_basicExpr482);
                    parenExpr27=parenExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, parenExpr27.getTree());

                    }
                    break;
                case 2 :
                    // F:\\antlr\\BoncExp.g:60:22: literal
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_basicExpr486);
                    literal28=literal();

                    state._fsp--;

                    adaptor.addChild(root_0, literal28.getTree());

                    }
                    break;
                case 3 :
                    // F:\\antlr\\BoncExp.g:60:33: memberFunctionExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_memberFunctionExpr_in_basicExpr491);
                    memberFunctionExpr29=memberFunctionExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, memberFunctionExpr29.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "basicExpr"

    public static class parenExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "parenExpr"
    // F:\\antlr\\BoncExp.g:62:1: parenExpr : LPAREN expr RPAREN -> ^( BRACKETEXP expr ) ;
    public final DirkExpParser.parenExpr_return parenExpr() throws RecognitionException {
        DirkExpParser.parenExpr_return retval = new DirkExpParser.parenExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token LPAREN30=null;
        Token RPAREN32=null;
        DirkExpParser.expr_return expr31 = null;


        CommonTree LPAREN30_tree=null;
        CommonTree RPAREN32_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // F:\\antlr\\BoncExp.g:63:5: ( LPAREN expr RPAREN -> ^( BRACKETEXP expr ) )
            // F:\\antlr\\BoncExp.g:63:9: LPAREN expr RPAREN
            {
            LPAREN30=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_parenExpr519);  
            stream_LPAREN.add(LPAREN30);

            pushFollow(FOLLOW_expr_in_parenExpr521);
            expr31=expr();

            state._fsp--;

            stream_expr.add(expr31.getTree());
            RPAREN32=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_parenExpr524);  
            stream_RPAREN.add(RPAREN32);



            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 63:30: -> ^( BRACKETEXP expr )
            {
                // F:\\antlr\\BoncExp.g:63:33: ^( BRACKETEXP expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(BRACKETEXP, "BRACKETEXP"), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parenExpr"

    public static class literal_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // F:\\antlr\\BoncExp.g:66:1: literal : ( numbericLiteral | stringLiteral );
    public final DirkExpParser.literal_return literal() throws RecognitionException {
        DirkExpParser.literal_return retval = new DirkExpParser.literal_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        DirkExpParser.numbericLiteral_return numbericLiteral33 = null;

        DirkExpParser.stringLiteral_return stringLiteral34 = null;



        try {
            // F:\\antlr\\BoncExp.g:67:5: ( numbericLiteral | stringLiteral )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>=INTEGER_LITERAL && LA10_0<=DATETIME_LITERAL)) ) {
                alt10=1;
            }
            else if ( (LA10_0==STRING_LITERAL||LA10_0==LBRACKET) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:67:9: numbericLiteral
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_numbericLiteral_in_literal559);
                    numbericLiteral33=numbericLiteral();

                    state._fsp--;

                    adaptor.addChild(root_0, numbericLiteral33.getTree());

                    }
                    break;
                case 2 :
                    // F:\\antlr\\BoncExp.g:68:9: stringLiteral
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_stringLiteral_in_literal569);
                    stringLiteral34=stringLiteral();

                    state._fsp--;

                    adaptor.addChild(root_0, stringLiteral34.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class numbericLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "numbericLiteral"
    // F:\\antlr\\BoncExp.g:71:1: numbericLiteral : ( INTEGER_LITERAL | DECIMAL_LITERAL | DATETIME_LITERAL );
    public final DirkExpParser.numbericLiteral_return numbericLiteral() throws RecognitionException {
        DirkExpParser.numbericLiteral_return retval = new DirkExpParser.numbericLiteral_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set35=null;

        CommonTree set35_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:72:5: ( INTEGER_LITERAL | DECIMAL_LITERAL | DATETIME_LITERAL )
            // F:\\antlr\\BoncExp.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set35=(Token)input.LT(1);
            if ( (input.LA(1)>=INTEGER_LITERAL && input.LA(1)<=DATETIME_LITERAL) ) {
                input.consume();
                adaptor.addChild(root_0, (CommonTree)adaptor.create(set35));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "numbericLiteral"

    public static class stringLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "stringLiteral"
    // F:\\antlr\\BoncExp.g:77:1: stringLiteral : ( STRING_LITERAL | variableExpr );
    public final DirkExpParser.stringLiteral_return stringLiteral() throws RecognitionException {
        DirkExpParser.stringLiteral_return retval = new DirkExpParser.stringLiteral_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token STRING_LITERAL36=null;
        DirkExpParser.variableExpr_return variableExpr37 = null;


        CommonTree STRING_LITERAL36_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:78:5: ( STRING_LITERAL | variableExpr )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==STRING_LITERAL) ) {
                alt11=1;
            }
            else if ( (LA11_0==LBRACKET) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:78:9: STRING_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    STRING_LITERAL36=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_stringLiteral642); 
                    STRING_LITERAL36_tree = (CommonTree)adaptor.create(STRING_LITERAL36);
                    adaptor.addChild(root_0, STRING_LITERAL36_tree);


                    }
                    break;
                case 2 :
                    // F:\\antlr\\BoncExp.g:79:7: variableExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_variableExpr_in_stringLiteral650);
                    variableExpr37=variableExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, variableExpr37.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stringLiteral"

    public static class memberFunctionExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "memberFunctionExpr"
    // F:\\antlr\\BoncExp.g:88:1: memberFunctionExpr : ( fieldPropertyExpr | methodExpr );
    public final DirkExpParser.memberFunctionExpr_return memberFunctionExpr() throws RecognitionException {
        DirkExpParser.memberFunctionExpr_return retval = new DirkExpParser.memberFunctionExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        DirkExpParser.fieldPropertyExpr_return fieldPropertyExpr38 = null;

        DirkExpParser.methodExpr_return methodExpr39 = null;



        try {
            // F:\\antlr\\BoncExp.g:89:5: ( fieldPropertyExpr | methodExpr )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==IDENTIFIER) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1==LPAREN) ) {
                    alt12=2;
                }
                else if ( ((LA12_1>=OR && LA12_1<=POWER)||LA12_1==RPAREN||LA12_1==COMMA) ) {
                    alt12=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:89:9: fieldPropertyExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_fieldPropertyExpr_in_memberFunctionExpr680);
                    fieldPropertyExpr38=fieldPropertyExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, fieldPropertyExpr38.getTree());

                    }
                    break;
                case 2 :
                    // F:\\antlr\\BoncExp.g:89:29: methodExpr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_methodExpr_in_memberFunctionExpr684);
                    methodExpr39=methodExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, methodExpr39.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "memberFunctionExpr"

    public static class fieldPropertyExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "fieldPropertyExpr"
    // F:\\antlr\\BoncExp.g:92:1: fieldPropertyExpr : IDENTIFIER ;
    public final DirkExpParser.fieldPropertyExpr_return fieldPropertyExpr() throws RecognitionException {
        DirkExpParser.fieldPropertyExpr_return retval = new DirkExpParser.fieldPropertyExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENTIFIER40=null;

        CommonTree IDENTIFIER40_tree=null;

        try {
            // F:\\antlr\\BoncExp.g:93:5: ( IDENTIFIER )
            // F:\\antlr\\BoncExp.g:93:9: IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();

            IDENTIFIER40=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_fieldPropertyExpr708); 
            IDENTIFIER40_tree = (CommonTree)adaptor.create(IDENTIFIER40);
            adaptor.addChild(root_0, IDENTIFIER40_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fieldPropertyExpr"

    public static class methodExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "methodExpr"
    // F:\\antlr\\BoncExp.g:96:1: methodExpr : IDENTIFIER LPAREN ( argument ( COMMA argument )* )? RPAREN -> ^( CALL IDENTIFIER ( argument )* ) ;
    public final DirkExpParser.methodExpr_return methodExpr() throws RecognitionException {
        DirkExpParser.methodExpr_return retval = new DirkExpParser.methodExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENTIFIER41=null;
        Token LPAREN42=null;
        Token COMMA44=null;
        Token RPAREN46=null;
        DirkExpParser.argument_return argument43 = null;

        DirkExpParser.argument_return argument45 = null;


        CommonTree IDENTIFIER41_tree=null;
        CommonTree LPAREN42_tree=null;
        CommonTree COMMA44_tree=null;
        CommonTree RPAREN46_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_argument=new RewriteRuleSubtreeStream(adaptor,"rule argument");
        try {
            // F:\\antlr\\BoncExp.g:97:5: ( IDENTIFIER LPAREN ( argument ( COMMA argument )* )? RPAREN -> ^( CALL IDENTIFIER ( argument )* ) )
            // F:\\antlr\\BoncExp.g:97:9: IDENTIFIER LPAREN ( argument ( COMMA argument )* )? RPAREN
            {
            IDENTIFIER41=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_methodExpr736);  
            stream_IDENTIFIER.add(IDENTIFIER41);

            LPAREN42=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_methodExpr738);  
            stream_LPAREN.add(LPAREN42);

            // F:\\antlr\\BoncExp.g:97:27: ( argument ( COMMA argument )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=PLUS && LA14_0<=MINUS)||(LA14_0>=NOT && LA14_0<=LPAREN)||(LA14_0>=INTEGER_LITERAL && LA14_0<=IDENTIFIER)||LA14_0==LBRACKET) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:97:28: argument ( COMMA argument )*
                    {
                    pushFollow(FOLLOW_argument_in_methodExpr741);
                    argument43=argument();

                    state._fsp--;

                    stream_argument.add(argument43.getTree());
                    // F:\\antlr\\BoncExp.g:97:37: ( COMMA argument )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // F:\\antlr\\BoncExp.g:97:38: COMMA argument
                    	    {
                    	    COMMA44=(Token)match(input,COMMA,FOLLOW_COMMA_in_methodExpr744);  
                    	    stream_COMMA.add(COMMA44);

                    	    pushFollow(FOLLOW_argument_in_methodExpr746);
                    	    argument45=argument();

                    	    state._fsp--;

                    	    stream_argument.add(argument45.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            RPAREN46=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_methodExpr752);  
            stream_RPAREN.add(RPAREN46);



            // AST REWRITE
            // elements: argument, IDENTIFIER
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 97:64: -> ^( CALL IDENTIFIER ( argument )* )
            {
                // F:\\antlr\\BoncExp.g:97:67: ^( CALL IDENTIFIER ( argument )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(CALL, "CALL"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.nextNode());
                // F:\\antlr\\BoncExp.g:97:85: ( argument )*
                while ( stream_argument.hasNext() ) {
                    adaptor.addChild(root_1, stream_argument.nextTree());

                }
                stream_argument.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "methodExpr"

    public static class argument_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "argument"
    // F:\\antlr\\BoncExp.g:106:1: argument : expr ;
    public final DirkExpParser.argument_return argument() throws RecognitionException {
        DirkExpParser.argument_return retval = new DirkExpParser.argument_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        DirkExpParser.expr_return expr47 = null;



        try {
            // F:\\antlr\\BoncExp.g:107:5: ( expr )
            // F:\\antlr\\BoncExp.g:107:9: expr
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_expr_in_argument800);
            expr47=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr47.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argument"

    public static class variableExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start "variableExpr"
    // F:\\antlr\\BoncExp.g:110:1: variableExpr : LBRACKET IDENTIFIER RBRACKET -> ^( VARIABLE IDENTIFIER ) ;
    public final DirkExpParser.variableExpr_return variableExpr() throws RecognitionException {
        DirkExpParser.variableExpr_return retval = new DirkExpParser.variableExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token LBRACKET48=null;
        Token IDENTIFIER49=null;
        Token RBRACKET50=null;

        CommonTree LBRACKET48_tree=null;
        CommonTree IDENTIFIER49_tree=null;
        CommonTree RBRACKET50_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");

        try {
            // F:\\antlr\\BoncExp.g:111:2: ( LBRACKET IDENTIFIER RBRACKET -> ^( VARIABLE IDENTIFIER ) )
            // F:\\antlr\\BoncExp.g:111:5: LBRACKET IDENTIFIER RBRACKET
            {
            LBRACKET48=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_variableExpr820);  
            stream_LBRACKET.add(LBRACKET48);

            IDENTIFIER49=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_variableExpr822);  
            stream_IDENTIFIER.add(IDENTIFIER49);

            RBRACKET50=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_variableExpr824);  
            stream_RBRACKET.add(RBRACKET50);



            // AST REWRITE
            // elements: IDENTIFIER
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 111:34: -> ^( VARIABLE IDENTIFIER )
            {
                // F:\\antlr\\BoncExp.g:111:37: ^( VARIABLE IDENTIFIER )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(VARIABLE, "VARIABLE"), root_1);

                adaptor.addChild(root_1, stream_IDENTIFIER.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "variableExpr"

    // Delegated rules


 

    public static final BitSet FOLLOW_logicalOrExpr_in_expr97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logicalAndExpr_in_logicalOrExpr118 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_OR_in_logicalOrExpr121 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_logicalAndExpr_in_logicalOrExpr123 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_equalityExpr_in_logicalAndExpr170 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_AND_in_logicalAndExpr173 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_equalityExpr_in_logicalAndExpr175 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr227 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_set_in_equalityExpr230 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr237 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr263 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_set_in_relationalExpr266 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplyExpr_in_additiveExpr302 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_set_in_additiveExpr305 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_multiplyExpr_in_additiveExpr312 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_powExpr_in_multiplyExpr334 = new BitSet(new long[]{0x0000000000380002L});
    public static final BitSet FOLLOW_set_in_multiplyExpr337 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_powExpr_in_multiplyExpr349 = new BitSet(new long[]{0x0000000000380002L});
    public static final BitSet FOLLOW_unaryExpr_in_powExpr375 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_POWER_in_powExpr378 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_unaryExpr_in_powExpr380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaryExpr406 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_unaryExpr_in_unaryExpr418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_memberExpr_in_unaryExpr432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_basicExpr_in_memberExpr460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parenExpr_in_basicExpr482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_basicExpr486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_memberFunctionExpr_in_basicExpr491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_parenExpr519 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_expr_in_parenExpr521 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RPAREN_in_parenExpr524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numbericLiteral_in_literal559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_literal569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_numbericLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_stringLiteral642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableExpr_in_stringLiteral650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldPropertyExpr_in_memberFunctionExpr680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodExpr_in_memberFunctionExpr684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_fieldPropertyExpr708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_methodExpr736 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_LPAREN_in_methodExpr738 = new BitSet(new long[]{0x000000017F860000L});
    public static final BitSet FOLLOW_argument_in_methodExpr741 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_COMMA_in_methodExpr744 = new BitSet(new long[]{0x000000017D860000L});
    public static final BitSet FOLLOW_argument_in_methodExpr746 = new BitSet(new long[]{0x0000000082000000L});
    public static final BitSet FOLLOW_RPAREN_in_methodExpr752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_argument800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_variableExpr820 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_variableExpr822 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_RBRACKET_in_variableExpr824 = new BitSet(new long[]{0x0000000000000002L});

}