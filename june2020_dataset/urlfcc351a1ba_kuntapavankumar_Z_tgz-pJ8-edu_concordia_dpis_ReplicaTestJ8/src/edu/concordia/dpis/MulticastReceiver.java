package edu.concordia.dpis;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

@Deprecated
public class MulticastReceiver extends Thread{
	
	private int port;
	
	public MulticastReceiver(int port) {
		this.port = port;
	}
	
	public void run(){
		
		MulticastSocket socket = null;
		DatagramPacket inPacket = null;
		DatagramSocket socket2 = null;
		byte[] receiveData = new byte[1024];
		
		try{
			System.out.println("Receiver is up");
			socket = new MulticastSocket(port);
			InetAddress address = InetAddress.getByName("230.0.0.1");
			socket.joinGroup(address);
			
			while(true){
				inPacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(inPacket);
				
				String received = new String(inPacket.getData());
				System.out.println(received);
				
				// after processing the request when you have to send the result back
				socket2 = new DatagramSocket();
				byte[] reply = new byte[1024];
				reply = "request executed".getBytes();
				DatagramPacket packet = new DatagramPacket(reply, reply.length,inPacket.getAddress(),inPacket.getPort());
				socket2.send(packet);
				System.out.println("response sent");
			}
		} catch (SocketException e) {
		System.out.println("SOCKET:" + e.getMessage());
		} catch (Exception e) {
		System.out.println("IO:" + e.getMessage());}
		
	}
	
	

}
