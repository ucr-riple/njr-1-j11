package edu.communications;

import edu.services.docs.DocDefaults;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 12.03.14.
 */
public class AddressTest {
    String apartment;
    String building;
    String street;
    String cityArea;
    String city;
    String region;
    String country;
    String zipCode;
    Address citizenAddress;

    @Before
    public void setUp() throws Exception {
        citizenAddress = new Address();
    }

    @Test
    public void shouldToString() throws Exception {
        //given

        //when
        country = "Ukraine";
        citizenAddress.setCountry(country);

        city = "Kyiv";
        citizenAddress.setCity(city);

        street = "Khreshchatyk";
        citizenAddress.setStreet(street);

        building = "1A";
        citizenAddress.setBuilding(building);

        apartment = "123H";
        citizenAddress.setApartment(apartment);

        zipCode = "01001";
        citizenAddress.setZipCode(zipCode);

        //then
        org.junit.Assert.assertEquals(
                zipCode +
                DocDefaults.COMMA_SPACE + country +
                DocDefaults.COMMA_SPACE + city +
                DocDefaults.COMMA_STREET + street +
                DocDefaults.COMMA_BUILDING + building +
                DocDefaults.COMMA_APPARTMENT + apartment, citizenAddress.toString());
    }

    @Test
    public void shouldSetRegionTest() throws Exception {
        //given
        region = "Kyivska obl.";

        //when
        citizenAddress.setRegion(region);

        //then
        org.junit.Assert.assertEquals(region, citizenAddress.getRegion());
    }

    @Test
    public void shouldSetCityArea() throws Exception {
        //given
        cityArea = "Shevchenkivski";

        //when
        citizenAddress.setCityArea(cityArea);

        //then
        org.junit.Assert.assertEquals(cityArea, citizenAddress.getCityArea());
    }
}
