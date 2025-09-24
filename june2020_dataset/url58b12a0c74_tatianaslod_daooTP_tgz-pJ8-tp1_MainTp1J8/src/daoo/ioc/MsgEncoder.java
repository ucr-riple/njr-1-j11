package daoo.ioc;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public interface MsgEncoder {

    byte[] encode(String msg);

    String decode(byte[] code);

}
