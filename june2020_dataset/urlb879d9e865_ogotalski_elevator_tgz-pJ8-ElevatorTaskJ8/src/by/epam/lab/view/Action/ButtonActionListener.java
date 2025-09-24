package by.epam.lab.view.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.epam.lab.controller.IAction;

public class ButtonActionListener implements ActionListener {
	private IAction viewController;

	public static enum ButtonActions {
		START_ACTION("Start"), ABORT_ACTION("Abort"), VIEW_LOG_ACTION(
				"View log");
		private final String actionString;

		private ButtonActions(String actionString) {
			this.actionString = actionString;
		}

		public String getActionString() {
			return actionString;
		}

		static public ButtonActions getAction(String actionString) {
			for (ButtonActions action : ButtonActions.values()) {
				if (action.getActionString().equals(actionString)) {
					return action;
				}
			}
			throw new RuntimeException("Unknown action");
		}

	}

	public ButtonActionListener(IAction viewController) {
		super();
		this.viewController = viewController;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		ButtonActions action = ButtonActions.getAction(ae.getActionCommand());
		switch (action) {
		case START_ACTION:
			viewController.start();
			break;
		case ABORT_ACTION:
			viewController.abort();
			break;
		case VIEW_LOG_ACTION:
			viewController.viewLog();
		};

	}

}
