package patterns.gof.behavioral.interpreter;

public class InterpreterContext {
	public String getBinary(int decimal) {
		return Integer.toBinaryString(decimal);
	}
	
	public String getHexadecimal(int decimal) {
		return Integer.toHexString(decimal);
	}
}