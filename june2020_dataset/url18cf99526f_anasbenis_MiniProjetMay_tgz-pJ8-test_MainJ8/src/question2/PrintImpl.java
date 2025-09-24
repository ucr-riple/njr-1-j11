package question2;

import compDefQ2.Print;

public class PrintImpl extends Print {

	@Override
	protected PrintingRequest make_pReq() {
		// TODO Auto-generated method stub
		return new PrintingRequest() {
			
			@Override
			public void requestToPrint(String info) {
				System.out.println(info);
			}
		};
	}


}
