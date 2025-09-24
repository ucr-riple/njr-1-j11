package expression.bean.value;

import expression.bean.IExpression;
import expression.bean.IExpressionVisitor;

public class ParamValue implements IExpression{
	
	public ParamValue(String param){
		this.param = param;
	}
	/**
	 * port id
	 */
	private String param;
	private String convertStr;
	
	public String getConvertStr() {
		return convertStr;
	}

	public void setConvertStr(String convertStr) {
		this.convertStr = convertStr;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public String getTransformParam(){
		//TODO:转换为正确字符串
		return "table.column";
	}


	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
}
