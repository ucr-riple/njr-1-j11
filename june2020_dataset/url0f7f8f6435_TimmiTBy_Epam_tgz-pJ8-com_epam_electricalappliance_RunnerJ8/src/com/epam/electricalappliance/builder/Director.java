package com.epam.electricalappliance.builder;

import com.epam.electricalappliance.device.bathroom.HairDryer;
import com.epam.electricalappliance.device.kitchen.Fridge;
import com.epam.electricalappliance.device.kitchen.Oven;

public class Director {

    public static Fridge getFridge() {
        FridgeBuilder fridgeBuilder = new FridgeBuilder();
        fridgeBuilder.buildIsOn();
        fridgeBuilder.buildWeight();
        fridgeBuilder.buildCost();
        fridgeBuilder.buildPower();
        return fridgeBuilder.getDevice();
    }

    public static Oven getOven() {
        OvenBuilder ovenBuilder = new OvenBuilder();
        ovenBuilder.buildIsOn();
        ovenBuilder.buildWeight();
        ovenBuilder.buildCost();
        ovenBuilder.buildPower();
        return ovenBuilder.getDevice();
    }

    public static HairDryer getHairDryer() {
        HairDryerBuilder hairDryerBuilder = new HairDryerBuilder();
        hairDryerBuilder.buildIsOn();
        hairDryerBuilder.buildWeight();
        hairDryerBuilder.buildCost();
        hairDryerBuilder.buildPower();

        return hairDryerBuilder.getDevice();
    }
}
