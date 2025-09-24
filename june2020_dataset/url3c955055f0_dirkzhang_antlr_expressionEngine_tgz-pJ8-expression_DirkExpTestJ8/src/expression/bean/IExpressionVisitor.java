package expression.bean;
import expression.bean.arithmetic.Addition;
import expression.bean.arithmetic.BitwiseAnd;
import expression.bean.arithmetic.BitwiseOr;
import expression.bean.arithmetic.BitwiseXor;
import expression.bean.arithmetic.Concat;
import expression.bean.arithmetic.Division;
import expression.bean.arithmetic.Module;
import expression.bean.arithmetic.Multi;
import expression.bean.arithmetic.Subtract;
import expression.bean.condition.AndExpression;
import expression.bean.condition.OrExpression;
import expression.bean.func.FunctionExpression;
import expression.bean.relation.Between;
import expression.bean.relation.EqualsTo;
import expression.bean.relation.GreaterThan;
import expression.bean.relation.GreaterThanEquals;
import expression.bean.relation.InExpression;
import expression.bean.relation.LikeExpression;
import expression.bean.relation.MinorThan;
import expression.bean.relation.MinorThanEquals;
import expression.bean.relation.NotEqualsTo;
import expression.bean.value.BracketValue;
import expression.bean.value.FinalValue;
import expression.bean.value.IdentifierValue;
import expression.bean.value.ParamValue;

/**
 *  interface : expression visitor
 * @author dirk.zhang
 * 2013-3-13 上午11:05:13
 */
public interface IExpressionVisitor {
	void visit(Addition bean);
	void visit(BitwiseAnd bean);
	void visit(BitwiseOr bean);
	void visit(BitwiseXor bean);
	void visit(Concat bean);
	void visit(Division bean);
	void visit(Multi bean);
	void visit(Subtract bean);
	void visit(AndExpression bean);
	void visit(OrExpression bean);
	void visit(Between bean);
	void visit(EqualsTo bean);
	void visit(GreaterThan bean);
	void visit(GreaterThanEquals bean);
	void visit(InExpression bean);
	void visit(LikeExpression bean);
	void visit(MinorThan bean);
	void visit(MinorThanEquals bean);
	void visit(NotEqualsTo bean);
	void visit(FinalValue bean);
	void visit(ParamValue bean);
	void visit(IdentifierValue bean);
	void visit(Module bean);
	void visit(FunctionExpression bean);
	void visit(BracketValue bean);
	
	
//	T visit(Addition bean);
//	T visit(BitwiseAnd bean);
//	T visit(BitwiseOr bean);
//	T visit(BitwiseXor bean);
//	T visit(Concat bean);
//	T visit(Division bean);
//	T visit(Multi bean);
//	T visit(Subtract bean);
//	T visit(AndExpression bean);
//	T visit(OrExpression bean);
//	T visit(Between bean);
//	T visit(EqualsTo bean);
//	T visit(GreaterThan bean);
//	T visit(GreaterThanEquals bean);
//	T visit(InExpression bean);
//	T visit(LikeExpression bean);
//	T visit(MinorThan bean);
//	T visit(MinorThanEquals bean);
//	T visit(NotEqualsTo bean);
//	T visit(FinalValue bean);
//	T visit(ParamValue bean);
//	T visit(IdentifierValue bean);
//	T visit(Module bean);
}
