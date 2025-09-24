package com.epam.electricalappliance;

import com.epam.electricalappliance.builder.Director;
import com.epam.electricalappliance.comparator.DeviceComparatorByCost;
import com.epam.electricalappliance.device.bathroom.HairDryer;
import com.epam.electricalappliance.device.kitchen.Fridge;
import com.epam.electricalappliance.device.kitchen.Oven;
import com.epam.electricalappliance.exeptions.LogicException;
import com.epam.electricalappliance.house.SmartHouse;
import com.epam.electricalappliance.utilsfordevices.Sort;
import com.epam.electricalappliance.utilsfordevices.TotalCounter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Runner {

    private static final Logger LOG = Logger.getLogger(Runner.class);

    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
    }


    public static void main(String[] args) {

        Fridge fridge = Director.getFridge();
        Oven oven = Director.getOven();
        HairDryer hairDryer = Director.getHairDryer();

        SmartHouse smartHouse = new SmartHouse();
        smartHouse.addDevice(fridge);
        smartHouse.addDevice(oven);
        smartHouse.addDevice(hairDryer);

        try {
            smartHouse.findDeviceByPower(0, 150);
        } catch (LogicException e) {
            e.printStackTrace();
        }

        try {
            new Sort().sort(smartHouse, new DeviceComparatorByCost());
        } catch (LogicException e) {
            e.printStackTrace();
        }

        try {
            new TotalCounter().countTotalCost(smartHouse);
        } catch (LogicException e) {
            e.printStackTrace();
        }
    }
}
