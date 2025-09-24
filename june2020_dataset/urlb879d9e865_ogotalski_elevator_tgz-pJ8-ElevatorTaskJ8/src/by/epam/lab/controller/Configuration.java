package by.epam.lab.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import by.epam.lab.Elevator;

public class Configuration {

	private static final String ANIMATION_BOOST_MUST_BE_LESS_THAN = "Animation boost must be less than ";
	private static final String PROPERTIES_FILENAME = "config.properties";
	private static final String PASSENGERS_NUMBER_MUST_BE_GREATER_THAN_0 = "Passengers number must be greater than 0";
	private static final String STORIES_NUMBER_MUST_BE_GREATER_THAN_1 = "Stories number must be greater than 1";
	private static final String ANIMATION_BOOST_MUST_BE_GREATER_THAN_0 = "Animation boost must be greater than 0";

	private static final String ANIMATION_BOOST_PROPERTY_NAME = "animationBoost";
	private static final String PASSENGERS_NUMBER_PROPERTY_NAME = "passengersNumber";
	private static final String ELEVATOR_CAPACITY_PROPERTY_NAME = "elevatorCapacity";
	private static final String STORIES_NUMBER_PROPERTY_NAME = "storiesNumber";

	private static final int MIN_STORIES_NUMBER = 2;
	private static final int MIN_PASSENGERS_NUMBER = 1;
	private static final int MIN_ANIMATION_BOOST = 0;
	private static final int SLEEP_MULTIPLIER = 25;
	private static final int DEFAULT_SLEEP = 1000;
	private static final int MAX_BOOST = DEFAULT_SLEEP / SLEEP_MULTIPLIER;
	
	private static Configuration configuration;

	
	public static Configuration getConfiguration() {
		if (configuration == null)
			configuration = new Configuration();
		return configuration;
	}

	private int storiesNumber = MIN_STORIES_NUMBER;
	private int elevatorCapacity = Elevator.MIN_ELEVATOR_COMPACITY;
	private int passengersNumber = MIN_PASSENGERS_NUMBER;
	private int animationBoost = MIN_ANIMATION_BOOST;

	private Configuration() {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init() throws IOException {
		Properties prop = new Properties();

		prop.load(new FileInputStream(PROPERTIES_FILENAME));
		setStoriesNumber(Integer.parseInt(prop
				.getProperty(STORIES_NUMBER_PROPERTY_NAME)));
		setElevatorCapacity(Integer.parseInt(prop
				.getProperty(ELEVATOR_CAPACITY_PROPERTY_NAME)));
		setPassengersNumber(Integer.parseInt(prop
				.getProperty(PASSENGERS_NUMBER_PROPERTY_NAME)));
		setAnimationBoost(Integer.parseInt(prop
				.getProperty(ANIMATION_BOOST_PROPERTY_NAME)));

	}

	public int getStoriesNumber() {
		return storiesNumber;
	}

	public void setStoriesNumber(int storiesNumber) {
		if (storiesNumber < MIN_STORIES_NUMBER)
			throw new IllegalArgumentException(
					STORIES_NUMBER_MUST_BE_GREATER_THAN_1);
		this.storiesNumber = storiesNumber;
	}

	public int getElevatorCapacity() {

		return elevatorCapacity;
	}

	public void setElevatorCapacity(int elevatorCapacity) {
		Elevator.validateCapacity(elevatorCapacity);
		this.elevatorCapacity = elevatorCapacity;
	}

	public int getPassengersNumber() {
		return passengersNumber;
	}

	public void setPassengersNumber(int passengersNumber) {
		if (passengersNumber < MIN_PASSENGERS_NUMBER)
			throw new IllegalArgumentException(
					PASSENGERS_NUMBER_MUST_BE_GREATER_THAN_0);
		this.passengersNumber = passengersNumber;
	}

	public int getAnimationBoost() {
		return animationBoost;
	}

	public void setAnimationBoost(int animationBoost) {
		if (animationBoost < MIN_ANIMATION_BOOST)
			throw new IllegalArgumentException(
					ANIMATION_BOOST_MUST_BE_GREATER_THAN_0);
		if (animationBoost * SLEEP_MULTIPLIER > DEFAULT_SLEEP)
			throw new IllegalArgumentException(
					ANIMATION_BOOST_MUST_BE_LESS_THAN
							+ (MAX_BOOST + 1));
		this.animationBoost = animationBoost;
	}
	public int getMaxBoost(){
		return MAX_BOOST;
	}
	public int getSleepTime() {
		return animationBoost > 0 ? DEFAULT_SLEEP
				- (SLEEP_MULTIPLIER * animationBoost - 1) : 0;
	}
}
