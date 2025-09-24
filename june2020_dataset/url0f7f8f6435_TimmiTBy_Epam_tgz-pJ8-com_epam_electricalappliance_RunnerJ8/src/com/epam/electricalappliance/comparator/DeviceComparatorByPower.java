package com.epam.electricalappliance.comparator;

import com.epam.electricalappliance.device.abstractdevice.Device;

import java.util.Comparator;

public class DeviceComparatorByPower implements Comparator<Device>{

    @Override
    public int compare(Device o1, Device o2) {
        return (o1.getPower() > o2.getPower() ? 1 : (o1.getPower() < o2.getPower() ? -1 : 0));
    }
}
