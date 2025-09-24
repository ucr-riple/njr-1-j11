/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ping.pong.net.connection.messaging;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Adrian
 */
public class DefaultEnvelopeTest
{
    /**
     * Test of Reliable methods, of class DefaultEnvelope.
     */
    @Test
    public void testSetReliable()
    {
        DefaultEnvelope instance = new DefaultEnvelope();
        assertEquals(true, instance.isReliable());
        instance.setReliable(false);
        assertEquals(false, instance.isReliable());
    }

    /**
     * Test of Message methods, of class DefaultEnvelope.
     */
    @Test
    public void testSetMessage()
    {
        DefaultEnvelope instance = new DefaultEnvelope();
        assertEquals(null, instance.getMessage());
        instance.setMessage("testMsg");
        assertEquals("testMsg", instance.getMessage());
    }

    @Test
    public void testSetMessage_String()
    {
        DefaultEnvelope<String> instance = new DefaultEnvelope<String>();
        assertEquals(null, instance.getMessage());
        instance.setMessage("testMsg");
        assertEquals("testMsg", instance.getMessage());
    }

    @Test
    public void testSetMessage_Integer()
    {
        DefaultEnvelope<Integer> instance = new DefaultEnvelope<Integer>();
        assertEquals(null, instance.getMessage());
        instance.setMessage(23);
        assertEquals(23, (int) instance.getMessage());
    }

    /**
     * Test of toString method, of class DefaultEnvelope.
     */
    @Test
    public void testToString()
    {
        DefaultEnvelope instance = new DefaultEnvelope();
        assertEquals("DefaultEnvelope{reliable=true, message=null}", instance.toString());
    }

    @Test
    public void testToString_String()
    {
        DefaultEnvelope<String> instance = new DefaultEnvelope<String>();
        instance.setMessage("testMsg");
        assertEquals("DefaultEnvelope{reliable=true, message=testMsg}", instance.toString());
    }
}
