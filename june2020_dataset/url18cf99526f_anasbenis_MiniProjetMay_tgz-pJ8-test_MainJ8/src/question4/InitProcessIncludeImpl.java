package question4;

import compClientDefQ4.InitProcessFollower;
import compClientDefQ4.InitProcessInclude;
import compDefQ4.InitProcess;

public class InitProcessIncludeImpl extends InitProcessInclude{

	@Override
	protected InitProcess make_initP() {
		// TODO Auto-generated method stub
		return new InitProcessImpl();
	}

	@Override
	protected InitProcessFollower make_initPFollower() {
		// TODO Auto-generated method stub
		return new InitProcessFollowerImpl();
	}

}
