// Generated from ../src/SQLGrammar.g4 by ANTLR 4.4

    package ru.spbau.tinydb.grammar;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import ru.spbau.tinydb.expressions.AssignmentExpression;
import ru.spbau.tinydb.expressions.bool.*;
import ru.spbau.tinydb.expressions.comparison.*;
import ru.spbau.tinydb.queries.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLGrammarParser extends Parser {
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
			DECIMAL_POINT = 41, EQUAL = 42, NOT_EQUAL = 43, LESS = 44, LESS_OR_EQUAL = 45, GREATER = 46,
			GREATER_OR_EQUAL = 47, QUOTES = 48;
	public static final String[] tokenNames = {
		"<INVALID>", "'OR'", "'AND'", "'NOT'", "'TRUE'", "'FALSE'", "'INTEGER'", 
		"'DOUBLE'", "'VARCHAR'", "'CREATE'", "'UNIQUE'", "'INDEX'", "'USING'", 
		"'BTREE'", "'HASH'", "'ASC'", "'DESC'", "'DELETE'", "'INSERT'", "'INTO'", 
		"'VALUES'", "'UPDATE'", "'SET'", "'TABLE'", "'SELECT'", "'FROM'", "'WHERE'", 
		"'INNER'", "'JOIN'", "'ON'", "','", "'*'", "'('", "')'", "';'", "WHITE_SPACE",
			"LOWER_CASE", "UPPER_CASE", "'_'", "DECIMAL_DIGIT", "SIGN", "'.'", "'='",
			"'!='", "'<'", "'<='", "'>'", "'>='", "'''"
	};
	public static final int
		RULE_script = 0, RULE_query = 1, RULE_createTable = 2, RULE_attribute = 3, 
		RULE_dataType = 4, RULE_varCharType = 5, RULE_value = 6, RULE_integerLiteral = 7, 
		RULE_doubleLiteral = 8, RULE_varCharLiteral = 9, RULE_firstLevelId = 10, 
		RULE_idSuffix = 11, RULE_createIndex = 12, RULE_selectFrom = 13, RULE_insertInto = 14, 
		RULE_updateSet = 15, RULE_assignmentExpression = 16, RULE_deleteFrom = 17, 
		RULE_filter = 18, RULE_whereCondition = 19, RULE_selectionTable = 20, 
		RULE_joinOnExpression = 21, RULE_secondLevelId = 22, RULE_booleanExpression = 23, 
		RULE_orExpression = 24, RULE_andExpression = 25, RULE_booleanFactor = 26, 
		RULE_booleanLiteral = 27, RULE_comparisonExpression = 28, RULE_equalExpression = 29, 
		RULE_notEqualExpression = 30, RULE_lessExpression = 31, RULE_lessOrEqualExpression = 32, 
		RULE_greaterExpression = 33, RULE_greaterOrEqualExpression = 34;
	public static final String[] ruleNames = {
		"script", "query", "createTable", "attribute", "dataType", "varCharType", 
		"value", "integerLiteral", "doubleLiteral", "varCharLiteral", "firstLevelId", 
		"idSuffix", "createIndex", "selectFrom", "insertInto", "updateSet", "assignmentExpression", 
		"deleteFrom", "filter", "whereCondition", "selectionTable", "joinOnExpression", 
		"secondLevelId", "booleanExpression", "orExpression", "andExpression", 
		"booleanFactor", "booleanLiteral", "comparisonExpression", "equalExpression", 
		"notEqualExpression", "lessExpression", "lessOrEqualExpression", "greaterExpression", 
		"greaterOrEqualExpression"
	};

	@Override
	public String getGrammarFileName() { return "SQLGrammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ScriptContext extends ParserRuleContext {
		public List<IQuery> result;
		public QueryContext query;
		public TerminalNode EOF() { return getToken(SQLGrammarParser.EOF, 0); }
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterScript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitScript(this);
		}
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);

		    List<IQuery> result = new ArrayList<>();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(70); ((ScriptContext)_localctx).query = query();

				        result.add(((ScriptContext)_localctx).query.result);
				    
				}
				}
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CREATE) | (1L << DELETE) | (1L << INSERT) | (1L << UPDATE) | (1L << SELECT))) != 0) );
			setState(77); match(EOF);

			        ((ScriptContext)_localctx).result =  Collections.unmodifiableList(result);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public IQuery result;
		public CreateTableContext createTable;
		public CreateIndexContext createIndex;
		public SelectFromContext selectFrom;
		public InsertIntoContext insertInto;
		public UpdateSetContext updateSet;
		public DeleteFromContext deleteFrom;
		public TerminalNode SEMICOLON() { return getToken(SQLGrammarParser.SEMICOLON, 0); }
		public CreateIndexContext createIndex() {
			return getRuleContext(CreateIndexContext.class,0);
		}
		public SelectFromContext selectFrom() {
			return getRuleContext(SelectFromContext.class,0);
		}
		public DeleteFromContext deleteFrom() {
			return getRuleContext(DeleteFromContext.class,0);
		}
		public CreateTableContext createTable() {
			return getRuleContext(CreateTableContext.class,0);
		}
		public InsertIntoContext insertInto() {
			return getRuleContext(InsertIntoContext.class,0);
		}
		public UpdateSetContext updateSet() {
			return getRuleContext(UpdateSetContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				{
				setState(80); ((QueryContext)_localctx).createTable = createTable();

				        ((QueryContext)_localctx).result =  ((QueryContext)_localctx).createTable.result;
				    
				}
				}
				break;
			case 2:
				{
				{
				setState(83); ((QueryContext)_localctx).createIndex = createIndex();

				        ((QueryContext)_localctx).result =  ((QueryContext)_localctx).createIndex.result;
				    
				}
				}
				break;
			case 3:
				{
				{
				setState(86); ((QueryContext)_localctx).selectFrom = selectFrom();

				        ((QueryContext)_localctx).result =  ((QueryContext)_localctx).selectFrom.result;
				    
				}
				}
				break;
			case 4:
				{
				{
				setState(89); ((QueryContext)_localctx).insertInto = insertInto();

				        ((QueryContext)_localctx).result =  ((QueryContext)_localctx).insertInto.result;
				    
				}
				}
				break;
			case 5:
				{
				{
				setState(92); ((QueryContext)_localctx).updateSet = updateSet();

				        ((QueryContext)_localctx).result =  ((QueryContext)_localctx).updateSet.result;
				    
				}
				}
				break;
			case 6:
				{
				{
				setState(95); ((QueryContext)_localctx).deleteFrom = deleteFrom();

				        ((QueryContext)_localctx).result =  ((QueryContext)_localctx).deleteFrom.result;
				    
				}
				}
				break;
			}
			setState(100); match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateTableContext extends ParserRuleContext {
		public CreateTableQuery result;
		public FirstLevelIdContext firstLevelId;
		public AttributeContext attribute;
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLGrammarParser.COMMA); }
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public TerminalNode CREATE() { return getToken(SQLGrammarParser.CREATE, 0); }
		public TerminalNode LEFT_PARENTHESIS() { return getToken(SQLGrammarParser.LEFT_PARENTHESIS, 0); }
		public TerminalNode TABLE() { return getToken(SQLGrammarParser.TABLE, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLGrammarParser.COMMA, i);
		}
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(SQLGrammarParser.RIGHT_PARENTHESIS, 0); }
		public CreateTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitCreateTable(this);
		}
	}

	public final CreateTableContext createTable() throws RecognitionException {
		CreateTableContext _localctx = new CreateTableContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_createTable);

		    List<Attribute> attributes = new ArrayList<>();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102); match(CREATE);
			setState(103); match(TABLE);
			setState(104); ((CreateTableContext)_localctx).firstLevelId = firstLevelId();
			setState(105); match(LEFT_PARENTHESIS);
			setState(106); ((CreateTableContext)_localctx).attribute = attribute();

			        attributes.add(((CreateTableContext)_localctx).attribute.result);
			    
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(108); match(COMMA);
				{
				setState(109); ((CreateTableContext)_localctx).attribute = attribute();

				        attributes.add(((CreateTableContext)_localctx).attribute.result);
				    
				}
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117); match(RIGHT_PARENTHESIS);

			        ((CreateTableContext)_localctx).result =  new CreateTableQuery(((CreateTableContext)_localctx).firstLevelId.result, attributes);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public Attribute result;
		public FirstLevelIdContext firstLevelId;
		public DataTypeContext dataType;
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitAttribute(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120); ((AttributeContext)_localctx).firstLevelId = firstLevelId();
			setState(121); ((AttributeContext)_localctx).dataType = dataType();

			        ((AttributeContext)_localctx).result =  new Attribute(((AttributeContext)_localctx).firstLevelId.result, ((AttributeContext)_localctx).dataType.result);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataTypeContext extends ParserRuleContext {
		public Attribute.DataType result;
		public VarCharTypeContext varCharType;
		public VarCharTypeContext varCharType() {
			return getRuleContext(VarCharTypeContext.class,0);
		}
		public TerminalNode DOUBLE_TYPE() { return getToken(SQLGrammarParser.DOUBLE_TYPE, 0); }
		public TerminalNode INTEGER_TYPE() { return getToken(SQLGrammarParser.INTEGER_TYPE, 0); }
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitDataType(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_dataType);
		try {
			setState(131);
			switch (_input.LA(1)) {
			case INTEGER_TYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(124); match(INTEGER_TYPE);

				        ((DataTypeContext)_localctx).result =  Attribute.IntegerType.getInstance();
				    
				}
				break;
			case DOUBLE_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(126); match(DOUBLE_TYPE);

				        ((DataTypeContext)_localctx).result =  Attribute.DoubleType.getInstance();
				    
				}
				break;
			case VAR_CHAR_TYPE:
				enterOuterAlt(_localctx, 3);
				{
				setState(128); ((DataTypeContext)_localctx).varCharType = varCharType();

				        ((DataTypeContext)_localctx).result =  new Attribute.VarcharType(((DataTypeContext)_localctx).varCharType.length);
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarCharTypeContext extends ParserRuleContext {
		public int length;
		public IntegerLiteralContext integerLiteral;
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public TerminalNode VAR_CHAR_TYPE() { return getToken(SQLGrammarParser.VAR_CHAR_TYPE, 0); }
		public TerminalNode LEFT_PARENTHESIS() { return getToken(SQLGrammarParser.LEFT_PARENTHESIS, 0); }
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(SQLGrammarParser.RIGHT_PARENTHESIS, 0); }
		public VarCharTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varCharType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterVarCharType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitVarCharType(this);
		}
	}

	public final VarCharTypeContext varCharType() throws RecognitionException {
		VarCharTypeContext _localctx = new VarCharTypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_varCharType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133); match(VAR_CHAR_TYPE);
			setState(134); match(LEFT_PARENTHESIS);
			setState(135); ((VarCharTypeContext)_localctx).integerLiteral = integerLiteral();
			setState(136); match(RIGHT_PARENTHESIS);

			        ((VarCharTypeContext)_localctx).length =  ((VarCharTypeContext)_localctx).integerLiteral.result;
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public Object result;
		public IntegerLiteralContext integerLiteral;
		public DoubleLiteralContext doubleLiteral;
		public VarCharLiteralContext varCharLiteral;
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public DoubleLiteralContext doubleLiteral() {
			return getRuleContext(DoubleLiteralContext.class,0);
		}
		public VarCharLiteralContext varCharLiteral() {
			return getRuleContext(VarCharLiteralContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_value);
		try {
			setState(148);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(139); ((ValueContext)_localctx).integerLiteral = integerLiteral();

				        ((ValueContext)_localctx).result =  ((ValueContext)_localctx).integerLiteral.result;
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(142); ((ValueContext)_localctx).doubleLiteral = doubleLiteral();

				        ((ValueContext)_localctx).result =  ((ValueContext)_localctx).doubleLiteral.result;
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(145); ((ValueContext)_localctx).varCharLiteral = varCharLiteral();

				        ((ValueContext)_localctx).result =  ((ValueContext)_localctx).varCharLiteral.result;
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerLiteralContext extends ParserRuleContext {
		public Integer result;
		public List<TerminalNode> DECIMAL_DIGIT() { return getTokens(SQLGrammarParser.DECIMAL_DIGIT); }
		public TerminalNode SIGN() { return getToken(SQLGrammarParser.SIGN, 0); }
		public TerminalNode DECIMAL_DIGIT(int i) {
			return getToken(SQLGrammarParser.DECIMAL_DIGIT, i);
		}
		public IntegerLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitIntegerLiteral(this);
		}
	}

	public final IntegerLiteralContext integerLiteral() throws RecognitionException {
		IntegerLiteralContext _localctx = new IntegerLiteralContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			_la = _input.LA(1);
			if (_la==SIGN) {
				{
				setState(150); match(SIGN);
				}
			}

			setState(154); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(153); match(DECIMAL_DIGIT);
				}
				}
				setState(156); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DECIMAL_DIGIT );

			        ((IntegerLiteralContext)_localctx).result =  new Integer(_input.getText(_localctx.start, _input.LT(-1)));
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoubleLiteralContext extends ParserRuleContext {
		public Double result;
		public List<TerminalNode> DECIMAL_DIGIT() { return getTokens(SQLGrammarParser.DECIMAL_DIGIT); }
		public TerminalNode DECIMAL_POINT() { return getToken(SQLGrammarParser.DECIMAL_POINT, 0); }
		public TerminalNode SIGN() { return getToken(SQLGrammarParser.SIGN, 0); }
		public TerminalNode DECIMAL_DIGIT(int i) {
			return getToken(SQLGrammarParser.DECIMAL_DIGIT, i);
		}
		public DoubleLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doubleLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterDoubleLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitDoubleLiteral(this);
		}
	}

	public final DoubleLiteralContext doubleLiteral() throws RecognitionException {
		DoubleLiteralContext _localctx = new DoubleLiteralContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_doubleLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(161);
				_la = _input.LA(1);
				if (_la==SIGN) {
					{
					setState(160); match(SIGN);
					}
				}

				setState(164); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(163); match(DECIMAL_DIGIT);
					}
					}
					setState(166); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DECIMAL_DIGIT );
				setState(168); match(DECIMAL_POINT);
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DECIMAL_DIGIT) {
					{
					{
					setState(169); match(DECIMAL_DIGIT);
					}
					}
					setState(174);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				{
				setState(176);
				_la = _input.LA(1);
				if (_la==SIGN) {
					{
					setState(175); match(SIGN);
					}
				}

				setState(179);
				_la = _input.LA(1);
				if (_la==DECIMAL_POINT) {
					{
					setState(178); match(DECIMAL_POINT);
					}
				}

				setState(182); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(181); match(DECIMAL_DIGIT);
					}
					}
					setState(184); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DECIMAL_DIGIT );
				}
				break;
			}

			        ((DoubleLiteralContext)_localctx).result =  new Double(_input.getText(_localctx.start, _input.LT(-1)));
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarCharLiteralContext extends ParserRuleContext {
		public String result;
		public List<TerminalNode> QUOTES() { return getTokens(SQLGrammarParser.QUOTES); }
		public TerminalNode QUOTES(int i) {
			return getToken(SQLGrammarParser.QUOTES, i);
		}
		public VarCharLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varCharLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterVarCharLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitVarCharLiteral(this);
		}
	}

	public final VarCharLiteralContext varCharLiteral() throws RecognitionException {
		VarCharLiteralContext _localctx = new VarCharLiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varCharLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190); match(QUOTES);
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OR) | (1L << AND) | (1L << NOT) | (1L << TRUE) | (1L << FALSE) | (1L << INTEGER_TYPE) | (1L << DOUBLE_TYPE) | (1L << VAR_CHAR_TYPE) | (1L << CREATE) | (1L << UNIQUE) | (1L << INDEX) | (1L << USING) | (1L << BTREE) | (1L << HASH) | (1L << ASC) | (1L << DESC) | (1L << DELETE) | (1L << INSERT) | (1L << INTO) | (1L << VALUES) | (1L << UPDATE) | (1L << SET) | (1L << TABLE) | (1L << SELECT) | (1L << FROM) | (1L << WHERE) | (1L << INNER) | (1L << JOIN) | (1L << ON) | (1L << COMMA) | (1L << ASTERISC) | (1L << LEFT_PARENTHESIS) | (1L << RIGHT_PARENTHESIS) | (1L << SEMICOLON) | (1L << WHITE_SPACE) | (1L << LOWER_CASE) | (1L << UPPER_CASE) | (1L << UNDERLINE) | (1L << DECIMAL_DIGIT) | (1L << SIGN) | (1L << DECIMAL_POINT) | (1L << EQUAL) | (1L << NOT_EQUAL) | (1L << LESS) | (1L << LESS_OR_EQUAL) | (1L << GREATER) | (1L << GREATER_OR_EQUAL))) != 0)) {
				{
				{
				setState(191);
				_la = _input.LA(1);
				if ( _la <= 0 || (_la==QUOTES) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(197); match(QUOTES);

			        ((VarCharLiteralContext)_localctx).result =  _input.getText(_localctx.start, _input.LT(-1)).replaceAll("\'", "");
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FirstLevelIdContext extends ParserRuleContext {
		public String result;
		public TerminalNode UPPER_CASE() { return getToken(SQLGrammarParser.UPPER_CASE, 0); }
		public IdSuffixContext idSuffix() {
			return getRuleContext(IdSuffixContext.class,0);
		}
		public TerminalNode UNDERLINE() { return getToken(SQLGrammarParser.UNDERLINE, 0); }
		public TerminalNode LOWER_CASE() { return getToken(SQLGrammarParser.LOWER_CASE, 0); }
		public FirstLevelIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_firstLevelId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterFirstLevelId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitFirstLevelId(this);
		}
	}

	public final FirstLevelIdContext firstLevelId() throws RecognitionException {
		FirstLevelIdContext _localctx = new FirstLevelIdContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_firstLevelId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			switch (_input.LA(1)) {
			case UNDERLINE:
				{
				{
				setState(200); match(UNDERLINE);
				setState(201); idSuffix();
				}
				}
				break;
			case LOWER_CASE:
			case UPPER_CASE:
				{
				{
				setState(202);
				_la = _input.LA(1);
				if ( !(_la==LOWER_CASE || _la==UPPER_CASE) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(204);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOWER_CASE) | (1L << UPPER_CASE) | (1L << UNDERLINE) | (1L << DECIMAL_DIGIT))) != 0)) {
					{
					setState(203); idSuffix();
					}
				}

				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        ((FirstLevelIdContext)_localctx).result =  _input.getText(_localctx.start, _input.LT(-1));
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdSuffixContext extends ParserRuleContext {
		public List<TerminalNode> UPPER_CASE() { return getTokens(SQLGrammarParser.UPPER_CASE); }
		public List<TerminalNode> DECIMAL_DIGIT() { return getTokens(SQLGrammarParser.DECIMAL_DIGIT); }
		public TerminalNode UPPER_CASE(int i) {
			return getToken(SQLGrammarParser.UPPER_CASE, i);
		}
		public List<TerminalNode> UNDERLINE() { return getTokens(SQLGrammarParser.UNDERLINE); }
		public TerminalNode UNDERLINE(int i) {
			return getToken(SQLGrammarParser.UNDERLINE, i);
		}
		public TerminalNode LOWER_CASE(int i) {
			return getToken(SQLGrammarParser.LOWER_CASE, i);
		}
		public List<TerminalNode> LOWER_CASE() { return getTokens(SQLGrammarParser.LOWER_CASE); }
		public TerminalNode DECIMAL_DIGIT(int i) {
			return getToken(SQLGrammarParser.DECIMAL_DIGIT, i);
		}
		public IdSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterIdSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitIdSuffix(this);
		}
	}

	public final IdSuffixContext idSuffix() throws RecognitionException {
		IdSuffixContext _localctx = new IdSuffixContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_idSuffix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(210);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOWER_CASE) | (1L << UPPER_CASE) | (1L << UNDERLINE) | (1L << DECIMAL_DIGIT))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(213); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOWER_CASE) | (1L << UPPER_CASE) | (1L << UNDERLINE) | (1L << DECIMAL_DIGIT))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateIndexContext extends ParserRuleContext {
		public CreateIndexQuery result;
		public FirstLevelIdContext firstLevelId;
		public FirstLevelIdContext firstLevelId(int i) {
			return getRuleContext(FirstLevelIdContext.class,i);
		}
		public TerminalNode ASC() { return getToken(SQLGrammarParser.ASC, 0); }
		public TerminalNode ON() { return getToken(SQLGrammarParser.ON, 0); }
		public List<FirstLevelIdContext> firstLevelId() {
			return getRuleContexts(FirstLevelIdContext.class);
		}
		public TerminalNode USING() { return getToken(SQLGrammarParser.USING, 0); }
		public TerminalNode DESC() { return getToken(SQLGrammarParser.DESC, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLGrammarParser.COMMA, i);
		}
		public TerminalNode INDEX() { return getToken(SQLGrammarParser.INDEX, 0); }
		public TerminalNode UNIQUE() { return getToken(SQLGrammarParser.UNIQUE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(SQLGrammarParser.COMMA); }
		public TerminalNode BTREE() { return getToken(SQLGrammarParser.BTREE, 0); }
		public TerminalNode CREATE() { return getToken(SQLGrammarParser.CREATE, 0); }
		public TerminalNode LEFT_PARENTHESIS() { return getToken(SQLGrammarParser.LEFT_PARENTHESIS, 0); }
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(SQLGrammarParser.RIGHT_PARENTHESIS, 0); }
		public TerminalNode HASH() { return getToken(SQLGrammarParser.HASH, 0); }
		public CreateIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterCreateIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitCreateIndex(this);
		}
	}

	public final CreateIndexContext createIndex() throws RecognitionException {
		CreateIndexContext _localctx = new CreateIndexContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_createIndex);

		    boolean isUnique = false;
		    String indexName = null;
		    String tableName = null;
		    List<String> attributeNames = new ArrayList<>();
		    boolean isAscending = true;
		    boolean isUsingHash = true;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215); match(CREATE);
			setState(218);
			_la = _input.LA(1);
			if (_la==UNIQUE) {
				{
				setState(216); match(UNIQUE);

				        isUnique = true;
				    
				}
			}

			setState(220); match(INDEX);
			setState(221); ((CreateIndexContext)_localctx).firstLevelId = firstLevelId();
			setState(222); match(ON);

			        indexName = ((CreateIndexContext)_localctx).firstLevelId.result;
			    
			setState(224); ((CreateIndexContext)_localctx).firstLevelId = firstLevelId();
			setState(225); match(LEFT_PARENTHESIS);

			        tableName = ((CreateIndexContext)_localctx).firstLevelId.result;
			    
			setState(227); ((CreateIndexContext)_localctx).firstLevelId = firstLevelId();

			        attributeNames.add(((CreateIndexContext)_localctx).firstLevelId.result);
			    
			setState(232);
			switch (_input.LA(1)) {
			case ASC:
				{
				setState(229); match(ASC);
				}
				break;
			case DESC:
				{
				{
				setState(230); match(DESC);

				        isAscending = false;
				    
				}
				}
				break;
			case COMMA:
			case RIGHT_PARENTHESIS:
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(234); match(COMMA);
				setState(235); ((CreateIndexContext)_localctx).firstLevelId = firstLevelId();

				        attributeNames.add(((CreateIndexContext)_localctx).firstLevelId.result);
				    
				}
				}
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(243); match(RIGHT_PARENTHESIS);
			setState(244); match(USING);
			setState(248);
			switch (_input.LA(1)) {
			case BTREE:
				{
				{
				setState(245); match(BTREE);

				        isUsingHash = false;
				    
				}
				}
				break;
			case HASH:
				{
				setState(247); match(HASH);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        ((CreateIndexContext)_localctx).result =  new CreateIndexQuery(tableName, indexName, attributeNames, isUnique, isAscending, isUsingHash);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectFromContext extends ParserRuleContext {
		public SelectFromQuery result;
		public FilterContext filter;
		public SelectionTableContext selectionTable;
		public WhereConditionContext whereCondition;
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public TerminalNode FROM() { return getToken(SQLGrammarParser.FROM, 0); }
		public SelectionTableContext selectionTable() {
			return getRuleContext(SelectionTableContext.class,0);
		}
		public TerminalNode SELECT() { return getToken(SQLGrammarParser.SELECT, 0); }
		public WhereConditionContext whereCondition() {
			return getRuleContext(WhereConditionContext.class,0);
		}
		public SelectFromContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectFrom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterSelectFrom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitSelectFrom(this);
		}
	}

	public final SelectFromContext selectFrom() throws RecognitionException {
		SelectFromContext _localctx = new SelectFromContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_selectFrom);

		    WhereCondition condition = null;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252); match(SELECT);
			setState(253); ((SelectFromContext)_localctx).filter = filter();
			setState(254); match(FROM);
			setState(255); ((SelectFromContext)_localctx).selectionTable = selectionTable();
			setState(259);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(256); ((SelectFromContext)_localctx).whereCondition = whereCondition();

				        condition = ((SelectFromContext)_localctx).whereCondition.result;
				    
				}
			}


			        ((SelectFromContext)_localctx).result =  new SelectFromQuery(((SelectFromContext)_localctx).selectionTable.result, ((SelectFromContext)_localctx).filter.result, condition);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InsertIntoContext extends ParserRuleContext {
		public InsertIntoQuery result;
		public FirstLevelIdContext firstLevelId;
		public ValueContext value;
		public FirstLevelIdContext firstLevelId(int i) {
			return getRuleContext(FirstLevelIdContext.class,i);
		}
		public TerminalNode RIGHT_PARENTHESIS(int i) {
			return getToken(SQLGrammarParser.RIGHT_PARENTHESIS, i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public List<FirstLevelIdContext> firstLevelId() {
			return getRuleContexts(FirstLevelIdContext.class);
		}
		public TerminalNode VALUES() { return getToken(SQLGrammarParser.VALUES, 0); }
		public TerminalNode LEFT_PARENTHESIS(int i) {
			return getToken(SQLGrammarParser.LEFT_PARENTHESIS, i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(SQLGrammarParser.COMMA, i);
		}
		public TerminalNode INTO() { return getToken(SQLGrammarParser.INTO, 0); }
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLGrammarParser.COMMA); }
		public List<TerminalNode> LEFT_PARENTHESIS() { return getTokens(SQLGrammarParser.LEFT_PARENTHESIS); }
		public TerminalNode INSERT() { return getToken(SQLGrammarParser.INSERT, 0); }
		public List<TerminalNode> RIGHT_PARENTHESIS() { return getTokens(SQLGrammarParser.RIGHT_PARENTHESIS); }
		public InsertIntoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertInto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterInsertInto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitInsertInto(this);
		}
	}

	public final InsertIntoContext insertInto() throws RecognitionException {
		InsertIntoContext _localctx = new InsertIntoContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_insertInto);

		    String tableName = null;
		    List<String> attributes = new ArrayList<>();
		    List<Object> values = new ArrayList<>();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(263); match(INSERT);
			setState(264); match(INTO);
			setState(265); ((InsertIntoContext)_localctx).firstLevelId = firstLevelId();
			setState(266); match(LEFT_PARENTHESIS);

			        tableName = ((InsertIntoContext)_localctx).firstLevelId.result;
			    
			setState(268); ((InsertIntoContext)_localctx).firstLevelId = firstLevelId();

			        attributes.add(((InsertIntoContext)_localctx).firstLevelId.result);
			    
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(270); match(COMMA);
				setState(271); ((InsertIntoContext)_localctx).firstLevelId = firstLevelId();

				        attributes.add(((InsertIntoContext)_localctx).firstLevelId.result);
				    
				}
				}
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(279); match(RIGHT_PARENTHESIS);
			setState(280); match(VALUES);
			setState(281); match(LEFT_PARENTHESIS);
			setState(282); ((InsertIntoContext)_localctx).value = value();

			        values.add(((InsertIntoContext)_localctx).value.result);
			    
			setState(290);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(284); match(COMMA);
				setState(285); ((InsertIntoContext)_localctx).value = value();

				        values.add(((InsertIntoContext)_localctx).value.result);
				    
				}
				}
				setState(292);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(293); match(RIGHT_PARENTHESIS);

			        ((InsertIntoContext)_localctx).result =  new InsertIntoQuery(tableName, attributes, values);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpdateSetContext extends ParserRuleContext {
		public UpdateTableQuery result;
		public FirstLevelIdContext firstLevelId;
		public AssignmentExpressionContext assignmentExpression;
		public WhereConditionContext whereCondition;
		public AssignmentExpressionContext assignmentExpression(int i) {
			return getRuleContext(AssignmentExpressionContext.class,i);
		}
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public TerminalNode UPDATE() { return getToken(SQLGrammarParser.UPDATE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(SQLGrammarParser.COMMA); }
		public List<AssignmentExpressionContext> assignmentExpression() {
			return getRuleContexts(AssignmentExpressionContext.class);
		}
		public TerminalNode SET() { return getToken(SQLGrammarParser.SET, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLGrammarParser.COMMA, i);
		}
		public WhereConditionContext whereCondition() {
			return getRuleContext(WhereConditionContext.class,0);
		}
		public UpdateSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterUpdateSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitUpdateSet(this);
		}
	}

	public final UpdateSetContext updateSet() throws RecognitionException {
		UpdateSetContext _localctx = new UpdateSetContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_updateSet);

		    String tableName = null;
		    List<AssignmentExpression> expressions = new ArrayList<>();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296); match(UPDATE);
			setState(297); ((UpdateSetContext)_localctx).firstLevelId = firstLevelId();
			setState(298); match(SET);
			setState(299); ((UpdateSetContext)_localctx).assignmentExpression = assignmentExpression();

			        tableName = ((UpdateSetContext)_localctx).firstLevelId.result;
			        expressions.add(((UpdateSetContext)_localctx).assignmentExpression.result);
			    
			setState(307);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(301); match(COMMA);
				setState(302); ((UpdateSetContext)_localctx).assignmentExpression = assignmentExpression();

				        expressions.add(((UpdateSetContext)_localctx).assignmentExpression.result);
				    
				}
				}
				setState(309);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(310); ((UpdateSetContext)_localctx).whereCondition = whereCondition();

			        ((UpdateSetContext)_localctx).result =  new UpdateTableQuery(tableName, expressions, ((UpdateSetContext)_localctx).whereCondition.result);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentExpressionContext extends ParserRuleContext {
		public AssignmentExpression result;
		public FirstLevelIdContext firstLevelId;
		public ValueContext value;
		public TerminalNode EQUAL() { return getToken(SQLGrammarParser.EQUAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterAssignmentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitAssignmentExpression(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_assignmentExpression);

		    String attributeName = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313); ((AssignmentExpressionContext)_localctx).firstLevelId = firstLevelId();
			setState(314); match(EQUAL);
			setState(315); ((AssignmentExpressionContext)_localctx).value = value();

			        attributeName = ((AssignmentExpressionContext)_localctx).firstLevelId.result;
			        value = ((AssignmentExpressionContext)_localctx).value.result;

			        if (value instanceof Integer) {
			            ((AssignmentExpressionContext)_localctx).result =  new AssignmentExpression<Integer>(attributeName, (Integer) value);
			        } else if (value instanceof Double) {
			            ((AssignmentExpressionContext)_localctx).result =  new AssignmentExpression<Double>(attributeName, (Double) value);
			        } else {
			            ((AssignmentExpressionContext)_localctx).result =  new AssignmentExpression<String>(attributeName, (String) value);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeleteFromContext extends ParserRuleContext {
		public DeleteFromQuery result;
		public FirstLevelIdContext firstLevelId;
		public WhereConditionContext whereCondition;
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public TerminalNode DELETE() { return getToken(SQLGrammarParser.DELETE, 0); }
		public TerminalNode FROM() { return getToken(SQLGrammarParser.FROM, 0); }
		public WhereConditionContext whereCondition() {
			return getRuleContext(WhereConditionContext.class,0);
		}
		public DeleteFromContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteFrom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterDeleteFrom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitDeleteFrom(this);
		}
	}

	public final DeleteFromContext deleteFrom() throws RecognitionException {
		DeleteFromContext _localctx = new DeleteFromContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_deleteFrom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318); match(DELETE);
			setState(319); match(FROM);
			setState(320); ((DeleteFromContext)_localctx).firstLevelId = firstLevelId();
			setState(321); ((DeleteFromContext)_localctx).whereCondition = whereCondition();

			        ((DeleteFromContext)_localctx).result =  new DeleteFromQuery(((DeleteFromContext)_localctx).firstLevelId.result, ((DeleteFromContext)_localctx).whereCondition.result);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FilterContext extends ParserRuleContext {
		public List<String> result;
		public FirstLevelIdContext firstLevelId;
		public FirstLevelIdContext firstLevelId(int i) {
			return getRuleContext(FirstLevelIdContext.class,i);
		}
		public TerminalNode ASTERISC() { return getToken(SQLGrammarParser.ASTERISC, 0); }
		public List<FirstLevelIdContext> firstLevelId() {
			return getRuleContexts(FirstLevelIdContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLGrammarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLGrammarParser.COMMA, i);
		}
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitFilter(this);
		}
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_filter);
		int _la;
		try {
			setState(337);
			switch (_input.LA(1)) {
			case ASTERISC:
				enterOuterAlt(_localctx, 1);
				{
				setState(324); match(ASTERISC);

				        ((FilterContext)_localctx).result =  null;
				    
				}
				break;
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				enterOuterAlt(_localctx, 2);
				{
				setState(326); ((FilterContext)_localctx).firstLevelId = firstLevelId();

				        ((FilterContext)_localctx).result =  new ArrayList<>();
				        _localctx.result.add(((FilterContext)_localctx).firstLevelId.result);
				    
				setState(334);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(328); match(COMMA);
					setState(329); ((FilterContext)_localctx).firstLevelId = firstLevelId();

					        _localctx.result.add(((FilterContext)_localctx).firstLevelId.result);
					    
					}
					}
					setState(336);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhereConditionContext extends ParserRuleContext {
		public WhereCondition result;
		public BooleanExpressionContext booleanExpression;
		public TerminalNode WHERE() { return getToken(SQLGrammarParser.WHERE, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public WhereConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterWhereCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitWhereCondition(this);
		}
	}

	public final WhereConditionContext whereCondition() throws RecognitionException {
		WhereConditionContext _localctx = new WhereConditionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_whereCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339); match(WHERE);
			setState(340); ((WhereConditionContext)_localctx).booleanExpression = booleanExpression();

			        ((WhereConditionContext)_localctx).result =  new WhereCondition(((WhereConditionContext)_localctx).booleanExpression.result);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionTableContext extends ParserRuleContext {
		public SelectionTable result;
		public FirstLevelIdContext firstLevelId;
		public JoinOnExpressionContext joinOnExpression;
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public JoinOnExpressionContext joinOnExpression(int i) {
			return getRuleContext(JoinOnExpressionContext.class,i);
		}
		public List<JoinOnExpressionContext> joinOnExpression() {
			return getRuleContexts(JoinOnExpressionContext.class);
		}
		public SelectionTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterSelectionTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitSelectionTable(this);
		}
	}

	public final SelectionTableContext selectionTable() throws RecognitionException {
		SelectionTableContext _localctx = new SelectionTableContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_selectionTable);

		    String tableName = null;
		    List<JoinOnExpression> expressions = new ArrayList<>();

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343); ((SelectionTableContext)_localctx).firstLevelId = firstLevelId();

			        tableName = ((SelectionTableContext)_localctx).firstLevelId.result;
			    
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INNER) {
				{
				{
				setState(345); ((SelectionTableContext)_localctx).joinOnExpression = joinOnExpression();

				        expressions.add(((SelectionTableContext)_localctx).joinOnExpression.result);
				    
				}
				}
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

			        ((SelectionTableContext)_localctx).result =  new SelectionTable(tableName, expressions);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JoinOnExpressionContext extends ParserRuleContext {
		public JoinOnExpression result;
		public FirstLevelIdContext firstLevelId;
		public SecondLevelIdContext secondLevelId;
		public List<SecondLevelIdContext> secondLevelId() {
			return getRuleContexts(SecondLevelIdContext.class);
		}
		public TerminalNode EQUAL() { return getToken(SQLGrammarParser.EQUAL, 0); }
		public TerminalNode ON() { return getToken(SQLGrammarParser.ON, 0); }
		public FirstLevelIdContext firstLevelId() {
			return getRuleContext(FirstLevelIdContext.class,0);
		}
		public SecondLevelIdContext secondLevelId(int i) {
			return getRuleContext(SecondLevelIdContext.class,i);
		}
		public TerminalNode INNER() { return getToken(SQLGrammarParser.INNER, 0); }
		public TerminalNode JOIN() { return getToken(SQLGrammarParser.JOIN, 0); }
		public JoinOnExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinOnExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterJoinOnExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitJoinOnExpression(this);
		}
	}

	public final JoinOnExpressionContext joinOnExpression() throws RecognitionException {
		JoinOnExpressionContext _localctx = new JoinOnExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_joinOnExpression);

		    String tableName = null;
		    SecondLevelId firstId = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355); match(INNER);
			setState(356); match(JOIN);
			setState(357); ((JoinOnExpressionContext)_localctx).firstLevelId = firstLevelId();
			setState(358); match(ON);
			setState(359); ((JoinOnExpressionContext)_localctx).secondLevelId = secondLevelId();
			setState(360); match(EQUAL);

			        tableName = ((JoinOnExpressionContext)_localctx).firstLevelId.result;
			        firstId = ((JoinOnExpressionContext)_localctx).secondLevelId.result;
			    
			setState(362); ((JoinOnExpressionContext)_localctx).secondLevelId = secondLevelId();

			        ((JoinOnExpressionContext)_localctx).result =  new JoinOnExpression(tableName, firstId, ((JoinOnExpressionContext)_localctx).secondLevelId.result);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SecondLevelIdContext extends ParserRuleContext {
		public SecondLevelId result;
		public FirstLevelIdContext firstLevelId;
		public FirstLevelIdContext firstLevelId(int i) {
			return getRuleContext(FirstLevelIdContext.class,i);
		}
		public List<FirstLevelIdContext> firstLevelId() {
			return getRuleContexts(FirstLevelIdContext.class);
		}

		public TerminalNode DECIMAL_POINT() {
			return getToken(SQLGrammarParser.DECIMAL_POINT, 0);
		}
		public SecondLevelIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_secondLevelId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterSecondLevelId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitSecondLevelId(this);
		}
	}

	public final SecondLevelIdContext secondLevelId() throws RecognitionException {
		SecondLevelIdContext _localctx = new SecondLevelIdContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_secondLevelId);

		    String tableName = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365); ((SecondLevelIdContext)_localctx).firstLevelId = firstLevelId();
				{
					setState(366);
					match(DECIMAL_POINT);

					tableName = ((SecondLevelIdContext) _localctx).firstLevelId.result;

					setState(368); ((SecondLevelIdContext)_localctx).firstLevelId = firstLevelId();

					((SecondLevelIdContext) _localctx).result = new SecondLevelId(tableName, ((SecondLevelIdContext) _localctx).firstLevelId.result);

				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpressionContext extends ParserRuleContext {
		public BooleanExpression result;
		public OrExpressionContext orExpression;
		public OrExpressionContext orExpression(int i) {
			return getRuleContext(OrExpressionContext.class,i);
		}
		public List<OrExpressionContext> orExpression() {
			return getRuleContexts(OrExpressionContext.class);
		}
		public TerminalNode OR() { return getToken(SQLGrammarParser.OR, 0); }
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterBooleanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitBooleanExpression(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_booleanExpression);

		    OrExpression first = null;
		    OrExpression second = null;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371); ((BooleanExpressionContext)_localctx).orExpression = orExpression();

			        first = ((BooleanExpressionContext)_localctx).orExpression.result;
			    
			setState(377);
			_la = _input.LA(1);
			if (_la==OR) {
				{
				setState(373); match(OR);
				setState(374); ((BooleanExpressionContext)_localctx).orExpression = orExpression();

				        second = ((BooleanExpressionContext)_localctx).orExpression.result;
				    
				}
			}


			        ((BooleanExpressionContext)_localctx).result =  new BooleanExpression(first, second);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrExpressionContext extends ParserRuleContext {
		public OrExpression result;
		public AndExpressionContext andExpression;
		public List<AndExpressionContext> andExpression() {
			return getRuleContexts(AndExpressionContext.class);
		}
		public AndExpressionContext andExpression(int i) {
			return getRuleContext(AndExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(SQLGrammarParser.AND, 0); }
		public OrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitOrExpression(this);
		}
	}

	public final OrExpressionContext orExpression() throws RecognitionException {
		OrExpressionContext _localctx = new OrExpressionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_orExpression);

		    AndExpression first = null;
		    AndExpression second = null;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381); ((OrExpressionContext)_localctx).andExpression = andExpression();

			        first = ((OrExpressionContext)_localctx).andExpression.result;
			    
			setState(387);
			_la = _input.LA(1);
			if (_la==AND) {
				{
				setState(383); match(AND);
				setState(384); ((OrExpressionContext)_localctx).andExpression = andExpression();

				        second = ((OrExpressionContext)_localctx).andExpression.result;
				    
				}
			}


			        ((OrExpressionContext)_localctx).result =  new OrExpression(first, second);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndExpressionContext extends ParserRuleContext {
		public AndExpression result;
		public BooleanFactorContext booleanFactor;
		public TerminalNode NOT() { return getToken(SQLGrammarParser.NOT, 0); }
		public BooleanFactorContext booleanFactor() {
			return getRuleContext(BooleanFactorContext.class,0);
		}
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitAndExpression(this);
		}
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_andExpression);

		   boolean negate = false;

		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(391); match(NOT);

				        negate = true;
				    
				}
			}

			setState(395); ((AndExpressionContext)_localctx).booleanFactor = booleanFactor();

			        ((AndExpressionContext)_localctx).result =  new AndExpression(((AndExpressionContext)_localctx).booleanFactor.result, negate);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanFactorContext extends ParserRuleContext {
		public BooleanFactor result;
		public BooleanLiteralContext booleanLiteral;
		public BooleanExpressionContext booleanExpression;
		public ComparisonExpressionContext comparisonExpression;
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode LEFT_PARENTHESIS() { return getToken(SQLGrammarParser.LEFT_PARENTHESIS, 0); }
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(SQLGrammarParser.RIGHT_PARENTHESIS, 0); }
		public BooleanFactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanFactor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterBooleanFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitBooleanFactor(this);
		}
	}

	public final BooleanFactorContext booleanFactor() throws RecognitionException {
		BooleanFactorContext _localctx = new BooleanFactorContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_booleanFactor);
		try {
			setState(409);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 1);
				{
				setState(398); ((BooleanFactorContext)_localctx).booleanLiteral = booleanLiteral();

				        ((BooleanFactorContext)_localctx).result =  new BooleanFactor(((BooleanFactorContext)_localctx).booleanLiteral.result);
				    
				}
				break;
			case LEFT_PARENTHESIS:
				enterOuterAlt(_localctx, 2);
				{
				setState(401); match(LEFT_PARENTHESIS);
				setState(402); ((BooleanFactorContext)_localctx).booleanExpression = booleanExpression();
				setState(403); match(RIGHT_PARENTHESIS);

				        ((BooleanFactorContext)_localctx).result =  new BooleanFactor(((BooleanFactorContext)_localctx).booleanExpression.result);
				    
				}
				break;
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				enterOuterAlt(_localctx, 3);
				{
				setState(406); ((BooleanFactorContext)_localctx).comparisonExpression = comparisonExpression();

				        ((BooleanFactorContext)_localctx).result =  new BooleanFactor(((BooleanFactorContext)_localctx).comparisonExpression.result);
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanLiteralContext extends ParserRuleContext {
		public BooleanLiteral result;
		public TerminalNode FALSE() { return getToken(SQLGrammarParser.FALSE, 0); }
		public TerminalNode TRUE() { return getToken(SQLGrammarParser.TRUE, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitBooleanLiteral(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_booleanLiteral);
		try {
			setState(415);
			switch (_input.LA(1)) {
			case TRUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(411); match(TRUE);

				        ((BooleanLiteralContext)_localctx).result =  BooleanLiteral.TrueBooleanLiteral.getInstance();
				    
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(413); match(FALSE);

				        ((BooleanLiteralContext)_localctx).result =  BooleanLiteral.FalseBooleanLiteral.getInstance();
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonExpressionContext extends ParserRuleContext {
		public ComparisonExpression result;
		public EqualExpressionContext equalExpression;
		public NotEqualExpressionContext notEqualExpression;
		public LessExpressionContext lessExpression;
		public LessOrEqualExpressionContext lessOrEqualExpression;
		public GreaterExpressionContext greaterExpression;
		public GreaterOrEqualExpressionContext greaterOrEqualExpression;
		public LessExpressionContext lessExpression() {
			return getRuleContext(LessExpressionContext.class,0);
		}
		public GreaterExpressionContext greaterExpression() {
			return getRuleContext(GreaterExpressionContext.class,0);
		}
		public LessOrEqualExpressionContext lessOrEqualExpression() {
			return getRuleContext(LessOrEqualExpressionContext.class,0);
		}
		public NotEqualExpressionContext notEqualExpression() {
			return getRuleContext(NotEqualExpressionContext.class,0);
		}
		public EqualExpressionContext equalExpression() {
			return getRuleContext(EqualExpressionContext.class,0);
		}
		public GreaterOrEqualExpressionContext greaterOrEqualExpression() {
			return getRuleContext(GreaterOrEqualExpressionContext.class,0);
		}
		public ComparisonExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterComparisonExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitComparisonExpression(this);
		}
	}

	public final ComparisonExpressionContext comparisonExpression() throws RecognitionException {
		ComparisonExpressionContext _localctx = new ComparisonExpressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_comparisonExpression);
		try {
			setState(435);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(417); ((ComparisonExpressionContext)_localctx).equalExpression = equalExpression();

				        ((ComparisonExpressionContext)_localctx).result =  ((ComparisonExpressionContext)_localctx).equalExpression.result;
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(420); ((ComparisonExpressionContext)_localctx).notEqualExpression = notEqualExpression();

				        ((ComparisonExpressionContext)_localctx).result =  ((ComparisonExpressionContext)_localctx).notEqualExpression.result;
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(423); ((ComparisonExpressionContext)_localctx).lessExpression = lessExpression();

				        ((ComparisonExpressionContext)_localctx).result =  ((ComparisonExpressionContext)_localctx).lessExpression.result;
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(426); ((ComparisonExpressionContext)_localctx).lessOrEqualExpression = lessOrEqualExpression();

				        ((ComparisonExpressionContext)_localctx).result =  ((ComparisonExpressionContext)_localctx).lessOrEqualExpression.result;
				    
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(429); ((ComparisonExpressionContext)_localctx).greaterExpression = greaterExpression();

				        ((ComparisonExpressionContext)_localctx).result =  ((ComparisonExpressionContext)_localctx).greaterExpression.result;
				    
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(432); ((ComparisonExpressionContext)_localctx).greaterOrEqualExpression = greaterOrEqualExpression();

				        ((ComparisonExpressionContext)_localctx).result =  ((ComparisonExpressionContext)_localctx).greaterOrEqualExpression.result;
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualExpressionContext extends ParserRuleContext {
		public EqualExpression result;
		public SecondLevelIdContext secondLevelId;
		public ValueContext value;
		public SecondLevelIdContext secondLevelId() {
			return getRuleContext(SecondLevelIdContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(SQLGrammarParser.EQUAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public EqualExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterEqualExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitEqualExpression(this);
		}
	}

	public final EqualExpressionContext equalExpression() throws RecognitionException {
		EqualExpressionContext _localctx = new EqualExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_equalExpression);

		    SecondLevelId id = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
			switch (_input.LA(1)) {
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				{
				setState(437); ((EqualExpressionContext)_localctx).secondLevelId = secondLevelId();
				setState(438); match(EQUAL);
				setState(439); ((EqualExpressionContext)_localctx).value = value();

				        id = ((EqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((EqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				{
				setState(442); ((EqualExpressionContext)_localctx).value = value();
				setState(443); match(EQUAL);
				setState(444); ((EqualExpressionContext)_localctx).secondLevelId = secondLevelId();

				        id = ((EqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((EqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        if (value instanceof Integer) {
			            ((EqualExpressionContext)_localctx).result =  new EqualExpression<Integer>(id, (Integer) value);
			        } else if (value instanceof Double) {
			            ((EqualExpressionContext)_localctx).result =  new EqualExpression<Double>(id, (Double) value);
			        } else {
			            ((EqualExpressionContext)_localctx).result =  new EqualExpression<String>(id, (String) value);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotEqualExpressionContext extends ParserRuleContext {
		public NotEqualExpression result;
		public SecondLevelIdContext secondLevelId;
		public ValueContext value;
		public SecondLevelIdContext secondLevelId() {
			return getRuleContext(SecondLevelIdContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode NOT_EQUAL() { return getToken(SQLGrammarParser.NOT_EQUAL, 0); }
		public NotEqualExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notEqualExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterNotEqualExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitNotEqualExpression(this);
		}
	}

	public final NotEqualExpressionContext notEqualExpression() throws RecognitionException {
		NotEqualExpressionContext _localctx = new NotEqualExpressionContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_notEqualExpression);

		    SecondLevelId id = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(461);
			switch (_input.LA(1)) {
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				{
				setState(451); ((NotEqualExpressionContext)_localctx).secondLevelId = secondLevelId();
				setState(452); match(NOT_EQUAL);
				setState(453); ((NotEqualExpressionContext)_localctx).value = value();

				        id = ((NotEqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((NotEqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				{
				setState(456); ((NotEqualExpressionContext)_localctx).value = value();
				setState(457); match(NOT_EQUAL);
				setState(458); ((NotEqualExpressionContext)_localctx).secondLevelId = secondLevelId();

				        id = ((NotEqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((NotEqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        if (value instanceof Integer) {
			            ((NotEqualExpressionContext)_localctx).result =  new NotEqualExpression<Integer>(id, (Integer) value);
			        } else if (value instanceof Double) {
			            ((NotEqualExpressionContext)_localctx).result =  new NotEqualExpression<Double>(id, (Double) value);
			        } else {
			            ((NotEqualExpressionContext)_localctx).result =  new NotEqualExpression<String>(id, (String) value);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LessExpressionContext extends ParserRuleContext {
		public LessExpression result;
		public SecondLevelIdContext secondLevelId;
		public ValueContext value;
		public SecondLevelIdContext secondLevelId() {
			return getRuleContext(SecondLevelIdContext.class,0);
		}
		public TerminalNode LESS() { return getToken(SQLGrammarParser.LESS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode GREATER() { return getToken(SQLGrammarParser.GREATER, 0); }
		public LessExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lessExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterLessExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitLessExpression(this);
		}
	}

	public final LessExpressionContext lessExpression() throws RecognitionException {
		LessExpressionContext _localctx = new LessExpressionContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_lessExpression);

		    SecondLevelId id = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			switch (_input.LA(1)) {
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				{
				setState(465); ((LessExpressionContext)_localctx).secondLevelId = secondLevelId();
				setState(466); match(LESS);
				setState(467); ((LessExpressionContext)_localctx).value = value();

				        id = ((LessExpressionContext)_localctx).secondLevelId.result;
				        value = ((LessExpressionContext)_localctx).value.result;
				    
				}
				break;
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				{
				setState(470); ((LessExpressionContext)_localctx).value = value();
				setState(471); match(GREATER);
				setState(472); ((LessExpressionContext)_localctx).secondLevelId = secondLevelId();

				        id = ((LessExpressionContext)_localctx).secondLevelId.result;
				        value = ((LessExpressionContext)_localctx).value.result;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        if (value instanceof Integer) {
			            ((LessExpressionContext)_localctx).result =  new LessExpression<Integer>(id, (Integer) value);
			        } else if (value instanceof Double) {
			            ((LessExpressionContext)_localctx).result =  new LessExpression<Double>(id, (Double) value);
			        } else {
			            ((LessExpressionContext)_localctx).result =  new LessExpression<String>(id, (String) value);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LessOrEqualExpressionContext extends ParserRuleContext {
		public LessOrEqualExpression result;
		public SecondLevelIdContext secondLevelId;
		public ValueContext value;
		public SecondLevelIdContext secondLevelId() {
			return getRuleContext(SecondLevelIdContext.class,0);
		}
		public TerminalNode LESS_OR_EQUAL() { return getToken(SQLGrammarParser.LESS_OR_EQUAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode GREATER_OR_EQUAL() { return getToken(SQLGrammarParser.GREATER_OR_EQUAL, 0); }
		public LessOrEqualExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lessOrEqualExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterLessOrEqualExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitLessOrEqualExpression(this);
		}
	}

	public final LessOrEqualExpressionContext lessOrEqualExpression() throws RecognitionException {
		LessOrEqualExpressionContext _localctx = new LessOrEqualExpressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_lessOrEqualExpression);

		    SecondLevelId id = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(489);
			switch (_input.LA(1)) {
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				{
				setState(479); ((LessOrEqualExpressionContext)_localctx).secondLevelId = secondLevelId();
				setState(480); match(LESS_OR_EQUAL);
				setState(481); ((LessOrEqualExpressionContext)_localctx).value = value();

				        id = ((LessOrEqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((LessOrEqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				{
				setState(484); ((LessOrEqualExpressionContext)_localctx).value = value();
				setState(485); match(GREATER_OR_EQUAL);
				setState(486); ((LessOrEqualExpressionContext)_localctx).secondLevelId = secondLevelId();

				        id = ((LessOrEqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((LessOrEqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        if (value instanceof Integer) {
			            ((LessOrEqualExpressionContext)_localctx).result =  new LessOrEqualExpression<Integer>(id, (Integer) value);
			        } else if (value instanceof Double) {
			            ((LessOrEqualExpressionContext)_localctx).result =  new LessOrEqualExpression<Double>(id, (Double) value);
			        } else {
			            ((LessOrEqualExpressionContext)_localctx).result =  new LessOrEqualExpression<String>(id, (String) value);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GreaterExpressionContext extends ParserRuleContext {
		public GreaterExpression result;
		public SecondLevelIdContext secondLevelId;
		public ValueContext value;
		public SecondLevelIdContext secondLevelId() {
			return getRuleContext(SecondLevelIdContext.class,0);
		}
		public TerminalNode LESS() { return getToken(SQLGrammarParser.LESS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode GREATER() { return getToken(SQLGrammarParser.GREATER, 0); }
		public GreaterExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_greaterExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterGreaterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitGreaterExpression(this);
		}
	}

	public final GreaterExpressionContext greaterExpression() throws RecognitionException {
		GreaterExpressionContext _localctx = new GreaterExpressionContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_greaterExpression);

		    SecondLevelId id = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(503);
			switch (_input.LA(1)) {
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				{
				setState(493); ((GreaterExpressionContext)_localctx).secondLevelId = secondLevelId();
				setState(494); match(GREATER);
				setState(495); ((GreaterExpressionContext)_localctx).value = value();

				        id = ((GreaterExpressionContext)_localctx).secondLevelId.result;
				        value = ((GreaterExpressionContext)_localctx).value.result;
				    
				}
				break;
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				{
				setState(498); ((GreaterExpressionContext)_localctx).value = value();
				setState(499); match(LESS);
				setState(500); ((GreaterExpressionContext)_localctx).secondLevelId = secondLevelId();

				        id = ((GreaterExpressionContext)_localctx).secondLevelId.result;
				        value = ((GreaterExpressionContext)_localctx).value.result;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			        if (value instanceof Integer) {
			            ((GreaterExpressionContext)_localctx).result =  new GreaterExpression<Integer>(id, (Integer) value);
			        } else if (value instanceof Double) {
			            ((GreaterExpressionContext)_localctx).result =  new GreaterExpression<Double>(id, (Double) value);
			        } else {
			            ((GreaterExpressionContext)_localctx).result =  new GreaterExpression<String>(id, (String) value);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GreaterOrEqualExpressionContext extends ParserRuleContext {
		public GreaterOrEqualExpression result;
		public SecondLevelIdContext secondLevelId;
		public ValueContext value;
		public SecondLevelIdContext secondLevelId() {
			return getRuleContext(SecondLevelIdContext.class,0);
		}
		public TerminalNode LESS_OR_EQUAL() { return getToken(SQLGrammarParser.LESS_OR_EQUAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode GREATER_OR_EQUAL() { return getToken(SQLGrammarParser.GREATER_OR_EQUAL, 0); }
		public GreaterOrEqualExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_greaterOrEqualExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).enterGreaterOrEqualExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLGrammarListener ) ((SQLGrammarListener)listener).exitGreaterOrEqualExpression(this);
		}
	}

	public final GreaterOrEqualExpressionContext greaterOrEqualExpression() throws RecognitionException {
		GreaterOrEqualExpressionContext _localctx = new GreaterOrEqualExpressionContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_greaterOrEqualExpression);

		    SecondLevelId id = null;
		    Object value = null;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(517);
			switch (_input.LA(1)) {
			case LOWER_CASE:
			case UPPER_CASE:
			case UNDERLINE:
				{
				setState(507); ((GreaterOrEqualExpressionContext)_localctx).secondLevelId = secondLevelId();
				setState(508); match(GREATER_OR_EQUAL);
				setState(509); ((GreaterOrEqualExpressionContext)_localctx).value = value();

				        id = ((GreaterOrEqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((GreaterOrEqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			case DECIMAL_DIGIT:
			case SIGN:
			case DECIMAL_POINT:
			case QUOTES:
				{
				setState(512); ((GreaterOrEqualExpressionContext)_localctx).value = value();
				setState(513); match(LESS_OR_EQUAL);
				setState(514); ((GreaterOrEqualExpressionContext)_localctx).secondLevelId = secondLevelId();

				        id = ((GreaterOrEqualExpressionContext)_localctx).secondLevelId.result;
				        value = ((GreaterOrEqualExpressionContext)_localctx).value.result;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}

			          if (value instanceof Integer) {
			              ((GreaterOrEqualExpressionContext)_localctx).result =  new GreaterOrEqualExpression<Integer>(id, (Integer) value);
			          } else if (value instanceof Double) {
			              ((GreaterOrEqualExpressionContext)_localctx).result =  new GreaterOrEqualExpression<Double>(id, (Double) value);
			          } else {
			              ((GreaterOrEqualExpressionContext)_localctx).result =  new GreaterOrEqualExpression<String>(id, (String) value);
			          }
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
			"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\62\u020c\4\2\t\2" +
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\2\6\2L\n\2\r\2\16\2M\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5"+
		"\3e\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4s\n\4\f\4\16"+
		"\4v\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0086"+
		"\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0097"+
		"\n\b\3\t\5\t\u009a\n\t\3\t\6\t\u009d\n\t\r\t\16\t\u009e\3\t\3\t\3\n\5"+
		"\n\u00a4\n\n\3\n\6\n\u00a7\n\n\r\n\16\n\u00a8\3\n\3\n\7\n\u00ad\n\n\f"+
		"\n\16\n\u00b0\13\n\3\n\5\n\u00b3\n\n\3\n\5\n\u00b6\n\n\3\n\6\n\u00b9\n"+
		"\n\r\n\16\n\u00ba\5\n\u00bd\n\n\3\n\3\n\3\13\3\13\7\13\u00c3\n\13\f\13"+
		"\16\13\u00c6\13\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\5\f\u00cf\n\f\5\f\u00d1"+
		"\n\f\3\f\3\f\3\r\6\r\u00d6\n\r\r\r\16\r\u00d7\3\16\3\16\3\16\5\16\u00dd"+
		"\n\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16"+
		"\u00eb\n\16\3\16\3\16\3\16\3\16\7\16\u00f1\n\16\f\16\16\16\u00f4\13\16"+
		"\3\16\3\16\3\16\3\16\3\16\5\16\u00fb\n\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\5\17\u0106\n\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\7\20\u0115\n\20\f\20\16\20\u0118\13\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u0123\n\20\f\20\16\20\u0126"+
		"\13\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21"+
		"\u0134\n\21\f\21\16\21\u0137\13\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\7\24\u014f\n\24\f\24\16\24\u0152\13\24\5\24\u0154\n\24\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\7\26\u015f\n\26\f\26\16\26\u0162\13"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u017c\n\31"+
		"\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0186\n\32\3\32\3\32\3\33"+
		"\3\33\5\33\u018c\n\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\5\34\u019c\n\34\3\35\3\35\3\35\3\35\5\35\u01a2\n"+
		"\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3"+
		"\36\3\36\3\36\3\36\3\36\5\36\u01b6\n\36\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\5\37\u01c2\n\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3"+
		" \3 \3 \5 \u01d0\n \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u01de\n!\3"+
		"!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u01ec\n\"\3\"\3\"\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u01fa\n#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\5$\u0208\n$\3$\3$\3$\2\2%\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
					" \"$&(*,.\60\62\64\668:<>@BDF\2\5\3\2\62\62\3\2&\'\3\2&)\u021d\2K\3\2" +
		"\2\2\4d\3\2\2\2\6h\3\2\2\2\bz\3\2\2\2\n\u0085\3\2\2\2\f\u0087\3\2\2\2"+
		"\16\u0096\3\2\2\2\20\u0099\3\2\2\2\22\u00bc\3\2\2\2\24\u00c0\3\2\2\2\26"+
		"\u00d0\3\2\2\2\30\u00d5\3\2\2\2\32\u00d9\3\2\2\2\34\u00fe\3\2\2\2\36\u0109"+
		"\3\2\2\2 \u012a\3\2\2\2\"\u013b\3\2\2\2$\u0140\3\2\2\2&\u0153\3\2\2\2"+
		"(\u0155\3\2\2\2*\u0159\3\2\2\2,\u0165\3\2\2\2.\u016f\3\2\2\2\60\u0175"+
		"\3\2\2\2\62\u017f\3\2\2\2\64\u018b\3\2\2\2\66\u019b\3\2\2\28\u01a1\3\2"+
		"\2\2:\u01b5\3\2\2\2<\u01c1\3\2\2\2>\u01cf\3\2\2\2@\u01dd\3\2\2\2B\u01eb"+
		"\3\2\2\2D\u01f9\3\2\2\2F\u0207\3\2\2\2HI\5\4\3\2IJ\b\2\1\2JL\3\2\2\2K"+
		"H\3\2\2\2LM\3\2\2\2MK\3\2\2\2MN\3\2\2\2NO\3\2\2\2OP\7\2\2\3PQ\b\2\1\2"+
		"Q\3\3\2\2\2RS\5\6\4\2ST\b\3\1\2Te\3\2\2\2UV\5\32\16\2VW\b\3\1\2We\3\2"+
		"\2\2XY\5\34\17\2YZ\b\3\1\2Ze\3\2\2\2[\\\5\36\20\2\\]\b\3\1\2]e\3\2\2\2"+
		"^_\5 \21\2_`\b\3\1\2`e\3\2\2\2ab\5$\23\2bc\b\3\1\2ce\3\2\2\2dR\3\2\2\2"+
		"dU\3\2\2\2dX\3\2\2\2d[\3\2\2\2d^\3\2\2\2da\3\2\2\2ef\3\2\2\2fg\7$\2\2"+
		"g\5\3\2\2\2hi\7\13\2\2ij\7\31\2\2jk\5\26\f\2kl\7\"\2\2lm\5\b\5\2mt\b\4"+
		"\1\2no\7 \2\2op\5\b\5\2pq\b\4\1\2qs\3\2\2\2rn\3\2\2\2sv\3\2\2\2tr\3\2"+
		"\2\2tu\3\2\2\2uw\3\2\2\2vt\3\2\2\2wx\7#\2\2xy\b\4\1\2y\7\3\2\2\2z{\5\26"+
		"\f\2{|\5\n\6\2|}\b\5\1\2}\t\3\2\2\2~\177\7\b\2\2\177\u0086\b\6\1\2\u0080"+
		"\u0081\7\t\2\2\u0081\u0086\b\6\1\2\u0082\u0083\5\f\7\2\u0083\u0084\b\6"+
		"\1\2\u0084\u0086\3\2\2\2\u0085~\3\2\2\2\u0085\u0080\3\2\2\2\u0085\u0082"+
		"\3\2\2\2\u0086\13\3\2\2\2\u0087\u0088\7\n\2\2\u0088\u0089\7\"\2\2\u0089"+
		"\u008a\5\20\t\2\u008a\u008b\7#\2\2\u008b\u008c\b\7\1\2\u008c\r\3\2\2\2"+
		"\u008d\u008e\5\20\t\2\u008e\u008f\b\b\1\2\u008f\u0097\3\2\2\2\u0090\u0091"+
		"\5\22\n\2\u0091\u0092\b\b\1\2\u0092\u0097\3\2\2\2\u0093\u0094\5\24\13"+
		"\2\u0094\u0095\b\b\1\2\u0095\u0097\3\2\2\2\u0096\u008d\3\2\2\2\u0096\u0090"+
		"\3\2\2\2\u0096\u0093\3\2\2\2\u0097\17\3\2\2\2\u0098\u009a\7*\2\2\u0099"+
		"\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b\u009d\7)"+
		"\2\2\u009c\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e"+
		"\u009f\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1\b\t\1\2\u00a1\21\3\2\2"+
		"\2\u00a2\u00a4\7*\2\2\u00a3\u00a2\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6"+
		"\3\2\2\2\u00a5\u00a7\7)\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ae\7+"+
		"\2\2\u00ab\u00ad\7)\2\2\u00ac\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae"+
		"\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00bd\3\2\2\2\u00b0\u00ae\3\2"+
		"\2\2\u00b1\u00b3\7*\2\2\u00b2\u00b1\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3"+
		"\u00b5\3\2\2\2\u00b4\u00b6\7+\2\2\u00b5\u00b4\3\2\2\2\u00b5\u00b6\3\2"+
		"\2\2\u00b6\u00b8\3\2\2\2\u00b7\u00b9\7)\2\2\u00b8\u00b7\3\2\2\2\u00b9"+
		"\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bd\3\2"+
		"\2\2\u00bc\u00a3\3\2\2\2\u00bc\u00b2\3\2\2\2\u00bd\u00be\3\2\2\2\u00be"+
					"\u00bf\b\n\1\2\u00bf\23\3\2\2\2\u00c0\u00c4\7\62\2\2\u00c1\u00c3\n\2\2" +
		"\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5"+
					"\3\2\2\2\u00c5\u00c7\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00c8\7\62\2\2" +
		"\u00c8\u00c9\b\13\1\2\u00c9\25\3\2\2\2\u00ca\u00cb\7(\2\2\u00cb\u00d1"+
		"\5\30\r\2\u00cc\u00ce\t\3\2\2\u00cd\u00cf\5\30\r\2\u00ce\u00cd\3\2\2\2"+
		"\u00ce\u00cf\3\2\2\2\u00cf\u00d1\3\2\2\2\u00d0\u00ca\3\2\2\2\u00d0\u00cc"+
		"\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\b\f\1\2\u00d3\27\3\2\2\2\u00d4"+
		"\u00d6\t\4\2\2\u00d5\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d5\3\2"+
		"\2\2\u00d7\u00d8\3\2\2\2\u00d8\31\3\2\2\2\u00d9\u00dc\7\13\2\2\u00da\u00db"+
		"\7\f\2\2\u00db\u00dd\b\16\1\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2"+
		"\u00dd\u00de\3\2\2\2\u00de\u00df\7\r\2\2\u00df\u00e0\5\26\f\2\u00e0\u00e1"+
		"\7\37\2\2\u00e1\u00e2\b\16\1\2\u00e2\u00e3\5\26\f\2\u00e3\u00e4\7\"\2"+
		"\2\u00e4\u00e5\b\16\1\2\u00e5\u00e6\5\26\f\2\u00e6\u00ea\b\16\1\2\u00e7"+
		"\u00eb\7\21\2\2\u00e8\u00e9\7\22\2\2\u00e9\u00eb\b\16\1\2\u00ea\u00e7"+
		"\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00f2\3\2\2\2\u00ec"+
		"\u00ed\7 \2\2\u00ed\u00ee\5\26\f\2\u00ee\u00ef\b\16\1\2\u00ef\u00f1\3"+
		"\2\2\2\u00f0\u00ec\3\2\2\2\u00f1\u00f4\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2"+
		"\u00f3\3\2\2\2\u00f3\u00f5\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f5\u00f6\7#"+
		"\2\2\u00f6\u00fa\7\16\2\2\u00f7\u00f8\7\17\2\2\u00f8\u00fb\b\16\1\2\u00f9"+
		"\u00fb\7\20\2\2\u00fa\u00f7\3\2\2\2\u00fa\u00f9\3\2\2\2\u00fb\u00fc\3"+
		"\2\2\2\u00fc\u00fd\b\16\1\2\u00fd\33\3\2\2\2\u00fe\u00ff\7\32\2\2\u00ff"+
		"\u0100\5&\24\2\u0100\u0101\7\33\2\2\u0101\u0105\5*\26\2\u0102\u0103\5"+
		"(\25\2\u0103\u0104\b\17\1\2\u0104\u0106\3\2\2\2\u0105\u0102\3\2\2\2\u0105"+
		"\u0106\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0108\b\17\1\2\u0108\35\3\2\2"+
		"\2\u0109\u010a\7\24\2\2\u010a\u010b\7\25\2\2\u010b\u010c\5\26\f\2\u010c"+
		"\u010d\7\"\2\2\u010d\u010e\b\20\1\2\u010e\u010f\5\26\f\2\u010f\u0116\b"+
		"\20\1\2\u0110\u0111\7 \2\2\u0111\u0112\5\26\f\2\u0112\u0113\b\20\1\2\u0113"+
		"\u0115\3\2\2\2\u0114\u0110\3\2\2\2\u0115\u0118\3\2\2\2\u0116\u0114\3\2"+
		"\2\2\u0116\u0117\3\2\2\2\u0117\u0119\3\2\2\2\u0118\u0116\3\2\2\2\u0119"+
		"\u011a\7#\2\2\u011a\u011b\7\26\2\2\u011b\u011c\7\"\2\2\u011c\u011d\5\16"+
		"\b\2\u011d\u0124\b\20\1\2\u011e\u011f\7 \2\2\u011f\u0120\5\16\b\2\u0120"+
		"\u0121\b\20\1\2\u0121\u0123\3\2\2\2\u0122\u011e\3\2\2\2\u0123\u0126\3"+
		"\2\2\2\u0124\u0122\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0127\3\2\2\2\u0126"+
		"\u0124\3\2\2\2\u0127\u0128\7#\2\2\u0128\u0129\b\20\1\2\u0129\37\3\2\2"+
		"\2\u012a\u012b\7\27\2\2\u012b\u012c\5\26\f\2\u012c\u012d\7\30\2\2\u012d"+
		"\u012e\5\"\22\2\u012e\u0135\b\21\1\2\u012f\u0130\7 \2\2\u0130\u0131\5"+
		"\"\22\2\u0131\u0132\b\21\1\2\u0132\u0134\3\2\2\2\u0133\u012f\3\2\2\2\u0134"+
		"\u0137\3\2\2\2\u0135\u0133\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u0138\3\2"+
		"\2\2\u0137\u0135\3\2\2\2\u0138\u0139\5(\25\2\u0139\u013a\b\21\1\2\u013a"+
					"!\3\2\2\2\u013b\u013c\5\26\f\2\u013c\u013d\7,\2\2\u013d\u013e\5\16\b\2" +
		"\u013e\u013f\b\22\1\2\u013f#\3\2\2\2\u0140\u0141\7\23\2\2\u0141\u0142"+
		"\7\33\2\2\u0142\u0143\5\26\f\2\u0143\u0144\5(\25\2\u0144\u0145\b\23\1"+
		"\2\u0145%\3\2\2\2\u0146\u0147\7!\2\2\u0147\u0154\b\24\1\2\u0148\u0149"+
		"\5\26\f\2\u0149\u0150\b\24\1\2\u014a\u014b\7 \2\2\u014b\u014c\5\26\f\2"+
		"\u014c\u014d\b\24\1\2\u014d\u014f\3\2\2\2\u014e\u014a\3\2\2\2\u014f\u0152"+
		"\3\2\2\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2\2\2\u0151\u0154\3\2\2\2\u0152"+
		"\u0150\3\2\2\2\u0153\u0146\3\2\2\2\u0153\u0148\3\2\2\2\u0154\'\3\2\2\2"+
		"\u0155\u0156\7\34\2\2\u0156\u0157\5\60\31\2\u0157\u0158\b\25\1\2\u0158"+
		")\3\2\2\2\u0159\u015a\5\26\f\2\u015a\u0160\b\26\1\2\u015b\u015c\5,\27"+
		"\2\u015c\u015d\b\26\1\2\u015d\u015f\3\2\2\2\u015e\u015b\3\2\2\2\u015f"+
		"\u0162\3\2\2\2\u0160\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0163\3\2"+
		"\2\2\u0162\u0160\3\2\2\2\u0163\u0164\b\26\1\2\u0164+\3\2\2\2\u0165\u0166"+
		"\7\35\2\2\u0166\u0167\7\36\2\2\u0167\u0168\5\26\f\2\u0168\u0169\7\37\2"+
					"\2\u0169\u016a\5.\30\2\u016a\u016b\7,\2\2\u016b\u016c\b\27\1\2\u016c\u016d" +
		"\5.\30\2\u016d\u016e\b\27\1\2\u016e-\3\2\2\2\u016f\u0170\5\26\f\2\u0170"+
					"\u0171\7+\2\2\u0171\u0172\b\30\1\2\u0172\u0173\5\26\f\2\u0173\u0174\b" +
		"\30\1\2\u0174/\3\2\2\2\u0175\u0176\5\62\32\2\u0176\u017b\b\31\1\2\u0177"+
		"\u0178\7\3\2\2\u0178\u0179\5\62\32\2\u0179\u017a\b\31\1\2\u017a\u017c"+
		"\3\2\2\2\u017b\u0177\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017d\3\2\2\2\u017d"+
		"\u017e\b\31\1\2\u017e\61\3\2\2\2\u017f\u0180\5\64\33\2\u0180\u0185\b\32"+
		"\1\2\u0181\u0182\7\4\2\2\u0182\u0183\5\64\33\2\u0183\u0184\b\32\1\2\u0184"+
		"\u0186\3\2\2\2\u0185\u0181\3\2\2\2\u0185\u0186\3\2\2\2\u0186\u0187\3\2"+
		"\2\2\u0187\u0188\b\32\1\2\u0188\63\3\2\2\2\u0189\u018a\7\5\2\2\u018a\u018c"+
		"\b\33\1\2\u018b\u0189\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018d\3\2\2\2"+
		"\u018d\u018e\5\66\34\2\u018e\u018f\b\33\1\2\u018f\65\3\2\2\2\u0190\u0191"+
		"\58\35\2\u0191\u0192\b\34\1\2\u0192\u019c\3\2\2\2\u0193\u0194\7\"\2\2"+
		"\u0194\u0195\5\60\31\2\u0195\u0196\7#\2\2\u0196\u0197\b\34\1\2\u0197\u019c"+
		"\3\2\2\2\u0198\u0199\5:\36\2\u0199\u019a\b\34\1\2\u019a\u019c\3\2\2\2"+
		"\u019b\u0190\3\2\2\2\u019b\u0193\3\2\2\2\u019b\u0198\3\2\2\2\u019c\67"+
		"\3\2\2\2\u019d\u019e\7\6\2\2\u019e\u01a2\b\35\1\2\u019f\u01a0\7\7\2\2"+
		"\u01a0\u01a2\b\35\1\2\u01a1\u019d\3\2\2\2\u01a1\u019f\3\2\2\2\u01a29\3"+
		"\2\2\2\u01a3\u01a4\5<\37\2\u01a4\u01a5\b\36\1\2\u01a5\u01b6\3\2\2\2\u01a6"+
		"\u01a7\5> \2\u01a7\u01a8\b\36\1\2\u01a8\u01b6\3\2\2\2\u01a9\u01aa\5@!"+
		"\2\u01aa\u01ab\b\36\1\2\u01ab\u01b6\3\2\2\2\u01ac\u01ad\5B\"\2\u01ad\u01ae"+
		"\b\36\1\2\u01ae\u01b6\3\2\2\2\u01af\u01b0\5D#\2\u01b0\u01b1\b\36\1\2\u01b1"+
		"\u01b6\3\2\2\2\u01b2\u01b3\5F$\2\u01b3\u01b4\b\36\1\2\u01b4\u01b6\3\2"+
		"\2\2\u01b5\u01a3\3\2\2\2\u01b5\u01a6\3\2\2\2\u01b5\u01a9\3\2\2\2\u01b5"+
		"\u01ac\3\2\2\2\u01b5\u01af\3\2\2\2\u01b5\u01b2\3\2\2\2\u01b6;\3\2\2\2"+
					"\u01b7\u01b8\5.\30\2\u01b8\u01b9\7,\2\2\u01b9\u01ba\5\16\b\2\u01ba\u01bb" +
					"\b\37\1\2\u01bb\u01c2\3\2\2\2\u01bc\u01bd\5\16\b\2\u01bd\u01be\7,\2\2" +
		"\u01be\u01bf\5.\30\2\u01bf\u01c0\b\37\1\2\u01c0\u01c2\3\2\2\2\u01c1\u01b7"+
		"\3\2\2\2\u01c1\u01bc\3\2\2\2\u01c2\u01c3\3\2\2\2\u01c3\u01c4\b\37\1\2"+
					"\u01c4=\3\2\2\2\u01c5\u01c6\5.\30\2\u01c6\u01c7\7-\2\2\u01c7\u01c8\5\16" +
		"\b\2\u01c8\u01c9\b \1\2\u01c9\u01d0\3\2\2\2\u01ca\u01cb\5\16\b\2\u01cb"+
					"\u01cc\7-\2\2\u01cc\u01cd\5.\30\2\u01cd\u01ce\b \1\2\u01ce\u01d0\3\2\2" +
		"\2\u01cf\u01c5\3\2\2\2\u01cf\u01ca\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d2"+
					"\b \1\2\u01d2?\3\2\2\2\u01d3\u01d4\5.\30\2\u01d4\u01d5\7.\2\2\u01d5\u01d6" +
		"\5\16\b\2\u01d6\u01d7\b!\1\2\u01d7\u01de\3\2\2\2\u01d8\u01d9\5\16\b\2"+
					"\u01d9\u01da\7\60\2\2\u01da\u01db\5.\30\2\u01db\u01dc\b!\1\2\u01dc\u01de" +
		"\3\2\2\2\u01dd\u01d3\3\2\2\2\u01dd\u01d8\3\2\2\2\u01de\u01df\3\2\2\2\u01df"+
					"\u01e0\b!\1\2\u01e0A\3\2\2\2\u01e1\u01e2\5.\30\2\u01e2\u01e3\7/\2\2\u01e3" +
					"\u01e4\5\16\b\2\u01e4\u01e5\b\"\1\2\u01e5\u01ec\3\2\2\2\u01e6\u01e7\5" +
					"\16\b\2\u01e7\u01e8\7\61\2\2\u01e8\u01e9\5.\30\2\u01e9\u01ea\b\"\1\2\u01ea" +
					"\u01ec\3\2\2\2\u01eb\u01e1\3\2\2\2\u01eb\u01e6\3\2\2\2\u01ec\u01ed\3\2" +
					"\2\2\u01ed\u01ee\b\"\1\2\u01eeC\3\2\2\2\u01ef\u01f0\5.\30\2\u01f0\u01f1" +
					"\7\60\2\2\u01f1\u01f2\5\16\b\2\u01f2\u01f3\b#\1\2\u01f3\u01fa\3\2\2\2" +
					"\u01f4\u01f5\5\16\b\2\u01f5\u01f6\7.\2\2\u01f6\u01f7\5.\30\2\u01f7\u01f8" +
					"\b#\1\2\u01f8\u01fa\3\2\2\2\u01f9\u01ef\3\2\2\2\u01f9\u01f4\3\2\2\2\u01fa" +
					"\u01fb\3\2\2\2\u01fb\u01fc\b#\1\2\u01fcE\3\2\2\2\u01fd\u01fe\5.\30\2\u01fe" +
					"\u01ff\7\61\2\2\u01ff\u0200\5\16\b\2\u0200\u0201\b$\1\2\u0201\u0208\3" +
					"\2\2\2\u0202\u0203\5\16\b\2\u0203\u0204\7/\2\2\u0204\u0205\5.\30\2\u0205" +
					"\u0206\b$\1\2\u0206\u0208\3\2\2\2\u0207\u01fd\3\2\2\2\u0207\u0202\3\2" +
					"\2\2\u0208\u0209\3\2\2\2\u0209\u020a\b$\1\2\u020aG\3\2\2\2+Mdt\u0085\u0096" +
					"\u0099\u009e\u00a3\u00a8\u00ae\u00b2\u00b5\u00ba\u00bc\u00c4\u00ce\u00d0" +
					"\u00d7\u00dc\u00ea\u00f2\u00fa\u0105\u0116\u0124\u0135\u0150\u0153\u0160" +
					"\u017b\u0185\u018b\u019b\u01a1\u01b5\u01c1\u01cf\u01dd\u01eb\u01f9\u0207";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}