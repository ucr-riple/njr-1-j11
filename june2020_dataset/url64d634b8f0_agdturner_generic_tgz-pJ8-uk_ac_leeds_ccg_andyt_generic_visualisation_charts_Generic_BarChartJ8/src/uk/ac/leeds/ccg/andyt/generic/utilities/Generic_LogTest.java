/**
 * Copyright 2012 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

import uk.ac.leeds.ccg.andyt.generic.logging.Generic_Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author geoagdt
 */
public class Generic_LogTest {

    public Generic_LogTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseLoggingProperties method, of class Generic_Log.
     */
//    @Test
//    public void testParseLoggingProperties() {
//        System.out.println("testParseLoggingProperties");
//        //File directory = new File("/nfs/see-fs-02_users/geoagdt/src/andyt/java/generic/data/");
//        File directory = new File("./data");
//        File logDirectory = new File(directory, "logs");
//        System.out.println(logDirectory.getAbsolutePath());
//        logDirectory.mkdirs();
//        String logname = "uk.ac.leeds.ccg.andyt.generic";
//        File loggingProperties_File = new File(
//                directory,
//                "logging.properties");
//        //Logger logger = null;
//        Logger expResult = null;
//        Generic_Log.parseLoggingProperties(
//                loggingProperties_File,
//                directory,
//                logDirectory,
//                logname);
//        
//        //assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of addFileHandler method, of class Generic_Log.
//     */
//    @Test
//    public void testAddFileHandler_3args() {
//        System.out.println("addFileHandler");
//        Level level = null;
//        File directory = null;
//        String name = "";
//        FileHandler expResult = null;
//        FileHandler result = Generic_Log.addFileHandler(level, directory, name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addFileHandler method, of class Generic_Log.
//     */
//    @Test
//    public void testAddFileHandler_5args() {
//        System.out.println("addFileHandler");
//        Level level = null;
//        File directory = null;
//        String name = "";
//        int logFileSizeLimit = 0;
//        int logFileCount = 0;
//        FileHandler expResult = null;
//        FileHandler result = Generic_Log.addFileHandler(level, directory, name, logFileSizeLimit, logFileCount);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of closeFileHandlers method, of class Generic_Log.
//     */
//    @Test
//    public void testCloseFileHandlers() {
//        System.out.println("closeFileHandlers");
//        Generic_Log.closeFileHandlers();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFileHandler method, of class Generic_Log.
//     */
//    @Test
//    public void testGetFileHandler() {
//        System.out.println("getFileHandler");
//        String name = "";
//        FileHandler expResult = null;
//        FileHandler result = Generic_Log.getFileHandler(name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of log method, of class Generic_Log.
//     */
//    @Test
//    public void testLog_Level_String() {
//        System.out.println("log");
//        Level level = null;
//        String message = "";
//        Generic_Log.log(level, message);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of log method, of class Generic_Log.
//     */
//    @Test
//    public void testLog_3args() {
//        System.out.println("log");
//        String loggerName = "";
//        Level level = null;
//        String message = "";
//        Generic_Log.log(loggerName, level, message);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLoggerNames method, of class Generic_Log.
//     */
//    @Test
//    public void testGetLoggerNames() {
//        System.out.println("getLoggerNames");
//        Generic_Log instance = new Generic_LogImpl();
//        List expResult = null;
//        List result = instance.getLoggerNames();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLoggerLevel method, of class Generic_Log.
//     */
//    @Test
//    public void testGetLoggerLevel() {
//        System.out.println("getLoggerLevel");
//        String loggerName = "";
//        Generic_Log instance = new Generic_LogImpl();
//        String expResult = "";
//        String result = instance.getLoggerLevel(loggerName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setLoggerLevel method, of class Generic_Log.
//     */
//    @Test
//    public void testSetLoggerLevel() {
//        System.out.println("setLoggerLevel");
//        String loggerName = "";
//        String levelName = "";
//        Generic_Log instance = new Generic_LogImpl();
//        instance.setLoggerLevel(loggerName, levelName);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getParentLoggerName method, of class Generic_Log.
//     */
//    @Test
//    public void testGetParentLoggerName() {
//        System.out.println("getParentLoggerName");
//        String loggerName = "";
//        Generic_Log instance = new Generic_LogImpl();
//        String expResult = "";
//        String result = instance.getParentLoggerName(loggerName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    public class Generic_LogImpl extends Generic_Log {
    }
}
