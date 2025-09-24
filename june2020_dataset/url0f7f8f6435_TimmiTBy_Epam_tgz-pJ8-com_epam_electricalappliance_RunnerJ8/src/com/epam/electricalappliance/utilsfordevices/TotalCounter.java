package com.epam.electricalappliance.utilsfordevices;

import com.epam.electricalappliance.device.abstractdevice.Device;
import com.epam.electricalappliance.exeptions.LogicException;
import com.epam.electricalappliance.house.SmartHouse;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class TotalCounter {

    public static final Logger LOG = Logger.getLogger(TotalCounter.class);

    public int countTotalPower(SmartHouse smartHouse) throws LogicException{
        if(smartHouse == null) {
            LOG.error("Smarthouse cant be null");
            throw new LogicException("ERROR, SmartHouse is NULL");
        }
        int totalPower = 0;
        Iterator<Device> it = smartHouse.iterator();
        while (it.hasNext()) {
            totalPower =  totalPower + it.next().getPower();
        }
        LOG.info("Total power: " + totalPower);
        return totalPower;
    }

    public int countTotalCost(SmartHouse smartHouse) throws LogicException{
        if(smartHouse == null) {
            LOG.error("Smarthouse cant be null");
            throw new LogicException("ERROR, SmartHouse is NULL");
        }
        int totalCost = 0;
        Iterator<Device> it = smartHouse.iterator();
        while (it.hasNext()) {
            totalCost =  totalCost + it.next().getCost();
        }
        LOG.info("Total cost: " + totalCost);
        return totalCost;
    }
}
