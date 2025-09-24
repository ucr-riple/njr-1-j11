package patterns.gof.behavioral.observer;

import patterns.gof.helpers.Client;

public class ObserverClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		WeatherData weatherData = new WeatherData();
		 
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(29, 65, 30.4f);
        weatherData.setMeasurements(39, 70, 29.4f);
        weatherData.setMeasurements(42, 72, 31.4f);
        currentDisplay.display();
		
		super.main("Observer");
	}
}