package com.gesoftware.venta.logging;

import com.gesoftware.venta.utility.ValidationUtility;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Logging class definition
 **/
public final class LoggingUtility {
    /* Log name */
    private String m_Name;

    /* Log file name */
    private String m_OutputFile;

    /* Logging level class definition */
    public static enum LoggingLevel {
        LEVEL_NONE    (0, ""),
        LEVEL_ERROR   (1, "   ERROR"),
        LEVEL_WARNING (2, " WARNING"),
        LEVEL_INFO    (3, "    INFO"),
        LEVEL_INTERNAL(4, "INTERNAL"),
        LEVEL_CORE    (5, "    CORE"),
        LEVEL_DEBUG   (6, "   DEBUG");

        /* Logging levels names to levels map */
        private static final Map<String, LoggingLevel> m_LoggingLevels = new HashMap<String, LoggingLevel>();
        static {
            for (final LoggingLevel level : LoggingLevel.values())
                m_LoggingLevels.put(level.getName().trim(), level);
        }

        /* Enum item value */
        private final int m_Value;

        /* Enum item name */
        private final String m_Name;

        /* *
         * METHOD: Logging level enum class constructor
         * AUTHOR: Eliseev Dmitry
         * */
        private LoggingLevel(final int value, final String name) {
            this.m_Value = value;
            this.m_Name  = name;
        } /* End of 'LoggingLevel::LoggingLevel' method */

        /* *
         * METHOD: Logging level value getter
         * RETURN: Current logging level value
         * AUTHOR: Eliseev Dmitry
         * */
        public int getValue() {
            return m_Value;
        } /* End of 'LoggingLevel::getValue' method */

        /* *
         * METHOD: Logging level name getter
         * RETURN: Current logging level value
         * AUTHOR: Eliseev Dmitry
         * */
        public String getName() {
            return m_Name;
        } /* End of 'LoggingLevel::getName' method */

        /* *
         * METHOD: Get logging level by it's name
         * RETURN: Logging level if success, LEVEL_NONE otherwise
         *  PARAM: [IN] name - logging level name
         * AUTHOR: Eliseev Dmitry
         * */
        public static LoggingLevel getByName(final String name) {
            if (name == null)
                return LoggingLevel.LEVEL_NONE;

            final LoggingLevel loggingLevel = m_LoggingLevels.get(name.toUpperCase());
            return (loggingLevel == null)?LoggingLevel.LEVEL_NONE:loggingLevel;
        } /* End of 'LoggingLevel::getByName' method */
    } /* End of 'LoggingLevel' enum class */

    /* Date formatter */
    private final SimpleDateFormat m_Formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /* Current logging level */
    private LoggingLevel m_LoggingLevel = LoggingLevel.LEVEL_NONE;

    /* Real logging utility instance */
    private static final LoggingUtility c_Self = new LoggingUtility();

    /* *
     * METHOD: Logging utility class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    private LoggingUtility() {
        m_Formatter.setTimeZone(Calendar.getInstance().getTimeZone());
        m_Name = "";
    } /* End of 'LoggingUtility::LoggingUtility' method */

    /* *
     * METHOD: Sets current logging level
     *  PARAM: [IN] level - logging level
     * AUTHOR: Eliseev Dmitry
     * */
    private void setLevel(final LoggingLevel level) {
        m_LoggingLevel = level;
    } /* End of 'LoggingLevel::setLevel' method */

    /* *
     * METHOD: Prints message to log file
     *  PARAM: [IN] level   - message level
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    private void print2File(final String outputString) {
        /* No output file specified */
        if (m_OutputFile == null)
            return;

