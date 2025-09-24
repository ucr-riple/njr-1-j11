package SampleFinallyCheck;

import java.util.HashSet;

public class AnotherTry {
	
	public static void main(String[] args) {
		HashSet<String> mySet=null;
		mySet=getMeSomeThing();
		System.out.println(mySet.size());
		
	}
	private static HashSet<String> getMeSomeThing(){
		HashSet<String> mySet=new HashSet<String>();
		mySet.add("Hello");
		try{
			return mySet;
		}finally {
			mySet.add("World");
		}
	}
}
