package question1;

import compDefQ1.Identification;

public class IdentificationImpl2 extends Identification{

	@Override
	protected Identifiable make_getID() {
		// TODO Auto-generated method stub
		return new Identifiable(){

			@Override
			public String getIDLabel() {
				// TODO Auto-generated method stub
				String ID="Bennis 21307126";
				return ID;
			}
			
		};
	}

}
