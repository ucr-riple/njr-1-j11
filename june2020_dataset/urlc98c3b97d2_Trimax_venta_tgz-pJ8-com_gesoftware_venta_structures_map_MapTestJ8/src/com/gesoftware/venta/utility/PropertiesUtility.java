package com.gesoftware.venta.utility;

import com.gesoftware.venta.logging.LoggingUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * PropertiesUtility class definition
 */
public final class PropertiesUtility {
    /* Properties loaded from configuration file */
    private final Properties properties = new Properties();

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] configurationFile - configuration file path
     * AUTHOR: Eliseev Dmitry
     * */
    public PropertiesUtility(final String configurationFile) {
        try {
            properties.load(new FileInputStream(configurationFile));
        } catch (final IOException ignored) {
            LoggingUtility.core("Can't load " + configurationFile + " file");
        }
    } /* End of 'PropertiesUtility::PropertiesUtility' method */

    /* *
     * METHOD: Try to parse property as string value
     * RETURN: Property value
     *  PARAM: [IN] property - property name
     * AUTHOR: Eliseev Dmitry
     * */
    public final String getPropertyAsString(final String property) {
        return (String) properties.get(property);
    } /* End of 'PropertiesUtility::getPropertyAsString' method */

    /* *
     * METHOD: Try to parse property as double value
     * RETURN: Property value
     *  PARAM: [IN] property - property name
     * AUTHOR: Eliseev Dmitry
     * */
    public final Double getPropertyAsDouble(final String property) {
        try {
            return Double.parseDouble(getPropertyAsString(property));
        } catch (Exception e) {
            LoggingUtility.core("Can't parse property " + property + " as double value: " + e.getMessage());
        }

        return null;
    } /* End of 'PropertiesUtility::getPropertyAsDouble' method */

    /* *
     * METHOD: Try to parse property as integer value
     * RETURN: Property value
     *  PARAM: [IN] property - property name
     * AUTHOR: Eliseev Dmitry
     * */
    public final Integer getPropertyAsInt(final String property) {
        try {
            return Integer.parseInt(getPropertyAsString(property));
        } catch (final Exception e) {
            LoggingUtility.core("Can't parse property " + property + " as integer value: " + e.getMessage());
        }

        /* Can't parse integer value */
        return null;
    } /* End of 'PropertiesUtility::getPropertyAsInt' method */

    /* *
     * METHOD: Try to parse property as boolean value
     * RETURN: Property value
     *  PARAM: [IN] property - property name
     * AUTHOR: Eliseev Dmitry
     * */
    public final Boolean getPropertyAsBoolean(final String property) {
        try {
            return Boolean.parseBoolean(getPropertyAsString(property));
        } catch (final Exception e) {
            LoggingUtility.core("Can't parse property " + property + " as boolean value: " + e.getMessage());
        }

        /* Can't parse boolean value */
        return null;
    } /* End of 'PropertiesUtility::getPropertyAsBoolean' method */
} /* End of 'PropertiesUtility' class */
