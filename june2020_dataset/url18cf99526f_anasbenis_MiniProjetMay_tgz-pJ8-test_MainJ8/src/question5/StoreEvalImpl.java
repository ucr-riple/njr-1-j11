package question5;

import compDefQ5.StoreEval;

public class StoreEvalImpl extends StoreEval {

	@Override
	protected StorableEval make_storeService() {
		// TODO Auto-generated method stub
		return new StorableEval(){

			@Override
			public void store(String name, double value) {
				System.out.println("Sauvegarde reussi");
				
			}
			
		};
	}

}
