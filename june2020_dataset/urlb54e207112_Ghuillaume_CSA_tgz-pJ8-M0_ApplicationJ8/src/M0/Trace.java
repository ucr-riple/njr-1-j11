package M0;

import M2.Interface;

public class Trace {
	
	public static void printInterfaceActivation(Interface iface, String message) {

		System.out.println("In " + iface.getParent().getName() + " : "
				+ iface.getName() + " (" + iface.getClass().getSuperclass().getSimpleName() 
				+ ") received message : " + message);
	}
	
	public static void printMessage(String message) {
		System.out.println(message);
	}
	
	public static void printError(String message) {
		System.err.println(message);
	}

}
