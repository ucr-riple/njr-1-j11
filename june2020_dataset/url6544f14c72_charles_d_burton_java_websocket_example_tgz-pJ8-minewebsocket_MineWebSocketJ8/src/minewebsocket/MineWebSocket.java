/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minewebsocket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import minewebsocket.handlers.MessageHandler;
import minewebsocket.interfaces.JSONListener;

/**
 *
 * @author charles
 */
public class MineWebSocket {
    //Ugly hack,never do this for real
    private static Scanner scan = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {
        /*System.out.print("Host: " );
        String host = scan.nextLine();
        System.out.print("Port: ");
        String port = scan.nextLine();*/
        //scan.close();
        //scan.nextLine()
        TorchAction rt = new TorchAction("chuckyvod.no-ip.biz", 5000);
        rt.startTest();
    }
}