        try {
            /* Open file */
            final FileWriter fileWriter = new FileWriter(m_OutputFile, true);
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            /* Writing data */
            bufferedWriter.write(outputString + "\r\n");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            /* Close file */
            bufferedWriter.close();
            fileWriter.close();
        } catch (final IOException ignored) {}
    } /* End of 'LoggingUtility::print2File' method */

    /* *
     * METHOD: Gets formatted logger name
     * AUTHOR: Eliseev Dmitry
     * */
    private String getName() {
        return ValidationUtility.isEmpty(m_Name)?"":"<" + m_Name + ">";
    } /* End of 'LoggingUtility::getName' method */

    /* *
     * METHOD: Prints message to log output
     *  PARAM: [IN] level   - message level
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    private synchronized void print(final LoggingLevel level, final String message) {
        /* Prepare formatted log message */
        final Calendar calendar = Calendar.getInstance(Calendar.getInstance().getTimeZone());
        final String output = getName() + " [" + m_Formatter.format(calendar.getTime()) + "] " + level.getName() + ": " + message;

        /* Writing to file */
        print2File(output);

        /* Print message to console */
        System.out.println(output);
    } /* End of 'LoggingUtility::print' method */

    /* *
     * METHOD: Prints message to log output if it's level allows
     *  PARAM: [IN] level   - message level
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    private void report(final LoggingLevel level, final String message) {
        if (m_LoggingLevel.getValue() >= level.getValue())
            print(level, message);
    } /* End of 'LoggingUtility::report' method */

    /* *
     * METHOD: Sets logger name
     *  PARAM: [IN] name - logger name
     * AUTHOR: Eliseev Dmitry
     * */
    private void setName(final String name) {
        m_Name = name;
    } /* End of 'LoggingUtility::setName' method */

    /* *
     * METHOD: Sets output file name
     *  PARAM: [IN] outputFile - output file name
     * AUTHOR: Eliseev Dmitry
     * */
    private void setFile(final String outputFile) {
        m_OutputFile = outputFile;
    } /* End of 'LoggingUtility::setFile' method */

    /* *
     * METHOD: Prints exception to log output
     *  PARAM: [IN] e - exception to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void exception(final Throwable e) {
        debug("Exception (" + e.getClass().getName() + ") message: " + e.getMessage());
        for (final StackTraceElement stackLine : e.getStackTrace())
            debug("Stack trace: " + stackLine.getMethodName() + ":" + stackLine.getLineNumber() + "@" + stackLine.getFileName());
    } /* End of 'LoggingUtility::exception' method */

    /* *
     * METHOD: Prints debug message to log
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void debug(final String message) {
        c_Self.report(LoggingLevel.LEVEL_DEBUG, message);
    } /* End of 'LoggingUtility::debug' method */

    /* *
     * METHOD: Prints core message to log
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void core(final String message) {
        c_Self.report(LoggingLevel.LEVEL_CORE, message);
    } /* End of 'LoggingUtility::core' method */

    /* *
     * METHOD: Prints internal message to log
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void internal(final String message) {
        c_Self.report(LoggingLevel.LEVEL_INTERNAL, message);
    } /* End of 'LoggingUtility::internal' method */

    /* *
     * METHOD: Prints info message to log
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void info(final String message) {
        c_Self.report(LoggingLevel.LEVEL_INFO, message);
    } /* End of 'LoggingUtility::info' method */

    /* *
     * METHOD: Prints warning message to log
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void warning(final String message) {
        c_Self.report(LoggingLevel.LEVEL_WARNING, message);
    } /* End of 'LoggingUtility::warning' method */

    /* *
     * METHOD: Prints error message to log
     *  PARAM: [IN] message - message to print
     * AUTHOR: Eliseev Dmitry
     * */
    public static void error(final String message) {
        c_Self.report(LoggingLevel.LEVEL_ERROR, message);
    } /* End of 'LoggingUtility::error' method */

    /* *
     * METHOD: Sets current logging level
     *  PARAM: [IN] level - logging level
     * AUTHOR: Eliseev Dmitry
     * */
    public static void setLoggingLevel(final LoggingLevel level) {
        c_Self.setLevel(level);
    } /* End of 'LoggingUtility::setLoggingLevel' method */

    /* *
     * METHOD: Sets logger name
     *  PARAM: [IN] name - logger name
     * AUTHOR: Eliseev Dmitry
     * */
    public static void setDisplayName(final String name) {
        c_Self.setName(name);
    } /* End of 'LoggingUtility::setName' method */

    /* *
     * METHOD: Sets output file name
     *  PARAM: [IN] outputFile - output file name
     * AUTHOR: Eliseev Dmitry
     * */
    public static void setOutputFile(final String outputFile) {
        c_Self.setFile(outputFile);
    } /* End of 'LoggingUtility::setOutputFile' method */
} /* End of 'LoggingUtility' class */