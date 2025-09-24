package question3;

import compDefQ3.ValidateComp;

public class ValidateImpl extends ValidateComp {

	@Override
	protected ValidateRequest make_validReq() {
		// TODO Auto-generated method stub
		return new ValidateRequest() {
			
			@Override
			public double evalAndValidate() {
				Double res = requires().evReq().eval();
				if (res > 20.0) res = 20.0;
				else if (res<0.0) res = 0.0;
				return res;
			}
		};
	}

}
