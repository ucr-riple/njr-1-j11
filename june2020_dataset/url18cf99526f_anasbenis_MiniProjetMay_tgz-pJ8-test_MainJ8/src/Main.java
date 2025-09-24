import question5.RootImpl;

import compClientDefQ5.Root;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test Q1
		//Identification.Component root = (new IdentificationImpl1()).newComponent();
		//System.out.println(root.getID().getIDLabel());
		
		//Identification.Component root2 = (new IdentificationImpl2()).newComponent();
		//System.out.println(root2.getID().getIDLabel());
		
		//CompositeQ2.Component root3=(new CompositeQ2Impl2()).newComponent();
		//root3.announceService().startProcessFor("Bennis");
		
		//Test Final
		Root.Component rt= (new RootImpl()).newComponent();
		rt.init().startInitStep();
		rt.eval().start();
		
	}

}
