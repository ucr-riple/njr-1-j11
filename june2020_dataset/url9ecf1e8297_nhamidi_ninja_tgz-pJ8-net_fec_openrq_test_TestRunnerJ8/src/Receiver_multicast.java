import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Random;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import RQLibrary.Encoder;
import RQLibrary.EncodingPacket;
import RQLibrary.EncodingSymbol;
import RQLibrary.Partition;
import RQLibrary.SingularMatrixException;
import RQLibrary.SourceBlock;

public class Receiver_multicast {
    
    // Simulate a lost of the canal
    public static boolean packetIsLost(float percentageLoss) {
	Random randomGenerator = new Random();
	int rand = randomGenerator.nextInt(100);
	if ( rand < (int) percentageLoss ) {
	    return true;
	}
	return false;
	
    }
    
    public static final int lookForTheAnswer(InetAddress groupeIP, int port) throws IOException {
	int packetData = 0;
	MulticastSocket socketReception;
	socketReception = new MulticastSocket(port);
	socketReception.joinGroup(groupeIP);
	byte[] receiveData = new byte[500];
	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	
	try {
	    socketReception.receive(receivePacket);
	    byte[] packetDataTab = receivePacket.getData();
	    
	    packetData = Utils.byteArrayToInt(packetDataTab);
	    
	} catch (Exception exc) {
	    System.out.println(exc);
	}
	
	socketReception.close();
	return packetData;
    }
    
    // byte[] serialized_data = new byte[256];
    
