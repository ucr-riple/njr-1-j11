package expression;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DirkExpLexer extends Lexer {
    public static final int LT=13;
    public static final int RBRACE=35;
    public static final int MOD=21;
    public static final int LETTER=36;
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
    public static final int STRING_LITERAL=29;
    public static final int WS=39;
    public static final int VARIABLE=7;
    public static final int BRACKETEXP=8;
    public static final int COMMA=31;
    public static final int INTEGER_LITERAL=26;
    public static final int IDENTIFIER=30;
    public static final int OR=9;
    public static final int GT=15;
    public static final int PLUS=17;
    public static final int MEMBERACCESS=5;
    public static final int CALL=6;
    public static final int DIV=20;
    public static final int RBRACKET=33;
    public static final int DECIMAL_LITERAL=27;

    // delegates
    // delegators

    public DirkExpLexer() {;} 
    public DirkExpLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public DirkExpLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    @Override
	public String getGrammarFileName() { return "F:\\antlr\\BoncExp.g"; }

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:115:5: ( 'and' )
            // F:\\antlr\\BoncExp.g:115:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:119:5: ( 'or' )
            // F:\\antlr\\BoncExp.g:119:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:123:5: ( 'not' )
            // F:\\antlr\\BoncExp.g:123:9: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:127:5: ( ',' )
            // F:\\antlr\\BoncExp.g:127:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:131:5: ( '+' )
            // F:\\antlr\\BoncExp.g:131:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:135:5: ( '-' )
            // F:\\antlr\\BoncExp.g:135:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MUL"
    public final void mMUL() throws RecognitionException {
        try {
            int _type = MUL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:139:5: ( '*' )
            // F:\\antlr\\BoncExp.g:139:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MUL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:143:5: ( '/' )
            // F:\\antlr\\BoncExp.g:143:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:147:5: ( '%' )
            // F:\\antlr\\BoncExp.g:147:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "POWER"
    public final void mPOWER() throws RecognitionException {
        try {
            int _type = POWER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:151:5: ( '^' )
            // F:\\antlr\\BoncExp.g:151:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "POWER"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:154:9: ( '=' )
            // F:\\antlr\\BoncExp.g:154:13: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "NOTEQUALS"
    public final void mNOTEQUALS() throws RecognitionException {
        try {
            int _type = NOTEQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:157:5: ( '!=' | '<>' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='!') ) {
                alt1=1;
            }
            else if ( (LA1_0=='<') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:157:9: '!='
                    {
                    match("!="); 


                    }
                    break;
                case 2 :
                    // F:\\antlr\\BoncExp.g:157:16: '<>'
                    {
                    match("<>"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTEQUALS"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:159:5: ( '<' )
            // F:\\antlr\\BoncExp.g:159:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "LTEQ"
    public final void mLTEQ() throws RecognitionException {
        try {
            int _type = LTEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:160:9: ( '<=' )
            // F:\\antlr\\BoncExp.g:160:13: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LTEQ"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:161:5: ( '>' )
            // F:\\antlr\\BoncExp.g:161:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "GTEQ"
    public final void mGTEQ() throws RecognitionException {
        try {
            int _type = GTEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:162:9: ( '>=' )
            // F:\\antlr\\BoncExp.g:162:13: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GTEQ"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:165:5: ( '(' )
            // F:\\antlr\\BoncExp.g:165:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:168:9: ( ')' )
            // F:\\antlr\\BoncExp.g:168:13: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:171:8: ( '{' )
            // F:\\antlr\\BoncExp.g:171:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:174:8: ( '}' )
            // F:\\antlr\\BoncExp.g:174:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LBRACKET"
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:180:5: ( '[' )
            // F:\\antlr\\BoncExp.g:180:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACKET"

    // $ANTLR start "RBRACKET"
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:184:5: ( ']' )
            // F:\\antlr\\BoncExp.g:184:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACKET"

    // $ANTLR start "DATETIME_LITERAL"
    public final void mDATETIME_LITERAL() throws RecognitionException {
        try {
            int _type = DATETIME_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:188:5: ( '\\'' STRING_LITERAL '\\'' )
            // F:\\antlr\\BoncExp.g:188:9: '\\'' STRING_LITERAL '\\''
            {
            match('\''); 
            mSTRING_LITERAL(); 
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATETIME_LITERAL"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:192:5: ( '\"' (~ ( '\"' | '[' | ']' ) )* '\"' )
            // F:\\antlr\\BoncExp.g:192:9: '\"' (~ ( '\"' | '[' | ']' ) )* '\"'
            {
            match('\"'); 
            // F:\\antlr\\BoncExp.g:192:13: (~ ( '\"' | '[' | ']' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='!')||(LA2_0>='#' && LA2_0<='Z')||LA2_0=='\\'||(LA2_0>='^' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:192:14: ~ ( '\"' | '[' | ']' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='Z')||input.LA(1)=='\\'||(input.LA(1)>='^' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:196:5: ( LETTER ( LETTER | Digit )* )
            // F:\\antlr\\BoncExp.g:196:9: LETTER ( LETTER | Digit )*
            {
            mLETTER(); 
            // F:\\antlr\\BoncExp.g:196:16: ( LETTER | Digit )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // F:\\antlr\\BoncExp.g:200:5: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // F:\\antlr\\BoncExp.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "DECIMAL_LITERAL"
    public final void mDECIMAL_LITERAL() throws RecognitionException {
        try {
            int _type = DECIMAL_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:204:5: ( ( INTEGER_LITERAL )? '.' ( Digit )* ( Exponent )? )
            // F:\\antlr\\BoncExp.g:204:9: ( INTEGER_LITERAL )? '.' ( Digit )* ( Exponent )?
            {
            // F:\\antlr\\BoncExp.g:204:9: ( INTEGER_LITERAL )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:204:10: INTEGER_LITERAL
                    {
                    mINTEGER_LITERAL(); 

                    }
                    break;

            }

            match('.'); 
            // F:\\antlr\\BoncExp.g:204:32: ( Digit )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:204:32: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // F:\\antlr\\BoncExp.g:204:39: ( Exponent )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='E'||LA6_0=='e') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // F:\\antlr\\BoncExp.g:204:39: Exponent
                    {
                    mExponent(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DECIMAL_LITERAL"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // F:\\antlr\\BoncExp.g:208:5: ( ( 'e' | 'E' ) INTEGER_LITERAL )
            // F:\\antlr\\BoncExp.g:208:9: ( 'e' | 'E' ) INTEGER_LITERAL
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            mINTEGER_LITERAL(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "INTEGER_LITERAL"
    public final void mINTEGER_LITERAL() throws RecognitionException {
        try {
            int _type = INTEGER_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:212:5: ( ( Digit )+ )
            // F:\\antlr\\BoncExp.g:212:9: ( Digit )+
            {
            // F:\\antlr\\BoncExp.g:212:9: ( Digit )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // F:\\antlr\\BoncExp.g:212:9: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER_LITERAL"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // F:\\antlr\\BoncExp.g:216:5: ( '0' .. '9' )
            // F:\\antlr\\BoncExp.g:216:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Digit"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\antlr\\BoncExp.g:221:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // F:\\antlr\\BoncExp.g:221:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    @Override
	public void mTokens() throws RecognitionException {
        // F:\\antlr\\BoncExp.g:1:8: ( AND | OR | NOT | COMMA | PLUS | MINUS | MUL | DIV | MOD | POWER | EQUALS | NOTEQUALS | LT | LTEQ | GT | GTEQ | LPAREN | RPAREN | LBRACE | RBRACE | LBRACKET | RBRACKET | DATETIME_LITERAL | STRING_LITERAL | IDENTIFIER | DECIMAL_LITERAL | INTEGER_LITERAL | WS )
        int alt8=28;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // F:\\antlr\\BoncExp.g:1:10: AND
                {
                mAND(); 

                }
                break;
            case 2 :
                // F:\\antlr\\BoncExp.g:1:14: OR
                {
                mOR(); 

                }
                break;
            case 3 :
                // F:\\antlr\\BoncExp.g:1:17: NOT
                {
                mNOT(); 

                }
                break;
            case 4 :
                // F:\\antlr\\BoncExp.g:1:21: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 5 :
                // F:\\antlr\\BoncExp.g:1:27: PLUS
                {
                mPLUS(); 

                }
                break;
            case 6 :
                // F:\\antlr\\BoncExp.g:1:32: MINUS
                {
                mMINUS(); 

                }
                break;
            case 7 :
                // F:\\antlr\\BoncExp.g:1:38: MUL
                {
                mMUL(); 

                }
                break;
            case 8 :
                // F:\\antlr\\BoncExp.g:1:42: DIV
                {
                mDIV(); 

                }
                break;
            case 9 :
                // F:\\antlr\\BoncExp.g:1:46: MOD
                {
                mMOD(); 

                }
                break;
            case 10 :
                // F:\\antlr\\BoncExp.g:1:50: POWER
                {
                mPOWER(); 

                }
                break;
            case 11 :
                // F:\\antlr\\BoncExp.g:1:56: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 12 :
                // F:\\antlr\\BoncExp.g:1:63: NOTEQUALS
                {
                mNOTEQUALS(); 

                }
                break;
            case 13 :
                // F:\\antlr\\BoncExp.g:1:73: LT
                {
                mLT(); 

                }
                break;
            case 14 :
                // F:\\antlr\\BoncExp.g:1:76: LTEQ
                {
                mLTEQ(); 

                }
                break;
            case 15 :
                // F:\\antlr\\BoncExp.g:1:81: GT
                {
                mGT(); 

                }
                break;
            case 16 :
                // F:\\antlr\\BoncExp.g:1:84: GTEQ
                {
                mGTEQ(); 

                }
                break;
            case 17 :
                // F:\\antlr\\BoncExp.g:1:89: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 18 :
                // F:\\antlr\\BoncExp.g:1:96: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 19 :
                // F:\\antlr\\BoncExp.g:1:103: LBRACE
                {
                mLBRACE(); 

                }
                break;
            case 20 :
                // F:\\antlr\\BoncExp.g:1:110: RBRACE
                {
                mRBRACE(); 

                }
                break;
            case 21 :
                // F:\\antlr\\BoncExp.g:1:117: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 22 :
                // F:\\antlr\\BoncExp.g:1:126: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 23 :
                // F:\\antlr\\BoncExp.g:1:135: DATETIME_LITERAL
                {
                mDATETIME_LITERAL(); 

                }
                break;
            case 24 :
                // F:\\antlr\\BoncExp.g:1:152: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 25 :
                // F:\\antlr\\BoncExp.g:1:167: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 26 :
                // F:\\antlr\\BoncExp.g:1:178: DECIMAL_LITERAL
                {
                mDECIMAL_LITERAL(); 

                }
                break;
            case 27 :
                // F:\\antlr\\BoncExp.g:1:194: INTEGER_LITERAL
                {
                mINTEGER_LITERAL(); 

                }
                break;
            case 28 :
                // F:\\antlr\\BoncExp.g:1:210: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\1\uffff\3\27\11\uffff\1\37\1\41\11\uffff\1\42\2\uffff\1\27\1\44"+
        "\1\27\5\uffff\1\46\1\uffff\1\47\2\uffff";
    static final String DFA8_eofS =
        "\50\uffff";
    static final String DFA8_minS =
        "\1\11\1\156\1\162\1\157\11\uffff\2\75\11\uffff\1\56\2\uffff\1\144"+
        "\1\60\1\164\5\uffff\1\60\1\uffff\1\60\2\uffff";
    static final String DFA8_maxS =
        "\1\175\1\156\1\162\1\157\11\uffff\1\76\1\75\11\uffff\1\71\2\uffff"+
        "\1\144\1\172\1\164\5\uffff\1\172\1\uffff\1\172\2\uffff";
    static final String DFA8_acceptS =
        "\4\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\2\uffff\1\21"+
        "\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\uffff\1\32\1\34\3\uffff"+
        "\1\16\1\15\1\20\1\17\1\33\1\uffff\1\2\1\uffff\1\1\1\3";
    static final String DFA8_specialS =
        "\50\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\32\1\uffff\2\32\22\uffff\1\32\1\14\1\26\2\uffff\1\11\1\uffff"+
            "\1\25\1\17\1\20\1\7\1\5\1\4\1\6\1\31\1\10\12\30\2\uffff\1\15"+
            "\1\13\1\16\2\uffff\32\27\1\23\1\uffff\1\24\1\12\1\27\1\uffff"+
            "\1\1\14\27\1\3\1\2\13\27\1\21\1\uffff\1\22",
            "\1\33",
            "\1\34",
            "\1\35",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\36\1\14",
            "\1\40",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\31\1\uffff\12\30",
            "",
            "",
            "\1\43",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\45",
            "",
            "",
            "",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        @Override
		public String getDescription() {
            return "1:1: Tokens : ( AND | OR | NOT | COMMA | PLUS | MINUS | MUL | DIV | MOD | POWER | EQUALS | NOTEQUALS | LT | LTEQ | GT | GTEQ | LPAREN | RPAREN | LBRACE | RBRACE | LBRACKET | RBRACKET | DATETIME_LITERAL | STRING_LITERAL | IDENTIFIER | DECIMAL_LITERAL | INTEGER_LITERAL | WS );";
        }
    }
 

}