/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ping.pong.net.connection.config;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Adrian
 */
public class DefaultConnectionConfigurationTest
{

    /**
     * Test of findKeyStorePath method, of class DefaultConnectionConfiguration.
     */
    @Test
    @Ignore
    public void testFindKeyStorePath()
    {
        String filepath = "";
        String expResult = "";
        String result = DefaultConnectionConfiguration.findKeyStorePath(filepath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of KeystorePassword methods, of class DefaultConnectionConfiguration.
     */
    @Test
    public void testGetKeystorePassword()
    {
        DefaultConnectionConfiguration instance = new DefaultConnectionConfiguration();
        assertEquals("pingpong123", instance.getKeystorePassword());
        instance.setKeystorePassword("kspass");
        assertEquals("kspass", instance.getKeystorePassword());
    }

    /**
     * Test of KeystorePath methods, of class DefaultConnectionConfiguration.
     */
    @Test
    public void testGetKeystorePath()
    {
        DefaultConnectionConfiguration instance = new DefaultConnectionConfiguration();
        assertEquals(ConnectionConfiguration.DEFAULT_KEY_STORE, instance.getKeystorePath());
        instance.setKeystorePath("kspath");
        assertEquals("kspath", instance.getKeystorePath());
    }

    /**
     * Test of IpAddress methods, of class DefaultConnectionConfiguration.
     */
    @Test
    public void testGetIpAddress()
    {
        DefaultConnectionConfiguration instance = new DefaultConnectionConfiguration();
        assertEquals("localhost", instance.getIpAddress());
        instance.setIpAddress("addr");
        assertEquals("addr", instance.getIpAddress());
    }

    /**
     * Test of Port methods, of class DefaultConnectionConfiguration.
     */
    @Test
    public void testPort()
    {
        DefaultConnectionConfiguration instance = new DefaultConnectionConfiguration();
        assertEquals(5011, instance.getPort());
        instance.setPort(1);
        assertEquals(1, instance.getPort());
    }

    /**
     * Test of UdpPort methods, of class DefaultConnectionConfiguration.
     */
    @Test
    public void testGetUdpPort()
    {
        DefaultConnectionConfiguration instance = new DefaultConnectionConfiguration();
        assertEquals(5012, instance.getUdpPort());
        instance.setUdpPort(1);
        assertEquals(1, instance.getUdpPort());
    }

    /**
     * Test of Ssl methods, of class DefaultConnectionConfiguration.
     */
    @Test
    public void testIsSsl()
    {
        DefaultConnectionConfiguration instance = new DefaultConnectionConfiguration();
        assertEquals(false, instance.isSsl());
        instance.setSsl(true);
        assertEquals(true, instance.isSsl());
    }
}
