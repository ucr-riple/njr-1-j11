import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Random;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import RQLibrary.Encoder;
import RQLibrary.EncodingPacket;
import RQLibrary.EncodingSymbol;
import RQLibrary.Partition;
import RQLibrary.SingularMatrixException;
import RQLibrary.SourceBlock;

public class Receiver_for_the_test extends Thread {
	private String fileName;
	private long fileSize = 0;
	private int overhead = -1;
	private InetAddress sendIP = null;
	private int srcPort = -1;
	private float canalloss = -1;

	// Simulate a lost of the canal
	public static boolean packet_is_loss(float percentageLoss) {
		Random randomGenerator = new Random();
		int rand = randomGenerator.nextInt(100);
		if (rand < (int) percentageLoss) {
			return true;
		}
		return false;

	}

	byte[] serialized_data = new byte[256];

	public Receiver_for_the_test(String fileName_in, long fileSize_in,
			int overhead_in, InetAddress sendIP_in, int srcPort_in,
			float canalloss_in) throws IOException {
		fileName = fileName_in;
		fileSize = fileSize_in;
		overhead = overhead_in;
		sendIP = sendIP_in;
		srcPort = srcPort_in;
		canalloss = canalloss_in;

	}

	public void run() {
		/*
		 * if (args.length != 5) { StringBuilder s = new StringBuilder();
		 * s.append("Usage: \n");
		 * s.append("    java -jar Receiver.jar pathToFile fileSize overhead port\n"
		 * );
		 * s.append("\n        - pathToFile  : path to store the received file."
		 * ); s.append(
		 * "\n        - overhead    : the necessary symbol overhead needed for decoding (usually between 0 and 2)."
		 * );
		 * s.append("\n        - destIP      : the IP address for the multicast."
		 * ); s.append(
		 * "\n        - portNumber  : the port the receiver will be listening on."
		 * ); s.append("\n        - canalloss   : the loss due to the canal.");
		 * System.out.println(s.toString()); System.exit(1); }
		 */

		EmetteurThreadMulti sending_thread = null;
		try {
			sending_thread = new EmetteurThreadMulti(sendIP, srcPort + 1);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		/**
		 * End of the tests on the parameter
		 */
		System.out.println("Listening for file " + fileName + " (" + fileSize
				+ " bytes) at port " + srcPort + "\n");
		/**
		 * preparation for the decoding
		 */
		int number_of_ack = 0;
		sending_thread.start();
		// create a new Encoder instance (usually one per file) and for that
		// get the file size
		// //////////////////////
		MulticastSocket serverSocket_for_the_size = null;
		try {
			serverSocket_for_the_size = new MulticastSocket(srcPort);
			serverSocket_for_the_size.joinGroup(sendIP);
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.err.println("Error opening socket.");
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] receiveData_for_the_size = new byte[51024];
		// create a UDP packet
		DatagramPacket receivePacket_for_the_size = new DatagramPacket(
				receiveData_for_the_size, receiveData_for_the_size.length);

		try {
			serverSocket_for_the_size.receive(receivePacket_for_the_size);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// get the packet's payload
		byte[] packetData_for_the_size = receivePacket_for_the_size.getData();
		for (int i = 0; i < 8; i++) {
			fileSize = (fileSize << 8) + (packetData_for_the_size[i] & 0xff);
		}
		byte[] first_packet_data = new byte[receivePacket_for_the_size
				.getData().length - 8];
		System.arraycopy(receivePacket_for_the_size.getData(), 8,
				first_packet_data, 0, first_packet_data.length);

		// ////////////////////////

		serverSocket_for_the_size.close();
		Encoder encoder = new Encoder((int) fileSize);

		// total number of source symbols (for all source blocks)overhead
		int Kt = encoder.getKt();
		System.out.println("# source symbols: " + Kt);

		// number of source blocks
		int no_blocks = encoder.Z;
		System.out.println("# source blocks: " + no_blocks);

		// the minimum amount of symbols we'll be waiting for before trying
		// to
		// decode
		int total_symbols = Kt + no_blocks * overhead;

		Partition KZ = new Partition(Kt, no_blocks);
		int KL = KZ.get(1);
		int KS = KZ.get(2);
		int ZL = KZ.get(3);
		int lol = 0;
		/**
		 * built of the socket
		 * 
		 */
		// sending_thread.start();
		// create socket and wait for packets
		int re_send_for_a_lost = 0;
		int number_of_received_packets = 0;

		boolean successfulDecoding = false;
		Set<EncodingSymbol> received_packets = new HashSet<EncodingSymbol>();

		// ////////////////////////////////////////////////////////////////

		// ////////////////////////////////////////////////////////////////

		while (!successfulDecoding) {
			MulticastSocket serverSocket = null;
			try {
				serverSocket = new MulticastSocket(srcPort);
				serverSocket.joinGroup(sendIP);
			} catch (SocketException e1) {
				e1.printStackTrace();
				System.err.println("Error opening socket.");
				System.exit(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("\nWaiting for packets...");
				// wait for all the symbols that we need...
				long flag = 0;

				for (int recv = 0; recv < total_symbols; recv++) {
					/**
					 * reception of the response and add noise
					 */
					// allocate some memory for receiving the packets
					byte[] receiveData = new byte[51024];
					byte[] packetData;

					DatagramPacket receivePacket = new DatagramPacket(
							receiveData, receiveData.length);
					// set the time to wait before close the socket
					int reception_timer = 3000;
					serverSocket.setSoTimeout(reception_timer);
					try {
						number_of_received_packets = number_of_received_packets + 1;
						serverSocket.receive(receivePacket);
						lol = lol + receivePacket.getLength();

					} catch (java.net.SocketTimeoutException e) {
						// call the sending thread
						sending_thread.setAckType(49);
						try {
							sending_thread.sendMsg();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						number_of_ack++;
						recv--;
						continue;
					}

					// get the packet's payload

					if (packet_is_loss(canalloss)) {
						re_send_for_a_lost = re_send_for_a_lost + 1;
						recv--;
						continue;
					}

					packetData = new byte[receivePacket.getData().length - 8];
					byte[] flag_push = new byte[8];
					System.arraycopy(receivePacket.getData(), 0, flag_push, 0,
							8);

					System.arraycopy(receivePacket.getData(), 8, packetData, 0,
							packetData.length);
					flag = 0;
					for (int l = 0; l < 8; l++) {
						flag = (flag << 8) + (flag_push[l] & 0xff);
					}

					ByteArrayInputStream bis = new ByteArrayInputStream(
							packetData);
					ObjectInput in = null;
					try {
						in = new ObjectInputStream(bis);
						received_packets.add((EncodingSymbol) in.readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					if (flag == 999999999) {
						break;
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

			// order received packets
			int maxESI = -1;

			for (EncodingSymbol es : received_packets)
				if (es.getESI() > maxESI)
					maxESI = es.getESI();

			Iterator<EncodingSymbol> it = received_packets.iterator();
			EncodingSymbol[][] aux = new EncodingSymbol[no_blocks][maxESI + 1];
			while (it.hasNext()) {
				EncodingSymbol pack = it.next();
				aux[pack.getSBN()][pack.getESI()] = pack;
			}

			/**
			 * decoding
			 */

			// where the decoded data will be stored
			byte[] decoded_data = null;

			// where the source blocks will be stored
			SourceBlock[] blocks = new SourceBlock[no_blocks];

			successfulDecoding = true;

			// for each block
			for (int sblock = 0; sblock < no_blocks; sblock++) {
				System.out.println("\nDecoding block: " + sblock);
				try {
					// get the time before decoding

					long before = System.currentTimeMillis();

					// decode
					if (sblock < ZL)
						blocks[sblock] = Encoder.decode(new EncodingPacket(0,
								aux[sblock], KL, Encoder.MAX_PAYLOAD_SIZE));
					else
						blocks[sblock] = Encoder.decode(new EncodingPacket(0,
								aux[sblock], KS, Encoder.MAX_PAYLOAD_SIZE));

					// get time after decoding
					long after = System.currentTimeMillis();

					long diff = (long) (after - before);
					System.out.println("\nSuccessfully decoded block: "
							+ sblock + " (in " + diff + " milliseconds)");

				} catch (SingularMatrixException e) {
					System.out.println("\nDecoding failed!");
					successfulDecoding = false;
					sending_thread.setAckType(49);
					try {
						sending_thread.sendMsg();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					number_of_ack++;
					continue;
				} catch (RuntimeException e) {
					// nb of lost packets
					int nb_packets_lost = Integer.parseInt(e.getMessage());

					// /////pb transforme le int en tableaux de byte
					sending_thread.setAckType(nb_packets_lost);
					successfulDecoding = false;
					try {
						sending_thread.sendMsg();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					number_of_ack++;
					// Thread.sleep(1000);
					// total_symbols=nb_packets_lost;
					continue;
				}
				if (successfulDecoding)
					continue;
			}

			// if the decoding was successful for all blocks, we can unpartition
			// the
			// data
			if (successfulDecoding) {
				decoded_data = encoder.unPartition(blocks);
				// and finally, write the decoded data to the file
				File file = new File(fileName);

				try {
					if (file.exists())
						file.createNewFile();
					Thread.sleep(1000);
					Files.write(file.toPath(), decoded_data);
					sending_thread.setAckType((byte) 48);
					sending_thread.sendMsg();
					sending_thread.join();
					number_of_ack++;
					System.out.println("nb of symboles : " + total_symbols);
					System.out.println("nb reiceive packets: "
							+ number_of_received_packets);
					System.out.println("nb re-send : " + re_send_for_a_lost);
					int partial_overhead = (number_of_received_packets - total_symbols) * 192;
					int total_overhead = (partial_overhead / 192) * 42
							+ partial_overhead;
					float total_overhead_pourcent = 100 * ((float) total_overhead / (float) fileSize);
					System.out.println("Overhead partiel(sans entĂŞte) : "
							+ partial_overhead);
					System.out.println("Overhead (avec entĂŞte) : "
							+ total_overhead + " soit :"
							+ total_overhead_pourcent + "%");
					System.out.println("Nombre total dâACK : " + number_of_ack);
					int total_delai = number_of_ack
							+ number_of_received_packets;
					System.out.println("Delai total dâenvoi du message : "
							+ total_delai);
					break;

				} catch (IOException e) {
					System.err.println("Could not open file.");
					e.printStackTrace();
					System.exit(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
