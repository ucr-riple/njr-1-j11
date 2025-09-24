package edu.clients;

import edu.services.docs.DocDefaults;
import org.junit.*;
import org.junit.Assert.*;

/**
 * Created by yurii.pyvovarenko on 3/11/14.
 */
public class FullNameTest {
    @Test
    public void shouldToString() throws Exception {
        //given
        FullName fn = new FullName("Surname", "name", "secondName");

        //when

        //then
        org.junit.Assert.assertEquals("Should be equal strings", "Surname name secondName", fn.toString());
    }

    @Test
    public void shouldToStringWhenEmptyValues() throws Exception {
        //given

        //when
        FullName fn = new FullName("","","");

        //then
        org.junit.Assert.assertEquals(
                DocDefaults.NO_SURNAME + " " + DocDefaults.NO_NAME + " " + DocDefaults.NO_SECOND_NAME,
                fn.toString());
    }

    @Test
    public void shouldFullNameConstructor() throws Exception {
        //when
        FullName fn = new FullName("Surn ame", "na me", "second Name");

        //then
        org.junit.Assert.assertEquals("Should be equal strings", "Surn ame na me second Name", fn.toString());
    }
}
