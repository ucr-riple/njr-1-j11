import by.epam.lab.controller.Configuration;
import by.epam.lab.controller.IView;
import by.epam.lab.controller.ViewController;



public class ElevatorTask {
	public static void main(String[] args) {
		IView view;
		if (Configuration.getConfiguration().getAnimationBoost() == 0)
			view = ViewController.getController(ViewController.CONSOLE);
		else
			view = ViewController.getController(ViewController.UI);
		view.runView();
	}

}
