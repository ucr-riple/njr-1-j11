package startrekj.hpbasic.functions;

import java.util.Date;

import startrekj.hpbasic.NumericExpression;

public class TIM implements NumericExpression {
	private int selector;
	private TIM(int selector) {
		this.selector = selector;
	}
	public static TIM TIM(int selector) {
		return new TIM(selector);
	}
	public Number evaluate() {
		if ( selector == 0 )
			return new Date().getSeconds();
		return new Date().getMinutes();
	}
	public String toString() {
		return "TIM(" + selector + ")";
	};
}
