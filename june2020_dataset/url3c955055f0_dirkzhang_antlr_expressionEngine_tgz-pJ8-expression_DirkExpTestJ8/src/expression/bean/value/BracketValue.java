package expression.bean.value;

import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class BracketValue implements IExpression{

	private IExpression expression;
	
	public IExpression getExpression() {
		return expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public BracketValue(IExpression expression){
		this.expression = expression;
	}
	
	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}

}
