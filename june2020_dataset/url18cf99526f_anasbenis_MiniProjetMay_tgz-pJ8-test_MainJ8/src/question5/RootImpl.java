package question5;

import question1.IdentificationImpl1;
import question3.CompositeQ3Impl;
import compClientDefQ3.CompositeQ3;
import compClientDefQ5.CompositeInitServise;
import compClientDefQ5.Root;
import compDefQ1.Identification;
import compDefQ5.ComputeEval;
import compDefQ5.IdEvalThenStore;
import compDefQ5.StoreEval;

public class RootImpl extends Root{

	@Override
	protected CompositeInitServise make_composite1() {
		// TODO Auto-generated method stub
		return new CompositeInitServiseImpl();
	}

	@Override
	protected CompositeQ3 make_composite2() {
		// TODO Auto-generated method stub
		return new CompositeQ3Impl();
	}

	@Override
	protected IdEvalThenStore make_idEvalThenStore() {
		// TODO Auto-generated method stub
		return new IdEvalThenStoreImpl();
	}

	@Override
	protected StoreEval make_strEval() {
		// TODO Auto-generated method stub
		return new StoreEvalImpl();
	}

	@Override
	protected ComputeEval make_cmpEval() {
		// TODO Auto-generated method stub
		return new ComputeEvalImpl();
	}

	@Override
	protected Identification make_ident() {
		// TODO Auto-generated method stub
		return new IdentificationImpl1();
	}

}
