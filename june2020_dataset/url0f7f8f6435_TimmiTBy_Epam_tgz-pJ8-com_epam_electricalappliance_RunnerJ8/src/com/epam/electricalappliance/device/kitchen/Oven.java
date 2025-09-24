package com.epam.electricalappliance.device.kitchen;

import com.epam.electricalappliance.device.abstractdevice.Device;
import org.apache.log4j.Logger;

public class Oven  extends Device {

    public static final Logger LOG = Logger.getLogger(Oven.class);

    public static final int MIN_BAKE_TEMPERATURE = 50;
    public static final int BASIC_BAKE_TEMPERATURE = 250;

    private int temperature;

    public Oven(int temperature) {
       if(temperature > MIN_BAKE_TEMPERATURE) {
           this.temperature = temperature;
       } else {
           this.temperature = temperature;
           LOG.warn("Temperature of oven: " + temperature + " it is not enough to bake your food");
       }

       LOG.info("Oven has been created");
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void bake() {
        setTemperature(BASIC_BAKE_TEMPERATURE);
        LOG.info("Oven is ready you can bake");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Oven other = (Oven) obj;
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
        return "Oven with " + getPower() + " power, status " +  isOn() + " " ;
    }
}
