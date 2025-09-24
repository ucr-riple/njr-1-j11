package edu.concordia.dpis.stationserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import edu.concordia.dpis.StationServer;
import edu.concordia.dpis.stationserver.domain.CriminalRecord;
import edu.concordia.dpis.stationserver.domain.CriminalStatus;
import edu.concordia.dpis.stationserver.domain.MissingRecord;
import edu.concordia.dpis.stationserver.domain.MissingStatus;
import edu.concordia.dpis.stationserver.domain.Record;
import edu.concordia.dpis.stationserver.domain.RecordType;
import edu.concordia.dpis.stationserver.domain.StationType;

// A BUSINESS CLASS IMPLEMENTING THE CLIENT-SERVER CONTRACT INTERFACE
public class FaultyStationServerImpl implements StationServer {

	// this station name
	private final StationType stationType;
	// a database holding all the records; a map with the key indicating the
	// first character of the last name and the value being list of all the
	// records in this station
	private HashMap<Character, List<Record>> records = new HashMap<Character, List<Record>>();
	// a simple properties file having the identity information about how to
	// contact the other station over UDP
	private Properties udpProperties = new Properties();
	private Properties tcpProperties = new Properties();
	// this station's udp server to return station's records count;
	private UDPServer udpServer;
	private TCPServer tcpServer;
	// apache logger
	private Logger log;
	// all the date's will be interpreted as mm/DD/yyyy
	private SimpleDateFormat dateFormat = new SimpleDateFormat("mm/DD/yyyy");

	public FaultyStationServerImpl startUDPServer(String port) {
		this.udpServer = new UDPServer(Integer.valueOf(port));
		this.log.debug(this.stationType.getStationCode()
				+ ":UDPServer started on port[" + port + "]");
		return this;
	}

	/*
	 * start's UDP server on the port number given in the properites file
	 */
	public FaultyStationServerImpl startUDPServer() {
		// read the host and port number to be started on for this station
		String port = extractHostAndPortNumber((String) udpProperties
				.get(this.stationType.getStationCode()))[1];
		return startUDPServer(port);
	}

