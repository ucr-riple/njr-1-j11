package expression.bean.func;
import java.util.List;

import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class FunctionExpression implements IExpression{

	private String funcName;
	private List<IExpression> arguments;
//	private IExpression argument;
	
	public List<IExpression> getArguments() {
		return arguments;
	}

	public void setArguments(List<IExpression> arguments) {
		this.arguments = arguments;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public FunctionExpression(String funcName,List<IExpression> arguments){
		this.funcName = funcName;
		this.arguments = arguments;
	}

	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
}
