package question5;

import question2.CompositeQ2Impl;
import question4.InitProcessIncludeImpl;
import compClientDefQ4.InitProcessInclude;
import compClientDefQ5.CompositeInitServise;
import compDefQ2.CompositeQ2;

public class CompositeInitServiseImpl extends CompositeInitServise{

	@Override
	protected CompositeQ2 make_cmp() {
		// TODO Auto-generated method stub
		return new CompositeQ2Impl();
	}

	@Override
	protected InitProcessInclude make_initProcess() {
		// TODO Auto-generated method stub
		return new InitProcessIncludeImpl();
	}



}
