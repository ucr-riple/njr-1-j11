package edu.sandbox;

import edu.communications.Address;

/**
 * Created by yurij.pyvovarenko on 16.04.14.
 */
public class EqualsAndHashCode {
    int intVal;
    String stringVal;
    Address address;

    public EqualsAndHashCode() {}

    public EqualsAndHashCode(
            int intVal,
            String stringVal,
            Address address) {
        this.intVal = intVal;
        this.stringVal = stringVal;
        this.address = address;
    }

    public int hashCode() {
        return intVal*31 + stringVal.hashCode() + address.hashCode();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == this)
            return true;
        if (otherObject == null)
            return false;

        //if (! (otherObject instanceof EqualsAndHashCode)) return false;
        if (getClass() != otherObject.getClass()) return false;

        EqualsAndHashCode other = (EqualsAndHashCode) otherObject;

        return
                //super.equals(other) &&
                (other.intVal == intVal)
                && (stringVal.equals(other.stringVal))
                && (address.equals(other.address));

    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
