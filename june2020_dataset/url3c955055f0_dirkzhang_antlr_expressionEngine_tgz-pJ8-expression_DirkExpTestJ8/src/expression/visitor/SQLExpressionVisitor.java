package expression.visitor;
import expression.SQLWriter;
import expression.bean.BinaryExpression;
import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;
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

public class SQLExpressionVisitor implements IExpressionVisitor{

	SQLWriter sqlWriter = new SQLWriter();
	
	public String getResult(){
		return sqlWriter.toString();
	}

	private void visitBinary(BinaryExpression exp){
		exp.getLeftExpression().accept(this);
		sqlWriter.write(" ");
		sqlWriter.write(exp.getStrExpression());
		sqlWriter.write(" ");
		exp.getRightExpression().accept(this);
	}
	
	@Override
	public void visit(Addition bean) {
		this.visitBinary(bean);
	}

	@Override
	public void visit(BitwiseAnd bean) {
		this.visitBinary(bean);
	}

	@Override
	public void visit(BitwiseOr bean) {

		this.visitBinary(bean);
	}

	@Override
	public void visit(BitwiseXor bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(Concat bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(Division bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(Multi bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(Subtract bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(AndExpression bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(OrExpression bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(Between bean) {
//		this.visitBinary(bean);
		//TODO:between
	}

	@Override
	public void visit(EqualsTo bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(GreaterThan bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(GreaterThanEquals bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(InExpression bean) {
//		this.visitBinary(bean);
		//TODO:IN
	}

	@Override
	public void visit(LikeExpression bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(MinorThan bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(MinorThanEquals bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(NotEqualsTo bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(FinalValue bean) {
//		this.visitBinary(bean);
		//TODO:value
		sqlWriter.write(bean.getText());
	}

	@Override
	public void visit(ParamValue bean) {
//		this.visitBinary(bean);
		sqlWriter.write(bean.getTransformParam());
	}

	@Override
	public void visit(IdentifierValue bean) {
//		this.visitBinary(bean);
		sqlWriter.write(bean.getIdentifier());
	}

	@Override
	public void visit(Module bean) {
		this.visitBinary(bean);
		
	}

	@Override
	public void visit(FunctionExpression bean) {
		sqlWriter.write(bean.getFuncName());
		sqlWriter.write("(");
		int i = 0;
		for (IExpression exp : bean.getArguments()) {
			exp.accept(this);
			if(++i<bean.getArguments().size())
				sqlWriter.write(",");
		}
		sqlWriter.write(")");
	}

	@Override
	public void visit(BracketValue bean) {
		sqlWriter.write("(");
		bean.getExpression().accept(this);
		sqlWriter.write(")");
	}
	


}