	public FaultyStationServerImpl startTCPPServer(String port) {
		try {
			tcpProperties.setProperty(this.stationType.getStationCode(),
					InetAddress.getLocalHost().getHostName() + " " + port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.tcpServer = new TCPServer(Integer.valueOf(port));
		this.log.debug(this.stationType.getStationCode()
				+ ":TCPServer started on port[" + port + "]");
		return this;
	}

	public void addOtherUDPStationHostNPort(StationType stationType,
			String host, String port) {
		udpProperties.setProperty(stationType.getStationCode(), host + " "
				+ port);
	}

	public void addOtherTCPStationHostNPort(StationType stationType,
			String host, String port) {
		tcpProperties.setProperty(stationType.getStationCode(), host + " "
				+ port);
	}

	/*
	 * start's TCP server on the port number given in the properites file
	 */
	public FaultyStationServerImpl startTCPPServer() {
		// read the host and port number to be started on for this station
		String port = extractHostAndPortNumber((String) tcpProperties
				.get(this.stationType.getStationCode()))[1];
		return startTCPPServer(port);
	}

	/**
	 * constructor
	 * 
	 * @param stationType
	 */
	public FaultyStationServerImpl(final StationType stationType) {
		// initialize this station type
		this.stationType = stationType;
		// initialize the logger
		this.log = Logger.getLogger(this.stationType.getStationCode());
		log.info(stationType.getStationName() + " initialized!");
		try {
			// load the udp.properties and tcp.properties file, if not found
			// write a new file
			// with the default values
			udpProperties.load(new FileInputStream("./udp.properties"));

		} catch (FileNotFoundException e) {
			log.error("config properties not found");
			applyDefaultUDPSettings();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			tcpProperties.load(new FileInputStream("tcp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			applyDefaultTCPSettings();
		}
	}

	// default values, for each station's port and host address

	private void applyDefaultUDPSettings() {

		udpProperties.setProperty("SPVM", "localhost 9091");
		udpProperties.setProperty("SPB", "localhost 9092");
		udpProperties.setProperty("SPL", "localhost 9093");

		try {
			udpProperties.store(new FileOutputStream("./udp.properties"), null);
			log.debug(this.stationType.getStationCode()
					+ ":Default config properites initialized");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// default values, for each station's port and host address

	private void applyDefaultTCPSettings() {

		tcpProperties.setProperty("SPVM", "localhost 9094");
		tcpProperties.setProperty("SPB", "localhost 9095");
		tcpProperties.setProperty("SPL", "localhost 9096");

		try {
			tcpProperties.store(new FileOutputStream("./tcp.properties"), null);
			log.debug(this.stationType.getStationCode()
					+ ":Default config properites initialized");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// a method to tokenize the string for host and port number
	private String[] extractHostAndPortNumber(final String str) {
		String hostAndPort[] = new String[] { "", "" };
		StringTokenizer st = new StringTokenizer(str);
		hostAndPort[0] = st.nextToken(" ");
		hostAndPort[1] = st.nextToken();
		return hostAndPort;
	}

	public boolean createRecord(String badgeId, Record record) {
		// create an entry in the map, if no key with the given last name's
		// first character is found
		newRecordsList(record.getLastName());
		// insert in to the map
		insertNewRecord(record.getLastName(), record);
		return true;
	}

	private boolean isOfficerAuthorized(String badgeId) {
		return badgeId.startsWith(this.stationType.getStationCode())
				|| "ADMIN".equals(badgeId);
	}

	/**
	 * creates the criminal record with the given details
	 */
	public boolean createCRecord(String badgeId, String firstName,
			String lastName, String description, String status) {
		return false;
	}

	/**
	 * create a missing record
	 */
	public boolean createMRecord(String badgeId, String firstName,
			String lastName, String address, String lastDate,
			String lastLocation, String status) {
		return false;
	}

	/**
	 * edits the existing record with a new status
	 */
	public boolean editRecord(String badgeId, String lastName, String recordID,
			String newStatus) {
		return false;
	}

	// record id must be of length 7 characters
	private boolean isInValidRecordId(String recordID) {
		return recordID == null || "".equals(recordID)
				|| recordID.length() != 7;
	}

	// make sure the list is ready to insert new record
	private void newRecordsList(final String lastName) {
		final List<Record> records = this.records.get(Character
				.toUpperCase(lastName.charAt(0)));

		if (records == null) {
			synchronized (this.records) {
				this.records.put(Character.toUpperCase(lastName.charAt(0)),
						new ArrayList<Record>());
			}
		}
	}

	// check for last name null and emptiness
	private boolean hasLastName(String lastName) {
		return lastName != null && !lastName.isEmpty();
	}

	// new record id is of length 5 character and is in a running sequence
	private String newRecordId(RecordType type) {
		String recordId;

		synchronized (this.records) {
			// boundary case, when running out of id's
			if (records.size() > 99999) {
				throw new RuntimeException("Out of primary key for record");
			}
			recordId = type.getCode()
					+ String.format("%05d", (countRecords() + 1));
		}
		return recordId;
	}

	// make an entry into the records
	private void insertNewRecord(String lastName, Record record) {
		List<Record> recordsList = this.records.get(Character
				.toUpperCase(lastName.charAt(0)));
		synchronized (recordsList) {
			this.records.get(Character.toUpperCase(lastName.charAt(0))).add(
					record);
		}
	}

	// brings the count of all the stations record count
	public String getRecordCounts(String badgeId) {
		return "";
	}

	// move a record completely from one station to the other
	public boolean transferRecord(String badgeId, String recordId,
			String remoteStationServerName) {
		return false;
	}

	// return ths number of records in this stations
	private long countRecords() {
		long no_of_records = 0;
		for (Character ch : records.keySet()) {
			no_of_records += this.records.get(ch).size();
		}
		return no_of_records;
	}

	// a TCP Client program to invoke the TCP server on the other side
	class TCPClient {
		private boolean transfer(String record, StationType station) {
			Socket s = null;

			String[] hostAndPort = extractHostAndPortNumber((String) tcpProperties
					.get(station.getStationCode().toString()));
			int serverPort = Integer.parseInt(hostAndPort[1]);
			try {
				s = new Socket(hostAndPort[0], serverPort);
				// input stream
				DataInputStream in = new DataInputStream(s.getInputStream());
				// output stream
				DataOutputStream out = new DataOutputStream(s.getOutputStream());

				out.writeUTF(record);
				boolean result = Boolean.parseBoolean(in.readUTF());
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return false;
		}
	}

	// A UDPClient to invoke the UDPServer
	class UDPClient {

		private String ping(String remoteHost, int serverPort)
				throws FileNotFoundException, IOException {
			String response = "";
			Properties properties = new Properties();
			properties.load(new FileInputStream("udp.properties"));
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket();
				byte[] m = "GET".getBytes();
				InetAddress aHost = InetAddress.getByName(remoteHost);
				DatagramPacket request = new DatagramPacket(m, m.length, aHost,
						serverPort);
				log.debug(stationType.getStationCode()
						+ ":UDPClient reuested for number of records");
				aSocket.send(request);
				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);
				response = new String(Arrays.copyOfRange(reply.getData(), 0,
						reply.getLength()));
				log.debug(stationType.getStationCode() + ":UDPServer replied");

			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null) {
					aSocket.close();
				}
			}
			return response;
		}
	}

	// UDPServer
	class UDPServer {
		DatagramSocket aSocket = null;
		final int port;

		public UDPServer(int port) {
			this.port = port;
			start();
		}

		public void start() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						aSocket = new DatagramSocket(port);
						while (true) {
							byte[] buffer = new byte[1000];
							DatagramPacket request = new DatagramPacket(buffer,
									buffer.length);
							aSocket.receive(request);

							StringBuffer sb = new StringBuffer();
							sb.append(stationType.getStationCode());
							// sb.append(" ");
							sb.append(countRecords());
							byte[] payload = sb.toString().getBytes("US-ASCII");
							DatagramPacket reply = new DatagramPacket(payload,
									payload.length, request.getAddress(),
									request.getPort());

							aSocket.send(reply);
						}
					} catch (SocketException e) {
						System.out.println("Socket: " + e.getMessage());
					} catch (IOException e) {
						System.out.println("IO: " + e.getMessage());
					} finally {
						if (aSocket != null) {
							aSocket.close();
						}
					}
				}
			}).start();
		}

		public void close() {
			if (aSocket != null) {
				aSocket.close();
			}
		}
	}

	// TCP SERVER
	class TCPServer {

		private int port;

		public TCPServer(int port) {
			this.port = port;
			start();
		}

		public void start() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						ServerSocket listenSocket = new ServerSocket(port);
						while (true) {
							Socket clientSocket = listenSocket.accept();
							Connection c = new Connection(clientSocket);
						}
					} catch (IOException e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
			}).start();
		}

		class Connection extends Thread {
			DataInputStream in;
			DataOutputStream out;
			Socket clientSocket;

			public Connection(Socket clientSocket) {
				this.clientSocket = clientSocket;
				try {
					in = new DataInputStream(clientSocket.getInputStream());
					out = new DataOutputStream(clientSocket.getOutputStream());
					this.start();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}

			@Override
			public void run() {
				try {
					String data = in.readUTF();
					log.debug("TCP Server recieved request for record transfer..."
							+ data);
					System.out
							.println("TCP Server recieved request for record transfer..."
									+ data);
					StringTokenizer st = new StringTokenizer(data, ",");
					String recordId = st.nextToken();
					RecordType recordType = RecordType.valueOf(st.nextToken());
					if (RecordType.CRIMINAL.equals(recordType)) {
						String firstName = st.nextToken();
						String lastName = st.nextToken();
						String description = st.nextToken();
						String status = st.nextToken();
						boolean res = createCRecord("ADMIN", firstName,
								lastName, description, status);
						out.writeUTF(Boolean.toString(res));
					} else if (RecordType.MISSING.equals(recordType)) {

						String firstName = st.nextToken();
						String lastName = st.nextToken();
						String address = st.nextToken();
						String lastDate = st.nextToken();
						String lastLocation = st.nextToken();
						String status = st.nextToken();
						boolean res = createMRecord("ADMIN", firstName,
								lastName, address, lastDate, lastLocation,
								status);
						out.writeUTF(Boolean.toString(res));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
