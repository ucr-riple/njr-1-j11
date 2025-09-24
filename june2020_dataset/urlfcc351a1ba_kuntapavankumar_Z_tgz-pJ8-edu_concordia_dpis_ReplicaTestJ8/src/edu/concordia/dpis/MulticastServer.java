package edu.concordia.dpis;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

@Deprecated
public class MulticastServer extends Thread {
	
	private long FIVE_SECONDS = 5000;
	private int itHasMoreRequest = 1;
	private int port;
	
	
	public MulticastServer(int port){
		this.port = port;	
	}

	public void run(){
		
		do
		{
			DatagramSocket socket = null;
			try
			{	System.out.println("server is up");
				socket = new DatagramSocket();
				byte[] buffer = new byte[1024];
				buffer = "just the testing code".getBytes();
				InetAddress group = InetAddress.getByName("230.0.0.1");
				DatagramPacket request = new DatagramPacket(buffer, buffer.length,group,port);
				socket.send(request);
				/*
				//receiving the reply
				byte[] replybuffer = new byte[1024];
				DatagramPacket reply = new DatagramPacket(replybuffer, replybuffer.length);
				socket.receive(reply);
				System.out.println(new String(reply.getData()));
				*/
				
			
			} catch (SocketException e) {
			System.out.println("SOCKET:" + e.getMessage());
			} catch (Exception e) {
			System.out.println("IO:" + e.getMessage());
			}/*finally {
				if (null != socket)
					socket.close();
			}*/
		
			getReply(socket); 
			// sleep for a while for the request processing at other replicas
			try
			{
				sleep((long)(Math.random() * FIVE_SECONDS));
			} 
			catch (InterruptedException e) 
			{ 
				System.out.println("Interrupt" + e.getMessage()); 
			}
			itHasMoreRequest++;
			
		}while(itHasMoreRequest != 2);
    }

	private void getReply(DatagramSocket socket) {
		while(true)
		{
			try	
			{
		
				byte[] replybuffer = new byte[1024];
				DatagramPacket reply = new DatagramPacket(replybuffer, replybuffer.length);
				socket.receive(reply);
				System.out.println(new String(reply.getData()));
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	} 
	
}