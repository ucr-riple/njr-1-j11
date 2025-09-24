package com.gesoftware.venta.network.model;

import com.gesoftware.venta.utility.CompressionUtility;
import java.nio.charset.Charset;
import java.io.Serializable;
import java.util.Arrays;

/* *
 * Message class definition
 * */
public final class Message implements Serializable {
    /* Time */
    private final long m_Timestamp;

    /* Message data */
    private final byte[] m_Data;

    /* *
     * METHOD: Message class constructor
     *  PARAM: [IN] data - bytes array data
     * AUTHOR: Eliseev Dmitry
     * */
    public Message(final byte data[]) {
        m_Timestamp = System.currentTimeMillis();
        m_Data      = data;
    } /* End of 'Message::Message' method */

    /* *
     * METHOD: Message class constructor
     *  PARAM: [IN] data - bytes array data
     * AUTHOR: Eliseev Dmitry
     * */
    public Message(final String data) {
        this(data.getBytes());
    } /* End of 'Message::Message' method */

    /* *
     * METHOD: Message class constructor
     *  PARAM: [IN] object - some serializable object
     * AUTHOR: Eliseev Dmitry
     * */
    public Message(final Object object) {
        this(CompressionUtility.compress(object));
    } /* End of 'Message::Message' method */

    /* *
     * METHOD: Bytes data representation getter
     * RETURN: Data bytes representation
     * AUTHOR: Eliseev Dmitry
     * */
    public final byte[] getData() {
        return m_Data;
    } /* End of 'Message::getData' method */

    /* *
     * METHOD: Gets message size
     * RETURN: Data size in bytes
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getSize() {
        return (m_Data != null)?m_Data.length:0;
    } /* End of 'Message::getSize' method */

    @Override
    public final String toString() {
        return (m_Data != null)?new String(m_Data, Charset.forName("UTF-8")):null;
    } /* End of 'Message::toString' method */

    /* *
     * METHOD: Compares two messages sizes
     * RETURN: TRUE if messages has same sizes, FALSE otherwise
     *  PARAM: [IN] message - message to compare with this one
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean messagesHasSameSizes(final Message message) {
        return m_Data != null && m_Data.length == message.m_Data.length;
    } /* End of 'Message::messagesHasSameSize' method */

    /* *
     * METHOD: Compares two messages by their values
     * RETURN: TRUE if messages has same sizes, FALSE otherwise
     *  PARAM: [IN] message - message to compare with this one
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean messagesAreEqual(final Message message) {
        /* Messages has different sizes */
        if (!messagesHasSameSizes(message))
            return false;

        /* At least one of characters is not equal to same at another message */
        for (int i = 0; i < message.m_Data.length; i++)
            if (m_Data[i] != message.m_Data[i])
                return false;

        /* Messages are equal */
        return true;
    } /* End of 'Message::messagesAreEqual' method */

    /* *
     * METHOD: Tries to restore object, that may be packed in message
     * RETURN: Restored object if success, null otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    public final Object getObject() {
        return CompressionUtility.decompress(m_Data);
    } /* End of 'Message::getObject' method */

    /* *
     * METHOD: Gets message sending time (in server time)
     * RETURN: Message sending time
     * AUTHOR: Eliseev Dmitry
     * */
    public final long getTimestamp() {
        return m_Timestamp;
    } /* End of 'Message::getTimestamp' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Message && messagesAreEqual((Message) obj);
    } /* End of 'Message::equals' method */

    @Override
    public final int hashCode() {
        return Arrays.hashCode(m_Data);
    } /* End of 'Message::hashCode' method */
} /* End of 'Message' class */
