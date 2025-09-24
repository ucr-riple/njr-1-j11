package math;

import java.math.BigDecimal;

public class FPTest {
	private static final String EMPTY="";
	public static void main(String[] args) {
		BigDecimal val=new BigDecimal("0.1");
		BigDecimal sum=new BigDecimal("0.0");;
		for (int i=0;i<10;i++){
			sum=sum.add(val);
		}
		System.out.println(sum.toPlainString());
		
		String protType=EMPTY;
		if (protType=="" || protType == null) {
			System.out.println("Invalid value");
		}
		else
		{
			System.out.println("Correct value");
		}
	}
}
