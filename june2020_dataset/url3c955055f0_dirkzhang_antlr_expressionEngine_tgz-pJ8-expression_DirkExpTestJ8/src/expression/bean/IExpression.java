package expression.bean;


/**
 * interface : expression bean
 * @author dirk.zhang
 * 2013-3-13 上午11:05:03
 */
public interface IExpression {
	
	public void accept(IExpressionVisitor visitor);
}
