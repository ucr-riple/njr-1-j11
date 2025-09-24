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
package uk.ac.leeds.ccg.andyt.generic.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_ErrorAndExceptionHandler;

public abstract class Generic_Log implements LoggingMXBean {

    private static final String sourceClass = Generic_Log.class.getName();
    private static final String sourcePackage = Generic_Log.class.getPackage().getName();
    public static Logger logger;
    /**
     * For looking up logging
     */
//    protected static final HashMap<String, Level> loggerNameLevel_HashMap = new HashMap<String, Level>();
//    protected static final HashMap<String, File> loggerNameFile_HashMap = new HashMap<String, File>();
//    protected static final HashMap<File, FileHandler> loggerFileFileHandler_HashMap = new HashMap<File, FileHandler>();
//    protected static final HashMap<FileHandler, File> loggerFileHandlerFile_HashMap = new HashMap<FileHandler, File>();
//    protected HashMap<String, Level> loggerNameLevel_HashMap;
//    protected HashMap<String, File> loggerNameFile_HashMap;
//    protected HashMap<File, FileHandler> loggerFileFileHandler_HashMap;
//    protected HashMap<FileHandler, File> loggerFileHandlerFile_HashMap;
    //protected transient FileHandler _FileHandler;
    public static final String Generic_DefaultLoggerName = "Generic_Log";
    public static final String Generic_DefaultLogDirectoryName = "logs";
    public static final int Generic_DefaultLogFileSizeLimit = Integer.MAX_VALUE;
    public static final int Generic_DefaultLogFileCount = 5;
    public static final boolean Generic_DefaultLogFileAppend = false;
    //protected transient static Logger _Logger;
    public static Level Generic_DefaultLogLevel = Level.ALL;

//    public Generic_Log() {
//    }
//
//    public Generic_Log(Generic_Log a_Generic_Log) {
//        this.loggerNameLevel_HashMap = a_Generic_Log.loggerNameLevel_HashMap;
//        this.loggerNameFile_HashMap = a_Generic_Log.loggerNameFile_HashMap;
//        this.loggerFileHandlerFile_HashMap = a_Generic_Log.loggerFileHandlerFile_HashMap;
//        this.loggerFileFileHandler_HashMap = a_Generic_Log.loggerFileFileHandler_HashMap;
//        //this._FileHandler = a_Generic_Log._FileHandler;
//    }
//
//    private void init() {
//        LogManager.getLogManager().addLogger(Logger.getLogger(Generic_DefaultLoggerName));
//        loggerNameLevel_HashMap = new HashMap<String, Level>();
//        loggerNameLevel_HashMap.put(Generic_DefaultLoggerName, Generic_DefaultLogLevel);
//        loggerNameFile_HashMap = new HashMap<String, File>();
//        loggerFileFileHandler_HashMap = new HashMap<File, FileHandler>();
//        loggerFileHandlerFile_HashMap = new HashMap<FileHandler, File>();
//    }
//
//    public Generic_Log(
//            Level level,
//            File directory,
//            String classname,
//            String filename) {
//        init(level,
//                directory,
//                classname,
//                filename);
//    }
//
//    public Generic_Log(
//            Level level,
//            File directory,
//            String filename) {
//        init(level,
//                directory,
//                Generic_DefaultLoggerName,
//                filename);
//    }
    /**
     * Creates loggers and registers them with the LogManager. The loggers 
     * created are configured by reading a logging.properties configuration file 
     * found as 
     * <code>
     * new File(
     * directory.toString + 
     * System.getProperty("java.util.logging.config.file"))
     * </code>
     * @param loggingPropertiesFile
     * @param directory The file directory base containing the 
     * logging.properties file (possibly at great depth!)
     * @param logDirectory The directory into which and LoggingFiles will be 
     * stored (possibly at some depth)
     * @param logname Typically the source package or some name for naming the 
     * loggers created
     */
    //public static Logger parseLoggingProperties(
    public static void parseLoggingProperties(
            File loggingPropertiesFile,
            File directory,
            File logDirectory,
            String logname) {
        String sourceMethod = "parseLoggingProperties(File,File,File,String)";
        //Logger logname_Logger = Logger.getLogger(logname);
        //logger = Logger.getLogger("");
        logger = Logger.getLogger(logname);
        Properties logging_Properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(loggingPropertiesFile);
            logging_Properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
        LogManager logManager = LogManager.getLogManager();
        try {
            FileInputStream fileInputStream = new FileInputStream(loggingPropertiesFile);
            logManager.readConfiguration(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            logger.log(
                    Level.SEVERE, "{0} in {1}.{2}", new Object[]{e.getMessage(), sourceClass, sourceMethod});
        }
//        System.out.println(logManager.getProperty("java.util.logging.config.class"));
//        System.out.println(logManager.getProperty("java.util.logging.config.file"));
//        System.out.println(logManager.getProperty(logname + ".level"));
        
        
        //System.out.println(System.getProperties().keySet().toString());
//        ResourceBundle resourceBundle = Logger.getLogger(name).getResourceBundle();
//        if (resourceBundle != null) {
//            System.out.println(resourceBundle.keySet().toString());
//            Enumeration<String> enumeration = resourceBundle.getKeys();
//            String key;
//            Object object;
//            while (enumeration.hasMoreElements()) {
//                key = enumeration.nextElement();
//                object = resourceBundle.getObject(key);
//                System.out.println(key + " " + object.toString());
//            }
//        }
        String level_String = logging_Properties.getProperty(
                logname + ".level");
        Level level = null;
        if (level_String != null) {
            level = Level.parse(level_String);
        } else {
            level = Level.ALL;
        }
        //Level level = logger.getLevel();
        //Level level = Level.ALL;
        //level = Logger.getLogger(GENESIS_Mortality.class.getName()).getLevel();
        // Initialise fileHandler
        //System.out.println(logging_Properties.getProperty("java.util.logging.FileHandler.limit"));
        String fileHandlerLimit = logging_Properties.getProperty("java.util.logging.FileHandler.limit");
        int fileHandlerLimit_int;
        if (fileHandlerLimit != null) {
            fileHandlerLimit_int = Integer.parseInt(fileHandlerLimit);
        } else {
            fileHandlerLimit_int = 10000000;
        }
        //System.out.println(logging_Properties.getProperty("java.util.logging.FileHandler.count"));
        String fileHandlerCount = logging_Properties.getProperty("java.util.logging.FileHandler.count");
        int fileHandlerCount_int;
        if (fileHandlerCount != null) {
            fileHandlerCount_int = Integer.parseInt(fileHandlerCount);
        } else {
            fileHandlerCount_int = 10;
        }
        // Set up FileHandler
        FileHandler fileHandler = getFileHandler(
                level,
                logDirectory,
                logname,
                fileHandlerLimit_int,
                fileHandlerCount_int);
        //Generic_Log.getFileHandler(logname);
        String fileHandlerFormatter_String = logging_Properties.getProperty("java.util.logging.FileHandler.formatter");
        if (fileHandlerFormatter_String != null) {
            fileHandlerFormatter_String = fileHandlerFormatter_String.trim();
            if (fileHandlerFormatter_String.equalsIgnoreCase("java.util.logging.SimpleFormatter")) {
                fileHandler.setFormatter(new SimpleFormatter());
            } else {
                if (fileHandlerFormatter_String.equalsIgnoreCase("java.util.logging.XMLFormatter")) {
                    fileHandler.setFormatter(new XMLFormatter());
                }
            }
        } else {
            fileHandler.setFormatter(new SimpleFormatter());
        }
        String fileHandlerLevel_String = logging_Properties.getProperty("java.util.logging.FileHandler.level");
        if (fileHandlerLevel_String != null) { 
            Level fileHandlerLevel = Level.parse(fileHandlerLevel_String.trim());
            if (fileHandlerLevel == null) {
                fileHandlerLevel = level;
            }
        }
        //String fileHandlerToString = fileHandler.toString();
        //logname_Logger.addHandler(fileHandler);
        logger.addHandler(fileHandler);
        // Set up ConsoleHandler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        String consoleHandlerLevel_String = logging_Properties.getProperty("java.util.logging.ConsoleHandler.level");
        if (consoleHandlerLevel_String != null) {
            Level consoleHandlerLevel = Level.parse(consoleHandlerLevel_String.trim());
            consoleHandler.setLevel(consoleHandlerLevel);
            String consoleFormatter_String = logging_Properties.getProperty("java.util.logging.ConsoleHandler.formatter").trim();
            if (consoleFormatter_String.equalsIgnoreCase("java.util.logging.SimpleFormatter")) {
                consoleHandler.setFormatter(new SimpleFormatter());
            } else {
                if (consoleFormatter_String.equalsIgnoreCase("java.util.logging.XMLFormatter")) {
                    consoleHandler.setFormatter(new XMLFormatter());
                }
            }
        }
//        logname_Logger.addHandler(consoleHandler);
//        LogManager.getLogManager().addLogger(logname_Logger);
        logger.addHandler(consoleHandler);
        //LogManager.getLogManager().addLogger(logname_Logger);
        // Log System.getProperties()
        Properties properties = System.getProperties();
        Enumeration<Object> enumeration = properties.keys();
        String key_String;
        String property;
        while (enumeration.hasMoreElements()) {
            key_String = enumeration.nextElement().toString();
            property = properties.getProperty(key_String);
            logger.log(Level.ALL, "{0} {1}", new Object[]{key_String, property});
            //logname_Logger.log(Level.ALL, "{0} {1}", new Object[]{key_String, property});
        }
        // Log loggingPropertiesKeys
        Enumeration<Object> loggingPropertiesKeys = logging_Properties.keys();
        while (loggingPropertiesKeys.hasMoreElements()) {
            key_String = loggingPropertiesKeys.nextElement().toString();
            property = logging_Properties.getProperty(key_String);
            //logname_Logger.log(Level.ALL, "{0} {1}", new Object[]{key_String, property});
            logger.log(Level.ALL, "{0} {1}", new Object[]{key_String, property});
        }
        fileHandler.flush();
        //return logger;
        //return logname_Logger;
    }
    
    //public static Logger parseLoggingProperties(
    public static void parseLoggingProperties(
            File directory,
            File logDirectory,
            String logname) {
        String sourceMethod = "parseLoggingProperties(File,File,String)";
        String loggingConfigFile_String = System.getProperty("java.util.logging.config.file");
        System.out.println(loggingConfigFile_String);
        if (loggingConfigFile_String == null) {
            System.err.println(
                    "System.getProperty(\"java.util.logging.config.file\") == null in " + sourceClass + "." + sourceMethod);
        }
        logDirectory.mkdirs();
        File loggingPropertiesFile = new File(
                directory.toString() + loggingConfigFile_String);
        if (!loggingPropertiesFile.exists()) {
            System.err.println(
                    loggingPropertiesFile.toString() + " does not exist in " + sourceClass + "." + sourceMethod);
        }
        parseLoggingProperties(
             loggingPropertiesFile,
             directory,
             logDirectory,
             logname);
//        logger = parseLoggingProperties(
//             loggingPropertiesFile,
//             directory,
//             logDirectory,
//             logname);
//        return logger;
    }

    public static FileHandler getFileHandler(
            Level level,
            File directory,
            String name) {
        return getFileHandler(
                level,
                directory,
                name,
                Generic_Log.Generic_DefaultLogFileSizeLimit,
                Generic_Log.Generic_DefaultLogFileCount);
    }

    public static FileHandler getFileHandler(
            Level level,
            File directory,
            String name,
            int logFileSizeLimit,
            int logFileCount) {
        //Generic_Log.loggerNameLevel_HashMap.put(name, level);
        //logger = Logger.getLogger(name);
        FileHandler fileHandler = null;
        try {
//            File logDirectory = new File(
//                    directory.getCanonicalPath()
//                    + System.getProperty("file.separator")
//                    + name);
//            logDirectory.mkdirs();
//            File logFile = new File(
//                    logDirectory,
//                    "log");
            File logFile = new File(
                    directory,
                    name + ".log");
//            fileHandler = Generic_Log.loggerFileFileHandler_HashMap.get(logFile);
//            if (fileHandler != null) {
//                logger.removeHandler(fileHandler);
//            }
            fileHandler = new FileHandler(
                    logFile.toString(),
                    logFileSizeLimit,
                    logFileCount,
                    Generic_DefaultLogFileAppend);
//            Generic_Log.loggerFileFileHandler_HashMap.put(logFile, fileHandler);
//            Generic_Log.loggerFileHandlerFile_HashMap.put(fileHandler, logFile);
//            Generic_Log.loggerNameFile_HashMap.put(name, logFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(Generic_ErrorAndExceptionHandler.IOException);
        }
        //logger.addHandler(fileHandler);
        //LogManager.getLogManager().addLogger(logger);
        return fileHandler;
    }

    public static void reset(){
        LogManager.getLogManager().reset();
    }
//    public static void closeFileHandlers() {
//        for (FileHandler fileHandler : loggerFileHandlerFile_HashMap.keySet()) {
//            fileHandler.close();
//        }
//    }
//
//    public static FileHandler getFileHandler(String name) {
//        File file = loggerNameFile_HashMap.get(name);
//        if (file == null) {
//            Logger.getLogger(Generic_Log.class.getName()).log(Level.WARNING, "No mapping in loggerNameFile_HashMap for key {0}", name);
//        }
//        FileHandler result = loggerFileFileHandler_HashMap.get(file);
//        if (file == null) {
//            Logger.getLogger(Generic_Log.class.getName()).log(Level.WARNING, "No mapping in loggerFileFileHandler_HashMap for key {0}", file);
//        }
//        return result;
//    }

//    public static void log(Level level, String message) {
//        Logger.getLogger(Generic_DefaultLoggerName).log(level, message);
//    }
//
//    public static void log(String loggerName, Level level, String message) {
//        Logger.getLogger(loggerName).log(level, message);
//    }

    @Override
    public List<String> getLoggerNames() {
        Enumeration<String> loggerNames_Enumeration = LogManager.getLogManager().getLoggerNames();
        List<String> loggerNames_List = Collections.emptyList();
        while (loggerNames_Enumeration.hasMoreElements()) {
            String loggerName = loggerNames_Enumeration.nextElement();
            loggerNames_List.add(loggerName);
        }
//        Set<String> loggerNames_Set = loggerNameLevel_HashMap.keySet();
//        loggerNames_List.addAll(loggerNames_Set);
        return loggerNames_List;
    }

    @Override
    public String getLoggerLevel(String loggerName) {
        Level level = Logger.getLogger(loggerName).getLevel();
//        Level level = loggerNameLevel_HashMap.get(loggerName);
        return level.getName();
    }

    @Override
    public void setLoggerLevel(String loggerName, String levelName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getParentLoggerName(String loggerName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
