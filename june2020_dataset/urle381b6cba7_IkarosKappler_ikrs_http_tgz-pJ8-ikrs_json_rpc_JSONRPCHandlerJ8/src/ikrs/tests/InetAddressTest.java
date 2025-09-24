package ikrs.tests;

import java.net.*;
import java.util.*;

public class InetAddressTest {




    public static void main( String[] argv ) {

	try {

	    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	    //Iterator<NetworkInterface> iter = interfaces.
	    while( interfaces.hasMoreElements() ) {

		NetworkInterface interf = interfaces.nextElement();
		System.out.println( "Interface: " + interf );

		Enumeration<InetAddress> addresses = interf.getInetAddresses();

		while( addresses.hasMoreElements() ) {

		    InetAddress addr = addresses.nextElement();
		    
		    //System.out.println( " Addresss: " + addr );
		    System.out.println( addr.getCanonicalHostName()+" "+    addr.getHostAddress());


		}

	    }
	    System.out.println( "" );


	    InetAddress localhost = InetAddress.getLocalHost();
	    System.out.println( "localhost="+localhost );
	    
	} catch( UnknownHostException e ) {  

	    e.printStackTrace();

	} catch( SocketException e ) {
	    e.printStackTrace();
	}

    }

}