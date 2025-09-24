package edu.sandbox;

import edu.communications.Address;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yurij.pyvovarenko on 16.04.14.
 */
public class EqualsTests {
    EqualsAndHashCode instance;
    EqualsAndHashCode equalInstance;
    Address address;

    @Before
    public void initObjects() {
        address = new Address();
        address.setZipCode("12345");

        instance = new EqualsAndHashCode();
        instance.setAddress(address);
        instance.setIntVal(5);
        instance.setStringVal("qaz");

        equalInstance = new EqualsAndHashCode();
        equalInstance.setAddress(address);
        equalInstance.setIntVal(5);
        equalInstance.setStringVal("qaz");
    }

    @Test
    public void shouldBeReflective() {
        //given

        //when

        //then
        org.junit.Assert.assertTrue(instance.equals(instance));
    }

    @Test
    public void shouldBeSymmetric() {
        //given

        //when
        org.junit.Assert.assertTrue(instance.equals(equalInstance));

        //then
        org.junit.Assert.assertTrue(equalInstance.equals(instance));
    }

    @Test
    public void shouldBeTransitive() {
        //given
        EqualsAndHashCode anotherInstance = new EqualsAndHashCode();
        anotherInstance.setAddress(address);
        anotherInstance.setIntVal(5);
        anotherInstance.setStringVal("qaz");

        //when
        org.junit.Assert.assertTrue(instance.equals(equalInstance));
        org.junit.Assert.assertTrue(equalInstance.equals(anotherInstance));

        //then
        org.junit.Assert.assertTrue(instance.equals(anotherInstance));
    }

    @Test
    public synchronized void shouldBeConsistent() throws InterruptedException {
        //when
        instance.setIntVal(3);
        equalInstance.setIntVal(4);
        wait(1000);
        instance.setIntVal(5);
        equalInstance.setIntVal(5);

        //then
        org.junit.Assert.assertTrue(instance.equals(equalInstance));
    }

    @Test
    public void shouldBeFalseWhenNull() {
        //when

        //then
        org.junit.Assert.assertFalse(instance.equals(null));
    }

    @Test
    public void shouldBeTrueWhenSameAddress() {
        //given
        EqualsAndHashCode anotherInstance = new EqualsAndHashCode();
        anotherInstance.setIntVal(5);
        anotherInstance.setStringVal("qaz");

        //when
        Address anotherAddress = new Address();
        anotherAddress.setZipCode("12345");
        anotherInstance.setAddress(anotherAddress);

        //then
        org.junit.Assert.assertTrue(equalInstance.equals(anotherInstance));
    }

    //anti
    @Test
    public void shouldBeFalseWhenAddressDiffers() {
        //given
        EqualsAndHashCode anotherInstance = new EqualsAndHashCode();
        anotherInstance.setIntVal(5);
        anotherInstance.setStringVal("qaz");

        //when
        Address anotherAddress = new Address();
        anotherAddress.setZipCode("02345");
        anotherInstance.setAddress(anotherAddress);

        //then
        org.junit.Assert.assertFalse(equalInstance.equals(anotherInstance));
    }
    //Р С Т У null + anti
}
