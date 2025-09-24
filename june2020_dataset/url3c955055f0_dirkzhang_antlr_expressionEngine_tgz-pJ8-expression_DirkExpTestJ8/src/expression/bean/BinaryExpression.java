package expression.bean;


public abstract class BinaryExpression implements IExpression {

	protected IExpression leftExpression;
	protected IExpression rightExpression;
	
	public BinaryExpression(IExpression leftExp,IExpression rightExp){
		this.leftExpression = leftExp;
		this.rightExpression = rightExp;
	}
	
	public IExpression getLeftExpression() {
		return leftExpression;
	}
	public void setLeftExpression(IExpression leftExpression) {
		this.leftExpression = leftExpression;
	}
	public IExpression getRightExpression() {
		return rightExpression;
	}
	public void setRightExpression(IExpression rightExpression) {
		this.rightExpression = rightExpression;
	}
	
	public abstract String getStrExpression();

}
