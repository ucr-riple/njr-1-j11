package by.epam.lab.controller;

import by.epam.lab.Building;

public class ConsoleViewController implements IView {

	@Override
	public void runView() {
		Building building = Building.getBuilding();
		building.fillBuilding();
		try {
			building.startElevator(Configuration.getConfiguration().getSleepTime());
			building.verify();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
