package patterns.gof.structural.proxy;

public class MathProxy {
	Matematika math = new Matematika();
	
	public int add(int a, int b) {
		return math.slozhit(a, b);
	}
	
	public int sub(int a, int b) {
		return math.vychest(a, b);
	}
	
	public int mul(int a, int b) {
		return math.peremnozhit(a, b);
	}
	
	public float div(int a, int b) {
		float value = 0;
		try {
			value = math.razdelit(a, b);
		} catch (ArithmeticException aex) {
			ProxyClient.addOutput("dividing by zero:");
		}
		return value;
	}
}