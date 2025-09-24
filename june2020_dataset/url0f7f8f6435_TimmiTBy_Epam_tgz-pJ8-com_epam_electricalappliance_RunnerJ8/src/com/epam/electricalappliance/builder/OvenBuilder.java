package com.epam.electricalappliance.builder;


import com.epam.electricalappliance.device.kitchen.Oven;

public class OvenBuilder extends Builder {

    Oven oven = new Oven(200);

    public void buildPower() {
        oven.setPower(200);
    }

    public void buildCost() {
        oven.setCost(600);
    }

    public void buildWeight() {
        oven.setWeight(30);
    }

    public void buildIsOn() {
        oven.setOn(true);
    }

    @Override
    public Oven getDevice() {
        return oven;
    }
}
