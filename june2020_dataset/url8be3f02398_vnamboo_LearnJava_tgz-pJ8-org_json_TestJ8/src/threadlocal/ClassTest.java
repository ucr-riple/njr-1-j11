package threadlocal;

import java.util.ArrayList;
import java.util.List;

public class ClassTest {
	private List<String> myStrings=new ArrayList<String>();
	private StaticInner myStaticInnner=new StaticInner();
	private NonStaticInner myNonStaticInnner=new NonStaticInner();
	
	
	public static void main(String[] args) {
		
	}
	
	private static class StaticInner {
		private void printStrings(){
//			for(String string:myStrings){
//				System.out.println(string);
//			}
		}
	}
	
	private class NonStaticInner{
		private void printStrings(){
			for(String string:myStrings){
				System.out.println(string);
			}
		}
	}
	
}
