

	import java.net.DatagramPacket;
	import java.net.InetAddress;
	import java.net.MulticastSocket;
	import java.net.SocketException;
	import java.io.IOException;
	import java.net.UnknownHostException;
	import java.util.Random;

	public class Receiver_2 {
		final static int size_int = 4;
		final static int ACK = -2;
		final static int NACK = -1;
		// final static double redondance = 1.00;

		final static int FLAG_PUSH = 999999999;
		final static int FLAG_STOP = 111111111;

		// Simulate a lost of the canal
		public static boolean packet_is_loss(float percentageLoss) {
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(100);
			if (rand < (int) percentageLoss) {
				return true;
			}
			return false;

		}

		public static final int byte_array_to_int(byte[] byte_array) {
			int interger=0;
			for (int i = 0; i < size_int; i++) {
				interger = (interger << 8) + (byte_array[i] & 0xff);
			}
			return interger;
		}

		public static byte[] parameter_test(byte[] receivePacket, int parameter_test) {

			byte[] packetData = receivePacket;

			switch (parameter_test) {
			case 1:

				break;
			case 10:

				break;
			case 20:

				break;
			default:
			}

			return packetData;
		}

		public static final int look_for_the_answer(InetAddress groupeIP, int port)
				throws IOException {
			int packetData = 0;
			MulticastSocket socketReception;
			socketReception = new MulticastSocket(port);
			socketReception.joinGroup(groupeIP);
			byte[] receiveData = new byte[51024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			try {
				socketReception.receive(receivePacket);
				byte[] packetData_2 = receivePacket.getData();
				
				
				
				packetData=byte_array_to_int(packetData_2);
				/*
				for (int l = 0; l < size_int; l++) {
					packetData = (packetData << 8) + (packetData_2[l] & 0xff);
				}
				*/

			} catch (Exception exc) {
				System.out.println(exc);
			}

			socketReception.close();
			return packetData;
		}

		byte[] serialized_data = new byte[256];

		public static void main(String[] args) throws Exception {

			if (args.length != 7) {
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

			double redondance = Double.valueOf(args[6]);

			String fileName = args[0];

			// check fileSize
			long fileSize = 0;

			// check overhead
			int overhead = -1;

			try {
				overhead = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				System.err
						.println("Invalid overhead value. (must be a positive integer)");
				System.exit(-1);
			}

			if (overhead < 0) {
				System.err
						.println("Invalid overhead value. (must be a positive integer)");
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
			int src_port = -1;

			try {
				src_port = Integer.valueOf(args[3]);
			} catch (NumberFormatException e) {
				System.err.println("Invalid port. (must be above 1024)");
				System.exit(-1);
			}

			if (src_port < 1024 || src_port >= 65535) {
				System.err.println("Invalid port. (must be above 1024)");
				System.exit(-1);
			}

			float canalloss = -1;

			try {
				canalloss = Integer.valueOf(args[4]);
			} catch (NumberFormatException e) {
				System.err
						.println("Invalid network loss percentage. (must be a float in [0.0, 100.0[)");
				System.exit(-1);
			}

			if (canalloss < 0 || canalloss >= 100) {
				System.err
						.println("Invalid network loss percentage. (must be a float in [0.0, 100.0[)");
				System.exit(-1);
			}

			int para_test = Integer.valueOf(args[5]);

			EmetteurThreadMulti sending_thread = new EmetteurThreadMulti(
					sendIP, src_port + 1);
			//sending_thread.setParameterTest(para_test);

			// Recepteur_thread Reception_thread = new Recepteur_thread(src_port+2);
			/**
			 * End of the tests on the parameter
			 */
			System.out.println("Listening for file " + fileName + " (" + fileSize
					+ " bytes) at port " + src_port + "\n");
			/**
			 * preparation for the decoding
			 */
			int number_of_ack = 0;
			sending_thread.start();
			// create a new Encoder instance (usually one per file) and for that
			// get the file size
			// //////////////////////
			

			
			
			
			
			
			int re_send_for_a_lost = 0;
			int number_of_received_packets = 0;

			boolean successfulDecoding = false;
			
			//int nb_of_utils_packet = (int) Math.round((float) total_symbols* redondance);
			int compteur_utils_packet = 0;
			long before_2 = System.currentTimeMillis();
			int id_data_packet;
			while (!successfulDecoding) {
				MulticastSocket serverSocket = null;
				try {
					serverSocket = new MulticastSocket(src_port);
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
					int total_symbols=500;
					total_symbols = total_symbols * 2;
					int nb_of_utils_packet=total_symbols;
					for (int recv = 0; recv < total_symbols; recv++) {
						compteur_utils_packet++;
						/**
						 * reception of the response and add noise
						 */
						// allocate some memory for receiving the packets
						

						byte[] receiveData = new byte[51024];
						byte[] packetData;

						DatagramPacket receivePacket = new DatagramPacket(
								receiveData, receiveData.length);
						// set the time to wait before close the socket
						int reception_timer = 2000;
						serverSocket.setSoTimeout(reception_timer);
						try {
							number_of_received_packets++;
							serverSocket.receive(receivePacket);
							// System.out.println("                 Socket ");

						} catch (java.net.SocketTimeoutException e) {
							// call the sending thread
							sending_thread.setAckType(NACK);

							sending_thread.sendMsg();
							System.out.println("Socket bloquĂŠe");
							number_of_ack++;
							recv--;
							compteur_utils_packet--;
							continue;
						}
						// If the packets is lost

						if (packet_is_loss(canalloss)) {
							re_send_for_a_lost = re_send_for_a_lost + 1;
							recv--;
							compteur_utils_packet--;
							continue;
						}
						packetData = new byte[receivePacket.getData().length
								- size_int * 2];

						byte[] flag_push = new byte[size_int];
						byte[] data_id = new byte[size_int];
						System.arraycopy(receivePacket.getData(), 0, flag_push, 0,
								size_int);
						System.arraycopy(receivePacket.getData(), size_int,
								data_id, 0, size_int);

						System.arraycopy(receivePacket.getData(), size_int * 2,
								packetData, 0, packetData.length);
						
						id_data_packet=byte_array_to_int(data_id);
						flag=byte_array_to_int(flag_push);
						
						
						
						
						
						
						

						packetData = parameter_test(packetData, para_test);

						

						if (flag == FLAG_PUSH) {
							if (nb_of_utils_packet < compteur_utils_packet) {
								System.out.println("          je decode");
								break;
							}
							System.out.println("          je peux pas decoder");
							sending_thread.setAckType((nb_of_utils_packet
									- compteur_utils_packet));
							sending_thread.sendMsg();
							recv--;
							continue;

						}

						

					}

				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Socket error.");
				}
				serverSocket.close();

				
				while (true) {
					sending_thread.setAckType(ACK);
					sending_thread.sendMsg();
					System.out.println("           envoie ACK 1 bon");
					break;
				}
				
				/*
						number_of_ack++;
						System.out.println("nb of symboles : " + total_symbols);
						System.out.println("nb reiceive packets: "
								+ number_of_received_packets);
						System.out.println("nb re-send : " + re_send_for_a_lost);
						int total_overhead = (number_of_received_packets - total_symbols) * 192;
						float total_overhead_pourcent = 100 * ((float) total_overhead / (float) fileSize);
						System.out.println("Overhead : " + total_overhead
								+ " soit :" + total_overhead_pourcent + "%");
						System.out.println("Nombre total dâACK : " + number_of_ack);

						System.out.println("Delai total dâenvoi du message : "
								+ time);
						break;
						*/

					}
			
			}
	
		
	}
