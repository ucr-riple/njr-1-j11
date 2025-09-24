package question1;

import compDefQ1.Identification;

public class IdentificationImpl1 extends Identification {

	@Override
	protected Identifiable make_getID() {
		// TODO Auto-generated method stub
		return new Identifiable(){

			@Override
			public String getIDLabel() {
				// TODO Auto-generated method stub
				String ID="Bennis/Med Anas/3-1";
				return ID;
			}
			
		};
	}

}
