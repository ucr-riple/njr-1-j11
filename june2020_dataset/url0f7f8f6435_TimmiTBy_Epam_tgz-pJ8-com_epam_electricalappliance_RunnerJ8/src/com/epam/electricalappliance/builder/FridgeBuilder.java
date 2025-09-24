package com.epam.electricalappliance.builder;

import com.epam.electricalappliance.device.kitchen.Fridge;

public class FridgeBuilder extends Builder {

    private Fridge fridge = new Fridge(7);

    public void buildPower() {
        fridge.setPower(300);
    }

    public void buildCost() {
        fridge.setCost(1000);
    }

    public void buildWeight() {
        fridge.setWeight(40);
    }

    public void buildIsOn() {
        fridge.setOn(true);
    }

    @Override
    public Fridge getDevice() {
        return fridge;
    }
}
