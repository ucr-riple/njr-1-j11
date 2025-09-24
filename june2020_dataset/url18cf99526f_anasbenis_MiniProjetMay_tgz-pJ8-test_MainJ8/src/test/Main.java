package test;

import compClientDefQ3.CompositeQ3;
import compClientDefQ5.Root;
import compDefQ1.Identification;
import compDefQ2.CompositeQ2;
import question1.IdentificationImpl1;
import question1.IdentificationImpl2;
import question2.CompositeQ2Impl;
import question2.impl2.CompositeQ2Impl2;
import question3.CompositeQ3Impl;
import question5.RootImpl;


public class Main {

	public static void main(String[] args) {
		
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
