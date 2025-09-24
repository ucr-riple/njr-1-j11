package expression.bean.arithmetic;

import expression.bean.BinaryExpression;
import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;



public class Addition extends BinaryExpression {

	public Addition(IExpression leftExp, IExpression rightExp) {
		super(leftExp, rightExp);
	}

	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getStrExpression() {
		return "+";
	}

}
