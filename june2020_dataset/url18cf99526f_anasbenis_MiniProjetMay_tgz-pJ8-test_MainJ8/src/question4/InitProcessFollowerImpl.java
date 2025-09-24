package question4;

import compClientDefQ4.InitProcessFollower;

public class InitProcessFollowerImpl extends InitProcessFollower{

	@Override
	protected Init make_doInitFollower() {
		// TODO Auto-generated method stub
		return new Init(){

			@Override
			public void startInitStep() {
				// TODO Auto-generated method stub
				System.out.println("Voici le premier msg");
				requires().getInit().startInitStep();
			}
			
		};
	}

}
