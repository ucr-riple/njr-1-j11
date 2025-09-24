package patterns.gof.helpers;

public abstract class Client {
	private static String output;
	
	public abstract void main();
	
	public void main(String object) {
		printAll(object);
	}
	
	private void printAll(String className) {
		System.out.print("Testing pattern: " + className);
		
		System.out.print(output);
		
	    printSplitter();
	}

	private void printSplitter() {
	    System.out.print("\n----------------------------------------\n");
	}
	
	public static void addOutput(String out) {
		output += "\nData: " + out;
	}
	
	public static void cleanOutput() {
		output = "";
	}
}