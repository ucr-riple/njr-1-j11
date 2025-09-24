// $ANTLR 3.4 C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g 2013-03-15 18:17:43

  package compiler;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class EsperLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public EsperLexer() {} 
    public EsperLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public EsperLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g"; }

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:72:12: ( ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+ )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:72:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:72:14: ( '\\t' | ' ' | '\\r' | '\\n' | '\\u000C' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '\t' && LA1_0 <= '\n')||(LA1_0 >= '\f' && LA1_0 <= '\r')||LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "DIGITS"
    public final void mDIGITS() throws RecognitionException {
        try {
            int _type = DIGITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:73:8: ( ( '-' )* ( '0' .. '9' )+ )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:73:10: ( '-' )* ( '0' .. '9' )+
            {
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:73:10: ( '-' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='-') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:73:10: '-'
            	    {
            	    match('-'); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:73:15: ( '0' .. '9' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGITS"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:74:5: ( '+' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:74:7: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:75:6: ( '-' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:75:8: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MULT"
    public final void mMULT() throws RecognitionException {
        try {
            int _type = MULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:76:5: ( '*' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:76:7: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MULT"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:77:4: ( '/' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:77:6: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "NULLITY"
    public final void mNULLITY() throws RecognitionException {
        try {
            int _type = NULLITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:78:9: ( 'nullity' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:78:11: 'nullity'
            {
            match("nullity"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NULLITY"

    // $ANTLR start "INFINITY"
    public final void mINFINITY() throws RecognitionException {
        try {
            int _type = INFINITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:79:10: ( 'inf' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:79:12: 'inf'
            {
            match("inf"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INFINITY"

    // $ANTLR start "NEGATIVEINFINITY"
    public final void mNEGATIVEINFINITY() throws RecognitionException {
        try {
            int _type = NEGATIVEINFINITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:80:18: ( '-inf' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:80:20: '-inf'
            {
            match("-inf"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEGATIVEINFINITY"

    // $ANTLR start "VARTRANSREAL"
    public final void mVARTRANSREAL() throws RecognitionException {
        try {
            int _type = VARTRANSREAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:81:13: ( 'transreal' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:81:15: 'transreal'
            {
            match("transreal"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VARTRANSREAL"

    // $ANTLR start "VARINT"
    public final void mVARINT() throws RecognitionException {
        try {
            int _type = VARINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:82:7: ( 'int' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:82:9: 'int'
            {
            match("int"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VARINT"

    // $ANTLR start "VARSTRING"
    public final void mVARSTRING() throws RecognitionException {
        try {
            int _type = VARSTRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:83:11: ( 'string' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:83:13: 'string'
            {
            match("string"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "VARSTRING"

    // $ANTLR start "LESSTHAN"
    public final void mLESSTHAN() throws RecognitionException {
        try {
            int _type = LESSTHAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:84:10: ( 'lt' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:84:12: 'lt'
            {
            match("lt"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LESSTHAN"

    // $ANTLR start "GREATERTHAN"
    public final void mGREATERTHAN() throws RecognitionException {
        try {
            int _type = GREATERTHAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:85:13: ( 'gt' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:85:15: 'gt'
            {
            match("gt"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GREATERTHAN"

    // $ANTLR start "LESSTHANEQUAL"
    public final void mLESSTHANEQUAL() throws RecognitionException {
        try {
            int _type = LESSTHANEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:86:15: ( 'lte' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:86:17: 'lte'
            {
            match("lte"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LESSTHANEQUAL"

    // $ANTLR start "GREATERTHANEQUAL"
    public final void mGREATERTHANEQUAL() throws RecognitionException {
        try {
            int _type = GREATERTHANEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:87:18: ( 'gte' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:87:20: 'gte'
            {
            match("gte"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GREATERTHANEQUAL"

    // $ANTLR start "EQUALTO"
    public final void mEQUALTO() throws RecognitionException {
        try {
            int _type = EQUALTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:88:9: ( 'eq' | 'equal' )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='e') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='q') ) {
                    int LA4_2 = input.LA(3);

                    if ( (LA4_2=='u') ) {
                        alt4=2;
                    }
                    else {
                        alt4=1;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:88:11: 'eq'
                    {
                    match("eq"); 



                    }
                    break;
                case 2 :
                    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:88:18: 'equal'
                    {
                    match("equal"); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUALTO"

    // $ANTLR start "DECLARE"
    public final void mDECLARE() throws RecognitionException {
        try {
            int _type = DECLARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:89:9: ( 'declare' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:89:11: 'declare'
            {
            match("declare"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DECLARE"

    // $ANTLR start "ASSIGN"
    public final void mASSIGN() throws RecognitionException {
        try {
            int _type = ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:90:8: ( 'set' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:90:10: 'set'
            {
            match("set"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ASSIGN"

    // $ANTLR start "PRINT"
    public final void mPRINT() throws RecognitionException {
        try {
            int _type = PRINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:91:7: ( 'print' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:91:9: 'print'
            {
            match("print"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PRINT"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:92:4: ( 'if' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:92:6: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "ELSEIF"
    public final void mELSEIF() throws RecognitionException {
        try {
            int _type = ELSEIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:93:8: ( 'elseif' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:93:10: 'elseif'
            {
            match("elseif"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ELSEIF"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:94:6: ( 'else' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:94:8: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "ENDIF"
    public final void mENDIF() throws RecognitionException {
        try {
            int _type = ENDIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:95:7: ( 'endif' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:95:9: 'endif'
            {
            match("endif"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ENDIF"

    // $ANTLR start "ENDFOR"
    public final void mENDFOR() throws RecognitionException {
        try {
            int _type = ENDFOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:96:8: ( 'endfor' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:96:10: 'endfor'
            {
            match("endfor"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ENDFOR"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:97:5: ( 'for' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:97:7: 'for'
            {
            match("for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "DECREASING"
    public final void mDECREASING() throws RecognitionException {
        try {
            int _type = DECREASING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:98:12: ( 'dec' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:98:14: 'dec'
            {
            match("dec"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DECREASING"

    // $ANTLR start "INCREASING"
    public final void mINCREASING() throws RecognitionException {
        try {
            int _type = INCREASING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:99:12: ( 'inc' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:99:14: 'inc'
            {
            match("inc"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INCREASING"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:100:7: ( 'while' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:100:9: 'while'
            {
            match("while"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "ENDWHILE"
    public final void mENDWHILE() throws RecognitionException {
        try {
            int _type = ENDWHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:101:10: ( 'endwhile' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:101:12: 'endwhile'
            {
            match("endwhile"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ENDWHILE"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:102:12: ( 'a' .. 'z' ( 'a' .. 'z' | DIGITS )* )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:102:14: 'a' .. 'z' ( 'a' .. 'z' | DIGITS )*
            {
            matchRange('a','z'); 

            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:102:23: ( 'a' .. 'z' | DIGITS )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= 'a' && LA5_0 <= 'z')) ) {
                    alt5=1;
                }
                else if ( (LA5_0=='-'||(LA5_0 >= '0' && LA5_0 <= '9')) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:102:24: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); 

            	    }
            	    break;
            	case 2 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:102:35: DIGITS
            	    {
            	    mDIGITS(); 


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:103:8: ( '\"' ( CHARACTER | DIGITS | ' ' )* '\"' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:103:10: '\"' ( CHARACTER | DIGITS | ' ' )* '\"'
            {
            match('\"'); 

            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:103:14: ( CHARACTER | DIGITS | ' ' )*
            loop6:
            do {
                int alt6=4;
                switch ( input.LA(1) ) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt6=1;
                    }
                    break;
                case '-':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                    alt6=2;
                    }
                    break;
                case ' ':
                    {
                    alt6=3;
                    }
                    break;

                }

                switch (alt6) {
            	case 1 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:103:15: CHARACTER
            	    {
            	    mCHARACTER(); 


            	    }
            	    break;
            	case 2 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:103:25: DIGITS
            	    {
            	    mDIGITS(); 


            	    }
            	    break;
            	case 3 :
            	    // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:103:32: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "CHARACTER"
    public final void mCHARACTER() throws RecognitionException {
        try {
            int _type = CHARACTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:104:11: ( 'a' .. 'z' | 'A' .. 'Z' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHARACTER"

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:107:11: ( ';' )
            // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:107:13: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SEMICOLON"

    public void mTokens() throws RecognitionException {
        // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:8: ( WHITESPACE | DIGITS | PLUS | MINUS | MULT | DIV | NULLITY | INFINITY | NEGATIVEINFINITY | VARTRANSREAL | VARINT | VARSTRING | LESSTHAN | GREATERTHAN | LESSTHANEQUAL | GREATERTHANEQUAL | EQUALTO | DECLARE | ASSIGN | PRINT | IF | ELSEIF | ELSE | ENDIF | ENDFOR | FOR | DECREASING | INCREASING | WHILE | ENDWHILE | IDENTIFIER | STRING | CHARACTER | SEMICOLON )
        int alt7=34;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:10: WHITESPACE
                {
                mWHITESPACE(); 


                }
                break;
            case 2 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:21: DIGITS
                {
                mDIGITS(); 


                }
                break;
            case 3 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:28: PLUS
                {
                mPLUS(); 


                }
                break;
            case 4 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:33: MINUS
                {
                mMINUS(); 


                }
                break;
            case 5 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:39: MULT
                {
                mMULT(); 


                }
                break;
            case 6 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:44: DIV
                {
                mDIV(); 


                }
                break;
            case 7 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:48: NULLITY
                {
                mNULLITY(); 


                }
                break;
            case 8 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:56: INFINITY
                {
                mINFINITY(); 


                }
                break;
            case 9 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:65: NEGATIVEINFINITY
                {
                mNEGATIVEINFINITY(); 


                }
                break;
            case 10 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:82: VARTRANSREAL
                {
                mVARTRANSREAL(); 


                }
                break;
            case 11 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:95: VARINT
                {
                mVARINT(); 


                }
                break;
            case 12 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:102: VARSTRING
                {
                mVARSTRING(); 


                }
                break;
            case 13 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:112: LESSTHAN
                {
                mLESSTHAN(); 


                }
                break;
            case 14 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:121: GREATERTHAN
                {
                mGREATERTHAN(); 


                }
                break;
            case 15 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:133: LESSTHANEQUAL
                {
                mLESSTHANEQUAL(); 


                }
                break;
            case 16 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:147: GREATERTHANEQUAL
                {
                mGREATERTHANEQUAL(); 


                }
                break;
            case 17 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:164: EQUALTO
                {
                mEQUALTO(); 


                }
                break;
            case 18 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:172: DECLARE
                {
                mDECLARE(); 


                }
                break;
            case 19 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:180: ASSIGN
                {
                mASSIGN(); 


                }
                break;
            case 20 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:187: PRINT
                {
                mPRINT(); 


                }
                break;
            case 21 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:193: IF
                {
                mIF(); 


                }
                break;
            case 22 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:196: ELSEIF
                {
                mELSEIF(); 


                }
                break;
            case 23 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:203: ELSE
                {
                mELSE(); 


                }
                break;
            case 24 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:208: ENDIF
                {
                mENDIF(); 


                }
                break;
            case 25 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:214: ENDFOR
                {
                mENDFOR(); 


                }
                break;
            case 26 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:221: FOR
                {
                mFOR(); 


                }
                break;
            case 27 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:225: DECREASING
                {
                mDECREASING(); 


                }
                break;
            case 28 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:236: INCREASING
                {
                mINCREASING(); 


                }
                break;
            case 29 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:247: WHILE
                {
                mWHILE(); 


                }
                break;
            case 30 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:253: ENDWHILE
                {
                mENDWHILE(); 


                }
                break;
            case 31 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:262: IDENTIFIER
                {
                mIDENTIFIER(); 


                }
                break;
            case 32 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:273: STRING
                {
                mSTRING(); 


                }
                break;
            case 33 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:280: CHARACTER
                {
                mCHARACTER(); 


                }
                break;
            case 34 :
                // C:\\Users\\Alex\\Dropbox\\EclipseWorkspace\\esper-compiler\\src\\compiler\\Esper.g:1:290: SEMICOLON
                {
                mSEMICOLON(); 


                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\2\uffff\1\27\4\uffff\13\31\6\uffff\1\31\1\uffff\1\31\1\54\3\31"+
        "\1\61\1\63\1\65\7\31\1\75\1\76\1\77\1\uffff\2\31\1\102\1\103\1\uffff"+
        "\1\104\1\uffff\1\31\1\uffff\2\31\1\113\1\31\1\115\2\31\3\uffff\2"+
        "\31\3\uffff\1\31\1\124\4\31\1\uffff\1\31\1\uffff\4\31\1\65\1\31"+
        "\1\uffff\1\137\3\31\1\143\1\144\2\31\1\147\1\150\1\uffff\1\151\2"+
        "\31\2\uffff\1\154\1\31\3\uffff\1\31\1\157\1\uffff\1\31\1\161\1\uffff"+
        "\1\162\2\uffff";
    static final String DFA7_eofS =
        "\163\uffff";
    static final String DFA7_minS =
        "\1\11\1\uffff\1\55\4\uffff\1\165\1\146\1\162\1\145\2\164\1\154\1"+
        "\145\1\162\1\157\1\150\6\uffff\1\154\1\uffff\1\143\1\55\1\141\1"+
        "\162\1\164\3\55\1\163\1\144\1\143\1\151\1\162\1\151\1\154\3\55\1"+
        "\uffff\1\156\1\151\2\55\1\uffff\1\55\1\uffff\1\141\1\uffff\1\145"+
        "\1\146\1\55\1\156\1\55\1\154\1\151\3\uffff\1\163\1\156\3\uffff\1"+
        "\154\1\55\1\146\1\157\1\150\1\141\1\uffff\1\164\1\uffff\1\145\1"+
        "\164\1\162\1\147\1\55\1\146\1\uffff\1\55\1\162\1\151\1\162\2\55"+
        "\1\171\1\145\2\55\1\uffff\1\55\1\154\1\145\2\uffff\1\55\1\141\3"+
        "\uffff\1\145\1\55\1\uffff\1\154\1\55\1\uffff\1\55\2\uffff";
    static final String DFA7_maxS =
        "\1\172\1\uffff\1\151\4\uffff\1\165\1\156\1\162\3\164\1\161\1\145"+
        "\1\162\1\157\1\150\6\uffff\1\154\1\uffff\1\164\1\172\1\141\1\162"+
        "\1\164\3\172\1\163\1\144\1\143\1\151\1\162\1\151\1\154\3\172\1\uffff"+
        "\1\156\1\151\2\172\1\uffff\1\172\1\uffff\1\141\1\uffff\1\145\1\167"+
        "\1\172\1\156\1\172\1\154\1\151\3\uffff\1\163\1\156\3\uffff\1\154"+
        "\1\172\1\146\1\157\1\150\1\141\1\uffff\1\164\1\uffff\1\145\1\164"+
        "\1\162\1\147\1\172\1\146\1\uffff\1\172\1\162\1\151\1\162\2\172\1"+
        "\171\1\145\2\172\1\uffff\1\172\1\154\1\145\2\uffff\1\172\1\141\3"+
        "\uffff\1\145\1\172\1\uffff\1\154\1\172\1\uffff\1\172\2\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\1\3\1\5\1\6\13\uffff\1\37\1\40\1\41\1\42"+
        "\1\11\1\4\1\uffff\1\37\22\uffff\1\25\4\uffff\1\15\1\uffff\1\16\1"+
        "\uffff\1\21\7\uffff\1\10\1\13\1\34\2\uffff\1\23\1\17\1\20\6\uffff"+
        "\1\33\1\uffff\1\32\6\uffff\1\27\12\uffff\1\30\3\uffff\1\24\1\35"+
        "\2\uffff\1\14\1\26\1\31\2\uffff\1\7\2\uffff\1\22\1\uffff\1\36\1"+
        "\12";
    static final String DFA7_specialS =
        "\163\uffff}>";
    static final String[] DFA7_transitionS = {
            "\2\1\1\uffff\2\1\22\uffff\1\1\1\uffff\1\23\7\uffff\1\5\1\4\1"+
            "\uffff\1\2\1\uffff\1\6\12\3\1\uffff\1\25\5\uffff\32\24\6\uffff"+
            "\3\22\1\16\1\15\1\20\1\14\1\22\1\10\2\22\1\13\1\22\1\7\1\22"+
            "\1\17\2\22\1\12\1\11\2\22\1\21\3\22",
            "",
            "\1\3\2\uffff\12\3\57\uffff\1\26",
            "",
            "",
            "",
            "",
            "\1\30",
            "\1\33\7\uffff\1\32",
            "\1\34",
            "\1\36\16\uffff\1\35",
            "\1\37",
            "\1\40",
            "\1\42\1\uffff\1\43\2\uffff\1\41",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\50",
            "",
            "\1\53\2\uffff\1\51\15\uffff\1\52",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\31\2\uffff\12\31\47\uffff\4\31\1\60\25\31",
            "\1\31\2\uffff\12\31\47\uffff\4\31\1\62\25\31",
            "\1\31\2\uffff\12\31\47\uffff\24\31\1\64\5\31",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            "\1\100",
            "\1\101",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            "\1\105",
            "",
            "\1\106",
            "\1\110\2\uffff\1\107\15\uffff\1\111",
            "\1\31\2\uffff\12\31\47\uffff\13\31\1\112\16\31",
            "\1\114",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\116",
            "\1\117",
            "",
            "",
            "",
            "\1\120",
            "\1\121",
            "",
            "",
            "",
            "\1\122",
            "\1\31\2\uffff\12\31\47\uffff\10\31\1\123\21\31",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "",
            "\1\131",
            "",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\136",
            "",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\145",
            "\1\146",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\152",
            "\1\153",
            "",
            "",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "\1\155",
            "",
            "",
            "",
            "\1\156",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            "\1\160",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            "\1\31\2\uffff\12\31\47\uffff\32\31",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WHITESPACE | DIGITS | PLUS | MINUS | MULT | DIV | NULLITY | INFINITY | NEGATIVEINFINITY | VARTRANSREAL | VARINT | VARSTRING | LESSTHAN | GREATERTHAN | LESSTHANEQUAL | GREATERTHANEQUAL | EQUALTO | DECLARE | ASSIGN | PRINT | IF | ELSEIF | ELSE | ENDIF | ENDFOR | FOR | DECREASING | INCREASING | WHILE | ENDWHILE | IDENTIFIER | STRING | CHARACTER | SEMICOLON );";
        }
    }
 

}