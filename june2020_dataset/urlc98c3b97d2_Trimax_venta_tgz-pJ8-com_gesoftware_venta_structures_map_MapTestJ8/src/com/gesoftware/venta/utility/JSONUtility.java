package com.gesoftware.venta.utility;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON utility class definition
 **/
public final class JSONUtility {

    /* *
     * METHOD: Parses JSON object to map
     * RETURN: Parsed map
     *  PARAM: [IN] object - JSON object
     * AUTHOR: Eliseev Dmitry
     * */
    private static Map<String, Object> parseMap(final JSONObject object) {
        final Map<String, Object> map = new HashMap<String, Object>();

        for (final Object key : object.keySet())
            map.put(key.toString(), parse(object.get(key)));

        return map;
    } /* End of 'JSONUtility::parseMap' method */

    /* *
     * METHOD: Parses JSON array to array
     * RETURN: Parsed array
     *  PARAM: [IN] collection - JSON array
     * AUTHOR: Eliseev Dmitry
     * */
    private static ArrayList<Object> parseArray(final JSONArray collection) {
        final ArrayList<Object> array = new ArrayList<Object>(collection.size());

        for (final Object object : collection)
            array.add(parse(object));

        return array;
    } /* End of 'JSONUtility::parseArray' method */

    /* *
     * METHOD: Parses JSON value to string
     * RETURN: Parsed string
     *  PARAM: [IN] value - JSON value
     * AUTHOR: Eliseev Dmitry
     * */
    private static String parseValue(final JSONValue value) {
        return value.toString();
    } /* End of 'JSONUtility::parseValue' method */

    /* *
     * METHOD: Parses JSON object to object
     * RETURN: Parsed object
     *  PARAM: [IN] object - JSON object
     * AUTHOR: Eliseev Dmitry
     * */
    private static Object parse(final Object object) {
        if (object instanceof JSONObject)
            return parseMap((JSONObject) object);
        else if (object instanceof JSONArray)
            return parseArray((JSONArray) object);
        else if (object instanceof JSONValue)
            return parseValue((JSONValue) object);

        return object;
    } /* End of 'JSONUtility::parse' method */

    /* *
     * METHOD: Parses JSON string to object
     * RETURN: Parsed object
     *  PARAM: [IN] data - JSON string
     * AUTHOR: Eliseev Dmitry
     * */
    public static Object parse(final String data) {
        return parse(JSONValue.parse(data));
    } /* End of 'JSONUtility::parse' method */
} /* End of 'JSONUtility' class */
