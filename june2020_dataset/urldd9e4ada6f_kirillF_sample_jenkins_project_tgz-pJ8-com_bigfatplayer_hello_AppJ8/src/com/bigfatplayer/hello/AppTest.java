package com.bigfatplayer.hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test for {@link App}
 */
@RunWith(JUnit4.class)
public class AppTest
{
    @Test
    public void simple()
    {
        assertTrue(true);
    }

    @Test
    public void testAdd()
    {
        App app = new App();
        int result = app.add(2, 2);
        assertEquals(4, result);
    }

    @Test
    public void mul()
    {
        App app = new App();
        int result = app.mul(3, 3);
        assertEquals(9, result);
        result = app.mul(2, 3);
        assertEquals(6, result);
        assertEquals(6, 6);
    }

    @Test
    public void failMul()
    {
        App app = new App();
        int result = app.mul(3, 3);
        assertEquals(9, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroDivisionTest() {
        App app = new App();
        app.divide(2, 0);
    }

}
