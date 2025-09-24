package expression.bean.relation;
import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class Between implements IExpression{
	private IExpression leftExpression;
	private IExpression betweenExpressionBegin;
	private IExpression betweenExpressionEnd;
	
	public Between(IExpression left,IExpression begin,IExpression end){
		this.leftExpression = left;
		this.betweenExpressionBegin = begin;
		this.betweenExpressionEnd = end;
	}

	public IExpression getLeftExpression() {
		return leftExpression;
	}

	public void setLeftExpression(IExpression leftExpression) {
		this.leftExpression = leftExpression;
	}

	public IExpression getBetweenExpressionBegin() {
		return betweenExpressionBegin;
	}

	public void setBetweenExpressionBegin(IExpression betweenExpressionBegin) {
		this.betweenExpressionBegin = betweenExpressionBegin;
	}

	public IExpression getBetweenExpressionEnd() {
		return betweenExpressionEnd;
	}

	public void setBetweenExpressionEnd(IExpression betweenExpressionEnd) {
		this.betweenExpressionEnd = betweenExpressionEnd;
	}


	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	

}
