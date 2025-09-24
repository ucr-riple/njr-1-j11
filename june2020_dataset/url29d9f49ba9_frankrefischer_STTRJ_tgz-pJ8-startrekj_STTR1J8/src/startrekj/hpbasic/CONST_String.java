package startrekj.hpbasic;


public class CONST_String implements StringExpression {
	private String string;
	public CONST_String(String string) {
		this.string = string;
	}
	public String evaluate() {
		return string;
	}
	@Override
	public String toString() {
		return "\"" + string + "\"";
	}
}
