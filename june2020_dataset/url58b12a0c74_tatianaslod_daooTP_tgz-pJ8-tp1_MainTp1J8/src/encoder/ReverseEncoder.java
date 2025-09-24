package encoder;


import daoo.ioc.MsgEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class ReverseEncoder implements MsgEncoder {
    @Override
    public byte[] encode(String msg) {
        return new StringBuilder(msg).reverse().toString().getBytes();
    }

    @Override
    public String decode(byte[] code) {
        return new StringBuilder(new String(code)).reverse().toString();
    }

    public static void main(String[] args) {
        MsgEncoderProvider.printEncoders();
    }
}
