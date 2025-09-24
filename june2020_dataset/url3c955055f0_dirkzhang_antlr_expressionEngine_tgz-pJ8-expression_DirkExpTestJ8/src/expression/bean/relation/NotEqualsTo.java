package expression.bean.relation;

import expression.bean.BinaryExpression;
import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class NotEqualsTo extends BinaryExpression{

	public NotEqualsTo(IExpression leftExp, IExpression rightExp) {
		super(leftExp, rightExp);
	}


	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String getStrExpression() {
		return "!=";
	}

}
