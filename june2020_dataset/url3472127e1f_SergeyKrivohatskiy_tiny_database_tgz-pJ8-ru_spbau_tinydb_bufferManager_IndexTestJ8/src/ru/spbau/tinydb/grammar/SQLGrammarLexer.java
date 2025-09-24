// Generated from ../src/SQLGrammar.g4 by ANTLR 4.4

    package ru.spbau.tinydb.grammar;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OR=1, AND=2, NOT=3, TRUE=4, FALSE=5, INTEGER_TYPE=6, DOUBLE_TYPE=7, VAR_CHAR_TYPE=8, 
		CREATE=9, UNIQUE=10, INDEX=11, USING=12, BTREE=13, HASH=14, ASC=15, DESC=16, 
		DELETE=17, INSERT=18, INTO=19, VALUES=20, UPDATE=21, SET=22, TABLE=23, 
		SELECT=24, FROM=25, WHERE=26, INNER=27, JOIN=28, ON=29, COMMA=30, ASTERISC=31, 
		LEFT_PARENTHESIS=32, RIGHT_PARENTHESIS=33, SEMICOLON=34, WHITE_SPACE=35, 
		LOWER_CASE=36, UPPER_CASE=37, UNDERLINE=38, DECIMAL_DIGIT=39, SIGN=40, 
		DECIMAL_POINT=41, EQUAL=42, NOT_EQUAL=43, LESS=44, LESS_OR_EQUAL=45, GREATER=46, 
		GREATER_OR_EQUAL=47, QUOTES=48;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "'0'"
	};
	public static final String[] ruleNames = {
		"OR", "AND", "NOT", "TRUE", "FALSE", "INTEGER_TYPE", "DOUBLE_TYPE", "VAR_CHAR_TYPE", 
		"CREATE", "UNIQUE", "INDEX", "USING", "BTREE", "HASH", "ASC", "DESC", 
		"DELETE", "INSERT", "INTO", "VALUES", "UPDATE", "SET", "TABLE", "SELECT", 
		"FROM", "WHERE", "INNER", "JOIN", "ON", "COMMA", "ASTERISC", "LEFT_PARENTHESIS", 
		"RIGHT_PARENTHESIS", "SEMICOLON", "WHITE_SPACE", "LOWER_CASE", "UPPER_CASE", 
		"UNDERLINE", "DECIMAL_DIGIT", "SIGN", "DECIMAL_POINT", "EQUAL", "NOT_EQUAL", 
		"LESS", "LESS_OR_EQUAL", "GREATER", "GREATER_OR_EQUAL", "QUOTES"
	};


	public SQLGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SQLGrammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 34: WHITE_SPACE_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WHITE_SPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:  skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\62\u0133\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3"+
		"\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3"+
		"\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\""+
		"\3\"\3#\3#\3$\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,"+
		"\3,\3-\3-\3.\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\2\2\62\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G"+
		"%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62\3\2\4\4\2\13\f\"\"\4\2--//\u0132\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2"+
		"\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U"+
		"\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2"+
		"\2\2\3c\3\2\2\2\5f\3\2\2\2\7j\3\2\2\2\tn\3\2\2\2\13s\3\2\2\2\ry\3\2\2"+
		"\2\17\u0081\3\2\2\2\21\u0088\3\2\2\2\23\u0090\3\2\2\2\25\u0097\3\2\2\2"+
		"\27\u009e\3\2\2\2\31\u00a4\3\2\2\2\33\u00aa\3\2\2\2\35\u00b0\3\2\2\2\37"+
		"\u00b5\3\2\2\2!\u00b9\3\2\2\2#\u00be\3\2\2\2%\u00c5\3\2\2\2\'\u00cc\3"+
		"\2\2\2)\u00d1\3\2\2\2+\u00d8\3\2\2\2-\u00df\3\2\2\2/\u00e3\3\2\2\2\61"+
		"\u00e9\3\2\2\2\63\u00f0\3\2\2\2\65\u00f5\3\2\2\2\67\u00fb\3\2\2\29\u0101"+
		"\3\2\2\2;\u0106\3\2\2\2=\u0109\3\2\2\2?\u010b\3\2\2\2A\u010d\3\2\2\2C"+
		"\u010f\3\2\2\2E\u0111\3\2\2\2G\u0113\3\2\2\2I\u0116\3\2\2\2K\u0118\3\2"+
		"\2\2M\u011a\3\2\2\2O\u011c\3\2\2\2Q\u011e\3\2\2\2S\u0120\3\2\2\2U\u0122"+
		"\3\2\2\2W\u0124\3\2\2\2Y\u0127\3\2\2\2[\u0129\3\2\2\2]\u012c\3\2\2\2_"+
		"\u012e\3\2\2\2a\u0131\3\2\2\2cd\7Q\2\2de\7T\2\2e\4\3\2\2\2fg\7C\2\2gh"+
		"\7P\2\2hi\7F\2\2i\6\3\2\2\2jk\7P\2\2kl\7Q\2\2lm\7V\2\2m\b\3\2\2\2no\7"+
		"V\2\2op\7T\2\2pq\7W\2\2qr\7G\2\2r\n\3\2\2\2st\7H\2\2tu\7C\2\2uv\7N\2\2"+
		"vw\7U\2\2wx\7G\2\2x\f\3\2\2\2yz\7K\2\2z{\7P\2\2{|\7V\2\2|}\7G\2\2}~\7"+
		"I\2\2~\177\7G\2\2\177\u0080\7T\2\2\u0080\16\3\2\2\2\u0081\u0082\7F\2\2"+
		"\u0082\u0083\7Q\2\2\u0083\u0084\7W\2\2\u0084\u0085\7D\2\2\u0085\u0086"+
		"\7N\2\2\u0086\u0087\7G\2\2\u0087\20\3\2\2\2\u0088\u0089\7X\2\2\u0089\u008a"+
		"\7C\2\2\u008a\u008b\7T\2\2\u008b\u008c\7E\2\2\u008c\u008d\7J\2\2\u008d"+
		"\u008e\7C\2\2\u008e\u008f\7T\2\2\u008f\22\3\2\2\2\u0090\u0091\7E\2\2\u0091"+
		"\u0092\7T\2\2\u0092\u0093\7G\2\2\u0093\u0094\7C\2\2\u0094\u0095\7V\2\2"+
		"\u0095\u0096\7G\2\2\u0096\24\3\2\2\2\u0097\u0098\7W\2\2\u0098\u0099\7"+
		"P\2\2\u0099\u009a\7K\2\2\u009a\u009b\7S\2\2\u009b\u009c\7W\2\2\u009c\u009d"+
		"\7G\2\2\u009d\26\3\2\2\2\u009e\u009f\7K\2\2\u009f\u00a0\7P\2\2\u00a0\u00a1"+
		"\7F\2\2\u00a1\u00a2\7G\2\2\u00a2\u00a3\7Z\2\2\u00a3\30\3\2\2\2\u00a4\u00a5"+
		"\7W\2\2\u00a5\u00a6\7U\2\2\u00a6\u00a7\7K\2\2\u00a7\u00a8\7P\2\2\u00a8"+
		"\u00a9\7I\2\2\u00a9\32\3\2\2\2\u00aa\u00ab\7D\2\2\u00ab\u00ac\7V\2\2\u00ac"+
		"\u00ad\7T\2\2\u00ad\u00ae\7G\2\2\u00ae\u00af\7G\2\2\u00af\34\3\2\2\2\u00b0"+
		"\u00b1\7J\2\2\u00b1\u00b2\7C\2\2\u00b2\u00b3\7U\2\2\u00b3\u00b4\7J\2\2"+
		"\u00b4\36\3\2\2\2\u00b5\u00b6\7C\2\2\u00b6\u00b7\7U\2\2\u00b7\u00b8\7"+
		"E\2\2\u00b8 \3\2\2\2\u00b9\u00ba\7F\2\2\u00ba\u00bb\7G\2\2\u00bb\u00bc"+
		"\7U\2\2\u00bc\u00bd\7E\2\2\u00bd\"\3\2\2\2\u00be\u00bf\7F\2\2\u00bf\u00c0"+
		"\7G\2\2\u00c0\u00c1\7N\2\2\u00c1\u00c2\7G\2\2\u00c2\u00c3\7V\2\2\u00c3"+
		"\u00c4\7G\2\2\u00c4$\3\2\2\2\u00c5\u00c6\7K\2\2\u00c6\u00c7\7P\2\2\u00c7"+
		"\u00c8\7U\2\2\u00c8\u00c9\7G\2\2\u00c9\u00ca\7T\2\2\u00ca\u00cb\7V\2\2"+
		"\u00cb&\3\2\2\2\u00cc\u00cd\7K\2\2\u00cd\u00ce\7P\2\2\u00ce\u00cf\7V\2"+
		"\2\u00cf\u00d0\7Q\2\2\u00d0(\3\2\2\2\u00d1\u00d2\7X\2\2\u00d2\u00d3\7"+
		"C\2\2\u00d3\u00d4\7N\2\2\u00d4\u00d5\7W\2\2\u00d5\u00d6\7G\2\2\u00d6\u00d7"+
		"\7U\2\2\u00d7*\3\2\2\2\u00d8\u00d9\7W\2\2\u00d9\u00da\7R\2\2\u00da\u00db"+
		"\7F\2\2\u00db\u00dc\7C\2\2\u00dc\u00dd\7V\2\2\u00dd\u00de\7G\2\2\u00de"+
		",\3\2\2\2\u00df\u00e0\7U\2\2\u00e0\u00e1\7G\2\2\u00e1\u00e2\7V\2\2\u00e2"+
		".\3\2\2\2\u00e3\u00e4\7V\2\2\u00e4\u00e5\7C\2\2\u00e5\u00e6\7D\2\2\u00e6"+
		"\u00e7\7N\2\2\u00e7\u00e8\7G\2\2\u00e8\60\3\2\2\2\u00e9\u00ea\7U\2\2\u00ea"+
		"\u00eb\7G\2\2\u00eb\u00ec\7N\2\2\u00ec\u00ed\7G\2\2\u00ed\u00ee\7E\2\2"+
		"\u00ee\u00ef\7V\2\2\u00ef\62\3\2\2\2\u00f0\u00f1\7H\2\2\u00f1\u00f2\7"+
		"T\2\2\u00f2\u00f3\7Q\2\2\u00f3\u00f4\7O\2\2\u00f4\64\3\2\2\2\u00f5\u00f6"+
		"\7Y\2\2\u00f6\u00f7\7J\2\2\u00f7\u00f8\7G\2\2\u00f8\u00f9\7T\2\2\u00f9"+
		"\u00fa\7G\2\2\u00fa\66\3\2\2\2\u00fb\u00fc\7K\2\2\u00fc\u00fd\7P\2\2\u00fd"+
		"\u00fe\7P\2\2\u00fe\u00ff\7G\2\2\u00ff\u0100\7T\2\2\u01008\3\2\2\2\u0101"+
		"\u0102\7L\2\2\u0102\u0103\7Q\2\2\u0103\u0104\7K\2\2\u0104\u0105\7P\2\2"+
		"\u0105:\3\2\2\2\u0106\u0107\7Q\2\2\u0107\u0108\7P\2\2\u0108<\3\2\2\2\u0109"+
		"\u010a\7.\2\2\u010a>\3\2\2\2\u010b\u010c\7,\2\2\u010c@\3\2\2\2\u010d\u010e"+
		"\7*\2\2\u010eB\3\2\2\2\u010f\u0110\7+\2\2\u0110D\3\2\2\2\u0111\u0112\7"+
		"=\2\2\u0112F\3\2\2\2\u0113\u0114\t\2\2\2\u0114\u0115\b$\2\2\u0115H\3\2"+
		"\2\2\u0116\u0117\4c|\2\u0117J\3\2\2\2\u0118\u0119\4C\\\2\u0119L\3\2\2"+
		"\2\u011a\u011b\7a\2\2\u011bN\3\2\2\2\u011c\u011d\4\62;\2\u011dP\3\2\2"+
		"\2\u011e\u011f\t\3\2\2\u011fR\3\2\2\2\u0120\u0121\7\60\2\2\u0121T\3\2"+
		"\2\2\u0122\u0123\7?\2\2\u0123V\3\2\2\2\u0124\u0125\7#\2\2\u0125\u0126"+
		"\7?\2\2\u0126X\3\2\2\2\u0127\u0128\7>\2\2\u0128Z\3\2\2\2\u0129\u012a\7"+
		">\2\2\u012a\u012b\7?\2\2\u012b\\\3\2\2\2\u012c\u012d\7@\2\2\u012d^\3\2"+
		"\2\2\u012e\u012f\7@\2\2\u012f\u0130\7?\2\2\u0130`\3\2\2\2\u0131\u0132"+
		"\7)\2\2\u0132b\3\2\2\2\3\2\3\3$\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}