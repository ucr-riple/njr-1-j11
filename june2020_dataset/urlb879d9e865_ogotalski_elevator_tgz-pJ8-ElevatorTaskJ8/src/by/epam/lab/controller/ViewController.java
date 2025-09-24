package by.epam.lab.controller;

public enum ViewController {
     CONSOLE, UI;
     public static IView getController(ViewController controller){
    	 switch (controller) {
		case UI:
			return new ActionViewController();
		default:
			return new ConsoleViewController();
		}
     }
}
