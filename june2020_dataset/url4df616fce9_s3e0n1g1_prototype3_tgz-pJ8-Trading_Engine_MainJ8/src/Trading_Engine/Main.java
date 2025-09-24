package Trading_Engine;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Test.AllTestSuite;

import gui.Mainmenu;

public class Main {
	public static void main(String[]args){
		Result result = JUnitCore.runClasses(AllTestSuite.class);
		System.out.println("Finish running All the tests!!!");
		System.out.println("total Junit test run count: " + result.getRunCount());
		if(result.wasSuccessful()){
			System.out.println("All tests are passed!!!");
		}else{
			System.out.println("Tests Failed!!!");
			System.out.println("Failure count: " + result.getFailureCount());
			for(Failure failure : result.getFailures()){
				System.out.println(failure.toString());
			}
		}
		
		myDatabase db = new myDatabase();
		new Mainmenu(db,result);
	}
}
