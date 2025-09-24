package com.epam.electricalappliance.utilsfordevices;

import com.epam.electricalappliance.device.abstractdevice.Device;
import com.epam.electricalappliance.exeptions.LogicException;
import com.epam.electricalappliance.house.SmartHouse;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Sort {

    public static final Logger LOG = Logger.getLogger(Sort.class);

    public void sort(SmartHouse smartHouse, Comparator comparator) throws LogicException {

        if (smartHouse == null) {
            LOG.error("Smarthouse can not be null");
            throw new LogicException("ERROR, SmartHouse is NULL");
        }

        ArrayList<Device> sortList = new ArrayList<Device>();
        Iterator<Device> it = smartHouse.iterator();
        while (it.hasNext()) {
            sortList.add(it.next());
        }
        Collections.sort(sortList, comparator);
        smartHouse.setDevicesInHouse(sortList);
        LOG.info("Devices were sorted");
    }
}
