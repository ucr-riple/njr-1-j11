package com.gesoftware.venta.utility;

import com.gesoftware.venta.logging.LoggingUtility;

import java.io.*;

/**
 * SerializationUtility class definition
 */
public final class SerializationUtility {

    /* *
     * METHOD: Transforms object to a byte array if possible
     * RETURN: Object's byte array representation
     *  PARAM: [IN] object - object for serialization
     * AUTHOR: Eliseev Dmitry
     * */
    public static byte[] pack(final Object object) {
        ByteArrayOutputStream bytesArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = null;

        /* Serialized object */
        byte[] serializedObject = null;

        try {
            objectOutput = new ObjectOutputStream(bytesArrayOutputStream);
            objectOutput.writeObject(object);

            /* Object serialization */
            serializedObject = bytesArrayOutputStream.toByteArray();
        } catch (final IOException e) {
            LoggingUtility.exception(e);
        } finally {
            /* Close object output stream */
            try {
                if (objectOutput != null)
                    objectOutput.close();
            } catch (final IOException e) {
                LoggingUtility.error("Error during closing object output stream: " + e.getMessage());
            }

            /* Close bytes output stream */
            try {
                bytesArrayOutputStream.close();
            } catch (final IOException e) {
                LoggingUtility.error("Error during closing bytes output stream: " + e.getMessage());
            }
        }

        return serializedObject;
    } /* End of 'SerializationUtility::pack' method */

    /* *
     * METHOD: Transforms a byte array object's representation to an object if possible
     * RETURN: Object
     *  PARAM: [IN] serializedObject - object's byte array representation
     * AUTHOR: Eliseev Dmitry
     * */
    public static Object unpack(final byte[] serializedObject) {
        final ByteArrayInputStream bytesArrayInputStream = new ByteArrayInputStream(serializedObject);
        ObjectInput objectInput = null;

        /* Object after restoration */
        Object object = null;

        try {
            objectInput = new ObjectInputStream(bytesArrayInputStream);
            object = objectInput.readObject();

        } catch (final ClassNotFoundException e) {
            LoggingUtility.error("Class not found: " + e.getMessage());
        } catch (final IOException e) {
            LoggingUtility.exception(e);
        } finally {
            /* Closing bytes array input stream */
            try {
                bytesArrayInputStream.close();
            } catch (final IOException e) {
                LoggingUtility.error("Error during closing bytes input stream: " + e.getMessage());
            }

            /* Closing object input stream */
            try {
                if (objectInput != null)
                    objectInput.close();
            } catch (final IOException e) {
                LoggingUtility.error("Error during closing object input stream: " + e.getMessage());
            }
        }

        return object;
    } /* End of 'SerializationUtility::pack' method */
} /* End of 'SerializationUtility' class */
