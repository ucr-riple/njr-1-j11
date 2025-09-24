package expression.bean.value;

import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class IdentifierValue implements IExpression{


	public IdentifierValue(String identifier){
		this.identifier = identifier;
	}
	private String identifier;
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	
	

}
