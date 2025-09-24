package patterns.gof.behavioral.observer;

public class CurrentConditionsDisplay implements Observer {
	private float temperature;
    private float humidity;
    private WeatherData weatherData;
 
    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObserver(this);
    }
 
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
 
    public void display() {
        ObserverClient.addOutput("data changed: " + temperature + " degrees Celsius and " + humidity + " humidity");
    }
}