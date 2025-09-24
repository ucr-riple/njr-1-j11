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
public class StationServerImpl implements StationServer {

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

	public StationServerImpl startUDPServer(String port) {
		this.udpServer = new UDPServer(Integer.valueOf(port));
		this.log.debug(this.stationType.getStationCode()
				+ ":UDPServer started on port[" + port + "]");
		return this;
	}

	/*
	 * start's UDP server on the port number given in the properites file
	 */
	public StationServerImpl startUDPServer() {
		// read the host and port number to be started on for this station
		String port = extractHostAndPortNumber((String) udpProperties
				.get(this.stationType.getStationCode()))[1];
		return startUDPServer(port);
	}

	public StationServerImpl startTCPPServer(String port) {
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
	public StationServerImpl startTCPPServer() {
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
	public StationServerImpl(final StationType stationType) {
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
		// check if the user is allowed to create this operation
		if (!isOfficerAuthorized(badgeId)) {
			log.error(badgeId
					+ " is not a authorized user to perform createCRecord");
			return false;
		}
		log.debug(badgeId + " initiated createCRecord");
		// if status is not provided
		if (CriminalStatus.valueOf(status) == null) {
			// Invalid status
			log.error("Invalid status");
			return false;
		}
		// if last name is given
		if (hasLastName(lastName)) {
			// create an entry in the map, if no key with the given last name's
			// first character is found
			newRecordsList(lastName);
			// new criminal record
			final Record record = new CriminalRecord(
					newRecordId(RecordType.CRIMINAL), firstName, lastName,
					description, CriminalStatus.valueOf(status));

			// insert in to the map
			insertNewRecord(lastName, record);
		} else {
			log.error("LastName is required");
			return false;
		}
		log.debug(this.stationType.getStationCode()
				+ ":Successfully created Criminal Record");
		System.out.println(this.stationType.getStationCode()
				+ ":Successfully created Criminal Record");
		return true;
	}

	/**
	 * create a missing record
	 */
	public boolean createMRecord(String badgeId, String firstName,
			String lastName, String address, String lastDate,
			String lastLocation, String status) {
		// check if the user is allowed to create this operation
		if (!isOfficerAuthorized(badgeId)) {
			log.error(badgeId
					+ " is not a authorized user to perform createMRecord");
			return false;
		}

		// if no status is given
		if (MissingStatus.valueOf(status) == null) {
			log.error("Invalid status");
			return false;
		}
		// if last name is given
		if (hasLastName(lastName)) {
			// create an entry in the map, if no key with the given last name's
			// first character is found
			newRecordsList(lastName);

			try {
				// new missing record
				Record newRecord = new MissingRecord(
						newRecordId(RecordType.MISSING), firstName, lastName,
						address, dateFormat.parse(lastDate), lastLocation,
						MissingStatus.valueOf(status));
				// insert new record
				insertNewRecord(lastName, newRecord);
			} catch (ParseException e) {
				e.printStackTrace();
				log.error("Error in creating missing record:" + e.getMessage());
				return false;
			}
		} else {
			log.error("Last name is required");
			return false;
		}
		log.debug(this.stationType.getStationCode()
				+ ":Successfully created Missing Record");
		System.out.println(this.stationType.getStationCode()
				+ ":Successfully created Missing Record");
		return true;

	}

	/**
	 * edits the existing record with a new status
	 */
	public boolean editRecord(String badgeId, String lastName, String recordID,
			String newStatus) {
		// check if the user is allowed to create this operation
		if (!isOfficerAuthorized(badgeId)) {
			log.error(badgeId
					+ " is not a authorized user to perform editRecord");
			System.out.println(badgeId
					+ " is not a authorized user to perform editRecord");
			return false;
		}

		// if no status is given
		try {
			if (newStatus == null && "".equals(newStatus)) {
				log.error("Invalid status:" + newStatus);
				return false;
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return false;
		}

		// if last name is given
		if (hasLastName(lastName)) {
			// if valid record ID
			if (isInValidRecordId(recordID)) {
				log.error("Invalid recordID:" + recordID);
				return false;
			}
			log.debug("Editing record " + recordID);
			final List<Record> list = this.records.get(Character
					.toUpperCase(lastName.charAt(0)));
			if (list == null) {
				// if no entry exists for this last name
				log.error("No entry exists for this recordID:" + recordID
						+ " with the given lastName:" + lastName);
				System.out.println("No entry exists for this recordID:"
						+ recordID + " with the given lastName:" + lastName);
				return false;
			}
			// get the lock over entire list; because if not done there's a
			// chance that transfer record might delete a record from this list
			// and we get the concurrent modification exception
			synchronized (list) {
				for (Record record : list) {
					if (record.getRecordID().equals(recordID)) {
						// get the lock over the record that need's to be edited
						synchronized (record) {
							// change its status
							record.setStatus(newStatus);
							log.debug(this.stationType.getStationCode()
									+ ":Successfully edited Record:" + recordID);
							System.out
									.println(this.stationType.getStationCode()
											+ ":Successfully edited Record:"
											+ recordID);
						}
						return true;
					}
				}
			}
		} else {
			log.error("LastName is required");
		}
		log.debug("Edit failed");
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
		log.debug(this.stationType.getStationCode()
				+ ":Running getRecordCounts");
		final StringBuffer sb = new StringBuffer();
		for (final Object key : udpProperties.keySet()) {
			if (!key.equals(this.stationType.getStationCode())) {
				String[] hostAndPort = extractHostAndPortNumber((String) udpProperties
						.get(key));
				UDPClient udpClient = new UDPClient();

				try {
					log.debug(this.stationType.getStationCode() + ":Pinging "
							+ hostAndPort[0] + "on port "
							+ Integer.valueOf(hostAndPort[1]));
					// ping all the servers for the record count
					String resp = udpClient.ping(hostAndPort[0],
							Integer.valueOf(hostAndPort[1]));
					sb.append(resp);
					sb.append(" ");
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else {
				log.debug(this.stationType.getStationCode()
						+ ":counting this station records");
				sb.append(stationType.getStationCode() + " " + countRecords()
						+ " ");
			}
		}

		return sb.toString();
	}

	// move a record completely from one station to the other
	public boolean transferRecord(String badgeId, String recordId,
			String remoteStationServerName) {
		log.debug("Transferring Record started");
		if (this.stationType
				.equals(StationTypeResolver.resolveStation(badgeId))) {
			// check for the record in this stations database
			Iterator<Character> keys = this.records.keySet().iterator();
			while (keys.hasNext()) {
				List<Record> recList = this.records.get(keys.next());
				Iterator<Record> recListItr = recList.iterator();
				synchronized (recListItr) {
					boolean result = false;
					Record ItemtoRemove = null;

					while (recListItr.hasNext()) {
						Record record = recListItr.next();
						// matching record
						if (record.getRecordID().equals(recordId)) {
							log.debug("Found record to be transferred");
							// invoke the transfer record over tcp
							// flatten the object record to string with comma
							// separated values
							TCPClient client = new TCPClient();
							StringBuffer buf = new StringBuffer();
							buf.append(record.getRecordID());
							buf.append(",");
							buf.append(record.getRecordType());
							if (record.getRecordType().equals(
									RecordType.CRIMINAL)) {
								CriminalRecord cRecord = (CriminalRecord) record;
								buf.append(",");
								buf.append(cRecord.getFirstName());
								buf.append(",");
								buf.append(cRecord.getLastName());
								buf.append(",");
								buf.append(cRecord.getDescription());
								buf.append(",");
								buf.append(cRecord.getStatus());

							} else {
								MissingRecord mRecord = (MissingRecord) record;
								buf.append(",");
								buf.append(mRecord.getFirstName());
								buf.append(",");
								buf.append(mRecord.getLastName());
								buf.append(",");
								buf.append(mRecord.getLastKnownAddress());
								buf.append(",");
								buf.append(mRecord.getLastSeenDate());
								buf.append(",");
								buf.append(mRecord.getLastSeenPlace());
								buf.append(",");
								buf.append(mRecord.getStatus());
							}
							log.debug("Requested record transfer..."
									+ buf.toString());
							// send over tcp
							result = client.transfer(buf.toString(),
									StationType
											.valueOf(remoteStationServerName));
							ItemtoRemove = record;
							break;
						}
					}// while
					if (result) {
						// if successful remove the record from this list
						boolean isRemoved;
						synchronized (ItemtoRemove) {
							isRemoved = recList.remove(ItemtoRemove);
						}
						log.debug("Transferred successfully");
						return isRemoved;
					} else {
						log.error("Could not transfer record");
					}
				}
			}
		} else {
			log.error("Officer with badgeId " + badgeId
					+ " does not belong to this station");
		}
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
