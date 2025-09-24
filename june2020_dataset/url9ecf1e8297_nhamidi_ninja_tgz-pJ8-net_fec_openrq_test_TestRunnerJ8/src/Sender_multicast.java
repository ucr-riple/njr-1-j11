/* 
 * Copyright 2014 Jose Lopes and Hamidi Nassim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import RQLibrary.Encoder;
import RQLibrary.EncodingPacket;
import RQLibrary.EncodingSymbol;
import RQLibrary.SourceBlock;

public class Sender_multicast {
    
    static Random randomGenerator = new Random();
    final static int ID_PACKET = randomGenerator.nextInt(100);
    
    public static final double redundancyInLine(double currentLost, double lost) {
	float alpha = Utils.ALPHA;
	if ( currentLost == 1000 )
	    currentLost = 0.0;
	currentLost = currentLost / 1000;
	System.out.println("je suce " + currentLost);
	if ( currentLost < (lost + 0.001) && currentLost > (lost - 0.001) ) {
	    System.out.println("je suis un K SOS ");
	} else
	    lost = lost + alpha * (currentLost - lost);
	System.out.println("je suce pour " + lost);
	return lost;
    }
    
    public static final byte[] packetMaker(int flag, int idPacket, EncodingSymbol symbols) {
	byte[] byteFlag = Utils.intToByteArray(flag);
	byte[] packetId = Utils.intToByteArray(idPacket);
	byte[] SNB = Utils.intToByteArray(symbols.getSBN());
	byte[] ESI = Utils.intToByteArray(symbols.getESI());
	byte[] finalPacket = new byte[symbols.getData().length + 4 * Utils.IntegerSize];
	// Header for the code
	System.arraycopy(byteFlag, 0, finalPacket, 0, Utils.IntegerSize);
	System.arraycopy(packetId, 0, finalPacket, Utils.IntegerSize, Utils.IntegerSize);
	System.arraycopy(SNB, 0, finalPacket, Utils.IntegerSize * 2, Utils.IntegerSize);
	System.arraycopy(ESI, 0, finalPacket, Utils.IntegerSize * 3, Utils.IntegerSize);
	// Put the data
	System.arraycopy(symbols.getData(), 0, finalPacket, Utils.IntegerSize * 4, symbols.getData().length);
	return finalPacket;
    }
    
    public static void main(String[] args) throws Exception {
	if ( args.length != 8 ) {
	    StringBuilder s = new StringBuilder();
	    s.append("Usage: \n");
	    s.append("    java -jar Sender.jar pathToFile expectedLoss overhead destIP portNumber\n");
	    s.append("\n        - pathToFile  : path to the file that shall be sent.");
	    s.append("\n        - overhead    : the necessary symbol overhead needed for decoding (usually between 0 and 2).");
	    s.append("\n        - destIP      : the IP address of the receiver.");
	    s.append("\n        - portNumber  : the port the receiver will be listening on.");
	    s.append("\n        - nb of recv  : the number of receivers will be listening.");
	    s.append("\n        - redondance  : Para de test.");
	    s.append("\n        - historic    : if you want historic.");
	    s.append("\n        - historic in line    : if you want historic in line.");
	    
	    System.out.println(s.toString());
	    System.exit(1);
	}
	
	/**
	 * Test on parameters
	 */
	double lost = Utils.LOST * 1000;
	
	double redondance = Double.valueOf(args[5]);
	Boolean historique = Boolean.valueOf(args[6]);
	Boolean historiqueInline = Boolean.valueOf(args[7]);
	// open file and convert to bytes
	String fileName = args[0];
	File file = new File(fileName);
	int lengthOfTheFile = (int) file.length();
	
	byte[] data = null;
	try {
	    data = Files.readAllBytes(file.toPath());
	} catch (IOException e1) {
	    System.err.println("Could not open file.");
	    e1.printStackTrace();
	    System.exit(1);
	}
	
	// check loss
	float percentageLoss = 99;
	
	// check overhead
	int overhead = -1;
	
	try {
	    overhead = Integer.valueOf(args[1]);
	} catch (NumberFormatException e) {
	    System.err.println("Invalid overhead value. (must be a positive integer)");
	    System.exit(-1);
	}
	
	if ( overhead < 0 ) {
	    System.err.println("Invalid overhead value. (must be a positive integer)");
	    System.exit(-1);
	}
	
	// get IP and transform to InetAddress
	InetAddress destIP = null;
	
	try {
	    destIP = InetAddress.getByName(args[2]);
	} catch (UnknownHostException e2) {
	    e2.printStackTrace();
	    System.err.println("invalid IP");
	    System.exit(1);
	}
	int nbOfRecv;
	
	nbOfRecv = Integer.valueOf(args[4]);
	if ( nbOfRecv < 0 ) {
	    System.err.println("Invalid number of user (must be above 0)");
	    System.exit(-1);
	}
	
	// check destination port
	int destPort = -1;
	
	try {
	    destPort = Integer.valueOf(args[3]);
	} catch (NumberFormatException e) {
	    System.err.println("Invalid destination port. (must be above 1024)");
	    System.exit(-1);
	}
	
	if ( destPort < 1024 || destPort >= 65535 ) {
	    System.err.println("Invalid destination port. (must be above 1024)");
	    System.exit(-1);
	}
	
	RecepteurThreadMulti receptionThread = new RecepteurThreadMulti(destPort + 1, destIP, nbOfRecv, historique);
	
	/**
	 * preparation for the encoding
	 */
	
	// create a new Encoder instance (usually one per file)
	Encoder encoder = new Encoder(data, percentageLoss, overhead);
	
	// array that will contain the encoded symbols
	EncodingPacket[] encodedSymbols = null;
	
	// array of source blocks
	SourceBlock[] sourceBlocks = null;
	int noBlocks;
	
	// total number of source symbols (for all source blocks)
	int Kt = encoder.getKt();
	
	// partition the data into source blocks
	sourceBlocks = encoder.partition();
	noBlocks = sourceBlocks.length;
	int nbPacket = -1;
	// start the thread
	receptionThread.start();
	// long temps = System.currentTimeMillis() - before_2;
	
	// int normal_redundancy = (int) Math.round((((float) lengthOfTheFile / Utils.SYMB_LENGTH) * redondance) - ((float) lengthOfTheFile /
	// Utils.SYMB_LENGTH));
	boolean oneTime = true;
	if ( oneTime ) {
	    receptionThread.setTotalNumberOfPackets((int) Math.round((float) lengthOfTheFile / Utils.SYMB_LENGTH) + 1);
	    oneTime = false;
	}
	
	long before = System.currentTimeMillis();
	while (receptionThread.getStatusEndLoop()) {
	    
	    /**
	     * built of the socket
	     */
	    
	    // open UDP socket
	    MulticastSocket clientSocket = null;
	    try {
		clientSocket = new MulticastSocket();
	    } catch (SocketException e1) {
		e1.printStackTrace();
		System.err.println("Error opening socket.");
		System.exit(1);
	    }
	    
	    // allocate memory for all the encoded symbols
	    encodedSymbols = new EncodingPacket[noBlocks];
	    
	    for (int block = 0; block < noBlocks; block++) {
		// the block we'll be encoding+sending
		SourceBlock sb = sourceBlocks[block];
		if ( !receptionThread.getStatusEndLoop() ) {
		    break;
		}
		
		encodedSymbols[block] = encoder.encode(sb);
		
		EncodingSymbol[] symbols = encodedSymbols[block].getEncoding_symbols();
		
		int noSymbols = symbols.length;
		/*
		 * serialize and send the encoded symbols
		 */
		/*
		 * ObjectOutput out = null; byte[] serialized_data = null;
		 */
		boolean oneTimeBis = true;
		
		try {
		    
		    // serialize and send each encoded symbol
		    
		    int k = (int) Math.round((float) Kt * redondance) + 2;
		    final int historyRate = (int) Math.round((float) Kt / Utils.INLINE_REDON_RATE);
		    int historyCount = historyRate;
		    
		    receptionThread.setTimeSimu();
		    
		    int nbPacketSend = 0;
		    
		    for (int i = 0; i < noSymbols; i++) {
			
			nbPacketSend++;
			
			int nbOfPacketLost = receptionThread.getNumberOfPackets();
			if ( historiqueInline && nbOfPacketLost != 0 ) {
			    
			    double history = redundancyInLine(nbOfPacketLost, lost);
			    
			    receptionThread.resetNumberOfPackets();
			    k = (int) Math.round(history * nbPacketSend + history * nbPacketSend * (history + 1) + (Kt - nbPacketSend) * (history + 1)
				    + (Kt - nbPacketSend));
			    System.out.println("            " + nbOfPacketLost + "    history    " + history + "     nb_packet_send   " + nbOfPacketLost
				    + "  Kt   " + Kt);
			    nbPacketSend = 0;
			}
			
			// see the current state of the receiver
			if ( !receptionThread.getStatusEndLoop() ) {
			    break;
			}
			// simple serialization
			/*
			 * ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 * 
			 * out = new ObjectOutputStream(bos); out.writeObject(symbols[i]); serialized_data = bos.toByteArray(); out.close(); bos.close();
			 */
			
			// test_1 = symbols[i].getData();
			// byte[] test = new byte[4];
			
			// setup an UDP packet with the serialized symbol
			// and
			// the destination info
			
			// byte[] byte_array = { 0, 0, 0, 0 };
			// byte[] packet_id = { 0, 0, 0, 0 };
			// byte[] serialized_data_with_length = new byte[serialized_data.length + 2 * Utils.IntegerSize];
			nbPacket++;
			if ( oneTimeBis ) {
			    Utils.printInFile(String.valueOf(System.currentTimeMillis()), destPort, 5);
			    oneTimeBis = false;
			}
			
			if ( i > k ) {
			    
			    byte[] dataReady = packetMaker(Utils.FLAG_PUSH, ID_PACKET, symbols[i]);
			    
			    DatagramPacket sendPacket = new DatagramPacket(dataReady, dataReady.length, destIP, destPort);
			    
			    clientSocket.send(sendPacket);
			    // Sender timer
			    for (int u = 1; u < 10000; u++) {
				if ( receptionThread.getNeedMore() ) {
				    
				    receptionThread.setNeedMore(false);
				    break;
				}
				
				Thread.sleep(1);
			    }
			    k = k + receptionThread.getNumberOfPackets();
			    receptionThread.resetNumberOfPackets();
			    
			} else if ( historiqueInline && (i >= historyCount) ) {
			    byte[] dataReady = packetMaker(Utils.FLAG_PUSH, ID_PACKET, symbols[i]);
			    DatagramPacket sendPacket = new DatagramPacket(dataReady, dataReady.length, destIP, destPort);
			    clientSocket.send(sendPacket);
			    historyCount = historyCount + historyRate;
			} else {
			    // if you send a normal packet
			    byte[] dataReady = packetMaker(lengthOfTheFile, ID_PACKET, symbols[i]);
			    DatagramPacket sendPacket = new DatagramPacket(dataReady, dataReady.length, destIP, destPort);
			    
			    clientSocket.send(sendPacket);
			    Thread.sleep(Utils.BIT_RATE);
			}
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    System.err.println("Socket error.");
		    System.exit(1);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
	    }
	    // close the socket
	    try {
		clientSocket.close();
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	
	int totalOverhead = (nbPacket - Kt) * Utils.SYMB_LENGTH;
	float totalOverheadPourcent = 100 * ((float) totalOverhead / (float) (Kt * Utils.SYMB_LENGTH));
	nbPacket = nbPacket + receptionThread.getNbAck();
	System.out.println(totalOverhead);
	System.out.println(totalOverheadPourcent);
	System.out.println(receptionThread.getNbAck());
	System.out.println((float) receptionThread.getTime_1() / 1000);
	System.out.println((float) receptionThread.getTime_2() / 1000);
	System.out.println((float) ((float) receptionThread.getTime_2() - (float) receptionThread.getTime_1()) / (float) receptionThread.getTime_1());
	// System.out.println(time_2);
	
	receptionThread.join();
    }
}
