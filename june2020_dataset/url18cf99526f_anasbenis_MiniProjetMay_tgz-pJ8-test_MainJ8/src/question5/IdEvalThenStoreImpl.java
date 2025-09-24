package question5;

import question3.EvalRequest;
import question4.InitRequest;
import compDefQ5.IdEvalThenStore;

public class IdEvalThenStoreImpl extends IdEvalThenStore{

	@Override
	protected EvalRequest make_evalReq() {
		// TODO Auto-generated method stub
		return new EvalRequest(){

			@Override
			public double eval() {
				// TODO Auto-generated method stub
				return requires().compReq().eval(requires().idReq().getIDLabel());
			}
			
		};
	}

	@Override
	protected InitRequest make_initReq() {
		// TODO Auto-generated method stub
		return new InitRequest(){

			@Override
			public String getIdForNewEval() {
				// TODO Auto-generated method stub
				return requires().idReq().getIDLabel();
			}
			
		};
	}

}
