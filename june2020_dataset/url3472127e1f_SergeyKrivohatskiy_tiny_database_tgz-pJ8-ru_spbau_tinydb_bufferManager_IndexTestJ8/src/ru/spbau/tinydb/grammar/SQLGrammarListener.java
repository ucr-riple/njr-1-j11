// Generated from ../src/SQLGrammar.g4 by ANTLR 4.4

    package ru.spbau.tinydb.grammar;

    import java.util.*;
    import ru.spbau.tinydb.queries.*;
    import ru.spbau.tinydb.expressions.*;
    import ru.spbau.tinydb.expressions.bool.*;
    import ru.spbau.tinydb.expressions.comparison.*;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SQLGrammarParser}.
 */
public interface SQLGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#idSuffix}.
	 * @param ctx the parse tree
	 */
	void enterIdSuffix(@NotNull SQLGrammarParser.IdSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#idSuffix}.
	 * @param ctx the parse tree
	 */
	void exitIdSuffix(@NotNull SQLGrammarParser.IdSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(@NotNull SQLGrammarParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(@NotNull SQLGrammarParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#selectFrom}.
	 * @param ctx the parse tree
	 */
	void enterSelectFrom(@NotNull SQLGrammarParser.SelectFromContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#selectFrom}.
	 * @param ctx the parse tree
	 */
	void exitSelectFrom(@NotNull SQLGrammarParser.SelectFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#updateSet}.
	 * @param ctx the parse tree
	 */
	void enterUpdateSet(@NotNull SQLGrammarParser.UpdateSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#updateSet}.
	 * @param ctx the parse tree
	 */
	void exitUpdateSet(@NotNull SQLGrammarParser.UpdateSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#lessExpression}.
	 * @param ctx the parse tree
	 */
	void enterLessExpression(@NotNull SQLGrammarParser.LessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#lessExpression}.
	 * @param ctx the parse tree
	 */
	void exitLessExpression(@NotNull SQLGrammarParser.LessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpression(@NotNull SQLGrammarParser.ComparisonExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpression(@NotNull SQLGrammarParser.ComparisonExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#joinOnExpression}.
	 * @param ctx the parse tree
	 */
	void enterJoinOnExpression(@NotNull SQLGrammarParser.JoinOnExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#joinOnExpression}.
	 * @param ctx the parse tree
	 */
	void exitJoinOnExpression(@NotNull SQLGrammarParser.JoinOnExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#deleteFrom}.
	 * @param ctx the parse tree
	 */
	void enterDeleteFrom(@NotNull SQLGrammarParser.DeleteFromContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#deleteFrom}.
	 * @param ctx the parse tree
	 */
	void exitDeleteFrom(@NotNull SQLGrammarParser.DeleteFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#secondLevelId}.
	 * @param ctx the parse tree
	 */
	void enterSecondLevelId(@NotNull SQLGrammarParser.SecondLevelIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#secondLevelId}.
	 * @param ctx the parse tree
	 */
	void exitSecondLevelId(@NotNull SQLGrammarParser.SecondLevelIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(@NotNull SQLGrammarParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(@NotNull SQLGrammarParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#varCharType}.
	 * @param ctx the parse tree
	 */
	void enterVarCharType(@NotNull SQLGrammarParser.VarCharTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#varCharType}.
	 * @param ctx the parse tree
	 */
	void exitVarCharType(@NotNull SQLGrammarParser.VarCharTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(@NotNull SQLGrammarParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(@NotNull SQLGrammarParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#booleanFactor}.
	 * @param ctx the parse tree
	 */
	void enterBooleanFactor(@NotNull SQLGrammarParser.BooleanFactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#booleanFactor}.
	 * @param ctx the parse tree
	 */
	void exitBooleanFactor(@NotNull SQLGrammarParser.BooleanFactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(@NotNull SQLGrammarParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(@NotNull SQLGrammarParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(@NotNull SQLGrammarParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(@NotNull SQLGrammarParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#equalExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualExpression(@NotNull SQLGrammarParser.EqualExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#equalExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualExpression(@NotNull SQLGrammarParser.EqualExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#firstLevelId}.
	 * @param ctx the parse tree
	 */
	void enterFirstLevelId(@NotNull SQLGrammarParser.FirstLevelIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#firstLevelId}.
	 * @param ctx the parse tree
	 */
	void exitFirstLevelId(@NotNull SQLGrammarParser.FirstLevelIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#whereCondition}.
	 * @param ctx the parse tree
	 */
	void enterWhereCondition(@NotNull SQLGrammarParser.WhereConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#whereCondition}.
	 * @param ctx the parse tree
	 */
	void exitWhereCondition(@NotNull SQLGrammarParser.WhereConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(@NotNull SQLGrammarParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(@NotNull SQLGrammarParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(@NotNull SQLGrammarParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(@NotNull SQLGrammarParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertInto(@NotNull SQLGrammarParser.InsertIntoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertInto(@NotNull SQLGrammarParser.InsertIntoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#lessOrEqualExpression}.
	 * @param ctx the parse tree
	 */
	void enterLessOrEqualExpression(@NotNull SQLGrammarParser.LessOrEqualExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#lessOrEqualExpression}.
	 * @param ctx the parse tree
	 */
	void exitLessOrEqualExpression(@NotNull SQLGrammarParser.LessOrEqualExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(@NotNull SQLGrammarParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(@NotNull SQLGrammarParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#notEqualExpression}.
	 * @param ctx the parse tree
	 */
	void enterNotEqualExpression(@NotNull SQLGrammarParser.NotEqualExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#notEqualExpression}.
	 * @param ctx the parse tree
	 */
	void exitNotEqualExpression(@NotNull SQLGrammarParser.NotEqualExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#varCharLiteral}.
	 * @param ctx the parse tree
	 */
	void enterVarCharLiteral(@NotNull SQLGrammarParser.VarCharLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#varCharLiteral}.
	 * @param ctx the parse tree
	 */
	void exitVarCharLiteral(@NotNull SQLGrammarParser.VarCharLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#createIndex}.
	 * @param ctx the parse tree
	 */
	void enterCreateIndex(@NotNull SQLGrammarParser.CreateIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#createIndex}.
	 * @param ctx the parse tree
	 */
	void exitCreateIndex(@NotNull SQLGrammarParser.CreateIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpression(@NotNull SQLGrammarParser.BooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpression(@NotNull SQLGrammarParser.BooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(@NotNull SQLGrammarParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(@NotNull SQLGrammarParser.ScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilter(@NotNull SQLGrammarParser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilter(@NotNull SQLGrammarParser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(@NotNull SQLGrammarParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(@NotNull SQLGrammarParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#greaterExpression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterExpression(@NotNull SQLGrammarParser.GreaterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#greaterExpression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterExpression(@NotNull SQLGrammarParser.GreaterExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#doubleLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDoubleLiteral(@NotNull SQLGrammarParser.DoubleLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#doubleLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDoubleLiteral(@NotNull SQLGrammarParser.DoubleLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#greaterOrEqualExpression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterOrEqualExpression(@NotNull SQLGrammarParser.GreaterOrEqualExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#greaterOrEqualExpression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterOrEqualExpression(@NotNull SQLGrammarParser.GreaterOrEqualExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(@NotNull SQLGrammarParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(@NotNull SQLGrammarParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#selectionTable}.
	 * @param ctx the parse tree
	 */
	void enterSelectionTable(@NotNull SQLGrammarParser.SelectionTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#selectionTable}.
	 * @param ctx the parse tree
	 */
	void exitSelectionTable(@NotNull SQLGrammarParser.SelectionTableContext ctx);
}