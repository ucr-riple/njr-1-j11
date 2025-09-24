package question2;

import compClientDefQ2.InitPrint;

public class InitPrintImpl extends InitPrint {

	@Override
	protected AnnounceInit make_announceService() {
		// TODO Auto-generated method stub
		return new AnnounceInit(){

			@Override
			public void startProcessFor(String subject) {
				String msg="début du processus dévaluation pour le candidat : "+subject;
				requires().printingService().requestToPrint(msg);
			}
			
		};
	}

}
