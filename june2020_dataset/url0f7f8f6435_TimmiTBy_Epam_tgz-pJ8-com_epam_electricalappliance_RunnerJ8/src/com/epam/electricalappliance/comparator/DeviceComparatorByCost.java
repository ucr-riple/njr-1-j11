package com.epam.electricalappliance.comparator;

import com.epam.electricalappliance.device.abstractdevice.Device;

import java.util.Comparator;

public class DeviceComparatorByCost implements Comparator<Device>{

    @Override
    public int compare(Device o1, Device o2) {
        return (o1.getCost() > o2.getCost() ? 1 : (o1.getCost() < o2.getCost() ? -1 : 0));
    }
}
