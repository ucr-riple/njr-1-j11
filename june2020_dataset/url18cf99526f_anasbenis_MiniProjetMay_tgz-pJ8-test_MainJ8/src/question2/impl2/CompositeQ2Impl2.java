package question2.impl2;


import question2.InitPrintImpl;
import compClientDefQ2.InitPrint;
import compDefQ2.CompositeQ2;
import compDefQ2.Print;

public class CompositeQ2Impl2 extends CompositeQ2 {

	@Override
	protected InitPrint make_init() {
		// TODO Auto-generated method stub
		return new InitPrintImpl();
	}

	@Override
	protected Print make_pr() {
		// TODO Auto-generated method stub
		return new PrintImpl2();
	}

}
