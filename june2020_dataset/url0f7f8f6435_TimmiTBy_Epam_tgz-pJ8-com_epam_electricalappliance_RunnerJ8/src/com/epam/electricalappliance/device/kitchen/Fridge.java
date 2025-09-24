package com.epam.electricalappliance.device.kitchen;
import com.epam.electricalappliance.device.abstractdevice.Device;
import org.apache.log4j.Logger;

public class Fridge  extends Device {

    public static final Logger LOG = Logger.getLogger(Fridge.class);

    public static final int MAX_FRIDGE_TEMPERATURE = 15;
    public static final int FREEZ_TEMPERATURE = -15;
    public static final int NORMAL_TEMPERATURE = 7;

    private int temperature;

    public Fridge(int temperature) {
       if(temperature < MAX_FRIDGE_TEMPERATURE) {
           this.temperature = temperature;
       } else {
           this.temperature = temperature;
           LOG.warn("Temperature of fridge too high: " + temperature + ". Dont put your food there" );
       }

       LOG.info("Fridge has been created");
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void freezMode() {
        setTemperature(FREEZ_TEMPERATURE);
        LOG.info("Fridge can freez everthing!");
    }

    public void normalMode() { 
        setTemperature(NORMAL_TEMPERATURE);
        LOG.info("Fridge in a normal mode");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fridge other = (Fridge) obj;
        if (temperature != other.temperature) {
            return false;
        }
        if(this.getPower() != other.getPower()) {
            return false;
        }
        if(this.getCost() != other.getCost()) {
            return false;
        }
        if(this.getWeight() != other.getWeight()) {
            return false;
        }
        if(this.isOn() != other.isOn()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + temperature;
        return result;
    }

    @Override
    public String toString() {
        return "Fridge with " + getPower() + " power, status " +  isOn() + " " ;
    }
}
