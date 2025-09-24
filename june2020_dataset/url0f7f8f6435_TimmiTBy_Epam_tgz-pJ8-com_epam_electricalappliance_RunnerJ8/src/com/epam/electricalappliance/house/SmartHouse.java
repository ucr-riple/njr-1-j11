package com.epam.electricalappliance.house;

import com.epam.electricalappliance.device.abstractdevice.Device;
import com.epam.electricalappliance.exeptions.LogicException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Iterator;

public class SmartHouse{

    public static final Logger LOG = Logger.getLogger(SmartHouse.class);

    private ArrayList<Device> devicesInHouse;

    public SmartHouse() {
        devicesInHouse = new ArrayList<>();
    }

    public void setDevicesInHouse(ArrayList<Device> devicesInHouse) {
        this.devicesInHouse = devicesInHouse;
    }

    public Iterator<Device> iterator() {
        return devicesInHouse.iterator();
    }

    public void addDevice(Device device) {
        devicesInHouse.add(device);
        LOG.info(device + "Has been added to SmartHouse");
    }

    public ArrayList<Device> findDeviceByPower(int minPower, int maxPower) throws LogicException{

        if (minPower > maxPower) {
            LOG.error("Wrong searching options");
            throw new LogicException("ERROR Min value can not be bigger then Max value");
        }

        ArrayList<Device> foundDevices = new ArrayList<Device>();
        for (Device device : devicesInHouse) {
            if (device.getPower() >= minPower && device.getPower() <= maxPower) {
                LOG.info(device + "has been found");
                foundDevices.add(device);
            }
        }
        if (foundDevices.isEmpty()) {
            LOG.info("No Devices were found");
            return foundDevices;
        } else {
            return foundDevices;
        }
    }
}
