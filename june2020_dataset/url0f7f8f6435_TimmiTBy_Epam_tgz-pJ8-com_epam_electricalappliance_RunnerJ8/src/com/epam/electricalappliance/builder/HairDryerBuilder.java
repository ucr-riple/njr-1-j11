package com.epam.electricalappliance.builder;


import com.epam.electricalappliance.device.bathroom.HairDryer;


public class HairDryerBuilder extends Builder{

    HairDryer hairDryer = new HairDryer(70);

    public void buildPower() {
        hairDryer.setPower(100);
    }

    public void buildCost() {
        hairDryer.setCost(50);
    }

    public void buildWeight() {
        hairDryer.setWeight(1);
    }

    public void buildIsOn() {
        hairDryer.setOn(true);
    }

    @Override
    public HairDryer getDevice() {
        return hairDryer;
    }
}
