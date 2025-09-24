package expression.bean.relation;

import java.util.List;

import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class InExpression implements IExpression{

	private IExpression leftExpression;
	private List<IExpression> expItemList;
	
	public InExpression(IExpression left,List<IExpression> exps){
		this.leftExpression = left;
		this.expItemList = exps;
	}
	
	public IExpression getLeftExpression() {
		return leftExpression;
	}

	public void setLeftExpression(IExpression leftExpression) {
		this.leftExpression = leftExpression;
	}

	public List<IExpression> getExpItemList() {
		return expItemList;
	}

	public void setExpItemList(List<IExpression> expItemList) {
		this.expItemList = expItemList;
	}
	

	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
}
