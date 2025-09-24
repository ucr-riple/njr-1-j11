package edu.communications;

import edu.services.docs.DocDefaults;

/**
 * Created by yurii.pyvovarenko on 3/4/14.
 */
public class Address {
    String apartment;
    String building;
    String street;
    String cityArea;
    String city;
    String region;
    String country;
    String zipCode;

    public String toString() {
        return zipCode +
                DocDefaults.COMMA_SPACE + country +
                DocDefaults.COMMA_SPACE + city +
                DocDefaults.COMMA_STREET + street +
                DocDefaults.COMMA_BUILDING + building +
                DocDefaults.COMMA_APPARTMENT + apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCityArea(String cityArea) {
        this.cityArea = cityArea;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityArea() {
        return this.cityArea;
    }

    public String getRegion() {
        return this.region;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (! (other instanceof Address) )
            return false;
        Address otherAddress = (Address) other;
        return toString().equals(otherAddress.toString());
    }

    public int hashCode() {
        return 7 * toString().hashCode();
    }
}
