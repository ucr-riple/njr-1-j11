package expression.bean.arithmetic;
import expression.bean.BinaryExpression;
import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

@Deprecated
public class BitwiseAnd extends BinaryExpression{

	public BitwiseAnd(IExpression leftExp, IExpression rightExp) {
		super(leftExp, rightExp);
	}

	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String getStrExpression() {
		return "&";
	}

}
