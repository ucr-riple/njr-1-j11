package encoder;

import daoo.ioc.MsgEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class PlainEncoder implements MsgEncoder {
    @Override
    public byte[] encode(String msg) {
        return msg.getBytes();
    }

    @Override
    public String decode(byte[] code) {
        return new String(code);
    }

}
