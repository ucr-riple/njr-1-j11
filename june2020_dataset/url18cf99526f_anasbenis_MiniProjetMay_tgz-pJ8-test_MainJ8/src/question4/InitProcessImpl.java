package question4;

import compDefQ4.InitProcess;

public class InitProcessImpl extends InitProcess {

	@Override
	protected Init make_doInit() {
		// TODO Auto-generated method stub
		return new Init() {

			@Override
			public void startInitStep() {
				System.out.println("Voici une implantation particuliĂ¨re.");
				String id = requires().initReq().getIdForNewEval();
				requires().doAnnounce().startProcessFor(id);
			}
		};
	}

}
