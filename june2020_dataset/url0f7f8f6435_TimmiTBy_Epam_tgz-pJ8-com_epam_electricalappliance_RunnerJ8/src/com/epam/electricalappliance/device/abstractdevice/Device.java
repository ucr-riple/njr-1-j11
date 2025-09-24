package com.epam.electricalappliance.device.abstractdevice;

import org.apache.log4j.Logger;

public abstract class Device {

    public static final Logger LOG = Logger.getLogger(Device.class);

    private boolean isOn;
    private int power;
    private int weight;
    private int cost;

    public Device() {

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        if (cost > 0) {
            this.cost = cost;
        } else {
            LOG.warn("Negative cost is meaningless");
        }

    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            LOG.warn("Negative weight is meaningless");
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        if (power > 0) {
            this.power = power;
        } else {
            LOG.warn("Negative power is meaningless");
        }
    }
}
