package expression.bean.condition;
import expression.bean.BinaryExpression;
import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class AndExpression extends BinaryExpression {
	public AndExpression(IExpression leftExp, IExpression rightExp) {
		super(leftExp, rightExp);
	}


	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	

	@Override
	public String getStrExpression() {
		return "AND";
	}

}
