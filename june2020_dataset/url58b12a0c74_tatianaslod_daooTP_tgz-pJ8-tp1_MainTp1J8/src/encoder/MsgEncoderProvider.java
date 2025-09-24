package encoder;

import daoo.ioc.MsgEncoder;

import java.io.*;
import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class MsgEncoderProvider {

    public static void printEncoders(){
        final ServiceLoader<MsgEncoder> loader = ServiceLoader.load(MsgEncoder.class);
        for (MsgEncoder msgEncoder : loader) {
            System.out.println(msgEncoder.getClass().getName());
        }
    }

    public static MsgEncoder getMsgEncoder() throws Exception {
        final ServiceLoader<MsgEncoder> loader = ServiceLoader.load(MsgEncoder.class);
        for (MsgEncoder encoder : loader) {
            if(encoder.getClass().getName().equals(getImplementationName())){
                return encoder;
            }
        }
        throw new Exception("No such Encoder found");
    }

    private static String getImplementationName() {
        final FileReader fr;
        try {
            fr = new FileReader("MsgEncoder.txt");
            final BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            fr.close();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }


    public static void main(String[] args) {
        try {
            MsgEncoderProvider.getMsgEncoder();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
