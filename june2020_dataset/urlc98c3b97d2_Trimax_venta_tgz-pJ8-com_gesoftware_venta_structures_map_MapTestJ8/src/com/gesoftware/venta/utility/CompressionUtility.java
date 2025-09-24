package com.gesoftware.venta.utility;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Compression utility class definition
 **/
public final class CompressionUtility {
    private static final int c_BufferSize = 4096;

    /* *
     * METHOD: Compress byte array to another byte array
     * RETURN: Compressed byte array
     *  PARAM: [IN] bytes - byte array to compress
     * AUTHOR: Eliseev Dmitry
     * */
    private static byte[] compressByteArray(final byte[] bytes) {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        final Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(bytes);
        deflater.finish();

        final byte[] tempBuffer = new byte[c_BufferSize];
        try {
            while (!deflater.finished())
                byteArrayOutputStream.write(tempBuffer, 0, deflater.deflate(tempBuffer));
        } catch (final Exception ignored) {}

        try {
            byteArrayOutputStream.close();
        } catch(final Exception ignored){}

        return byteArrayOutputStream.toByteArray();
    } /* End of 'CompressionUtility::compressByteArray' method */

    /* *
     * METHOD: Transforms object to a compressed byte array if possible
     * RETURN: Object's byte array representation
     *  PARAM: [IN] object - object for compression
     * AUTHOR: Eliseev Dmitry
     * */
    public static byte[] compress(final Object object) {
        return compressByteArray(SerializationUtility.pack(object));
    } /* End of 'CompressionUtility::compress' method */

    /* *
     * METHOD: Decompress byte array to another byte array
     * RETURN: Decompressed byte array
     *  PARAM: [IN] bytes - byte array to decompress
     * AUTHOR: Eliseev Dmitry
     * */
    private static byte[] decompressByteArray(final byte[] bytes){
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final Inflater inflater = new Inflater();
        inflater.setInput(bytes);

        final byte[] tempBuffer = new byte[c_BufferSize];
        try{
            while (!inflater.finished())
                byteArrayOutputStream.write(tempBuffer, 0, inflater.inflate(tempBuffer));

        } catch (final Exception ignored) {}

        try {
            byteArrayOutputStream.close();
        } catch(final Exception ignored){}

        return byteArrayOutputStream.toByteArray();
    } /* End of 'CompressionUtility::decompressByteArray' method */

    /* *
     * METHOD: Transforms ompressed byte array to an object if possible
     * RETURN: Decompressed object
     *  PARAM: [IN] compressedObject - compressed object byte representation
     * AUTHOR: Eliseev Dmitry
     * */
    public static Object decompress(final byte[] compressedObject) {
        return SerializationUtility.unpack(decompressByteArray(compressedObject));
    } /* End of 'CompressionUtility::decompress' method */
} /* End of 'CompressionUtility' class */
