package question3;

import question2.PrintImpl;
import compClientDefQ3.CompositeQ3;
import compDefQ2.Print;
import compDefQ3.StartEval;
import compDefQ3.ValidateComp;

public class CompositeQ3Impl extends CompositeQ3 {

	@Override
	protected Print make_pr() {
		// TODO Auto-generated method stub
		return new PrintImpl();
	}

	@Override
	protected StartEval make_startEv() {
		// TODO Auto-generated method stub
		return new StartEvalImpl();
	}

	@Override
	protected ValidateComp make_validate() {
		// TODO Auto-generated method stub
		return new ValidateImpl();
	}


}
