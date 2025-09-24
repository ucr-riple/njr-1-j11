package edu.clients;

import edu.communications.Address;
import edu.services.docs.DocDefaults;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 3/11/14.
 */
public class CitizenFieldsTest {
    Citizen citizen;

    @Before
    public void createCitizen() {
        citizen = new Citizen();
    }

    @Test
    public void GetFullNameTest() throws Exception {
        //given
        citizen = new Citizen("Petrenko","Taras","Ivanovych");

        //when

        //then
        org.junit.Assert.assertNotNull("full name shouldn't be null", citizen.getFullName());
    }

    @Test
    public void testGetFullNameString() throws Exception {
        //given
        citizen = new Citizen("Petrenko","Taras","Ivanovych");

        //when

        //then
        org.junit.Assert.assertEquals("full name should be " + "Petrenko Taras Ivanovych",
                "Petrenko Taras Ivanovych", citizen.getFullNameString());
    }

    @Test
    public void testGetEmailAddress() throws Exception {
        //given

        //when
        citizen.setEmailAddress("citizen@gmail.com");

        //then
        org.junit.Assert.assertEquals("citizen@gmail.com", citizen.getEmailAddress());
    }

    @Test
    public void testGetEmailAddressNull() throws Exception {
        //given

        //when

        //then
        org.junit.Assert.assertNull(citizen.getEmailAddress());
    }

    @Test
    public void testSetEmailAddress() throws Exception {
        //given

        //when
        citizen.setEmailAddress("citizen@gmail.com");

        //then
        org.junit.Assert.assertNotNull(citizen.getEmailAddress());
    }

    @Test
    public void testGetAddress_whenNull() throws Exception {
        //when

        //then
        org.junit.Assert.assertNull(citizen.getAddress());
     }

    @Test
    public void testGetAddress() throws Exception {
        //when
        Address citizenAddress = new Address();
        citizen.setAddress(citizenAddress);

        //then
        org.junit.Assert.assertNotNull(citizen.getAddress());
    }


    @Test
    public void testGetAddressString() throws Exception {
        //given
        Address citizenAddress = new Address();

        String country = "Ukraine";
        citizenAddress.setCountry(country);

        String city = "Kyiv";
        citizenAddress.setCity(city);

        String street = "Khreshchatyk";
        citizenAddress.setStreet(street);

        String building = "1A";
        citizenAddress.setBuilding(building);

        String apartment = "123H";
        citizenAddress.setApartment(apartment);

        String zipCode = "01001";
        citizenAddress.setZipCode(zipCode);

        //when
        citizen.setAddress(citizenAddress);

        //then
        org.junit.Assert.assertEquals(
                zipCode +
                        DocDefaults.COMMA_SPACE + country +
                        DocDefaults.COMMA_SPACE + city +
                        DocDefaults.COMMA_STREET + street +
                        DocDefaults.COMMA_BUILDING + building +
                        DocDefaults.COMMA_APPARTMENT + apartment, citizen.getAddressString());
    }

    @Test
    public void shouldSetAddress() throws Exception {
        //when
        Address citizenAddress = new Address();
        citizen.setAddress(citizenAddress);

        //then
        org.junit.Assert.assertEquals(citizenAddress, citizen.getAddress());
    }

    @Test
    public void shoundGetOfficialIdWhenNull() throws Exception {
        //when
        //then
        org.junit.Assert.assertNull(citizen.getOfficialId());
    }

    @Test
    public void shouldGetOfficialId() throws Exception {
        //when
        citizen.setOfficialId("1234567890");

        //then
        org.junit.Assert.assertEquals("1234567890",citizen.getOfficialId());
    }

    @Test
    public void shouldGetName() throws Exception {
        //when
        citizen = new Citizen("Petrenko","Taras","Ivanovych");

        //then
        org.junit.Assert.assertEquals("Petrenko Taras Ivanovych", citizen.getName());
    }

    @Test
    public void ShouldGetNameWhenItIsNull() throws Exception {
        //when

        //then
        org.junit.Assert.assertNull(citizen.getName());
    }

    @Test
    public void shouldSetOfficialId() throws Exception {
        //when
        citizen.setOfficialId("");

        //then
        org.junit.Assert.assertEquals("",citizen.getOfficialId());
    }

    @Test
    public void shouldGetOfficialIdWhenItNotDefined() throws Exception {
        //when
        //then
        org.junit.Assert.assertNull(citizen.getOfficialId());
    }

    @Test
    public void shouldToString() throws Exception {
        //when
        citizen = new Citizen("","Tag","");

        //then
        org.junit.Assert.assertEquals("Tag", citizen.toString());
    }

    @Test
    public void shouldToStringWhenNoNameDefined() throws Exception {
        //when
        //then
        org.junit.Assert.assertNull(citizen.getOfficialId());
    }
}