package expression.bean.value;

import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class FinalValue implements IExpression{


	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	
	public FinalValue(String text,ValueType type){
		this.text = text;
		this.type = type;
	}
	
	private String text;
	private ValueType type;
	
	
	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public ValueType getType() {
		return type;
	}


	public void setType(ValueType type) {
		this.type = type;
	}

	public static enum ValueType{
		Integer,
		Decimal,
		//Decimal_Literal,
		DateTime,
		Boolean,
		String
	}
}


