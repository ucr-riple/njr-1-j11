package question5;

import compDefQ5.ComputeEval;

public class ComputeEvalImpl extends ComputeEval{

	@Override
	protected ComputableEval make_evalService() {
		// TODO Auto-generated method stub
		return new ComputableEval(){

			@Override
			public double eval(String subject) {
				// TODO Auto-generated method stub
				//Le calcule aléatoire de la note 
				int lower = 0;
				int higher = 20;
				int random = (int)(Math.random() * (higher-lower)) + lower;
				return random;
			}
			
		};
	}

}
