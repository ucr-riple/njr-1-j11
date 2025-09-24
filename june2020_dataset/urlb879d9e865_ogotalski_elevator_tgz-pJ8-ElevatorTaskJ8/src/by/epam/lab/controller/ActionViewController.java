package by.epam.lab.controller;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import by.epam.lab.Building;
import by.epam.lab.Elevator;
import by.epam.lab.Floor;
import by.epam.lab.utils.ReverseIterator;
import by.epam.lab.utils.TextAreaAppender;
import by.epam.lab.view.ControlPanel;
import by.epam.lab.view.FloorView;
import by.epam.lab.view.LogViewFrame;
import by.epam.lab.view.MainFrame;
import by.epam.lab.view.Action.ButtonActionListener;
import by.epam.lab.view.Action.ButtonActionListener.ButtonActions;
import by.epam.lab.view.Action.UpdateListener;
import static by.epam.lab.utils.AppLogger.*;

public class ActionViewController implements IAction,IView {
	private static final String VALIDATION = "Validation";
	private static final String VALIDATION_ERROR = "Validation error";
	private static final String VALIDATION_COMPLETED = "Validation completed";
	private static final String ELEVATOR_INTERRUPDED = "ELEVATOR interrupded";
	private static final String ABORTING_TRANSPORTATION = "ABORTING_TRANSPORTATION";
	private static final String INPUT_ERROR = "Input error";

	private MainFrame mainFrame;
	private ControlPanel controlPanel;
	private ThreadGroup threadGroup;
	private Map<Floor, FloorView> floorsMap;
	private Building building;
	private Timer timer;

	public ActionViewController() {
		

	}
	public void createView() {
		mainFrame = new MainFrame();
		controlPanel = new ControlPanel();
		controlPanel.addButtonActionListener(new ButtonActionListener(this));
		controlPanel.setButtonAction(ButtonActions.START_ACTION);
		mainFrame.addComponent(controlPanel);
		mainFrame.setVisible(true);
	}

	@Override
	public void start() {
		try {
			getParamsFromControlPanel();
			threadGroup = new ThreadGroup("Building");
			building = Building.getBuilding();
			building.fillBuilding();

			if (Configuration.getConfiguration().getAnimationBoost() > 0) {
				drawFloorPanel();

			}
			drawLogPanel();
			controlPanel.setButtonAction(ButtonActions.ABORT_ACTION);
			new Thread(threadGroup, this).start();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(mainFrame, e.getMessage(),
					INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void drawLogPanel() {
		JTextArea logArea = new JTextArea();

		JScrollPane scrollPane = new JScrollPane(logArea);
		scrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
		scrollPane.setMinimumSize(new Dimension(Integer.MAX_VALUE, 200));
		mainFrame.addComponent(scrollPane);
		mainFrame.repaint();
		LOG.addAppender(new TextAreaAppender(logArea));
	}

	private void getParamsFromControlPanel() {
		Configuration configuration = Configuration.getConfiguration();
		configuration.setElevatorCapacity(Integer.parseInt(controlPanel.getElevatorCapacity()));
		configuration.setPassengersNumber(Integer.parseInt(controlPanel.getPassengersNumber()));
		configuration.setStoriesNumber(Integer.parseInt(controlPanel.getStoriesNumber()));
		configuration.setAnimationBoost(controlPanel.getAnimationBoost());
		
	}

	private void drawFloorPanel() {
		floorsMap = new HashMap<Floor, FloorView>(Configuration.getConfiguration().getStoriesNumber());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		FloorView.setNumPassengersView(building.getElevator().getCapacity());
		fillFloorsMap(panel);
		JScrollPane pane = new JScrollPane(panel);
		mainFrame.addComponent(pane);
		timer = new Timer(Configuration.getConfiguration().getSleepTime(),
				new UpdateListener(this));
		timer.start();
	}

	private void fillFloorsMap(JPanel panel) {
		FloorView view;
		List<Floor> floors = building.getFloors();
		Elevator elevator = building.getElevator();
		ReverseIterator<Floor> iterator = new ReverseIterator<Floor>(floors);
		Floor floor;
		while (iterator.hasNext()) {
			floor = iterator.next();
			view = new FloorView(floor.getDispatchPassengers(),
					floor.getArrivalPassengers());
			panel.add(view);
			floorsMap.put(floor, view);
		}
		view = floorsMap.get(elevator.getCurrentFloor());
		view.setElevatorPassengers(elevator.getElevatorPassengers());
		panel.setVisible(true);
	}

	public void updateView() {

		for (Floor floor : building.getFloors()) {
			floorsMap.get(floor).update(floor.getDispatchPassengers(),
					floor.getArrivalPassengers());

		}
		Elevator elevator = building.getElevator();
		floorsMap.get(elevator.getCurrentFloor()).setElevatorPassengers(
				elevator.getElevatorPassengers());
		mainFrame.repaint();
	}

	@Override
	public void abort() {
		LOG.info(ABORTING_TRANSPORTATION);
		threadGroup.interrupt();
		timer.stop();
	}

	@Override
	public void viewLog() {
		new LogViewFrame();

	}

	

	@Override
	public void run() {
		try {
			building.startElevator(Configuration.getConfiguration().getSleepTime());
			if (building.verify()) {
				JOptionPane.showMessageDialog(mainFrame, VALIDATION_COMPLETED);
			} else {
				JOptionPane.showMessageDialog(mainFrame, VALIDATION_ERROR,
						VALIDATION, JOptionPane.ERROR_MESSAGE);
			}
		} catch (InterruptedException e) {
			LOG.trace(ELEVATOR_INTERRUPDED);
		} finally {
			controlPanel.setButtonAction(ButtonActions.VIEW_LOG_ACTION);
		}

	}
	@Override
	public void runView() {
		createView();
		
	}

}
