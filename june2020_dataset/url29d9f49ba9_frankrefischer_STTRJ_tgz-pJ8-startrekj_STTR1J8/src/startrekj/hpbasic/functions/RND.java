package startrekj.hpbasic.functions;

import java.util.Random;

import startrekj.hpbasic.NumericExpression;

public class RND implements NumericExpression {
	private RND() {
	}
	public static RND RND() {
		return new RND();
	}

	public Number evaluate() {
		return new Random().nextDouble();
	}
	@Override
	public String toString() {
		return "RND()";
	}
}
