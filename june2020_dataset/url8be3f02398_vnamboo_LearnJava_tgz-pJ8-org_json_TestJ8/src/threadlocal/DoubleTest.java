package threadlocal;
public class DoubleTest {
public static void main(String[] args) {
	double val1=100d;
	if(val1==Double.NaN){
		System.out.println("True");
	}
	val1=100d/0;
	if(Double.isInfinite(val1)){
		System.out.println("True");
	}
	Double val2=.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009d/0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009d;
	if(val2.equals(Double.NaN)){
		System.out.println("True111");
	}
	System.out.println(1.5566d - (10/Math.pow(10, 4d)));
}
}
