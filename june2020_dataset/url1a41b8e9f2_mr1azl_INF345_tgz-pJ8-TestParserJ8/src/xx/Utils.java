package xx;

import org.w3c.dom.Node;

import yyy.NodeTest;

/**
 */
public class Utils {
	private static final Double ZERO = new Double(0);
	private static final Double ONE = new Double(1);
	private static final Double NOT_A_NUMBER = new Double(Double.NaN);


    /**
     * Converts the supplied object to String.
     * @param object to convert
    
     * @return String value */
    public static String asString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        if (object instanceof Number) {
            double d = ((Number) object).doubleValue();
            long l = ((Number) object).longValue();
            return d == l ? String.valueOf(l) : String.valueOf(d);
        }
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue() ? "true" : "false";
        }
        if (object instanceof Node) {
            return ((Node) object).getTextContent();
        }
        if (object == null) {
            return "";
        }
        return String.valueOf(object);
    }

    /**
     * Method asNumber.
     * @param object Object
     * @return Number
     */
    public static Number asNumber(Object object) {
        if (object instanceof Number) {
            return (Number) object;
        }
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue() ? ONE : ZERO;
        }
        if (object instanceof String) {
            try {
                return new Double((String) object);
            }
            catch (NumberFormatException ex) {
                return NOT_A_NUMBER;
            }
        }
        return asNumber(asString(object));
    }

    /**
     * Method asDouble.
     * @param object Object
     * @return double
     */
    public static double asDouble(Object object) {
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue() ? 0.0 : 1.0;
        }
        if (object instanceof String) {
            if (object.equals("")) {
                return 0.0;
            }
            try {
                return Double.parseDouble((String) object);
            }
            catch (NumberFormatException ex) {
                return Double.NaN;
            }
        }
        return asDouble(asString(object));
    }

    /**
     * Method asBoolean.
     * @param object Object
     * @return boolean
     */
    public static boolean asBoolean(Object object) {
        if (object instanceof Number) {
            double value = ((Number) object).doubleValue();
            final int negZero = -0;
            return value != 0 && value != negZero && !Double.isNaN(value);
        }
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        }
        if(object instanceof String ){
        	return  Boolean.valueOf((String)object);
        }
        if(object instanceof NodeTest ){
        	return  Boolean.valueOf(((NodeTest)object).getNodeValue());
        }
        return object != null;
    }
}