    public static void main(String[] args) throws Exception {
	
	if ( args.length != 8 ) {
	    StringBuilder s = new StringBuilder();
	    s.append("Usage: \n");
	    s.append("    java -jar Receiver.jar pathToFile fileSize overhead port\n");
	    s.append("\n        - pathToFile  : path to store the received file.");
	    s.append("\n        - overhead    : the necessary symbol overhead needed for decoding (usually between 0 and 2).");
	    s.append("\n        - destIP      : the IP address for the multicast.");
	    s.append("\n        - portNumber  : the port the receiver will be listening on.");
	    s.append("\n        - canalloss   : the loss due to the canal.");
	    s.append("\n        - test   : Para de test.");
	    s.append("\n        - redondance  : Para de test.");
	    System.out.println(s.toString());
	    System.exit(1);
	}
	System.out.println(Utils.IntegerSize);
	Boolean historyInLine = Boolean.valueOf(args[7]);
	double redondance = Double.valueOf(args[6]);
	
	String fileName = args[0];
	
	// check fileSize
	long fileSize = 0;
	
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
	InetAddress sendIP = null;
	
	try {
	    sendIP = InetAddress.getByName(args[2]);
	} catch (UnknownHostException e2) {
	    e2.printStackTrace();
	    System.err.println("invalid IP");
	    System.exit(1);
	}
	
	// check source port
	int srcPort = -1;
	
	try {
	    srcPort = Integer.valueOf(args[3]);
	} catch (NumberFormatException e) {
	    System.err.println("Invalid port. (must be above 1024)");
	    System.exit(-1);
	}
	
	if ( srcPort < 1024 || srcPort >= 65535 ) {
	    System.err.println("Invalid port. (must be above 1024)");
	    System.exit(-1);
	}
	
	float canalloss = -1;
	
	try {
	    canalloss = Integer.valueOf(args[4]);
	} catch (NumberFormatException e) {
	    System.err.println("Invalid network loss percentage. (must be a float in [0.0, 100.0[)");
	    System.exit(-1);
	}
	
	if ( canalloss < 0 || canalloss >= 100 ) {
	    System.err.println("Invalid network loss percentage. (must be a float in [0.0, 100.0[)");
	    System.exit(-1);
	}
	
	int testParameter = Integer.valueOf(args[5]);
	
	EmetteurThreadMulti sendingThread = new EmetteurThreadMulti(sendIP, srcPort + 1);
	
	System.out.println("Listening for file " + fileName + " (" + fileSize + " bytes) at port " + srcPort + "\n");
	/**
	 * preparation for the decoding
	 */
	sendingThread.start();
	// the RTT
	
	Treatment treatmentThread = new Treatment(Utils.RTT);
	
	/**
	 * Get the first packet to get the size of the file
	 */
	MulticastSocket serverSocketForTheSize = null;
	try {
	    serverSocketForTheSize = new MulticastSocket(srcPort);
	    serverSocketForTheSize.joinGroup(sendIP);
	} catch (SocketException e1) {
	    e1.printStackTrace();
	    System.err.println("Error opening socket.");
	    System.exit(1);
	}
	byte[] dataReceiverSize = new byte[500];
	
	// //////////////////////////
	// //////////////////////////changer nom de variable
	// //////////////////////////
	// //////////////////////////
	
	// create a UDP packet
	DatagramPacket receivePacketForTheSize = new DatagramPacket(dataReceiverSize, dataReceiverSize.length);
	
	serverSocketForTheSize.receive(receivePacketForTheSize);
	boolean one_time = true;
	if ( one_time ) {
	    Utils.printInFile(String.valueOf(System.currentTimeMillis()), srcPort, 0);
	    one_time = false;
	}
	
	// get the packet's payload
	byte[] packetData_for_the_size = receivePacketForTheSize.getData();
	for (int i = 0; i < Utils.IntegerSize; i++) {
	    fileSize = (fileSize << 8) + (packetData_for_the_size[i] & 0xff);
	}
	byte[] first_packet_data = new byte[receivePacketForTheSize.getData().length - Utils.IntegerSize * 2];
	System.arraycopy(receivePacketForTheSize.getData(), Utils.IntegerSize * 2, first_packet_data, 0, first_packet_data.length);
	treatmentThread.addPacket(first_packet_data);
	serverSocketForTheSize.close();
	
	// /////////////////////////////////
	// /////////////////////////////////
	// create a new Encoder instance (usually one per file) and for that
	// get the file size
	Encoder encoder = new Encoder((int) fileSize);
	
	// total number of source symbols (for all source blocks)overhead
	int Kt = encoder.getKt();
	System.out.println("# source symbols: " + Kt);
	
	// number of source blocks
	int noBlocks = encoder.Z;
	System.out.println("# source blocks: " + noBlocks);
	
	// the minimum amount of symbols we'll be waiting for before trying
	// to
	// decode
	int totalSymbols = Kt + noBlocks * overhead;
	
	Partition KZ = new Partition(Kt, noBlocks);
	int KL = KZ.get(1);
	int KS = KZ.get(2);
	int ZL = KZ.get(3);
	
	/**
	 * built of the socket
	 * 
	 */
	// create socket and wait for packets
	int nbPacketLost = 0;
	int nbReceivedPackets = 0;
	
	boolean successfulDecoding = false;
	
	int utilsPacket = totalSymbols;
	int utilsPacketCounter = 0;
	long before = System.currentTimeMillis();
	int packetID;
	
	// while (!successfulDecoding) {
	MulticastSocket serverSocket = null;
	try {
	    serverSocket = new MulticastSocket(srcPort);
	    serverSocket.joinGroup(sendIP);
	} catch (SocketException e1) {
	    e1.printStackTrace();
	    System.err.println("Error opening socket.");
	    System.exit(1);
	}
	try {
	    System.out.println("\nWaiting for packets...");
	    // wait for all the symbols that we need...
	    int flag = 0;
	    totalSymbols = totalSymbols * 2;
	    
	    int b = 1;
	    for (int recv = 0; recv < totalSymbols; recv++) {
		b++;
		
		utilsPacketCounter++;
		/**
		 * reception of the response and add noise
		 */
		// test of a delay in a ack
		if ( testParameter == 5 && (utilsPacket - 2) >= utilsPacketCounter ) {
		    if ( (utilsPacket - 2) == utilsPacketCounter ) {
			testParameter++;
		    }
		    continue;
		}
		
		// allocate some memory for receiving the packets
		byte[] receiveData = new byte[500];
		byte[] packetData;
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		// set the timeout for the receiver
		int timerReception = 2 * Utils.RTT;
		serverSocket.setSoTimeout(timerReception);
		try {
		    nbReceivedPackets++;
		    serverSocket.receive(receivePacket);
		    
		} catch (java.net.SocketTimeoutException e) {
		    // call the sending thread
		    sendingThread.setAckType((utilsPacket - utilsPacketCounter));
		    
		    sendingThread.sendMsg();
		    System.out.println("Socket bloquĂŠe");
		    recv--;
		    utilsPacketCounter--;
		    continue;
		}
		
		packetData = new byte[receivePacket.getData().length - Utils.IntegerSize * 2];
		
		byte[] flag_push = new byte[Utils.IntegerSize];
		byte[] data_id = new byte[Utils.IntegerSize];
		System.arraycopy(receivePacket.getData(), 0, flag_push, 0, Utils.IntegerSize);
		System.arraycopy(receivePacket.getData(), Utils.IntegerSize, data_id, 0, Utils.IntegerSize);
		
		System.arraycopy(receivePacket.getData(), Utils.IntegerSize * 2, packetData, 0, packetData.length);
		
		packetID = Utils.byteArrayToInt(data_id);
		flag = Utils.byteArrayToInt(flag_push);
		if ( packetIsLost(canalloss) ) {
		    nbPacketLost++;
		    recv--;
		    utilsPacketCounter--;
		    continue;
		} else
		    treatmentThread.addPacket(packetData);
		
		// System.out.println("  flag push     "+flag+"   id packet   "+id_data_packet);
		// Add packet to the rtt queue
		
		// retard of FLAG
		if ( ((utilsPacket - 4) < utilsPacketCounter) && testParameter == 6 ) {
		    testParameter++;
		    flag = Utils.FLAG_PUSH;
		    System.out.println("                                       " + utilsPacket);
		    System.out.println("                                       " + utilsPacketCounter);
		}
		
		// lost of a PUSH flag
		if ( flag == Utils.FLAG_PUSH && testParameter == 2 ) {
		    System.out.println("          je decode");
		    testParameter = 10;
		    continue;
		}
		
		// got a flag and enough packets decode
		if ( flag == Utils.FLAG_PUSH ) {
		    // System.out.println(nb_of_utils_packet + " ce que j'ai besoin " + compteur_utils_packet + " ce que j'ai vraiment " + b);
		    if ( utilsPacket <= utilsPacketCounter ) {
			System.out.println("          je decode");
			break;
		    }
		    
		    if ( historyInLine ) {
			int Plost = Math.round((float) (1000 * (treatmentThread.getESI() - treatmentThread.getNbRecv())) / treatmentThread.getESI());
			System.out.println("                     lol     " + Plost);
			sendingThread.setAckType(Plost);
			sendingThread.sendMsg();
			recv--;
			continue;
			
		    }
		    System.out.println("          je peux pas decoder");
		    sendingThread.setAckType((utilsPacket - utilsPacketCounter));
		    sendingThread.sendMsg();
		    recv--;
		    continue;
		    
		}
		if ( utilsPacket < utilsPacketCounter ) {
		    continue;
		}
	    }
	    
	} catch (IOException e) {
	    e.printStackTrace();
	    System.err.println("Socket error.");
	}
	serverSocket.close();
	/**
	 * organize into source blocks
	 */
	
	int maxESI = -1;
	
	while (treatmentThread.getNbRecv() < utilsPacket) {
	    Thread.sleep(10);
	}
	treatmentThread.setEndBoucle();
	
	Utils.printInFile(String.valueOf(System.currentTimeMillis()), srcPort, 0);
	final Set<EncodingSymbol> received_packets = treatmentThread.getEncodingSymbol();
	
	sendingThread.setAckType(Utils.ACK);
	sendingThread.sendMsg();
	System.out.println("           envoie Utils.ACK 1 bon");
	
	// order received packets
	// EncodingSymbol[][] aux_2 = null;
	EncodingSymbol[][] aux = null;
	try {
	    for (EncodingSymbol es : received_packets) {
		
		if ( es.getESI() > maxESI )
		    maxESI = es.getESI();
	    }
	    
	    Iterator<EncodingSymbol> it = received_packets.iterator();
	    aux = new EncodingSymbol[noBlocks][maxESI + 1];
	    
	    while (it.hasNext()) {
		EncodingSymbol pack = it.next();
		aux[pack.getSBN()][pack.getESI()] = pack;
	    }
	} catch (ConcurrentModificationException e) {
	    e.printStackTrace();
	}
	
	/**
	 * decoding
	 */
	
	// where the decoded data will be stored
	byte[] decoded_data = null;
	SourceBlock[] blocks = new SourceBlock[noBlocks];
	boolean decodage = true;
	if ( decodage ) {
	    successfulDecoding = true;
	    
	    // for each block
	    for (int sblock = 0; sblock < noBlocks; sblock++) {
		try {
		    // get the time before decoding
		    
		    long beforeDecoding = System.currentTimeMillis();
		    
		    // decode
		    if ( sblock < ZL )
			blocks[sblock] = Encoder.decode(new EncodingPacket(0, aux[sblock], KL, Encoder.MAX_PAYLOAD_SIZE));
		    else
			blocks[sblock] = Encoder.decode(new EncodingPacket(0, aux[sblock], KS, Encoder.MAX_PAYLOAD_SIZE));
		    
		    // get time after decoding
		    long after = System.currentTimeMillis();
		    
		    long diff = (long) (after - beforeDecoding);
		    // print_in_file(" \n apres dĂŠcodage \n",src_port);
		    Utils.printInFile(String.valueOf(System.currentTimeMillis()), srcPort, 0);
		    System.out.println("\nSuccessfuint lly decoded block: " + sblock + " (in " + diff + " milliseconds)");
		    
		} catch (SingularMatrixException e) {
		    // Decoding failed
		    System.out.println("\nDecoding failed!");
		    successfulDecoding = false;
		    sendingThread.setAckType(Utils.NACK);
		    sendingThread.sendMsg();
		    continue;
		} catch (RuntimeException e) {
		    // Decoding failed not enough packets
		    int nb_packets_lost = Integer.parseInt(e.getMessage());
		    // compteur_utils_packet = 0;
		    sendingThread.setAckType(nb_packets_lost);
		    utilsPacket = nb_packets_lost;
		    successfulDecoding = false;
		    
		    // Retard of an Utils.ACK 4 sec
		    if ( testParameter == 3 ) {
			RecepteurThreadMulti recepThread = new RecepteurThreadMulti(srcPort, sendIP, 1, false);
			recepThread.start();
			
			for (int u = 1; u < 4000; u++) {
			    if ( recepThread.getNumberOfPackets() == Utils.FLAG_PUSH ) {
				break;
			    }
			    
			    Thread.sleep(1);
			}
			sendingThread.sendMsg();
			recepThread.join();
			testParameter = 10;
			continue;
		    } else
			sendingThread.sendMsg();
		    continue;
		}
		if ( successfulDecoding )
		    continue;
		
	    }
	}
	
	// if the decoding was successful for all blocks, we can unpartition
	// the
	// data
	if ( successfulDecoding ) {
	    if ( decodage ) {
		decoded_data = encoder.unPartition(blocks);
		
		// //////////////////////
		// and finally, write the decoded data to the file
		File file = new File(fileName);
		
		try {
		    if ( file.exists() )
			file.createNewFile();
		    
		    /*
		     * while (true) { // Lost of an Utils.ACK if ( para_test == 1 ) { if ( look_for_the_answer(sendIP, src_port) == Utils.FLAG_PUSH ) {
		     * sending_thread.set_ACK_NACK(Utils.ACK); sending_thread.send_thing(); System.out.println("           envoie Utils.ACK 1 mauvais"); break;
		     * }
		     * 
		     * // Retard of an Utils.ACK 4 sec } else if ( para_test == 3 ) { Recepteur_thread_multi recep_thread = new Recepteur_thread_multi(src_port,
		     * sendIP, 1, false); recep_thread.start();
		     * 
		     * for (int u = 1; u < 4000; u++) { if ( recep_thread.get_number_of_packets() == Utils.FLAG_PUSH ) { break; }
		     * 
		     * Thread.sleep(1); } sending_thread.set_ACK_NACK(Utils.ACK); sending_thread.send_thing();
		     * System.out.println("           envoie Utils.ACK 1 mauvais 3"); recep_thread.join(); break; } // If no lost else {
		     * sending_thread.set_ACK_NACK(Utils.ACK); sending_thread.send_thing(); System.out.println("           envoie Utils.ACK 1 bon"); break;
		     * 
		     * } }
		     */
		    
		    // Terminate the thread
		    treatmentThread.setEndBoucle();
		    treatmentThread.join();
		    Files.write(file.toPath(), decoded_data);
		    
		} catch (IOException e) {
		    System.err.println("Could not open file.");
		    e.printStackTrace();
		    System.exit(1);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    
	    long time = System.currentTimeMillis() - before;
	    
	    // Print the result
	    System.out.println("nb reiceive packets: " + nbReceivedPackets);
	    System.out.println((float) (100 * nbPacketLost) / 834);
	    // int total_overhead = (nbReceivedPackets - totalSymbols) * Utils.SYMB_LENGTH;
	    
	    System.out.println("Delai total dâenvoi du message : " + time);
	    // break;
	    
	}
	// }
    }
}
